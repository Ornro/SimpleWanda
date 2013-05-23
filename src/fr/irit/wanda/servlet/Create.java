package fr.irit.wanda.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import fr.irit.wanda.dao.LinkedEntityAO;
import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.entities.Entity;
import fr.irit.wanda.entities.LinkedEntity.PRIVACY;
import fr.irit.wanda.entities.Metadata;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotAllowedToProceedException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;
import fr.irit.wanda.service.IRequest;
import fr.irit.wanda.service.impl.RequestImpl;

/**
 * Servlet implementation class New_entities
 */
@WebServlet("/Create")
public class Create extends Servlet {
	enum ENTITIES {
		METADATA, USER, LINKS, SITE, SESSION, CORPUS, VIDEO, VIEW, ANNOTATION, MONTAGE, ADDRIGHT
	}

	private static final long MaxMemorySize = 1073741824; // 1go
	private static final long serialVersionUID = 1L;
	IRequest remoteRequest;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Create() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		remoteRequest = new RequestImpl(request);
		String message = "";
		if (!ServletFileUpload.isMultipartContent(request)){
			ENTITIES ent = ENTITIES.valueOf(getString(request,"entity").toUpperCase());

			switch (ent) {
			case METADATA:
				message = handlerMetadata(request);
				break;
			case USER:
				message = handlerUser(request);
				break;
			case SITE:
				message = handlerSite(request);
				break;
			case SESSION:
				message = handlerSession(request);
				break;
			case CORPUS:
				message = handleCorpus(request);
				break;
			case VIEW:
				message = handleView(request);
				break;
			case LINKS:
				message = handleLink(request);
				break;
			case ADDRIGHT:
				message = handleAddRight(request);
				break;
			/*case MONTAGE:
				message = handlerMontage(request);
				break;*/

			default:
			}
		}else message = upload(request);
		response.sendRedirect("Home.jsp");
	}

	/*private String handlerMontage(HttpServletRequest request) {

		String[] list_view = request.getParameterValues("views_montage");
		Collection<View> views = new ArrayList<View>();
		
		for (String s : list_view){
			views.add(instanciator.view(s));
		}
		
	    Session s = instanciator.session(getString(request, "session_montage"));
		
		Montage m = new Montage(views, s, getString(request, "link_montage"), getString(request, "name_montage"));
		ccfg.remoteRequest.addMontage(m);
		return "Votre montage a bien été ajouté";
		
	}*/

	private String handleAddRight(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private String handlerMetadata(HttpServletRequest request) {
		ArrayList<Entity> ear = new ArrayList<Entity>();
		if (getString(request, "Video_meta") != null) {
			ear.add(new Entity("video"));
		}
		if (getString(request, "Annotation_meta") != null) {
			ear.add(new Entity("annotation"));
		}
		if (getString(request, "Corpus_meta") != null) {
			ear.add(new Entity("corpus"));
		}
		if (getString(request, "Vue_meta") != null) {
			ear.add(new Entity("view"));
		}
		if (getString(request, "Site_meta") != null) {
			ear.add(new Entity("site"));
		}
		if (getString(request, "Session_meta") != null) {
			ear.add(new Entity("session"));
		}
		if (getString(request, "Fichier video_meta") != null) {
			ear.add(new Entity("links"));
		}
		Metadata m = new Metadata(getString(request, "name_meta"), getBoolean(
				request, "obligation_meta"), getBoolean(request, "private_meta"),
				getString(request, "description_meta"));
		m.setConcerns(ear);

		// Ajout de la metadata
		remoteRequest.createMetadata(m);
		return "Votre metadonnee a bien été ajoutée";
	}

	private String handlerUser(HttpServletRequest request) {
		User.ROLE role = User.ROLE.valueOf(getString(request,"role").toUpperCase());
		try {
			remoteRequest.createUser(new User(getString(request,"name"), getString(request,"forename"), role , getString(request,"mail")));
		} catch (AlreadyRegistredException | NotAllowedToProceedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Votre user a bien été ajouté";
	}


	/*private String handlerVideo(HttpServletRequest request) {
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"fatherId"), getString(request,"fatherEntityName"));
		try {
			int id = remoteRequest.createVideo(new NamedEntity("video",getString(request,"name")),ne,PRIVACY.fromInt(getInt(request,"privacy")));
			saveMetadata(request, id);
		} catch (NotAllowedToProceedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyRegistredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Votre video a bien été ajoutée";
	}
	
	private String handlerAnnotation(HttpServletRequest request) {
		upload(request);
			
		return "Votre annotation a bien été ajoutée";
	}*/
	
	private String handlerSite(HttpServletRequest request) {
		try {
			int id = remoteRequest.createSite(new NamedEntity("site",getString(request,"name")));
			saveMetadata(request, id);
		} catch (NotAllowedToProceedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyRegistredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return "Votre site a bien été ajoutée";
	}
	
	private String handlerSession(HttpServletRequest request) {
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"fatherId"), getString(request,"fatherEntityName"));
		try {
			int id = remoteRequest.createSession(new NamedEntity("session",getString(request,"name")),ne);
			saveMetadata(request, id);
		} catch (AlreadyRegistredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotAllowedToProceedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Votre session a bien été ajoutée";
	}

	private String handleCorpus(HttpServletRequest request) {
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"fatherId"), getString(request,"fatherEntityName"));
		try {
			int id = remoteRequest.createCorpus(new NamedEntity("corpus",getString(request,"name")),ne);
			saveMetadata(request, id);
		} catch (AlreadyRegistredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotAllowedToProceedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Votre corpus a bien été ajoutée";
	}
	
	private String handleView(HttpServletRequest request) {
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"fatherId"), getString(request,"fatherEntityName"));
		try {
			int id = remoteRequest.createView(new NamedEntity("view",getString(request,"name")),ne);
			saveMetadata(request, id);
		} catch (AlreadyRegistredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotAllowedToProceedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Votre vue a bien été ajoutée";
	}
	
	private String handleLink(HttpServletRequest request){
		
		
		return "Votre lien a bien été ajouté";
	}
	
	public String upload(HttpServletRequest request){
    	String name = "";
    	String entity = "";
    	String father = "";
    	String res = "";
    	String fmt = "";
    	int fatherId = -1;
    	int privacy = -1;
    	int id = -1;
    	
    	FileItemFactory factory = new DiskFileItemFactory();

    	// Configure a repository (to ensure a secure temp location is used)
    	ServletContext servletContext = this.getServletConfig().getServletContext();
    	File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
    	((DiskFileItemFactory) factory).setRepository(repository);

    	// Create a new file upload handler
    	ServletFileUpload upload = new ServletFileUpload(factory);
    	
    	// Set max size for uploaded file
    	upload.setSizeMax(MaxMemorySize);

    	// Parse the request
    	try {
			List<FileItem> items = upload.parseRequest(request);
			
			Iterator<FileItem> iter = items.iterator();
	    	while (iter.hasNext()) {
	    	    FileItem item = iter.next();

	    	    if (item.isFormField()) {
	    	    	String currentField = item.getFieldName();
	    	    	if (currentField.equals("name")){
                		name = item.getString();
                	}else if (currentField.equals("entity")){
                		entity = item.getString(); 
                	}else if (currentField.equals("fatherId")){
                		fatherId = Integer.parseInt(item.getString());
                	}else if (currentField.equals("fatherEntityName")){
                		father = item.getString(); 
                	}else if (currentField.equals("privacy")){
                		privacy = Integer.parseInt(item.getString());
                	}else if (currentField.equals("format_video")){
                		res = item.getString();
                	}else if (currentField.equals("resolution_video")){
                    	fmt = item.getString();
                    	}
                }else { // should be last because iterator conserves sending order
                	NamedEntity ne = new NamedEntityAO().getName(fatherId, father);
                	
        	    	id = remoteRequest.createAnnotation(new NamedEntity(entity,name),ne,PRIVACY.fromInt(privacy));
        	    	save(item,entity,id);
                }
	    	}
	    	new LinkedEntityAO().tempFileMeta(res,fmt,id);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	return "Uploaded";
    }
	
	private void save (FileItem item, String entity, int id){
    	String givenName = item.getName();
    	String path = this.getServletConfig().getServletContext().getRealPath("/");
    	
    	String givenExt = givenName.substring(givenName.lastIndexOf("."));
    	String destination = "Wanda"+File.separator+entity+File.separator+id+givenExt;
    	
    	new LinkedEntityAO().setLink(id, destination);
    	File destinationFile = new File(path+destination);
    	
    	destinationFile.getParentFile().mkdirs();
    	try {
			if (!destinationFile.exists()) {
				destinationFile.createNewFile();
			}
	    	item.write(destinationFile);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
    	
	private String saveMetadata(HttpServletRequest request, int id){
		String entity = getString(request,"entity");
		Entity e = new Entity (id,entity);
		Collection<Metadata> cm;
		try {
			cm = remoteRequest.getMetadatas(e);
			for (Metadata m : cm){
				try {
					remoteRequest.createMetaContent(new MetadataContent (m, e, getString(request, m.getName())));
					return "Les métadonnées ont bien été renseignées";
				} catch (AlreadyRegistredException e1) {
					e1.printStackTrace();
				}
			}
		} catch (NotFoundInDatabaseException e2) {
			return "Il n'y a pas de métadonnées à renseigner";
		}
		return "Il n'y a pas de métadonnées à renseigner";
	}
}
