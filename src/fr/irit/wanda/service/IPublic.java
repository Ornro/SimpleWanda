package fr.irit.wanda.service;

import java.util.Collection;

import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public interface IPublic {

	/**
	 * Returns the public metatada concerning an entity.
	 * 
	 * @param concerned
	 *            the entity of which you want metadata
	 * @return LinkedMetadata Collection representing the content of all the
	 *         public metadata
	 * @throws NotFoundInDatabaseException
	 */
	public Collection<MetadataContent> getMetadata(NamedEntity concerned) throws NotFoundInDatabaseException;

	/**
	 * Gives the string to print the common hierarchy of files
	 * 
	 * @return the html string
	 */
	public String printHierarchy();

}
