package fr.irit.wanda.service;

import fr.irit.wanda.entities.A3;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public interface IAdmin {

	/**
	 * Adds an a3 to the a3 list
	 * 
	 * @param a3
	 *            the a3 to add
	 * @return the new A3 id
	 */
	public int addA3(A3 a3);

	/**
	 * Adds a new site
	 * 
	 * @param site
	 *            the new site
	 * @return true if success false if not
	 * @throws AlreadyRegistredException
	 * @throws NotFoundInDatabaseException
	 */
	public boolean createSite(NamedEntity site)
			throws AlreadyRegistredException, NotFoundInDatabaseException;

	/**
	 * Grant a user the management of a site
	 * 
	 * @param site
	 *            the site
	 * @param manager
	 *            the manager to be
	 * @return true if success false if not
	 */
	public boolean addSiteManager(NamedEntity site, User manager);

}
