package fr.irit.wanda.service;

import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.Montage;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public interface ICorpusManager {

	/**
	 * Adds a metadata to an object (security check) is supposed to be applied
	 * to corpus and video
	 * 
	 * @param metadata
	 *            the linkedMetadata representation to save
	 * @return true if success false if not
	 * @throws AlreadyRegistredException
	 */
	public boolean addMetadata(MetadataContent metadata) throws AlreadyRegistredException;

	/**
	 * Creates a user account with no rights
	 * 
	 * @param user
	 *            the user to add
	 * @return the created user id -1 if problem occured
	 */
	public int createUser(User user) throws AlreadyRegistredException;

	/**
	 * Creates a new session bound to the given corpus
	 * 
	 * @param session
	 *            the new session
	 * @param father
	 *            the father
	 * @return true if success false if not
	 * @throws AlreadyRegistredException
	 * @throws NotFoundInDatabaseException
	 */
	public boolean createSession(NamedEntity session, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException;

	/**
	 * Creates a new view
	 * 
	 * @param view
	 *            the new view
	 * @param father
	 *            the father
	 * 
	 * @return true if success false if not
	 * @throws AlreadyRegistredException
	 * @throws NotFoundInDatabaseException
	 */
	public boolean createView(NamedEntity view, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException;

	/**
	 * Creates a new montage
	 * 
	 * @param montage
	 *            the new montage
	 * @return true if success false if not
	 */
	public boolean createMontage(Montage montage);


	/**
	 * Validates a video or an annotation
	 * 
	 * @param entity
	 * @return
	 */
	public boolean validate(NamedEntity entity);

}
