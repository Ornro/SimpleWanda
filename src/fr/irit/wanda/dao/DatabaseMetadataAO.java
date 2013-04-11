package fr.irit.wanda.dao;

import java.util.ArrayList;
import java.util.Collection;

public class DatabaseMetadataAO extends DAO {

	public DatabaseMetadataAO() {
		super();
	}

	/**
	 * Returns the name of the columns for the given table
	 * 
	 * @param table
	 *            the table
	 * @return the names
	 */
	private Collection<String> getColumsNames(String table) {
		ArrayList<String> result = new ArrayList<String>();
		set("SELECT column_name FROM information_schema.columns WHERE table_name = ?;");
		setString(1, table);
		if (!executeQuery())
			return result;
		do {
			result.add(getString("column_name"));
		} while (next());

		return result;
	}

	/**
	 * Returns all the table names from our database
	 * 
	 * @return the table names
	 */
	private Collection<String> getTableNames() {
		ArrayList<String> result = new ArrayList<String>();
		set("SELECT table_name FROM information_schema.tables WHERE table_schema='public';");
		if (!executeQuery())
			return result;
		do {
			result.add(getString("table_name"));
		} while (next());
		return result;
	}

	/**
	 * Returns the names of all containers
	 * 
	 * @return the names
	 */
	public Collection<String> getContainersNames() {
		ArrayList<String> result = new ArrayList<String>();
		set("SELECT table_name FROM containers");
		if (!executeQuery())
			return result;
		do {
			result.add(getString("table_name"));
		} while (next());
		return result;
	}

	/**
	 * Returns the name of the contents for a container
	 * 
	 * @param containerName
	 *            the name of the container
	 * @return the names
	 */
	public Collection<String> getContainerContentNames(String containerName) {
		ArrayList<String> result = new ArrayList<String>();
		for (String table : getTableNames()) {
			if (getColumsNames(table).contains("_".concat(containerName))) {
				result.add(table);
			}
		}
		return result;
	}

}
