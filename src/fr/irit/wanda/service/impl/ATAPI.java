package fr.irit.wanda.service.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import fr.irit.wanda.dao.A3AO;
import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.LinkedEntityAO;
import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.entities.A3;
import fr.irit.wanda.entities.Job;
import fr.irit.wanda.entities.LinkedEntity;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class ATAPI extends HttpServlet {
	
	/**
	 * List of parameters to get in the context.
	 */
	private final static String[] servletParameters = { "ATAPI", "A3API" };

	private final static String atapiXmlParam = "atapi_xml_url";

	/**
	 * Local context.
	 */
	private static Map<String, String> parameters = new HashMap<String, String>();

	/**
	 * URL to error page. Retrieved from context.
	 */
	private static String atapiXmlUrl;
	
	/**
	 * Stack of all errors occuring while initialization.
	 */
	private ArrayList<String> initErrors = new ArrayList<String>();
	
	private static final int BUFSIZE = 4500;

	public void init() throws ServletException {
		ServletContext config = super.getServletContext();

		/*
		 * Fill local context from application context.
		 */
		for (String param_name : servletParameters) {
			String value = config.getInitParameter(param_name);
			if (value == null) {
				initErrors.add("Parameter \"" + param_name
						+ "\" haven't been initialized. ");
			} else {
				parameters.put(param_name, value);
			}
		}

		/*
		 * Get error page's URL from application context.
		 */
		atapiXmlUrl = config.getInitParameter(atapiXmlParam);
		if (atapiXmlUrl == null) {
			throw new ServletException("Parameter \"" + atapiXmlParam
					+ "\" haven't been initialized. ");
		}
	}
	
	private User getUser(HttpServletRequest request){
		X509Certificate[] certChain = (X509Certificate[]) request
				.getAttribute("javax.servlet.request.X509Certificate");

		if (certChain != null) {
			X509Certificate cert = certChain[certChain.length - 1];
		}
			UserAO uao = new UserAO();
			User user = null;
			try {
				user = uao.getUser("benjamin.babic@hotmail.fr");
			} catch (NotFoundInDatabaseException e) {
				e.printStackTrace();
			}
			return user;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String action = (String) request.getParameter("action");

		if (action != null) {

			if (action.equals("grant")) {
				doGrant(request, response);
				return;
			}
			if (action.equals("ungrant")) {
				doUnGrant(request, response);
				return;
			}
			if (action.equals("list")) {
				doList(request, response);
				return;
			}
			if (action.equals("download")) {
				doGetVideo(request, response);
				return;
			}
		}

		Collection<String> errors = new ArrayList<String>();
		errors.add(super.getServletName() + ": Unknown command. ");
		request.setAttribute("errors", errors);
		super.getServletContext().getRequestDispatcher(atapiXmlUrl)
				.forward(request, response);
	}
	
	private static Element getHierarchyXML() {
		NamedEntityAO accessObject = new NamedEntityAO();
		Collection<NamedEntity> sites = accessObject.getAll("site");
		Element e = new Element("server");
		for (NamedEntity s : sites) {
			e.addContent(getContainerHierarchy(s));
		}
		return e;
	}
	
	
	private static Element getContainerHierarchy(NamedEntity container) {
		
		Element elt = new Element(container.getEntityName());
		Attribute att = new Attribute("name", container.getName());
		elt.setAttribute(att);
		
		ContainerAO cao = new ContainerAO();
		for (NamedEntity ne : cao.getContent(container)) {
			elt.addContent(getContainerHierarchy(ne));
		}
		
		return elt;
	}
	
	//Méthode pout debug
		static void affiche(Document document)
		{
			try
			{
		      //On utilise ici un affichage classique avec getPrettyFormat()
		      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		      sortie.output(document, System.out);
			}
		  	catch (java.io.IOException e){}
		}

	private void doList(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String status = "ko";
		Collection<ActionStatus> results = new ArrayList<ActionStatus>();
		Collection<String> errors = new ArrayList<String>();

		try {
			if (errors.isEmpty()) {
				status = "ok";
			}
		} catch (Exception e) {
			errors.add(super.getServletName() + ": Exception \""
					+ e.getClass().getSimpleName() + "\" occured. ("
					+ e.getMessage() + ")");
		} finally {

			// give feedback
			ActionStatus actionStatus = new ActionStatus("list",status);
			actionStatus.setResult(getHierarchyXML());
			results.add(actionStatus);
			request.setAttribute("results", results);
			request.setAttribute("errors", errors);
			super.getServletContext().getRequestDispatcher(atapiXmlUrl)
					.forward(request, response);
		}
	}
	
	private void doGrant(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionStatus actionStatus = new ActionStatus("grant", "ko");
		int a3id = -1;
		int idvideo = -1;
		User user = null;
		String a3uri = "";
		String videoUri = "";
		String videoName = "";
		Collection<ActionStatus> results = new ArrayList<ActionStatus>();
		Collection<String> errors = new ArrayList<String>();

		// read parameters
		try {
			a3id = getIntParam("a3id", request);
		} catch (Exception e) {
			a3uri = request.getParameter("a3");
			if (a3uri == null)
				errors.add(super.getServletName()
						+ ": a3id or a3 parameter is missing. ("
						+ e.getMessage() + ")" + "\n"
						+ request.getContextPath());
		}
		try {
			idvideo = getIntParam("videoid", request);
		} catch (Exception e) {
			videoUri = request.getParameter("video");
			if (videoUri == null)
				errors.add(super.getServletName()
						+ ": videoid or video parameter is missing. ("
						+ e.getMessage() + ")");
		}
		try {
			user = getUser(request);
		} catch (Exception e) {
			errors.add(super.getServletName()
					+ ": Authentication problem occured. (" + e.getMessage()
					+ ")");
		}

		// call service
		if (errors.isEmpty()) {
			try {
				Job job;

				String[] tab = videoUri.split("/");
				videoName = tab[tab.length];
				LinkedEntityAO lao = new LinkedEntityAO();	
				idvideo = lao.getVideoID(videoName);

				if (a3id != -1)
					job = newJob(user, a3id, idvideo);
				else
					job = newJob(user, a3uri, idvideo);
				actionStatus.setStatus("ok");
				actionStatus.setResult("<a:job id=\"" + job.getId() + "\"/>");
				actionStatus.setTarget(job.getA3());
			} catch (Exception e) {
				errors.add(super.getServletName() + ": Exception \""
						+ e.getClass().getSimpleName() + "\" occured. ("
						+ e.getMessage() + ")");
			} 
		}

		// give feedback
		results.add(actionStatus);
		request.setAttribute("results", results);
		request.setAttribute("errors", errors);
		super.getServletContext().getRequestDispatcher(atapiXmlUrl)
				.forward(request, response);
	}
	
	private void doUnGrant(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionStatus actionStatus = new ActionStatus("ungrant", "ko");
		int jobid = -1;
		User user = null;
		Collection<String> errors = new ArrayList<String>();

		// read parameters
		try {
			jobid = getIntParam("jobid", request);
		} catch (Exception e) {
			errors.add(super.getServletName()
					+ ": jobid parameter is missing. (" + e.getMessage() + ")");
		}
		try {
			user = getUser(request);
		} catch (Exception e) {
			errors.add(super.getServletName()
					+ ": Authentication problem occured. (" + e.getMessage()
					+ ")");
		}

		// call service
		if (errors.isEmpty()) {
			try {
				deleteJob(user, jobid);
				actionStatus.setStatus("ok");
				actionStatus.setTarget(jobid);
			} catch (Exception e) {
				errors.add(super.getServletName() + ": Exception occured. ("
						+ e.getMessage() + ")");
			}
		}

		// give feedback
		Collection<ActionStatus> results = new ArrayList<ActionStatus>();
		results.add(actionStatus);
		request.setAttribute("results", results);
		request.setAttribute("errors", errors);
		super.getServletContext().getRequestDispatcher(atapiXmlUrl)
				.forward(request, response);

	}
	
	private void doGetVideo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		User user = null;
		String videoUri = "";
		String videoPath = "";
		String videoName = "";
		Collection<String> errors = new ArrayList<String>();

		// read parameters
		videoUri = request.getParameter("video");
		if (videoUri == null)
			errors.add(super.getServletName()
					+ ": video parameter is missing. (video parameter should be an URI)");
		try {
			user = getUser(request);
		} catch (Exception e) {
			errors.add(super.getServletName()
					+ ": Authentication problem occured. (" + e.getMessage()
					+ ")");
		}

		// call service
		if (errors.isEmpty()) {
			try {
				String[] tab = videoUri.split("/");
				videoName = tab[tab.length];
				LinkedEntityAO lao = new LinkedEntityAO();
				int idvideo = lao.getVideoID(videoName);
				videoPath = lao.getSingleLink(idvideo);
				
			} catch (Exception e) {
				errors.add(super.getServletName() + ": Exception \""
						+ e.getClass().getSimpleName() + "\" occured. ("
						+ e.getMessage() + ")");
			}
		}

		// give feedback
		if (errors.isEmpty()) {
			doDownload(request, response, videoPath, videoName);
		}else{
			request.setAttribute("errors", errors);
			super.getServletContext().getRequestDispatcher(atapiXmlUrl)
					.forward(request, response);
		}
	}
	
	  /**
     *  Sends a file to the ServletResponse output stream.  Typically
     *  you want the browser to receive a different name than the
     *  name the file has been saved in your local database, since
     *  your local names need to be unique.
     *
     *  @param req The request
     *  @param resp The response
     *  @param filename The name of the file you want to download.
     *  @param original_filename The name the browser should receive.
     */
    private void doDownload( HttpServletRequest req, HttpServletResponse resp,
                             String filename, String original_filename )
        throws IOException
    {
        File                file        = new File(filename);
        int                 length   = 0;
        ServletOutputStream op       = resp.getOutputStream();
        ServletContext      context  = getServletConfig().getServletContext();
        String              mimetype = context.getMimeType( filename );

        //
        //  Set the response and go!
        //
        //
        resp.setContentType( (mimetype != null) ? mimetype : "video/ogg" );
        resp.setContentLength( (int)file.length() );
        resp.setHeader( "Content-Disposition", "attachment; filename=\"" + original_filename + "\"" );

        //
        //  Stream to the requester.
        //
        byte[] bbuf = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(file));

        while ((in != null) && ((length = in.read(bbuf)) != -1))
        {
            op.write(bbuf,0,length);
        }

        in.close();
        op.flush();
        op.close();
    }

    private Integer getIntParam(String param, HttpServletRequest request)
			throws Exception {
		// Check parameter.
		String idString = request.getParameter(param);
		Integer id;
		if (idString == null) {
			throw new Exception("Parameter " + param + " is missing. ");
		}
		try {
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
			throw new Exception("Parameter " + param
					+ " should be a number. ");
		}
		return id;
	}
    
    
    public Job newJob(User puser, int aid, int vid) {
		User user = puser;
		A3AO a3ao = new A3AO();
		A3 a3 = a3ao.getA3(aid);

		// check right to access A3
		//TODO méthode hasUserAccessTOA3
		if (!hasUserAccessToA3(user, a3))
			throw new Exception(user.getName()
					+ " cannot access to A3 " + a3.getName());

		// check right to access video
		//TODO méthode pour tester si l'utilisateur a le droit d'accéder à la vidéo

		Job job = new Job();
		job.setId(-1);
		job.setVersion(0);
		job.setA3(aid);
		job.setUserID(user.getId());
		job.setVideo(vid);
		job.setCreation(new Date());
		//TODO méthode (dans JobAO) pour insérer le job dans sa table 
		//JobAO jao = new JobAO();
		//jao.saveJob(job);

		// System.out.println(this.getClass().getName()+".newJob(): A job linking A3 "+aid+" to video "+vid+" has been created. ");

		return job;
	}

	public Job newJob(User user, String a3_uri, int vid) throws Exception {
		//TODO méthode pour récupérer un A3 à partir de son uri (getA3fromURI)
		A3AO a3ao = new A3AO();
		A3 a3 = a3ao.getA3fromURI(a3_uri);
		if (a3 == null)
			throw new Exception("A3 uri " + a3_uri + " not found");
		// System.out.println(this.getClass().getName()+".newJob(): "+a3_uri+" is "+a3.getId());
		// // For debug purpose only.
		return newJob(user, a3.getId(), vid);
	}
	
	public static void main(String [] args) {
		org.jdom2.Document document = new Document(getHierarchyXML());
		affiche(document);
	}
	
	public void deleteJob(User u, int jid) throws Exception {
		//TODO méthode (dans JobAO) pour récupérer le job à partir de son id
		//JobAO jao = new JobAO();
		Job job = null;
		//job = jao.getJob(jid);
		int oid = job.getUserID();

		if (!(oid == u.getId())) //Ajouté également un test pour savoir si le user a les droits de supprimer le job 
								//(il a les droits si c'est le manager du site)
			throw new Exception(
					"Insufficient rights to delete this Job. ");
		
		//TODO méthode (dans JobAO) pour supprimer le job dans la table 
		//JobAO jao = new JobAO();
		//jao.deleteJob(job);
	}

}
