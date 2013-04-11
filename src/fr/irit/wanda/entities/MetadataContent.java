package fr.irit.wanda.entities;

/*
 * Convention utilisée la table liant au metadata à un nom de la forme 
 * "nom_de_l'entité"meta. Exemple videometa pour les meta des videos
 */
public class MetadataContent extends Metadata {

	private Entity concerned;
	private String content = "";

	public MetadataContent(Metadata m, Entity Concerned, String Content) {
		super(m.id, m.name, m.obligation, m.privacy, m.description);
		this.concerned = Concerned;
		this.setContent(Content);
	}
	
	public MetadataContent(Metadata m, Entity Concerned) {
		super(m.name, m.obligation, m.privacy, m.description);
		this.concerned = Concerned;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the concerned
	 */
	public Entity getConcerned() {
		return concerned;
	}

	/**
	 * @param concerned the concerned to set
	 */
	public void setConcerned(Entity concerned) {
		this.concerned = concerned;
	}

}
