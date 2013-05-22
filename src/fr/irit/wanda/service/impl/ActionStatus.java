package fr.irit.wanda.service.impl;

public class ActionStatus{
	private String action;
	private int target; 
	private String status;
	private Object result; // We should just use toString. 
	
	public ActionStatus(String action, int target, String status) {
		this.action = action;
		this.target = target;
		this.status = status;
		result="";
	}
	
	public ActionStatus(String action, String status) {
		this.action = action;
		this.status = status;
		result="";
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public void setTarget(int target) {
		this.target = target;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAction() {
		return action;
	}
	
	public int getTarget() {
		return target;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
	
	public Object getResult() {
		return result;
	}
}
