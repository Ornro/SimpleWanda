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


<div id="createForm">
    <h3>Édition de site</h3>
    <div style="margin-bottom: 5px" class="description">
        <p>
            Vous avez décidé d'éditer le <%=ne.getEntityName()%>  <%=ne.getName()%> .
            Toute modification de votre part sera enregistrée en base de données. 
        </p>
    </div>
    <div class="space"></div>
    <form class="form" method="post" action="Edit" name="<%=ne.getEntityName()%>">
        <input type="hidden" name="entity" value="<%=ne.getEntityName()%>"> 
        <input type="hidden"  name="entityId"  value="<%=entityId%>">
        <input type="hidden"  name="entityName"  value="<%=entityName%>">
        <label for="name"><span>Nom</span></label>
        <input type="text" name="name" value="<% out.print(ne.getName()); %>" autofocus required /> 
        <% out.print(remoteRequest.getMetadataFormEdit(new Entity (Integer.parseInt(entityId), entityName))); %>
        <p class="validate">
            <input class="validate_button" name="validate" type="submit" size="40" value="Valider" />
        </p>
    </form>
</div>