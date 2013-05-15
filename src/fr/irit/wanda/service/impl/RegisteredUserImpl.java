package fr.irit.wanda.service.impl;

import java.util.Collection;

import org.apache.commons.fileupload.FileItem;

import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.LinkedEntityAO;
import fr.irit.wanda.dao.MetadataAO;
import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.entities.LinkedEntity;
import fr.irit.wanda.entities.LinkedEntity.PRIVACY;
import fr.irit.wanda.entities.LinkedEntity.WORKFLOW;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotAllowedToProceedException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class RegisteredUserImpl {

	protected RegisteredUserImpl() {
	}

	protected boolean submitAnnotation(LinkedEntity annotation,
			FileItem annotationFile) {
		// TODO submitAnnotation
		return false;
	}

	protected FileItem getAnnotation(String annotationName) {
		// TODO getAnnotation
		return null;
	}

	protected FileItem getVideo(String videoName) {
		// TODO getVideo
		return null;
	}

	protected boolean editPrivacy(NamedEntity entity, PRIVACY privacy)
			throws NotFoundInDatabaseException {
		LinkedEntityAO nao = new LinkedEntityAO();
		return nao.editPrivacy(entity, privacy);
	}

	protected Collection<MetadataContent> getMetadata(NamedEntity concerned)
			throws NotFoundInDatabaseException {

		MetadataAO metadataAO = new MetadataAO();
		return metadataAO.getMetadatas(concerned);
	}

	protected boolean createVideo(NamedEntity video, NamedEntity father, PRIVACY privacy)
			throws AlreadyRegistredException, NotAllowedToProceedException,
			NotFoundInDatabaseException {
		LinkedEntity realVideo = new LinkedEntity(video,privacy,WORKFLOW.WAITING);
		new LinkedEntityAO().createLinkedEntity(realVideo, father);
		return editPrivacy(video,privacy);
	}
	
	protected boolean createAnnotation(NamedEntity annotation, NamedEntity father, PRIVACY privacy)
			throws AlreadyRegistredException, NotAllowedToProceedException,
			NotFoundInDatabaseException {
		LinkedEntity realAnnotation = new LinkedEntity(annotation,privacy,WORKFLOW.WAITING);
		new LinkedEntityAO().createLinkedEntity(realAnnotation, father);
		return editPrivacy(annotation,privacy);
	}

}
