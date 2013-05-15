package fr.irit.wanda.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Receptacle pour les champs redondants présents dans les servlet
 * 
 * @author Wanda Team
 *
 */
public abstract class Servlet extends HttpServlet {

	protected static final long serialVersionUID = 1L;
	
	
	public static String getString(HttpServletRequest request, String s){
		return (String)request.getParameter(s);	
	}
	
	public static Boolean getBoolean(HttpServletRequest request, String s){
		return Boolean.parseBoolean(request.getParameter(s));	
	}
	
	public static int getInt(HttpServletRequest request, String s){
		return Integer.parseInt(request.getParameter(s));	
	}	
}
