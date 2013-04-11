package fr.irit.wanda.entities;

import java.util.Collection;

/**
 * This object is used to represent a metadata. It id may be null if the object
 * is not in the database. It does not contain the value of the given meta for
 * any singular objet but only the informations on the metadata itself. It is
 * not linked to any other existing object.
 * 
 * @author Benjamin Babic
 */
public class Metadata extends NamedEntity{
	protected String description;
	protected boolean obligation;
	protected boolean privacy; 
	protected Collection<Entity> concerns = null;

	/**
	 * Constructor used by MetadataDAO for creating a metadata object from the
	 * database
	 * 
	 * @param id
	 *            database id for this object
	 * @param name
	 *            the name
	 * @param obligation
	 *            true if this meta is essential
	 * @param hoover
	 *            text displayed when mouse hoovers the metadata
	 * @param description
	 *            detailed description of what is this metadata used for
	 */
	public Metadata(int id, String name, boolean obligation, boolean privacy,
			String description) {
		super(id,"metadata",name);
		this.description = description;
		this.obligation = obligation;
		this.privacy = privacy;
	}

	/**
	 * Default constructor used to create a metadata object.
	 * 
	 * @param name
	 *            the name
	 * @param obligation
	 *            true if this meta is essential
	 * @param hoover
	 *            text displayed when mouse hoovers the metadata
	 * @param description
	 *            detailed description of what is this metadata used for
	 */
	public Metadata(String name, boolean obligation, boolean privacy,
			String description) {
		super("metadata",name);
		this.description = description;
		this.obligation = obligation;
		this.privacy = privacy;
	}

	/**
	 * @return the concerns
	 */
	public Collection<Entity> getConcerns() {
		return concerns;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the obligation
	 */
	public boolean isObligation() {
		return obligation;
	}

	/**
	 * @return the visibility
	 */
	public boolean isPrivate() {
		return privacy;
	}

	/**
	 * @param concerns
	 *            the concerns to set
	 */
	public void setConcerns(Collection<Entity> concerns) {
		this.concerns = concerns;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param obligation
	 *            the obligation to set
	 */
	public void setObligation(boolean obligation) {
		this.obligation = obligation;
	}

	/**
	 * @param visibility
	 *            the visibility to set
	 */
	public void setPrivacy(boolean visibility) {
		this.privacy = visibility;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Metadata [name=" + name + ", description=" + description
				+ ", obligation=" + obligation + "]";
	}
}
