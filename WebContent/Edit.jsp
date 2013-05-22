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

<% if (entityName.equals("site")){ %>
<div id="createForm" class="">
	<h3>�dition de site</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			Vous avez d�cid� d'�diter le site <% out.print(ne.getName()); %>.
			Toute modification de votre part sera enregistr�e en base de donn�es. 
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Edit" id="add_site" name="site">
		<input type="hidden" name="entity" value="site"> 
		<label for="name"><span>Nom</span></label> 
		<input type="text" name="name" value="<% out.print(ne.getName()); %>" autofocus required /> 
		<% out.print(remoteRequest.getMetadataFormEdit(new Entity (Integer.parseInt(entityId), entityName))); %>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider" />
		</p>
	</form>
</div>
<% }else if (entityName.equals("corpus")){ %>
<div id="createForm" class="">
	<h3>�dition de corpus</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			Vous avez d�cid� d'�diter le corpus <% out.print(ne.getName()); %>.
			Toute modification de votre part sera enregistr�e en base de donn�es. 
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Edit" id="add_corpus" name="corpus">
		<input type="hidden"  name="entity"  value="corpus">
		<label for="name"><span>Nom</span></label>
		<input name="name" type="text" size="40" value="<% out.print(ne.getName()); %>" autofocus required/>
		<% out.print(remoteRequest.getMetadataFormEdit(new Entity (entityName))); %>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form>
</div>
<%}else if (entityName.equals("session")){%>
<div id="createForm" class="">
	<h3>�dition de session</h3>
	<div style="margin-bottom: 5px" class="description">
			<p>
			Vous avez d�cid� d'�diter la session <% out.print(ne.getName()); %>.
			Toute modification de votre part sera enregistr�e en base de donn�es. 
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Edit" id="add_session" name="session">
		<input type="hidden"  name="entity"  value="session">
		<label for="name"><span>Nom</span></label>
		<input name="name" type="text" size="40" value="<% out.print(ne.getName()); %>" autofocus required/>
		<% out.print(remoteRequest.getMetadataFormEdit(new Entity (entityName))); %>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form><br>
</div>
<%}else if (entityName.equals("view")){%>
<div id="createForm" class="">
	<h3>�dition de vue</h3>
	<div style="margin-bottom: 5px" class="description">
				<p>
			Vous avez d�cid� d'�diter la vue <% out.print(ne.getName()); %>.
			Toute modification de votre part sera enregistr�e en base de donn�es. 
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Edit" id="add_view" name="view">
		<input type="hidden"  name="entity"  value="view">
		<label for="name"><span>Nom</span></label>
		<input name="name" type="text" size="40" value="<% out.print(ne.getName()); %>" autofocus required/>
		<% out.print(remoteRequest.getMetadataFormEdit(new Entity (entityName))); %>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form><br>
</div>
<%} else if (entityName.equals("video")){%>
<div id="createForm" class="">
	<h3>�dition de vid�o</h3>
	<div style="margin-bottom: 5px" class="description">
				<p>
			Vous avez d�cid� d'�diter la vid�o <% out.print(ne.getName()); %>.
			Toute modification de votre part sera enregistr�e en base de donn�es. 
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Edit" id="add_video" name="video"><br>
		<input type="hidden"  name="entity"  value="video">
		<label for="name_video"><span>Nom</span></label>
		<input name="name" type="text" value="<% out.print(ne.getName()); %>" autofocus required/>
		<br>
		<label for="privacy"><span>Privaut�</span></label>
		<select name="privacy">
		  <option value="0">Priv�e</option>
		  <option value="1">Restreinte</option>
		  <option value="2">Publique</option>
		</select>
		<% out.print(remoteRequest.getMetadataFormEdit(new Entity (entityName))); %>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form>
</div>
<%} else if (entityName.equals("annotation")){%>
<div id="createForm" class="">
	<h3>�dition d'annotations</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			Vous avez d�cid� d'�diter l'annotation <% out.print(ne.getName()); %>.
			Toute modification de votre part sera enregistr�e en base de donn�es. 
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Edit" id="add_annotation" name="annotation" enctype="multipart/form-data"><br>
		<input type="hidden"  name="entity"  value="annotation">
		<label for="name_video"><span>Nom</span></label>
		<input name="name" type="text" value="<% out.print(ne.getName()); %>" autofocus required/>
		<br>
		<label for="privacy"><span>Privaut�</span></label>
		<select name="privacy">
		  <option value="0">Priv�e</option>
		  <option value="1">Restrainte</option>
		  <option value="2">Publique</option>
		</select>
		<label for="annotationfile"><span>File</span></label>
		<input name="annotationfile" type="file" />
		<% out.print(remoteRequest.getMetadataFormEdit(new Entity (entityName))); %>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form>
</div>
<%}%>
