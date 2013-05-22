package fr.irit.wanda.service.impl;

import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.LinkedEntityAO;
import fr.irit.wanda.dao.MetadataAO;
import fr.irit.wanda.entities.LinkedEntity;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.LinkedEntity.PRIVACY;
import fr.irit.wanda.entities.LinkedEntity.WORKFLOW;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class CorpusManagerImpl {


	protected CorpusManagerImpl() {

	}

	protected boolean addMetadata(MetadataContent metadata)
			throws AlreadyRegistredException {
		return new MetadataAO().addContent(metadata);
	}
	
	// TODO addUser to a corpus
	// TODO create subcorpus
	protected int createSession(NamedEntity session, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException {
		return new ContainerAO().createContainer(session, father);
	}

	// TODO correct corpus != session
	protected int createView(NamedEntity view, NamedEntity father)
			throws AlreadyRegistredException, NotFoundInDatabaseException {
		return new ContainerAO().createContainer(view, father);
	}

	protected int createMontage(NamedEntity montage, NamedEntity father, PRIVACY privacy) throws AlreadyRegistredException, NotFoundInDatabaseException {
		LinkedEntity linkedMontage = new LinkedEntity(montage,privacy,WORKFLOW.WAITING);
		
		return new LinkedEntityAO().createLinkedEntity(linkedMontage, father);
	}

	protected boolean validate(NamedEntity entity) {
		// TODO validate entity
		return false;
	}

}
