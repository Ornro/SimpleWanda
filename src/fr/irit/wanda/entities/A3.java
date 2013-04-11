package fr.irit.wanda.entities;

public class A3 extends NamedEntity {

	private String uri;
	private String description;
	
	public A3(int id, String name, String uri, String description) {
		super(id, "a3", name);
		this.uri = uri;
		this.description = description;
	}
	
	public A3(String name, String uri, String description) {
		super ("a3",name);
		this.uri = uri;
		this.description = description;
	}
		
	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}
	
	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
