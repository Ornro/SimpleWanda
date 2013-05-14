package fr.irit.wanda.service.impl;

import java.util.Collection;

import org.apache.commons.fileupload.FileItem;

import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.entities.*;
import fr.irit.wanda.entities.User.ACCESS_RIGHT;
import fr.irit.wanda.entities.User.ROLE;

import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotAllowedToProceedException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

import fr.irit.wanda.service.*;

public class RequestImpl implements IRequest {

	User caller;
	AdminImpl adminRequest = new AdminImpl();
	PublicImpl publicRequest = new PublicImpl();
	RegisteredUserImpl registeredUserRequest = new RegisteredUserImpl();
	SiteManagerImpl siteManagerRequest = new SiteManagerImpl();
	CorpusManagerImpl corpusManagerRequest = new CorpusManagerImpl();

	public RequestImpl(String userEmail) {
		try {
			this.caller = new UserAO().getUser(userEmail);
		} catch (NotFoundInDatabaseException e) {
			this.caller = null;
		}
	}

	public boolean isAllowedToProceed(Entity entity)
			throws NotAllowedToProceedException {
		try {
			if (new UserAO().isAuthorized(caller, entity)) {

			}

		} catch (Exception e) {
			throw new NotAllowedToProceedException();
		}
		return true;
	}

	public boolean isRegistered() {
		return (caller != null);
	}

	@Override
	public int addA3(A3 a3) throws NotAllowedToProceedException {
		if (caller.getRole() == ROLE.ADMIN)
			return adminRequest.addA3(a3);
		else
			throw new NotAllowedToProceedException();
	}

	@Override
	public boolean createSite(NamedEntity site)
			throws NotAllowedToProceedException, AlreadyRegistredException,
			NotFoundInDatabaseException {
		if (caller.getRole() == ROLE.ADMIN)
			return adminRequest.createSite(site);
		else
			throw new NotAllowedToProceedException();
		
	}

	@Override
	public boolean addSiteManager(NamedEntity site, User manager)
			throws NotAllowedToProceedException {
		if (caller.getRole() == ROLE.ADMIN)
			return adminRequest.addSiteManager(site, manager);
		else
			throw new NotAllowedToProceedException();
	}

	@Override
	public boolean addMetadata(MetadataContent metadata)
			throws AlreadyRegistredException, NotAllowedToProceedException {
		if (isAllowedToProceed(metadata.getConcerned())) {
			if (metadata.getConcerned().isSite()) {
				return siteManagerRequest.addMetadata(metadata);

			} else if (metadata.getConcerned().isCorpus()) {
				return corpusManagerRequest.addMetadata(metadata);
			}
		}
		return false;
	}

	@Override
	public int createUser(User user) throws AlreadyRegistredException,
			NotAllowedToProceedException {
		if (caller.getRole() == ROLE.ADMIN)
			return createUser(user);
		else
			throw new NotAllowedToProceedException();
	}

