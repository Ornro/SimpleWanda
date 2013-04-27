package fr.irit.wanda.entities;

public class NamedEntity extends Entity {

	protected String name = null;

	public NamedEntity(String entityName, String name) {
		super(entityName);
		this.name = name;
	}

	public NamedEntity(int id, String entityName, String name) {
		super(id, entityName);
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamedEntity other = (NamedEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (id != other.id && id != -1 && other.id != -1)
			return false;
		return true;
	}
	

}
