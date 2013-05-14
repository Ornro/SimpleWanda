package fr.irit.wanda.service;

import java.util.Collection;

import org.apache.commons.fileupload.FileItem;

import fr.irit.wanda.entities.A3;
import fr.irit.wanda.entities.Annotation;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.Montage;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotAllowedToProceedException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public interface IRequest {

	int addA3(A3 a3) throws NotAllowedToProceedException;

	boolean createSite(NamedEntity site) throws NotAllowedToProceedException,
			AlreadyRegistredException, NotFoundInDatabaseException;

	boolean addSiteManager(NamedEntity site, User manager)
			throws NotAllowedToProceedException;

	boolean addMetadata(MetadataContent metadata)
			throws AlreadyRegistredException, NotAllowedToProceedException;

	int createUser(User user) throws AlreadyRegistredException,
			NotAllowedToProceedException;

	boolean createSession(NamedEntity session, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException,
			NotAllowedToProceedException;

	boolean createView(NamedEntity view, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException,
			NotAllowedToProceedException;

	boolean createMontage(Montage montage) throws NotAllowedToProceedException;

	boolean validate(NamedEntity entity) throws NotAllowedToProceedException;

	boolean submitAnnotation(Annotation annotation, FileItem annotationFile)
			throws NotAllowedToProceedException;

	FileItem getAnnotation(String annotationName)
			throws NotAllowedToProceedException;

	Collection<MetadataContent> getMetadata(NamedEntity concerned)
			throws NotFoundInDatabaseException;

	FileItem getVideo(String videoName);

	boolean createCorpus(NamedEntity corpus, NamedEntity father)
			throws AlreadyRegistredException, NotAllowedToProceedException,
			NotFoundInDatabaseException;

	boolean editPrivacy(NamedEntity entity, int privacy)
			throws NotFoundInDatabaseException, NotAllowedToProceedException;

	boolean addCorpusManager(NamedEntity corpus, User manager)
			throws NotAllowedToProceedException;

	String printHierarchy();

	boolean createVideo(NamedEntity video, NamedEntity father, int privacy)
			throws AlreadyRegistredException, NotAllowedToProceedException,
			NotFoundInDatabaseException;

}
