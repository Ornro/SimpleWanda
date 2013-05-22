package fr.irit.wanda.service.impl;

import fr.irit.wanda.dao.A3AO;
import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.MetadataAO;
import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.entities.A3;
import fr.irit.wanda.entities.Metadata;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.entities.User.ACCESS_RIGHT;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.InvalidParameterException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class AdminImpl{

	protected AdminImpl() {

	}

	protected int addA3(A3 a3) {
		A3AO a3AccessObject = new A3AO();
		try {
			return a3AccessObject.addA3(a3);
		} catch (AlreadyRegistredException e) {
			try {
				return a3AccessObject.getA3(a3.getName()).getId();
			} catch (NotFoundInDatabaseException e1) {
				// should not happen
				return -1;
			}
		}
	}
	
	protected int createUser(User user) throws AlreadyRegistredException {
		return new UserAO().add(user);
	}

	protected int createSite(NamedEntity site)
			throws AlreadyRegistredException, NotFoundInDatabaseException {

		if (!site.getEntityName().equals("site"))
			return -1;// is not a site

		return new ContainerAO().createContainer(site, null);
	}

	protected boolean addSiteManager(NamedEntity site, User manager) {
		UserAO userAccessObject = new UserAO();
		if (!site.getEntityName().equals("site"))
			return false; // is not a site

		try {
			userAccessObject.setAccessRight(site, ACCESS_RIGHT.OWN, manager);
		} catch (InvalidParameterException e) {
			return false; // should not happen @see UserAO
		}
		return true;
	}
	
	protected int createMetadata(Metadata m){
		MetadataAO mao = new MetadataAO();
		try {
			return mao.addMetadata(m);
		} catch (AlreadyRegistredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
		
	}
}
