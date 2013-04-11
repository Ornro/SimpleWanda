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
import fr.irit.wanda.service.IRegisteredUser;

public class RegisteredUserImpl implements IRegisteredUser {

	private User user;

	private RegisteredUserImpl(User user) {
		this.user = user;
	}

	/**
	 * Returns the user interface if the user is registred
	 * 
	 * @param user
	 *            the user
	 * @return null if user not registred
	 */
	public static RegisteredUserImpl getInstance(User user) {
		UserAO uao = new UserAO();
		if (!uao.exists(user))
			return null;
		return new RegisteredUserImpl(user);
	}

	@Override
	public boolean submitAnnotation(Annotation annotation,
			FileItem annotationFile) {
		// TODO submitAnnotation
		return false;
	}

	@Override
	public FileItem getAnnotation(String annotationName) {
		// TODO getAnnotation
		return null;
	}

	@Override
	public FileItem getVideo(String videoName) {
		// TODO getVideo
		return null;
	}

	@Override
	public boolean editPrivacy(NamedEntity entity, boolean privacy)
			throws NotFoundInDatabaseException {
		UserAO uao = new UserAO();
		if (uao.isAuthorized(this.user,entity))
			return false; // if the user is not owner he can't edit privacy

		NamedEntityAO nao = new NamedEntityAO();
		return nao.editPrivacy(entity, privacy);
	}

	@Override
	public Collection<MetadataContent> getMetadata(NamedEntity concerned)
			throws NotFoundInDatabaseException {

		MetadataAO metadataAO = new MetadataAO();
		return metadataAO.getMetadatas(concerned);
	}

	@Override
	public boolean createVideo(Video video) {
		// TODO createVideo
		return false;
	}

}
