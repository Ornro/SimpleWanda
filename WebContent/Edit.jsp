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
    Entity e = new Entity(Integer.parseInt(entityId), entityName);
%>


<div id="createForm">
    <h3>�dition de <%=ne.getEntityName()%></h3>
    <div style="margin-bottom: 5px" class="description">
        <p>
            Vous avez d�cid� d'�diter le <%=ne.getEntityName()%>  <%=ne.getName()%> .
            Toute modification de votre part sera enregistr�e en base de donn�es. 
        </p>
    </div>
    <div class="space"></div>
    <form class="form" method="post" action="Edit" name="<%=ne.getEntityName()%>">
        <input type="hidden" name="entity" value="<%=ne.getEntityName()%>"> 
        <input type="hidden"  name="entityId"  value="<%=entityId%>">
        <input type="hidden"  name="entityName"  value="<%=entityName%>">
        <label for="name"><span>Nom</span></label>
        <input type="text" name="name" value="<%=ne.getName()%>" autofocus required /> 
		
        <% 
		try {
			for (Metadata m : remoteRequest.getMetadatas(e)){
				try{
				MetadataContent mc = remoteRequest.getMetadataContent(e,m);
				%>
				<label for="<%=m.getName()%>"><span><%=m.getName()%><% if(m.isObligation()){%>*<% }%></span></label>
				<input name="<%=m.getName()%>" type="text" value="<%=mc.getContent()%>" <% if(m.isObligation()){%>required<% }%>/>
				<%
				} catch (Exception e2){
				%>
				<label for="<%=m.getName()%>"><span><%=m.getName()%><% if(m.isObligation()){%>*<% }%></span></label>
				<input name="<%=m.getName()%>" type="text" placeholder="<%=m.getDescription()%>" <% if(m.isObligation()){%>required<% }%>/>
				<%
				}
			}
		}catch(Exception e1){
		%><br><div>Il n'y a pas de m�tadonn�es auxquelles vous avez acc�s.</div><%
		}
		%>
        <p class="validate">
            <input class="validate_button" name="validate" type="submit" size="40" value="Valider" />
        </p>
    </form>
</div>