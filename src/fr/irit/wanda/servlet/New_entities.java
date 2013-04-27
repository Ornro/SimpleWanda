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

/**
 * Servlet implementation class New_entities
 */
@WebServlet("/New_entities")
public class New_entities extends Servlet {
	enum Type_entities {
		METADATA, USER, RULE, ROLE, SITE, SESSION, WORKFLOW, TYPE, CORPUS, VIDEO, VIEW, ANNOTATION, MONTAGE
	}

	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public New_entities() {
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
		Type_entities ent = Type_entities.valueOf(getString(request,
				"hidden_field").toUpperCase());

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
		/*case VIDEO:
			message = handlerVideo(request);
			break;*/
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
		return "Votre annotation a bien été ajoutée";
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
		return "Votre montage a bien été ajouté";
		
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

		return "Votre metadonnee a bien été ajoutée";
	}*/

	/*private String handlerUser(HttpServletRequest request) {
		Role r = instanciator.role(getString(request, "role_user"));

		User u = new User(getString(request, "name_user"), getString(request,
				"prenom_user"), r, getString(request, "mail_user"));
		ccfg.remoteRequest.addUser(u);

		return "Votre user a bien été ajouté";
	}*/


	private String handlerSite(HttpServletRequest request) {
		try {
			ccfg.remoteRequest.createSite(new NamedEntity("site",getString(request,"name_site")));
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
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"id"), "corpus");
		try {
			ccfg.remoteRequest.createSession(new NamedEntity("session",getString(request,"name_session")),ne);
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
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"id"), "site");
		try {
			ccfg.remoteRequest.createSession(new NamedEntity("corpus",getString(request,"name_corpus")),ne);
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
		NamedEntity ne = new NamedEntityAO().getName(getInt(request,"id"), "session");
		try {
			ccfg.remoteRequest.createSession(new NamedEntity("view",getString(request,"name_view")),ne);
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
	
	/*private String handlerWorkflow(HttpServletRequest request) {
		Workflow w = new Workflow(getString(request, "name_workflow"),
				getString(request, "desc_worflow"));

		ccfg.remoteRequest.addWorkflow(w);

		return "Votre workflow a bien été ajouté";
	}*/

	/*private String handlerType(HttpServletRequest request) {
		Type w = new Type(getString(request, "name_type"), getString(request,
				"desc_typew"));

		ccfg.remoteRequest.addType(w);

		return "Votre type a bien été ajouté";
	}*/




}
