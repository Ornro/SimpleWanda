package fr.irit.wanda.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotAllowedToProceedException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class New_entities
 */
@WebServlet("/Create")
public class Create extends Servlet {
	enum ENTITIES {
		METADATA, USER, RULE, ROLE, SITE, SESSION, WORKFLOW, TYPE, CORPUS, VIDEO, VIEW, ANNOTATION, MONTAGE
	}

	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Create() {
		super("benjamin.babic@hotmail.fr");
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
		String message = null;
		ENTITIES ent = ENTITIES.valueOf(getString(request,
				"entity").toUpperCase());

		switch (ent) {
		/*case METADATA:
			message = handlerMetadata(request);
			break;
		case USER:
			message = handlerUser(request);
			break;
		case RULE:
			message = handlerRule(request);
			break;
		case ROLE:
			message = handlerRole(request);
			break;*/
		case SITE:
			message = handlerSite(request);
			break;
		case SESSION:
			message = handlerSession(request);
			break;
		/*case WORKFLOW:
			message = handlerWorkflow(request);
			break;
		case TYPE:
			message = handlerType(request);
			break;*/
		case CORPUS:
			message = handleCorpus(request);
			break;
		case VIDEO:
			message = handlerVideo(request, response);
			break;
		case VIEW:
			message = handleView(request);
			break;
		/*case ANNOTATION:
			message = handlerAnnotation(request);
			break;
		case MONTAGE:
			message = handlerMontage(request);
			break;*/

		default:
		}
		try {
			request.setAttribute("confirm", message);
			getServletContext().getRequestDispatcher("/Home.jsp")
					.forward(request, response);
		} catch (ServletException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			e.printStackTrace();
		}
	}

	/*private String handlerAnnotation(HttpServletRequest request) {
	    Workflow w = instanciator.workflow(getString(request, "workflow_annotation"));
	    Video e = instanciator.video(getString(request, "video_annotation"));
	    View v = instanciator.view(getString(request, "view_annotation"));
		Annotation a = new Annotation(w, e, v, getString(request, "name_annotation"));


		ccfg.remoteRequest.addAnnotation(a);
		return "Votre annotation a bien �t� ajout�e";
	}*/

	/*private String handlerMontage(HttpServletRequest request) {

		String[] list_view = request.getParameterValues("views_montage");
		Collection<View> views = new ArrayList<View>();
		
		for (String s : list_view){
			views.add(instanciator.view(s));
		}
		
	    Session s = instanciator.session(getString(request, "session_montage"));
		
		Montage m = new Montage(views, s, getString(request, "link_montage"), getString(request, "name_montage"));
		ccfg.remoteRequest.addMontage(m);
		return "Votre montage a bien �t� ajout�";
		
	}*/

	/*private String handlerMetadata(HttpServletRequest request) {
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
		Metadata m = new Metadata(getString(request, "name_meta"), getBoolean(
				request, "obligation_meta"), getString(request, "hoover_meta"),
				getString(request, "description_meta"));
		m.setConcerns(ear);

		// Ajout de la metadata
		ccfg.remoteRequest.addMetadata(m);

		return "Votre metadonnee a bien �t� ajout�e";
	}*/

	/*private String handlerUser(HttpServletRequest request) {
		Role r = instanciator.role(getString(request, "role_user"));

		User u = new User(getString(request, "name_user"), getString(request,
				"prenom_user"), r, getString(request, "mail_user"));
		ccfg.remoteRequest.addUser(u);

		return "Votre user a bien �t� ajout�";
	}*/


	private String handlerVideo(HttpServletRequest request, HttpServletResponse response) {
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"fatherId"), getString(request,"fatherEntityName"));
		try {
			ccfg.remoteRequest.createVideo(new NamedEntity("video",getString(request,"name")),ne,getInt(request,"privacy"));
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();

			Part part = request.getPart("file");

			Matcher m = Pattern.compile("filename=\"(.*)\"", Pattern.CASE_INSENSITIVE).matcher(
			part.getHeader("content-disposition"));
			String filename;
			if (m.find()) {
			filename = m.group(1);
			} else {
			filename = "upload" + System.currentTimeMillis();
			}
			part.write("C:/path/" + filename);
			out.println("File '" + filename + "' uploaded.");
		} catch (NotAllowedToProceedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyRegistredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Votre site a bien �t� ajout�e";
	}
	
	private String handlerSite(HttpServletRequest request) {
		try {
			ccfg.remoteRequest.createSite(new NamedEntity("site",getString(request,"name")));
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

		return "Votre site a bien �t� ajout�e";
	}
	
	private String handlerSession(HttpServletRequest request) {
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"fatherId"), getString(request,"fatherEntityName"));
		try {
			ccfg.remoteRequest.createSession(new NamedEntity("session",getString(request,"name")),ne);
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

		return "Votre session a bien �t� ajout�e";
	}

	private String handleCorpus(HttpServletRequest request) {
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"fatherId"), getString(request,"fatherEntityName"));
		try {
			ccfg.remoteRequest.createCorpus(new NamedEntity("corpus",getString(request,"name")),ne);
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

		return "Votre corpus a bien �t� ajout�e";
	}
	
	private String handleView(HttpServletRequest request) {
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"fatherId"), getString(request,"fatherEntityName"));
		try {
			ccfg.remoteRequest.createView(new NamedEntity("view",getString(request,"name")),ne);
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

		return "Votre vue a bien �t� ajout�e";
	}
	
	/*private String handlerWorkflow(HttpServletRequest request) {
		Workflow w = new Workflow(getString(request, "name_workflow"),
				getString(request, "desc_worflow"));

		ccfg.remoteRequest.addWorkflow(w);

		return "Votre workflow a bien �t� ajout�";
	}*/

	/*private String handlerType(HttpServletRequest request) {
		Type w = new Type(getString(request, "name_type"), getString(request,
				"desc_typew"));

		ccfg.remoteRequest.addType(w);

		return "Votre type a bien �t� ajout�";
	}*/

}
