package fr.irit.wanda.entities;

public class LinkedEntity extends NamedEntity {
	private String link;
	private PRIVACY privacy;
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
	
	public enum PRIVACY{
		PRIVATE(0),RESTRICTED(1),PUBLIC(2);
		private int value;

        private PRIVACY(int value) {
                this.value = value;
        }
        
        public int getValue(){
        	return this.value;
        }
        
        public static PRIVACY fromInt(int i){
        	switch(i){
    		case 0:
    			return PRIVACY.PRIVATE;
    		case 1:
    			return PRIVACY.RESTRICTED;
    		case 2:
    			return PRIVACY.PUBLIC;
    		}
    		return PRIVACY.RESTRICTED;
        }
	}
	
	

	public LinkedEntity(int id, String entityName, String name) {
		super(id, entityName, name);

	}
	
	public LinkedEntity(String entityName, String name) {
		super(entityName, name);

	}

}
