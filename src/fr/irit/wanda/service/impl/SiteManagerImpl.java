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

public class SiteManagerImpl {

	protected SiteManagerImpl() {
	}

	protected boolean createCorpus(NamedEntity corpus, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException {
		return new ContainerAO().createContainer(corpus, father);
	}

	protected boolean addCorpusManager(NamedEntity corpus, User corpusManager) {
		UserAO userAccessObject = new UserAO();
		ContainerAO containerAccsessObject = new ContainerAO();
		try {
			userAccessObject.setAccessRight(corpus, ACCESS_RIGHT.OWN,
					corpusManager);
		} catch (InvalidParameterException e) {
			return false; // should not happen @see UserAO
		}

		return true;
	}

	protected boolean addMetadata(MetadataContent metadata)
			throws AlreadyRegistredException {
		return new MetadataAO().addContent(metadata);
	}
}
