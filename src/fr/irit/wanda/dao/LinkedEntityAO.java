package fr.irit.wanda.dao;

import java.io.File;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import fr.irit.wanda.configuration.HirarchyConfiguration;
import fr.irit.wanda.entities.Entity;
import fr.irit.wanda.entities.LinkedEntity;
import fr.irit.wanda.entities.NamedEntity;
import fr.irit.wanda.entities.VideoFile;
import fr.irit.wanda.entities.LinkedEntity.PRIVACY;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class LinkedEntityAO extends NamedEntityAO {
	HirarchyConfiguration hc = HirarchyConfiguration.getInstance();
	
	public LinkedEntityAO() {
		super();
	}
	
	private int addLink(int entity_id, String entity_table_name){
		set("INSERT INTO links(entity_id,entity_table_name) VALUES (?,?);",Statement.RETURN_GENERATED_KEYS);
		setInt(1,entity_id);
		setString(2,entity_table_name);
		
		if (executeUpdate()){
			getGeneratedKeys();
			return getInt("idlinks");
		}
		return -1;
	}

	public int addVideoLink(int videoId, String link){
		set("INSERT INTO links(entity_id,entity_table_name,link) VALUES (?,?,?);",Statement.RETURN_GENERATED_KEYS);
		setInt(1,videoId);
		setString(2,"video");
		setString(3,link);
		
		if (executeUpdate()){
			getGeneratedKeys();
			return getInt("idlinks");
		}
		return -1;
	}

	// TODO create job
	public boolean createJob(){
		return false;
	}

	public int createLinkedEntity(final LinkedEntity linkedEntity,
			final NamedEntity father) throws AlreadyRegistredException, NotFoundInDatabaseException {
		if (!isLinkedEntity(linkedEntity))
			return -1;
		
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
		
		set("INSERT INTO " + thisTable + "(name,"+fatherTable+",privacy,workflow,owner) VALUES (?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
		setString(1,linkedEntity.getName());
		setInt(2,father.getId());
		setInt(3,linkedEntity.getPrivacy().getValue());
		setInt(4,linkedEntity.getWorkflow().getValue());
		setInt(5,linkedEntity.getOwner().getId());
		
		if (executeUpdate()){
			getGeneratedKeys();
			int id = getInt("id"+thisTable);
			return addLink(id, thisTable);
		}
		return -1;
	}
	
	public void tempFileMeta(String res, String format, int id){
		set("INSERT INTO linksmeta(links,metadata,content) VALUES (?,?,?)");
		setInt(1,id);
		setInt(2,2);
		setString(3,res);
		executeUpdate();
		
		set("INSERT INTO linksmeta(links,metadata,content) VALUES (?,?,?)");
		setInt(1,id);
		setInt(2,1);
		setString(3,format);
		executeUpdate();
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
	
	public Collection<VideoFile> getLinks(int entity_id) throws NotFoundInDatabaseException{
		ArrayList<VideoFile> results = new ArrayList<VideoFile>();
		set("SELECT * FROM links WHERE entity_id=?;");
		setInt(1,entity_id);
		if (executeQuery()){
			do{
				int id = getInt("idlinks");
				String link = getString("link");
				results.add(new VideoFile(id,link,new MetadataAO().getMetadatas(new Entity(id,"links"))));
			}while (next());
		}
		return results;
	}
	
	public String getSingleLink(int entity_id){
		set("SELECT link FROM links WHERE entity_id=?;");
		setInt(1,entity_id);
		executeQuery();
		return getString("link");
	}
	
	public File getVideoFile(int jobid, User caller){
		set("SELECT link FROM job,links WHERE idjob=? AND wuser=? AND links.idlinks=job.idlink AND validity<=?;");
		setInt(1,jobid);
		setInt(2,caller.getId());
		setTimestamp(3,new Timestamp(Calendar.getInstance().getTimeInMillis()));
		executeQuery();
		
		return new File(getString("link"));
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

	public boolean setLink(int id, String link){
		set("UPDATE links SET link=? WHERE idlinks=?;");
		setString(1,link);
		setInt(2,id);
		return executeUpdate();
	}
}
