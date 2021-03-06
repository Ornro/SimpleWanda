package fr.irit.wanda.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


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
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotAllowedToProceedException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;
import fr.irit.wanda.service.IRequest;
import fr.irit.wanda.service.impl.RequestImpl;

/**
 * Servlet implementation class New_entities
 */
@WebServlet("/Edit")
public class Edit extends Servlet {
	enum ENTITIES {
		METADATA, USER, RULE, ROLE, SITE, SESSION, WORKFLOW, TYPE, CORPUS, VIDEO, VIEW, ANNOTATION, MONTAGE
	}

	private static final long MaxMemorySize = 1073741824; // 1go
	private static final long serialVersionUID = 1L;
	IRequest remoteRequest;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Edit() {
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
		ENTITIES ent = ENTITIES.valueOf(getString(request,"entity").toUpperCase());

			switch (ent) {
			/*case METADATA:
				message = handlerMetadata(request);
				break;*/
			/*case USER:
				message = handlerUser(request);
				break;*/
			case SITE:
			case SESSION:
			case CORPUS:
			case VIEW:
				message = handlerContainer(request);
				break;
			case VIDEO:
				message = handlerVideo(request);
				break;
			case ANNOTATION:
				message = handlerAnnotation(request);
				break;
			/*case MONTAGE:
				message = handlerMontage(request);
				break;*/

			default:
		}
		
		response.sendRedirect("");
	}

	private String handlerVideo(HttpServletRequest request) {
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

		return "Votre video a bien �t� ajout�e";
	}
	
	private String handlerAnnotation(HttpServletRequest request) {
		upload(request);
			
		return "Votre annotation a bien �t� ajout�e";
	}
	
	private String handlerContainer(HttpServletRequest request) {
		Entity e = new Entity(getInt(request,"entityId"), getString(request,"entityName"));
		String valeurColonne = getString(request,"name");
		String nomColonne = "name";
		String typeColonne = "string";
		
		Map <String,String> subMap = new HashMap<String, String>();
		subMap.put(valeurColonne, typeColonne);
		Map<String,Map<String,String>> map = new HashMap<String, Map<String,String>>();
		map.put(nomColonne, subMap);
		
		remoteRequest.editNamedEntity(map, e);		
		return "Votre site a bien �t� mis � jour";
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

		return "Votre session a bien �t� ajout�e";
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

		return "Votre corpus a bien �t� ajout�e";
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
	
	

	public String upload(HttpServletRequest request){
    	String name = "";
    	String entity = "";
    	String father = "";
    	int fatherId = -1;
    	int privacy = -1;
    	
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
                	}
                }else { // should be last because iterator conserves sending order
                	NamedEntity ne = new NamedEntityAO().getName(fatherId, father);
        	    	int id = remoteRequest.createAnnotation(new NamedEntity(entity,name),ne,PRIVACY.fromInt(privacy));
        	    	String contentType = item.getContentType();
        	    	String givenName = item.getName();
        	        String path = servletContext.getRealPath("/");
        	        
        	    	String givenExt = givenName.substring(givenName.lastIndexOf("."));
        	    	String destination = "Wanda"+File.separator+entity+File.separator+id+givenExt;
        	    	
                	System.out.println(destination);
                	new LinkedEntityAO().setLink(id, destination);
                	File destinationFile = new File(path+destination);

            		destinationFile.getParentFile().mkdirs();
        			if (!destinationFile.exists()) {
        				destinationFile.createNewFile();
        			}
                	item.write(destinationFile);
                }
	    	}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	return "Uploaded";
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
					return "Les m�tadonn�es ont bien �t� renseign�es";
				} catch (AlreadyRegistredException e1) {
					e1.printStackTrace();
				}
			}
		} catch (NotFoundInDatabaseException e2) {
			return "Il n'y a pas de m�tadonn�es � renseigner";
		}
		return "Il n'y a pas de m�tadonn�es � renseigner";
	}
}
