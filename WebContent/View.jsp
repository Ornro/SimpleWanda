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
	NamedEntity ne = new NamedEntityAO().getName(Integer.parseInt(entityId), entityName);
%>	

<h3>Details</h3>
<div style="margin-bottom: 5px" class="description">
	"Vous venez de cliquer sur <strong>"<%=ne.getName()%>"</strong> (<%=ne.getEntityName()%>)
	"Voici la liste des informations auxquelles vous avez accès.
</div>
<br><br>
<div id="list2">
	<ol>
	<% 
		try {
			for (MetadataContent mc : remoteRequest.getMetadatasContent(ne)){
			%><li><p><em><%=mc.getName()%> : <%=mc.getContent()%></p></li><%
		}
		}catch(Exception e){
		%><div>Il n'y a pas de metadonnes auxquelles vous avez acces.</div><%
		}
	%>
	</ol>
</div>


