package fr.irit.wanda.entities;

import java.util.Collection;

public class Entity {


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (entityName == null) {
			if (other.entityName != null)
				return false;
		} else if (!entityName.equals(other.entityName))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	protected String entityName;
	protected int id;
	protected Collection<Metadata> meta = null; // for supressing LinkedMetadata
	
	public Entity(int id,String name){
		this.id=id;
		this.entityName=name;
	}
	
	public Entity(String name){
		this.id=-1;
		this.entityName=name;
	}
	
	/**
	 * @return the name
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName
	 *            the name to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMetaTable() {
		return this.entityName + "meta";
	}
	
	public String getAccessTable() {
		return "user"+this.entityName+"access";
	}
	
	public String getTableId(){
		return "id"+this.entityName;
	}
}
