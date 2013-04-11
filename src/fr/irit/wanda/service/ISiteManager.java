package fr.irit.wanda.service;

import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public interface ISiteManager {

	/**
	 * Creates a new corpus
	 * 
	 * @param corpus
	 *            the new corpus
	 * @param father
	 *            the father
	 * @return true if success false if not
	 * @throws AlreadyRegistredException
	 * @throws NotFoundInDatabaseException
	 */
	public boolean createCorpus(NamedEntity corpus, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException;

	/**
	 * Grant a user the management of a corpus
	 * 
	 * @param corpus
	 *            the corpus
	 * @param manager
	 *            the manager to be
	 * @return true if success false if not
	 */
	public boolean addCorpusManager(NamedEntity corpus, User manager);

	/**
	 * Adds a metadata to an object (security check) is supposed to be applied
	 * to corpus, video (inherited) or a site
	 * 
	 * @param metadata
	 *            the linkedMetadata representation to save
	 * @return true if success false if not
	 */
	public boolean addMetadata(MetadataContent metadata)
			throws AlreadyRegistredException;
}
