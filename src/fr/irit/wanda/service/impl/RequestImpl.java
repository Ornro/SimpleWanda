package fr.irit.wanda.service.impl;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.LinkedEntityAO;
import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.dao.MetadataAO;
import fr.irit.wanda.entities.*;
import fr.irit.wanda.entities.LinkedEntity.PRIVACY;
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

	public RequestImpl(HttpServletRequest request) {
		try {
			this.caller = getCaller(request);
		} catch (Exception e) {
			this.caller = null;
			System.err.println("USER NOT FOUND ");
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
	public int createSite(NamedEntity site)
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
			return adminRequest.createUser(user);
		else
			throw new NotAllowedToProceedException();
	}

	@Override
	public int createSession(NamedEntity session, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException,
			NotAllowedToProceedException {
		if (isAllowedToProceed(father)) {
			if (session.isSession()) {
				return corpusManagerRequest.createSession(session, father);
			}
		}
		return -1;
	}

	@Override
	public int createView(NamedEntity view, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException,
			NotAllowedToProceedException {
		if (isAllowedToProceed(father)) {
			if (view.isView()) {
				return corpusManagerRequest.createView(view, father);
			}
		}
		return -1;
	}

	@Override
	public boolean createMontage(LinkedEntity montage)
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
	public boolean submitAnnotation(LinkedEntity annotation,
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
	public boolean editPrivacy(NamedEntity entity, PRIVACY privacy)
			throws NotFoundInDatabaseException, NotAllowedToProceedException {
		if (isAllowedToProceed(entity)) {
			return registeredUserRequest.editPrivacy(entity, privacy);
		}
		return false;
	}

	@Override
	public int createVideo(NamedEntity video,NamedEntity father, PRIVACY privacy)
			throws AlreadyRegistredException, NotAllowedToProceedException,
			NotFoundInDatabaseException	{
		video.setOwner(caller);
		return registeredUserRequest.createVideo(video, father, privacy);
	}
	
	@Override
	public int createAnnotation(NamedEntity annotation,NamedEntity father, PRIVACY privacy)
			throws AlreadyRegistredException, NotAllowedToProceedException,
			NotFoundInDatabaseException	{
		annotation.setOwner(caller);
		return registeredUserRequest.createAnnotation(annotation, father, privacy);
	}

	@Override
	public int createCorpus(NamedEntity corpus, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException,
			NotAllowedToProceedException {
		if (isAllowedToProceed(father)) {
			if (corpus.isCorpus()) {
				return siteManagerRequest.createCorpus(corpus, father);
			}
		}
		return -1;
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
				chaine += "<br><span style=\"margin-left:10px;\">"+ printAJAXCreateLink("-1_metadata_metadata")+"<img title=\"Add Metadata\" src=\"/SimpleWanda/img/add.png \" class=\"icon\"/><small> Add metadata </small></a></span><br>";
				chaine += "<span style=\"margin-left:10px;\">"+ printAJAXCreateLink("-1_site_site")+"<img title=\"Add Site\" src=\"/SimpleWanda/img/add.png \" class=\"icon\"/><small> Add site </small></a></span><br>";
				chaine += "<span style=\"margin-left:10px;\">"+ printAJAXCreateLink("-1_user_user")+"<img title=\"Add Site\" src=\"/SimpleWanda/img/add.png \" class=\"icon\"/><small> Add user </small></a></span><br>";
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
			chaine += displayEntity(container);
		}

		return chaine; // on retourne le texte html a afficher
	}

	private String beginContainer(NamedEntity container) {
		String chaine = "";
		chaine += "<li>"; // on cree son element de liste

		chaine += "&nbsp; &nbsp;"; // on l'identifie
		chaine += "<img class=\"icon\" src=\"/SimpleWanda/img/folder-horizontal.png\"/>&nbsp;";
		chaine += "<span id=\"dynamicMenu\" onmouseover=\"quickMenu('"+ container.getId() + "_" + container.getEntityName() +"_icons"+"')\" onmouseout=\"quickMenu2('"+ container.getId() + "_" + container.getEntityName() +"_icons"+"')\" >";
		chaine += printAJAXLink(container, "View.jsp")+container.getName()+"</a>";
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
				chaine += printAJAXLink(container, "Edit.jsp")
						+ "&nbsp<img title=\"Edit\" src=\"/SimpleWanda/img/edit.png\" class=\"icon\" \\></a>&nbsp;";	
				if (container.getEntityName().equals("video")){
					chaine += printAJAXCreateLink(container.getId()+"_links_"+container.getEntityName())+ "<img title=\"Add file\" src=\"/SimpleWanda/img/add.png \" class=\"icon\"/\\> </a>";
				}else if(container.getEntityName().equals("site")){
					chaine += printAJAXCreateLink(container.getId()+"_addRight_"+container.getEntityName())+ "<img title=\"Add admin\" src=\"/SimpleWanda/img/info.png \" class=\"icon\"/\\> </a>";
				}
			}
		} catch (NotAllowedToProceedException e) {
		}
		return chaine;
	}

	private String endContainer(NamedEntity container) {
		String chaine = "";
		if (container.getEntityName().equals("video")){
			try {
				for (VideoFile vf : new LinkedEntityAO().getLinks(container.getId())){
					chaine += "<li class=\"file\">";
					chaine += "<span>";
					chaine += "<a href=\""+vf.getLink()+"\" target=\"_blank\"  download>"+vf.getDisplayName()+"</a>";
					chaine += "</span>";
					chaine += "</li>";
				}
			} catch (NotFoundInDatabaseException e) {
				e.printStackTrace();
			}
		}
		chaine += "</ol>"; // on ferme la liste des fils
		chaine += "</li>"; // on clos cet élément

		return chaine;
	}

	private String displayEntity(NamedEntity entity) {
		int eid = entity.getId();
		String ename = entity.getEntityName();
		String chaine = "";
		chaine += "<li class=\"file\">";
		chaine += "<span id=\"dynamicMenu\" onmouseover=\"quickMenu('"+ eid + "_" + ename +"_icons"+"')\" onmouseout=\"quickMenu2('"+ eid + "_" + ename +"_icons"+"')\" >";
		chaine += printAJAXLink(entity, "View.jsp")+entity.getName()+"</a>&nbsp";
		chaine += "<span id=\""+ eid + "_" + ename +"_icons"+"\" class=\"hidden\"><a href=\""+new LinkedEntityAO().getSingleLink(eid)+"\" target=\"_blank\"  download><img class=\"icon\" src=\"/SimpleWanda/img/download.png \"/></span></a>";
		chaine += "</span>";
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
	
	public Collection<Metadata> getMetadatas(Entity e) throws NotFoundInDatabaseException{
		MetadataAO metaAO = new MetadataAO();
		try {
			return metaAO.getConcernedMetadata(e);
		} catch (NotFoundInDatabaseException e1) {
			return null;
		}
	}

	@Override
	public int createMetadata(Metadata m) {
		if (caller.getRole() == ROLE.ADMIN)
			return adminRequest.createMetadata(m);
		return 0;
	}	
	
	public boolean createMetaContent(MetadataContent mc) throws AlreadyRegistredException{
		return new MetadataAO().addContent(mc);
	}
	
	public Collection<MetadataContent> getMetadatasContent(Entity e) throws NotFoundInDatabaseException{
		Collection<MetadataContent> meta = new MetadataAO().getMetadatas(e);
		
		return meta;
	}
	
	public boolean editNamedEntity(Map<String, Map<String, String>> args, Entity e) {
		NamedEntityAO ne = new NamedEntityAO();
		return ne.edit(args, e);
	}
	
	public MetadataContent getMetadataContent (Entity e, Metadata m) throws NotFoundInDatabaseException{
		return new MetadataAO().getMetadataContent(e,m);
	}
	
	public User getCaller(HttpServletRequest request) throws Exception {
		System.out.println("Called get caller");
        X509Certificate[] certChain = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
        System.out.println(certChain);
        if (certChain != null) {
        	X509Certificate cert = certChain[certChain.length-1];
        	X500Name x500name = new JcaX509CertificateHolder(cert).getSubject();
        	RDN cn = x500name.getRDNs(BCStyle.CN)[0];
        	String email = IETFUtils.valueToString(cn.getFirst().getValue());
        	System.out.println(email);
        	return new UserAO().getUser(email);
		}else
			throw new Exception("You are not logged in. ");
	}
}
