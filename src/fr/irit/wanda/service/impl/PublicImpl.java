package fr.irit.wanda.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import fr.irit.wanda.dao.ContainerAO;
import fr.irit.wanda.dao.MetadataAO;
import fr.irit.wanda.dao.NamedEntityAO;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.exception.NotFoundInDatabaseException;
import fr.irit.wanda.service.IPublic;

public class PublicImpl implements IPublic {

	

	@Override
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

	@Override
	public String printHierarchy() {
		String chaine = "";
		chaine += "<h3>Arborescence</h3>";
		chaine += "<ol class=\"tree\">";

		NamedEntityAO accessObject = new NamedEntityAO();
		Collection<NamedEntity> sites = accessObject.getAll("site");
		for (NamedEntity s : sites) {
			chaine += printHierarchy(s);
		}
		chaine += "</ol>";
		return chaine;
	}

	/**
	 * Prints hierarchy html begining with the given container
	 * 
	 * @param container
	 * @return
	 */
	private static String printHierarchy(NamedEntity container) {
		ContainerAO cao = new ContainerAO();
		String chaine = "";
		if (cao.isContainer(container)) {
			chaine += beginContainer(container.getName());
			for (NamedEntity e : cao.getContent(container)) {
				chaine += printHierarchy(e); // on cree chaque élément fils
													// de son <li> a son </li>
			}
			chaine += endContainer();
		} else {
			displayEntity(container.getName());
		}

		return chaine; // on retourne le texte html a afficher
	}

	private static String beginContainer(String name) {
		String chaine = "";
		chaine += "<li>"; // on cree son element de liste

		chaine += "<label for=\"folder\">"; // on l'identifie
		chaine += name;
		chaine += "</label>";

		chaine += "<input type=\"checkbox\" id=\"folder\" />"; // syle
		chaine += "<ol>"; // on commence une sous liste

		return chaine;
	}

	private static String endContainer() {
		String chaine = "";
		chaine += "</ol>"; // on ferme la liste des fils
		chaine += "</li>"; // on clos cet élément

		return chaine;
	}

	private static String displayEntity(String name) {
		String chaine = "";
		chaine += "<li class=\"file\">";
		chaine += "<a href=\"\">";
		chaine += name;
		chaine += "</a>";
		chaine += "</li>";

		return chaine;
	}

}
