package fr.irit.wanda.service.impl;

import fr.irit.wanda.dao.A3AO;
import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.UserAO;
import fr.irit.wanda.entities.A3;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.entities.User.ACCESS_RIGHT;
import fr.irit.wanda.entities.User.ROLE;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.InvalidParameterException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;
import fr.irit.wanda.service.IAdmin;

public class AdminImpl implements IAdmin {

	private AdminImpl() {

	}

	/**
	 * Creates the instance of admin implementation returns null if the given
	 * user cannot access to this interface
	 * 
	 * @param user
	 *            the admin user
	 * @return null if permission is not sufficient
	 */
	public static AdminImpl getInstance(User user) {
		UserAO uao = new UserAO();
		try {
			if (uao.getRole(user.getMail()) == ROLE.ADMIN)
				return new AdminImpl();
		} catch (NotFoundInDatabaseException e) {
			System.err.println("User " + user.getMail()
					+ " not found in database;");
		}
		return null;
	}

	@Override
	public int addA3(A3 a3) {
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

	@Override
	public boolean createSite(NamedEntity site)
			throws AlreadyRegistredException, NotFoundInDatabaseException {

		if (!site.getEntityName().equals("site"))
			return false;// is not a site

		return new ContainerAO().createContainer(site, null);
	}

	@Override
	public boolean addSiteManager(NamedEntity site, User manager) {
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

}
