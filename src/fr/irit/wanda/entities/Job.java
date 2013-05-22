package fr.irit.wanda.entities;

import java.util.Date;

public class Job {
	private long version; 
	private int id;
	
	private int a3;
	private int userID; 
	private int video;
	
	private Date creation; 
	
	public Job(){
		version = 0; 
		id = -1; 
		a3 = -1; 
		userID = -1; 
		video = -1; 
		creation = new Date();
	}
	
	public Job(int pid, int userId){
		id = pid; 
		userID = userId;
	}
	
	public Job clone(){
		Job clone = new Job();
		
		clone.setId(id);
		clone.setVersion(version); 
		
		clone.setA3(a3); 
		clone.setUserID(userID); 
		clone.setVideo(video);
		
		clone.setCreation((Date) creation.clone());
		
		return clone;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getA3() {
		return a3;
	}

	public void setA3(int a3) {
		this.a3 = a3;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getVideo() {
		return video;
	}

	public void setVideo(int video) {
		this.video = video;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}
}
