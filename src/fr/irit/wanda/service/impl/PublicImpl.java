package fr.irit.wanda.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.MetadataAO;
import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class PublicImpl {

	

	public Collection<MetadataContent> getMetadata(NamedEntity concerned)
			throws NotFoundInDatabaseException {
		
		MetadataAO metadataAO = new MetadataAO();
		ArrayList<MetadataContent> raw = (ArrayList<MetadataContent>) metadataAO
				.getMetadatas(concerned);
		ArrayList<MetadataContent> refined = new ArrayList<MetadataContent>();
		
		// if the content is public then we return it
		for (MetadataContent content : raw) {
			if (!content.isPrivate())
				refined.add(content);
		}
		return refined;
	}

	

}
