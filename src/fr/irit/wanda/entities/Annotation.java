package fr.irit.wanda.entities;




public class Annotation extends NamedEntity {
    private User owner = null;
    private Video related = null;
    private int view = -1;
    private String link = null;
    private WORKFLOW workflow;
    
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
     * Default constructor used to create an annotation object.
     * 
     * @param id
     *            the id
     * @param owner
     *            the owner
     * @param related
     *            the type of the annotation
     * @param link
     *            the link
     */   
    public Annotation(int id, WORKFLOW workflow, Video related, String name) {
        super(id,"annotation",name);
        
        this.workflow = workflow;
        this.related = related;
    }
    
    public Annotation(WORKFLOW workflow, Video related, int view, String name, User owner) {
        super(-1,"annotation",name);
        this.workflow = workflow;
        this.related = related;
        this.name = name;
        this.owner = owner;
        this.view = view;
    }
    
    public Annotation(WORKFLOW workflow, Video related, int view, String name) {
        super(-1,"annotation",name);
        this.workflow = workflow;
        this.related = related;
        this.name = name;
        this.view = view;
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
     * @param workflow new value of workflow
     */
    public void setWorkflow(WORKFLOW workflow) {
        this.workflow = workflow;
    }

    /**
     * Get the value of owner
     *
     * @return the value of owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Set the value of owner
     *
     * @param owner new value of owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Get the value of related
     *
     * @return the value of related
     */
    public Video getRelated() {
        return related;
    }

    /**
     * Set the value of related
     *
     * @param related new value of related
     */
    public void setRelated(Video related) {
        this.related = related;
    }

    /**
     * Get the value of link
     *
     * @return the value of link
     */
    public String getLink() {
        return link;
    }

    /**
     * Set the value of link
     *
     * @param link new value of link
     */
    public void setLink(String link) {
        this.link = link;
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the view
	 */
	public int getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(int view) {
		this.view = view;
	}  
	
}
