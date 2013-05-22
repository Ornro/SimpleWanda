<%@ page import="fr.irit.wanda.entities.*"%>
<%@ page import="fr.irit.wanda.dao.*"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.io.*"%>
<%@ page import="fr.irit.wanda.service.IRequest" %>
<%@ page import="fr.irit.wanda.service.impl.RequestImpl" %>

<%
	String rawId = (String)request.getParameter("id");
	String entityName = rawId.split("_")[1];
	String entityId = rawId.split("_")[0];
	IRequest remoteRequest = new RequestImpl("benjamin.babic@hotmail.fr");
	out.print(remoteRequest.getMetadatasContent(Integer.parseInt(entityId), entityName));
%>	

