package fr.irit.wanda.entities;

import java.util.Collection;

public class VideoFile {
	private String link = null;
	private int id = -1;
	private Collection<MetadataContent> metadatas;
	
	public VideoFile(int id, String link, Collection<MetadataContent> metadatas){
		this.id = id;
		this.link = link;
		this.metadatas = metadatas;
	}
	
	public String getDisplayName(){
		String name = "";
		for (MetadataContent mc: metadatas){
			if (mc.getName().equals("Resolution"))
				name += mc.getContent()+"_";
			if (mc.getName().equals("Format"))
				name += mc.getContent();			
		}
		return name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<MetadataContent> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(Collection<MetadataContent> metadatas) {
		this.metadatas = metadatas;
	}
}
