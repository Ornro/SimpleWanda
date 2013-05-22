package fr.irit.wanda.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import fr.irit.wanda.entities.Entity;
import fr.irit.wanda.entities.Metadata;
import fr.irit.wanda.entities.MetadataContent;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class MetadataAO extends DAO {

	public MetadataAO() {
		super();
	}

	/**
	 * Adds concerned entities to a Metadata
	 * 
	 * @param c
	 *            collection of entities's to add
	 * @param m
	 *            metadata
	 */
	public void addConcerns(Collection<Entity> c, Metadata m) {
		for (Entity e : c) {
			addConcerns(e, m);
		}
	}

	/**
	 * Adds concerned entities to a Metadata
	 * 
	 * @param e
	 *            a single entity to add
	 * @param m
	 *            metadata
	 */
	public void addConcerns(Entity e, Metadata m) {
		set("INSERT INTO metaconcerns(idmetadata,concerns) VALUES (?,?);");
		setInt(1, m.getId());
		setString(2, e.getEntityName());
		executeUpdate();
	}

	/**
	 * Adds a metadata content;
	 * 
	 * @param content
	 *            the metadata content representation
	 * @return true if success
	 * @throws AlreadyRegistredException
	 */
	public boolean addContent(MetadataContent content)
			throws AlreadyRegistredException {
		Entity concerned = content.getConcerned();

		set("INSERT INTO " + concerned.getMetaTable() + " ("
				+ concerned.getEntityName()
				+ ",metadata,content) VALUES (?,?,?);");
		setInt(1, concerned.getId());
		setInt(2, content.getId());
		setString(3, content.getContent());
		if (!executeUpdate())
			throw new AlreadyRegistredException(
					"Either the metadata does not concern the entity or they are already bound by a value");
		return true;
	}

	/**
	 * Adds a metadata to the database if it does not already exists. Name is
	 * unique. Name, description, obligation, privacy must be given.
	 * 
	 * @throws AlreadyRegistredException
	 *             if the metadata is already registered in database
	 * @param m
	 *            fr.irit.wanda.entities.Metadata the metadata to add
	 * @return database id of the created object or -1 if an error occured
	 */
	public int addMetadata(Metadata m) throws AlreadyRegistredException {
		if (exists(m)) // If the metadata already exists: error
			throw new AlreadyRegistredException("Metadata named " + m.getName()
					+ " already is registered in daabase.");

		set("INSERT INTO metadata(name, description, obligation, privacy) VALUES (?,?,?,?);",
				Statement.RETURN_GENERATED_KEYS); // Retrieve the generated id
		setString(1, m.getName());
		setString(2, m.getDescription());
		setBoolean(3, m.isObligation());
		setBoolean(4, m.isPrivate());
		executeUpdate();
		getGeneratedKeys();
		m.setId(getInt("idmetadata"));

		if (m.getConcerns() != null)
			addConcerns(m.getConcerns(), m);
		return m.getId();
	}

	/**
	 * Returns true if the given metadata is recorded in the database
	 * 
	 * @param m
	 *            Metadata
	 * @return boolean
	 * @see fr.irit.wanda.dao.NamedEntityAO
	 */
	public boolean exists(Metadata m) {
		return new NamedEntityAO().exists(m,null);
	}

	/*
	 * Extracts a metadata object from the ResultSet containing a full row of
	 * the metadata table. Object returned can be null.
	 */
	protected Metadata extract() {
		return new Metadata(getInt("idmetadata"), getString("name"),
				getBoolean("obligation"), getBoolean("privacy"),
				getString("description"));
	}

	/**
	 * Returns the metadata concerned entities names
	 * 
	 * @param m
	 *            the metadata
	 * @return collection of entities
	 * @throws NotFoundInDatabaseException
	 *             if the metadata has no concerns
	 */
	public Collection<Entity> getConcernedEntities(Metadata m)
			throws NotFoundInDatabaseException {
		Collection<Entity> c = new ArrayList<Entity>();
		set("SELECT concerns FROM metaconcerns WHERE idmetadata=?;");
		setInt(1, m.getId());
		if (!executeQuery())
			throw new NotFoundInDatabaseException(m.getName()
					+ " has no concerns");
		do {
			c.add(new Entity(getString("concerns")));
		} while (next());

		return c;
	}

	/**
	 * Returns the metadata that are concerning the entity
	 * 
	 * @param e
	 *            the entity
	 * @return collection of metadata
	 * @throws NotFoundInDatabaseException
	 *             if the entity has no metadata
	 */
	public Collection<Metadata> getConcernedMetadata(Entity e)
			throws NotFoundInDatabaseException {
		Collection<Metadata> c = new ArrayList<Metadata>();

		set("SELECT metadata.* FROM metaconcerns, metadata "
				+ "WHERE metaconcerns.concerns=? "
				+ "AND metaconcerns.idmetadata = metadata.idmetadata ORDER BY obligation DESC;");
		setString(1, e.getEntityName());
		if (!executeQuery())
			throw new NotFoundInDatabaseException(e.getEntityName()
					+ " is not concerned");

		do {
			c.add(extract());
		} while (next());

		return c;
	}

	/**
	 * Returns the metadata object stored in the databese with the given name
	 * 
	 * @param name
	 *            the name of the metadata you are looking fore
	 * @throws NotFoundInDatabaseException
	 *             when the metadata specified by given name does not exist.
	 * @return Metadata object, may be null when unexpected error occurs.
	 */
	public Metadata getMetadata(String name) throws NotFoundInDatabaseException {
		set("SELECT * FROM metadata WHERE name=?;");
		setString(1, name);
		if (!executeQuery()) {
			throw new NotFoundInDatabaseException("Metadata " + name
					+ " appears to be inexistant.");
		}
		return extract();
	}

	/**
	 * Returns the metadata object stored in the databese with the given name
	 * 
	 * @param entityName
	 *            the name of the metadata you are looking fore
	 * @return Metadata object, may be null.
	 */
	public Metadata getMetadata(int id) throws NotFoundInDatabaseException {
		set("SELECT * FROM metadata WHERE idmetadata=?;");
		setInt(1, id);
		if (!executeQuery())
			throw new NotFoundInDatabaseException("Metadata " + id
					+ " appears to be inexistant.");

		Metadata m = extract();

		return m;
	}

	/**
	 * Create the representation of a metadata content for the given entity and
	 * metadata
	 * 
	 * @param e
	 *            the entity
	 * @param m
	 *            the metadata
	 * @return the content of the metadata in a MetadataContent object
	 */
	public MetadataContent getMetadataContent(Entity e, Metadata m)
			throws NotFoundInDatabaseException {
		MetadataContent lm = new MetadataContent(m, e);

		set("SELECT * FROM " + e.getMetaTable() + " WHERE metadata = ?;");
		setInt(1, lm.getId());

		if (!executeQuery()){
			throw new NotFoundInDatabaseException(
					"There is no metadata called " + m.getName() + " in table "
							+ e.getEntityName());
		
		}
		lm.setContent(getString("content"));
		return lm;
	}

	/**
	 * Returns the content of all metadata that concerns the given entity
	 * 
	 * @param e
	 *            the entity
	 * @return Collection of metadata contents
	 * @throws NotFoundInDatabaseException
	 */
	public Collection<MetadataContent> getMetadatas(Entity e)
			throws NotFoundInDatabaseException {
		ArrayList<MetadataContent> lmc = new ArrayList<MetadataContent>();
		String linkingTable = e.getMetaTable();
		String entityName = e.getEntityName();
		set("SELECT " + linkingTable + ".content, metadata.* "
				+ "FROM metaconcerns, metadata, " + linkingTable
				+ " WHERE metaconcerns.concerns=? "
				+ "AND metaconcerns.idmetadata=metadata.idmetadata " + "AND "
				+ linkingTable + ".metadata=metaconcerns.idmetadata " + "AND "
				+ linkingTable + "." + entityName + "=?;");

		setString(1, entityName);
		setInt(2, e.getId());
		if (!executeQuery())
			throw new NotFoundInDatabaseException(
					"The entity does not seems to have any metadata content filled.");
		do {
			lmc.add(new MetadataContent(extract(), e,getString("content")));
		} while (next());

		return lmc;
	}
}
