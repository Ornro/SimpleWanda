package fr.irit.wanda.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import fr.irit.wanda.configuration.HirarchyConfiguration;
import fr.irit.wanda.entities.Entity;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class NamedEntityAO extends DAO {
	public NamedEntityAO() {
		super();
	}

	public NamedEntityAO(ResultSet rs) {
		super();
		this.rs = rs;
	}

	/**
	 * Returns all entities from a table
	 * 
	 * @param tableName
	 * @return collection of entities that are in the given table
	 */
	public Collection<NamedEntity> getAll(String tableName) {
		ArrayList<NamedEntity> namedEntities = new ArrayList<NamedEntity>();

		set("SELECT * FROM " + tableName + ";");
		if (!executeQuery()) return namedEntities;
		do {
			NamedEntity ne = new NamedEntity(getInt("id" + tableName),
					tableName, getString("name"));
			namedEntities.add(ne);
		} while (next());

		return namedEntities;
	}

	public NamedEntity getID(NamedEntity ne) {
		System.out.println(ne.getEntityName() + " " + ne.getName());
		set("SELECT * FROM " + ne.getEntityName() + " WHERE name=?;");
		setString(1, ne.getName());
		executeQuery();
		ne.setId(getInt("id" + ne.getEntityName()));
		return ne;
	}
	
	public NamedEntity getName(int id, String tableName) {
		set("SELECT * FROM " + tableName + " WHERE id=?;");
		setInt(1, id);
		executeQuery();
		return new NamedEntity(id,tableName,getString("Name"));
	}

	/**
	 * Changes the privacy setting of en entity. It should be or a metadata or a
	 * video or an annotation.
	 * 
	 * @param ne
	 *            the entity
	 * @param newValue
	 *            the new value to assign
	 * @return true if success
	 * @throws NotFoundInDatabaseException
	 */
	public boolean editPrivacy(NamedEntity ne, boolean newValue)
			throws NotFoundInDatabaseException {
		if (ne.getId() != -1) {
			set("UPDATE " + ne.getEntityName() + " SET privacy=? WHERE "
					+ ne.getTableId() + "=?");
			setBoolean(1, newValue);
			setInt(2, ne.getId());
		} else {
			set("UPDATE " + ne.getEntityName() + " SET privacy=? WHERE name=?");
			setBoolean(1, newValue);
			setString(2, ne.getName());
		}
		if (!executeUpdate())
			throw new NotFoundInDatabaseException("Entity " + ne.getName()
					+ " cannot be updated");
		return true;
	}

	/**
	 * Returns true if the given entity is recorded in the database
	 * 
	 * @param ne
	 *            the entity
	 * @return boolean
	 */
	public boolean exists(NamedEntity ne) {
		if (ne.getId() != -1)
			return exists(ne.getId(), ne.getEntityName());
		return exists(ne.getName(), ne.getEntityName());
	}

	/*
	 * Tells if the entity named by the given parameter exists
	 */
	private boolean exists(String name, String tableName) {
		set("SELECT * FROM " + tableName + " WHERE name=?;");
		setString(1, name);
		return executeQuery();
	}

	/*
	 * Tells if the entity identified by the given parameter exists
	 */
	private boolean exists(int id, String tableName) {
		set("SELECT * FROM " + tableName + " WHERE id" + tableName + "=?;");
		setInt(1, id);
		return executeQuery();
	}

	/**
	 * Return the father of the given son, null if there is no father.
	 * 
	 * @param son
	 *            the son
	 * @return the father
	 */
	public Entity getFather(Entity son) throws NotFoundInDatabaseException {
		HirarchyConfiguration HC = HirarchyConfiguration.getInstance();
		Hashtable<String, ArrayList<String>> hierarchy = HC.getHierarchy();
		Iterator<String> it = hierarchy.keySet().iterator();

		while (it.hasNext()) { // for each container
			String father = it.next();
			String fatherColumName = "_" + father;
			ArrayList<String> sons = hierarchy.get(father); // list it content
			if (sons.contains(son.getEntityName())) { // if our element is
														// content
				set("SELECT " + fatherColumName + " FROM "
						+ son.getEntityName() + " WHERE " + son.getTableId()
						+ "=?;");
				setInt(1, son.getId());
				if (!executeQuery()) {
					throw new NotFoundInDatabaseException(son.getEntityName()
							+ " " + son.getId() + " cannot be found");
				}
				int fatherId = getInt(fatherColumName);
				if (fatherId != -1)
					return new Entity(fatherId, father);
			}
		}
		return null;
	}

	/**
	 * Returns all the upper hierarchy starting with the given entity. E.G it
	 * the hierarchy is Site1-Corpus1-SubCorpus1-Session1 and the parameter
	 * Session1 it will return a collection containing SubCorpus1-Corpus1-Site1
	 * 
	 * @param root
	 *            the root element
	 * @return the upper hierarchy (may be empty)
	 * @throws NotFoundInDatabaseException
	 */
	public Collection<Entity> getUpperHierarchy(Entity root)
			throws NotFoundInDatabaseException {
		ArrayList<Entity> result = new ArrayList<Entity>();
		Entity father = null;
		do {
			father = getFather(root);
			root = father;
			result.add(father);
		} while (father != null);
		return result;
	}
}
