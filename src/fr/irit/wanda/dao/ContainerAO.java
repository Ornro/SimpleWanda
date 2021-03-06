package fr.irit.wanda.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import fr.irit.wanda.configuration.HirarchyConfiguration;
import fr.irit.wanda.entities.Entity;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class ContainerAO extends DAO {

	HirarchyConfiguration hc = HirarchyConfiguration.getInstance();

	/**
	 * Returns the content of a container
	 * 
	 * @param container
	 *            the container to get content from
	 * @return the collection representing the content
	 */
	public final Collection<NamedEntity> getContent(final Entity container) {
		ArrayList<NamedEntity> result = new ArrayList<NamedEntity>();
		String cTableName = container.getEntityName();

		for (String content : hc.getHierarchy().get(cTableName)) {
			set("SELECT id" + content + ",name FROM " + content + " WHERE _"
					+ cTableName + "=?;");
			setInt(1, container.getId());
			if (executeQuery()) {
				do {
					result.add(new NamedEntity(getInt("id" + content), content,
							getString("name")));
				} while (next());

				closeAll();
			}
		}
		return result;
	}

	/**
	 * Returns if a named entity is content of an other. The container's id must
	 * be given.
	 * 
	 * @param content
	 *            the content to check
	 * @param container
	 *            the container
	 * @return true if the relation exists false if not
	 */
	public final boolean isContentOf(final NamedEntity content,
			final NamedEntity container) {
		String containerColumnName = "_" + container.getEntityName();
		set("SELECT * FROM " + content.getEntityName() + " WHERE "
				+ containerColumnName + "=?;");
		setInt(1, container.getId());
		return executeQuery();
	}

	/**
	 * Tells if an entity is a container
	 * 
	 * @param entity
	 *            the entity
	 * @return true if it is false if not
	 */
	public final boolean isContainer(final Entity entity) {
		return hc.getHierarchy().containsKey(entity.getEntityName());
	}

	public final ArrayList<String> getSonsNames(final NamedEntity container) {
		return hc.getHierarchy().get(container.getEntityName());
	}

	/**
	 * Creates a new container (site, corpus, session, view)
	 * 
	 * @param container
	 *            the container to create
	 * @param father
	 *            the container that is the father
	 * @return true on success false if one of the needed entity is not a
	 *         container
	 * @throws AlreadyRegistredException
	 * @throws NotFoundInDatabaseException
	 */
	public final int createContainer(NamedEntity container,
			NamedEntity father) throws AlreadyRegistredException,
			NotFoundInDatabaseException {

		if (!isContainer(container))
			return -1; // if that is not a container

		NamedEntityAO nao = new NamedEntityAO();
		
		if (container.getEntityName().equals("site")) {
			if (nao.exists(container,null)) // if the entity already exists
				throw new AlreadyRegistredException(
						"The container to create already exists");
			set("INSERT INTO site(name) VALUES (?);",Statement.RETURN_GENERATED_KEYS);
			setString(1, container.getName());
		} else {
			if (nao.exists(container,null)) // if the entity already exists
				throw new AlreadyRegistredException(
						"The container to create already exists");
			
			if (!isContainer(father))
				return -1; // if that is not a container

			if (!nao.exists(father,null)) // id must be declared in father
				throw new AlreadyRegistredException("The father does not exist");

			String containerTable = container.getEntityName();
			String fatherTable = "_" + father.getEntityName();

			if (container.getOwner() != null) {
				set("INSERT INTO " + containerTable + "(name," + fatherTable
						+ ",owner) VALUES (?,?,?);",Statement.RETURN_GENERATED_KEYS);
				setString(1, container.getName());
				setInt(2, father.getId());
				setInt(3, container.getOwner().getId());
			} else {
				set("INSERT INTO " + containerTable + "(name," + fatherTable
						+ ") VALUES (?,?);",Statement.RETURN_GENERATED_KEYS);
				setString(1, container.getName());
				setInt(2, father.getId());
			}
		}

		if (!executeUpdate()) // if creation failed
			throw new NotFoundInDatabaseException("The relation inbetween "
					+ container.getEntityName() + ":" + container.getName()
					+ " and " + father.getEntityName() + ":" + father.getName()
					+ " doesn't seem to exist");
		else{
				getGeneratedKeys();
				int id = getInt("id"+container.getEntityName());
				return id;
		}
	}
}
