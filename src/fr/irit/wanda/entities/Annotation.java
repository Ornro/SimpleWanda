package fr.irit.wanda.entities;

import fr.irit.wanda.entities.LinkedEntity.WORKFLOW;




public class Annotation extends NamedEntity {
    private User owner = null;
    private NamedEntity related = null;
    private int view = -1;
    private String link = null; 
    private WORKFLOW workflow;
    
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
    public Annotation(int id, WORKFLOW workflow, NamedEntity related, String name) {
        super(id,"annotation",name);
        
        this.workflow = workflow;
        this.related = related;
    }
    
    public Annotation(WORKFLOW workflow, NamedEntity related, int view, String name, User owner) {
        super(-1,"annotation",name);
        this.workflow = workflow;
        this.related = related;
        this.name = name;
        this.owner = owner;
        this.view = view;
    }
    
    public Annotation(WORKFLOW workflow, NamedEntity related, int view, String name) {
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
    public NamedEntity getRelated() {
        return related;
    }

    /**
     * Set the value of related
     *
     * @param related new value of related
     */
    public void setRelated(NamedEntity related) {
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