	@Override
	public boolean createSession(NamedEntity session, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException,
			NotAllowedToProceedException {
		if (isAllowedToProceed(father)) {
			if (session.isSession()) {
				return corpusManagerRequest.createSession(session, father);
			}
		}
		return false;
	}

	@Override
	public boolean createView(NamedEntity view, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException,
			NotAllowedToProceedException {
		if (isAllowedToProceed(father)) {
			if (view.isView()) {
				return corpusManagerRequest.createView(view, father);
			}
		}
		return false;
	}

	@Override
	public boolean createMontage(Montage montage)
			throws NotAllowedToProceedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validate(NamedEntity entity)
			throws NotAllowedToProceedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean submitAnnotation(Annotation annotation,
			FileItem annotationFile) throws NotAllowedToProceedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FileItem getAnnotation(String annotationName)
			throws NotAllowedToProceedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<MetadataContent> getMetadata(NamedEntity concerned)
			throws NotFoundInDatabaseException {
		if (isRegistered()) {
			return registeredUserRequest.getMetadata(concerned);
		} else {
			return publicRequest.getMetadata(concerned);
		}
	}

	@Override
	public FileItem getVideo(String videoName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean editPrivacy(NamedEntity entity, int privacy)
			throws NotFoundInDatabaseException, NotAllowedToProceedException {
		if (isAllowedToProceed(entity)) {
			return registeredUserRequest.editPrivacy(entity, privacy);
		}
		return false;
	}

	@Override
	public boolean createVideo(NamedEntity video,NamedEntity father, int privacy)
			throws AlreadyRegistredException, NotAllowedToProceedException,
			NotFoundInDatabaseException	{
		video.setOwner(caller);
		return registeredUserRequest.createVideo(video, father, privacy);
	}

	@Override
	public boolean createCorpus(NamedEntity corpus, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException,
			NotAllowedToProceedException {
		if (isAllowedToProceed(father)) {
			if (corpus.isCorpus()) {
				return siteManagerRequest.createCorpus(corpus, father);
			}
		}
		return false;
	}

	@Override
	public boolean addCorpusManager(NamedEntity corpus, User manager)
			throws NotAllowedToProceedException {
		// TODO Auto-generated method stub site corpus
		if (isAllowedToProceed(corpus)) {
			if (corpus.isCorpus()) {
				return siteManagerRequest.addCorpusManager(corpus, manager);
			}
		}
		return false;
	}

	@Override
	public String printHierarchy() {
		String chaine = "";
		chaine += "<h3>Arborescence</h3>";
		chaine += "<ol class=\"tree\">";

		NamedEntityAO accessObject = new NamedEntityAO();
		Collection<NamedEntity> sites = accessObject.getAll("site");
		for (NamedEntity s : sites) {
			chaine += printHierarchy(s);
		}
		chaine += "</ol>";
		if (caller != null) {
			if (caller.getRole() == ROLE.ADMIN){
				chaine += "<br><span style=\"float:right\">"+ printAJAXCreateLink("-1_site_site")+"<img title=\"Add Site\" src=\"/SimpleWanda/img/add.png \" class=\"icon\"/><small> Add site </small></a></span>";
			}
		}
		
		return chaine;
	}

	/**
	 * Prints hierarchy html begining with the given container
	 * 
	 * @param container
	 * @return
	 */
	private String printHierarchy(NamedEntity container) {
		ContainerAO cao = new ContainerAO();
		String chaine = "";
		if (cao.isContainer(container)) {
			chaine += beginContainer(container);
			for (NamedEntity e : cao.getContent(container)) {
				chaine += printHierarchy(e);
			}
			chaine += endContainer(container);
		} else {
			displayEntity(container);
		}

		return chaine; // on retourne le texte html a afficher
	}

	private String beginContainer(NamedEntity container) {
		String chaine = "";
		chaine += "<li>"; // on cree son element de liste

		chaine += "&nbsp; &nbsp;"; // on l'identifie
		chaine += "<span id=\"dynamicMenu\" onmouseover=\"quickMenu('"+ container.getId() + "_" + container.getEntityName() +"_icons"+"')\" onmouseout=\"quickMenu2('"+ container.getId() + "_" + container.getEntityName() +"_icons"+"')\" >";
		chaine += printAJAXLink(container, "view")+container.getName()+"</a>";
		chaine += "<span id=\""+ container.getId() + "_" + container.getEntityName() +"_icons"+"\" class=\"hidden\">"+addIcons(container)+addSons(container)+"</span>";
		chaine += "</span>";

		chaine += "<input type=\"checkbox\" id=\"folder\" \\>"; // style
		chaine += "<ol>"; // on commence une sous liste

		return chaine;
	}
	
	private String printAJAXLink(NamedEntity container,String action){
		return "<a class=\"add_entities\" id=\"" + container.getId() + "_" + container.getEntityName() 
				+ "\" name=\"" + action
				+ "\" onclick=\"change_div(this.name,this.id)\">";
	}
	
	public String printAJAXCreateLink(String id){
			return "<a class=\"add_entities\" id=\"" + id 
					+ "\" name=\"Form.jsp\" onclick=\"change_div(this.name,this.id)\">";
	}

	private String addIcons(NamedEntity container) {
		String chaine = "";
		try {
			if (isAllowedToProceed(container)) {
				chaine += printAJAXLink(container, "edit")
						+ " &nbsp<img title=\"Edit\" src=\"/SimpleWanda/img/edit.png\" class=\"icon\" \\> </a>";				
			}
		} catch (NotAllowedToProceedException e) {
		}
		return chaine;
	}

	private String endContainer(NamedEntity container) {
		String chaine = "";
		chaine += "</ol>"; // on ferme la liste des fils
		chaine += "</li>"; // on clos cet �l�ment

		return chaine;
	}

	private String displayEntity(NamedEntity entity) {
		String chaine = "";
		chaine += "<li class=\"file\">";
		chaine += printAJAXLink(entity, "view")+entity.getName()+"</a>";
		chaine += "<a href=\"#\"><img src=\"/SimpleWanda/img/download.png \" </a>";
		chaine += "</li>";

		return chaine;
	}
	
	private String addSons(NamedEntity container){
		
		String chaine="";
		try {
			if (isAllowedToProceed(container)) {
				for (String son:new ContainerAO().getSonsNames(container)){
					chaine += printAJAXCreateLink(container.getId()+"_"+son+"_"+container.getEntityName())+ "<img title=\"Add "+son+"\" src=\"/SimpleWanda/img/add.png \" class=\"icon\"/\\></a>";
					chaine += "&nbsp;";
				}
			}
		} catch (NotAllowedToProceedException e) {
			System.err.println("ERROR");
		}
		return chaine;
	}
}