package fr.irit.wanda.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import fr.irit.wanda.entities.A3;
import fr.irit.wanda.entities.Job;
import fr.irit.wanda.entities.User;
import fr.irit.wanda.exception.AlreadyRegistredException;
import fr.irit.wanda.exception.NotFoundInDatabaseException;

public class JobAO extends DAO{
	public JobAO() {
		super();
	}

	public JobAO(ResultSet rs) {
		super();
		this.rs = rs;
	}
	
	/**
	 * Adds a Job to the database if it does not already exists. 
	 * 
	 * @throws AlreadyRegistredException
	 *             if the Job is already registered in database
	 *          
	 * @return database id of the created object or -1 if an error occured
	 */
	public int addJob(Job job) throws AlreadyRegistredException {
		if (exists(job)) // If the annotation already exists: error
			throw new AlreadyRegistredException("Job " + job.getId()
					+ " already is registered in database.");

		set("INSERT INTO job(idlink, validity, wuser) VALUES (?,?,?,?);",
				Statement.RETURN_GENERATED_KEYS); // Retrieve the generated id
		LinkedEntityAO lao = new LinkedEntityAO();
		setInt(1, lao.getLinkId(job.getVideo()));
		setTimestamp(2, new Timestamp(job.getCreation().getTime()));
		setInt(3,job.getUserID());
		executeUpdate();
		getGeneratedKeys();
		job.setId(getInt("idjob"));
		return job.getId();
	}
	
	/**
	 * Returns true if the given job is recorded in the database
	 * 
	 * @param j
	 *            job
	 * @return boolean
	 */
	public boolean exists(Job j) {
		if (j.getId() != -1)
			return exists(j.getId());
		else
			return false;
	}
	
	/**
	 * Returns true if the given job id is recorded in the database
	 * 
	 * @param id
	 *            the id
	 * @return boolean
	 */
	public boolean exists(int id) {
		set("SELECT idjob FROM job WHERE idjob=?;");
		setInt(1, id);
		return executeQuery();
	}
	
	/**
	 * Returns the job object stored in the database with the given id
	 * 
	 * @param id
	 *            the id of the job you are looking for
	 * @return Job object, may be null.
	 */
	public Job getJob(int id) throws NotFoundInDatabaseException {
		set("SELECT * FROM job WHERE idjob=?;");
		setInt(1, id);
		executeQuery();
		Job j = extract();
		if (j == null)
			throw new NotFoundInDatabaseException("Job " + id
					+ " appears to be inexistant.");
		return j;
	}
	
	protected Job extract() {
		return new Job(getInt("idjob"),getInt("wuser"));
	}
	
	public boolean deleteJob(Job j) {
		set("DELETE FROM job WHERE idjob=?;");
		setInt(1, j.getId());
		return executeUpdate();
	}

}
