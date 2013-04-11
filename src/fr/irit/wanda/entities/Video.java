package fr.irit.wanda.entities;

import java.util.Collection;
import java.util.HashMap;


public class Video extends NamedEntity {
	private WORKFLOW workflow = null;
	private int view = -1;
	private User owner = null;
	private Collection<Annotation> annotations = null;
	private HashMap<String, HashMap<String, String>> concreteVideo = null;
	
	 public enum WORKFLOW{
			VALID(0),INVALID(1),WAITING(2),SEEN(3);
			private int value;

	        private WORKFLOW(int value) {
	                this.value = value;
	        }
	        
	        public int getValue(){
	        	return this.value;
	        }
	        
	        public static WORKFLOW fromInt(int i){
	        	switch(i){
	    		case 0: // is currently validated by manager
	    			return WORKFLOW.VALID;
	    		case 1: // was validated by manager but isn't anymore
	    			return WORKFLOW.INVALID;
	    		case 2: // waiting for manager approbation
	    			return WORKFLOW.WAITING;
	    		case 3: // seen but not validated yet
	    			return WORKFLOW.SEEN;
	    		}
	    		return WORKFLOW.WAITING;
	        }
		}

	/**
	 * Default constructor used to create a video object.
	 * 
	 * @param id
	 *            the id
	 * @param workflow
	 *            the workflow
	 * @param view
	 *            the view
	 * @param wandaUser
	 *            the wanda user
	 */
	public Video(int id, WORKFLOW workflow, int view, String name) {
		super(id, "video", name);

		this.workflow = workflow;
		this.view = view;
	}

	public Video(WORKFLOW workflow, int view, String name) {
		super(-1, "video", name);

		this.workflow = workflow;
		this.view = view;
	}
	
	public Video(WORKFLOW workflow, int view, String name, User owner) {
		super(-1, "video", name);

		this.workflow = workflow;
		this.view = view;
		this.owner = owner;
	}

	public void makeConcrete(
			HashMap<String, HashMap<String, String>> concreteVideo) {
		this.concreteVideo = concreteVideo;
	}

	/**
	 * Get the value of workflow
	 * 
	 * @return the value of workflow
	 */
	public WORKFLOW getWorkflow() {
		return workflow;
	}

	/**
	 * Set the value of workflow
	 * 
	 * @param workflow
	 *            new value of workflow
	 */
	public void setWorkflow(WORKFLOW workflow) {
		this.workflow = workflow;
	}

	/**
	 * Get the value of view
	 * 
	 * @return the value of view
	 */
	public int getView() {
		return view;
	}

	/**
	 * Set the value of view
	 * 
	 * @param view
	 *            new value of view
	 */
	public void setView(int view) {
		this.view = view;
	}

	/**
	 * Get the value of wandaUser
	 * 
	 * @return the value of wandaUser
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Set the value of wandaUser
	 * 
	 * @param wandaUser
	 *            new value of wandaUser
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the annotations
	 */
	public Collection<Annotation> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations
	 *            the annotations to set
	 */
	public void setAnnotations(Collection<Annotation> annotations) {
		this.annotations = annotations;
	}

	/**
	 * @return the concreteVideo
	 */
	public HashMap<String, HashMap<String, String>> getConcreteVideo() {
		return concreteVideo;
	}

	/**
	 * @param concreteVideo
	 *            the concreteVideo to set
	 */
	public void setConcreteVideo(
			HashMap<String, HashMap<String, String>> concreteVideo) {
		this.concreteVideo = concreteVideo;
	}

}
