package fr.irit.wanda.dao;

import fr.irit.wanda.configuration.HirarchyConfiguration;
import fr.irit.wanda.entities.Entity;
import fr.irit.wanda.entities.LinkedEntity;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.LinkedEntity.PRIVACY;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class LinkedEntityAO extends NamedEntityAO {
	HirarchyConfiguration hc = HirarchyConfiguration.getInstance();
	
	public LinkedEntityAO() {
		super();
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
	public boolean editPrivacy(NamedEntity ne, PRIVACY newValue)
			throws NotFoundInDatabaseException {
		if (ne.getId() != -1) {
			set("UPDATE " + ne.getEntityName() + " SET privacy=? WHERE "
					+ ne.getTableId() + "=?");
			setInt(1, newValue.getValue());
			setInt(2, ne.getId());
		} else {
			set("UPDATE " + ne.getEntityName() + " SET privacy=? WHERE name=?");
			setInt(1, newValue.getValue());
			setString(2, ne.getName());
		}
		if (!executeUpdate())
			throw new NotFoundInDatabaseException("Entity " + ne.getName()
					+ " cannot be updated");
		return true;
	}
	
	/**
	 * Tells if an entity is a container
	 * 
	 * @param entity
	 *            the entity
	 * @return true if it is false if not
	 */
	public final boolean isLinkedEntity(final Entity entity) {
		return hc.getLinkedEntities().contains(entity.getEntityName());
	}
	
	public boolean createLinkedEntity(final LinkedEntity linkedEntity,
			final NamedEntity father) throws AlreadyRegistredException, NotFoundInDatabaseException {
		if (!isLinkedEntity(linkedEntity))
			return false;
		
		NamedEntityAO nao = new NamedEntityAO();
		ContainerAO cao = new ContainerAO();
		UserAO uao = new UserAO();
		
		if (nao.exists(linkedEntity,father)) // if the entity already exists
			throw new AlreadyRegistredException(
					"The linked entity to create already exists");
		
		if (!cao.isContainer(father))
			throw new NotFoundInDatabaseException(
					"The father is not a container");
			
		if (!nao.exists(father,null))
			throw new NotFoundInDatabaseException(
					"The father does not exist");
		
		if (!uao.exists(linkedEntity.getOwner()))
			throw new NotFoundInDatabaseException(
					"The owner does not exist");
				
		String thisTable = linkedEntity.getEntityName();
		String fatherTable = "_" + father.getEntityName();
		
		set("INSERT INTO " + thisTable + "(name,"+fatherTable+",privacy,workflow,owner) VALUES (?,?,?,?,?);");
		setString(1,linkedEntity.getName());
		setInt(2,father.getId());
		setInt(3,linkedEntity.getPrivacy().getValue());
		setInt(4,linkedEntity.getWorkflow().getValue());
		setInt(5,linkedEntity.getOwner().getId());
		return executeQuery();
	}
}
