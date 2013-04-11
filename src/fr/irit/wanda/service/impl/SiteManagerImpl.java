package fr.irit.wanda.service.impl;

import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.MetadataAO;
import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.entities.User.ACCESS_RIGHT;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.InvalidParameterException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;
import fr.irit.wanda.service.ISiteManager;

public class SiteManagerImpl implements ISiteManager {

	private NamedEntity site;
	private User manager; // for future implementation

	private SiteManagerImpl(NamedEntity site, User user) {
		this.site = site;
		this.manager = user;
	}

	/**
	 * Creates the instance of site manager returns null if the given user
	 * cannot access to this interface
	 * 
	 * @param site
	 *            the site to manage
	 * @param user
	 *            the user
	 * @return null if permission is not sufficient
	 */
	public static SiteManagerImpl getInstance(NamedEntity site, User user) {
		UserAO uao = new UserAO();
		try {
			if (uao.isAuthorized(user,site))
				return new SiteManagerImpl(site, user);
		} catch (NotFoundInDatabaseException e) {
			System.err.println("User " + user.getMail()
					+ " not found in database;");
		}
		return null;
	}

	@Override
	public boolean createCorpus(NamedEntity corpus, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException {

		if (!corpus.getEntityName().equals("corpus"))
			return false; // if object is corpus

		if (!father.equals(this.site))
			return false; // the site the user manages is different

		return new ContainerAO().createContainer(corpus, father);
	}

	@Override
	public boolean addCorpusManager(NamedEntity corpus, User corpusManager) {
		UserAO userAccessObject = new UserAO();
		ContainerAO containerAccsessObject = new ContainerAO();

		if (!corpus.getEntityName().equals("corpus"))
			return false; // it's not a corpus

		if (containerAccsessObject.isContentOf(corpus, this.site))
			return false; // it's not content of the site that the user manages

		try {
			userAccessObject.setAccessRight(corpus, ACCESS_RIGHT.OWN,
					corpusManager);
		} catch (InvalidParameterException e) {
			return false; // should not happen @see UserAO
		}

		return true;
	}

	@Override
	public boolean addMetadata(MetadataContent metadata)
			throws AlreadyRegistredException {
		if (!metadata.getConcerned().getEntityName().equals("site"))
			return false; // avoid expensive checking
		if (!metadata.getConcerned().equals(this.site))
			return false; // the site for which authorisation was granted is
							// différent
		return new MetadataAO().addContent(metadata);
	}
}
