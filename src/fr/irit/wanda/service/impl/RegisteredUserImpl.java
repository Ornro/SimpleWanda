package fr.irit.wanda.service.impl;

import java.util.Collection;

import org.apache.commons.fileupload.FileItem;

import fr.irit.wanda.dao.MetadataAO;
import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.entities.Annotation;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.entities.User.ACCESS_RIGHT;
import fr.irit.wanda.entities.Video;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class RegisteredUserImpl {


	protected RegisteredUserImpl() {
	}

	protected boolean submitAnnotation(Annotation annotation,
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

	protected boolean editPrivacy(NamedEntity entity, boolean privacy)
			throws NotFoundInDatabaseException {
		NamedEntityAO nao = new NamedEntityAO();
		return nao.editPrivacy(entity, privacy);
	}

	protected Collection<MetadataContent> getMetadata(NamedEntity concerned)
			throws NotFoundInDatabaseException {

		MetadataAO metadataAO = new MetadataAO();
		return metadataAO.getMetadatas(concerned);
	}

	protected boolean createVideo(Video video) {
		// TODO createVideo
		return false;
	}

}
