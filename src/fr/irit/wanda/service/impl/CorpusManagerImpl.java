package fr.irit.wanda.service.impl;

import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.MetadataAO;
import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.Montage;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.entities.User.ACCESS_RIGHT;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;
import fr.irit.wanda.service.ICorpusManager;

public class CorpusManagerImpl implements ICorpusManager {

	private NamedEntity corpus;
	private User manager; // for future implementation

	private CorpusManagerImpl(NamedEntity corpus, User user) {
		this.corpus=corpus;
		this.manager=user;
	}

	/**
	 * Creates the instance of corpus manager returns null if the given user
	 * cannot access to this interface
	 * 
	 * @param corpus
	 *            the corpus to manage
	 * @param user
	 *            the user
	 * @return null if permission is not sufficient
	 */
	public static CorpusManagerImpl getInstance(NamedEntity corpus, User user) {
		UserAO uao = new UserAO();
		try {
			if (uao.isAuthorized(user, corpus))
				return new CorpusManagerImpl(corpus, user);
		} catch (NotFoundInDatabaseException e) {
			System.err.println("User " + user.getMail()
					+ " not found in database;");
		}
		return null;
	}

	@Override
	public boolean addMetadata(MetadataContent metadata)
			throws AlreadyRegistredException {
		ContainerAO containerAccsessObject = new ContainerAO();
		if (!metadata.getConcerned().getEntityName().equals("corpus"))
			return false; // entity is a corpus
		if (containerAccsessObject.isContentOf(corpus, this.corpus))
			return false; // it's not content of the corpus that the user manages
		return new MetadataAO().addContent(metadata);
	}

	@Override
	public int createUser(User user) throws AlreadyRegistredException {
		return new UserAO().add(user);
	}
	
	// TODO addUser to a corpus
	// TODO create subcorpus

	@Override
	public boolean createSession(NamedEntity session, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException {
		if (!session.getEntityName().equals("session"))
			return false; // is not a session
		if (!father.equals(this.corpus))
			return false; // is not the managed corpus
		return new ContainerAO().createContainer(session, father);
	}

	@Override
	// TODO correct corpus != session
	public boolean createView(NamedEntity view, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException {
		if (!view.getEntityName().equals("view"))
			return false; // is not a view
		if (!father.equals(this.corpus))
			return false; // is not the managed corpus
		return new ContainerAO().createContainer(view, father);
	}

	@Override
	public boolean createMontage(Montage montage) {
		// TODO createMontage
		return false;
	}

	@Override
	public boolean validate(NamedEntity entity) {
		// TODO validate entity
		return false;
	}

}
