<%@ page import="fr.irit.wanda.entities.*"%>
<%@ page import="fr.irit.wanda.dao.*"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.io.*"%>
<%@ page import="fr.irit.wanda.service.IRequest" %>
<%@ page import="fr.irit.wanda.service.impl.RequestImpl" %>

<%
	String rawId = (String)request.getParameter("id");
	String fatherEntityName = rawId.split("_")[2];
	String entityName = rawId.split("_")[1];
	String fatherId = rawId.split("_")[0]; 
	IRequest remoteRequest = new RequestImpl("benjamin.babic@hotmail.fr");
	Entity e = new Entity(entityName);

%>

<% if (entityName.equals("site")){ %>
<div id="createForm" class="">
	<h3>Création de site</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			Dans cette partie vous avez la possibilité de créer des sites. Seul l'administrateur du 
			logiciel peut en créer. Pour rappel, un site est un lieu de travail, une communauté, 
			regroupement de personnes dans un lieux géographique.<br><br> 
			Vous devez simplement renseigner le nom du site et les métadonnées qui lui sont rattachées.	
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Create" id="add_site" name="site">
		<input type="hidden" name="entity" value="site"> 
		<label for="name"><span>Nom</span></label> 
		<input type="text" name="name" placeholder="Nom du site" autofocus required /> 
		 <% 
		try {
			for (Metadata m : remoteRequest.getMetadatas(e)){
				%>
				<label for="<%=m.getName()%>"><span><%=m.getName()%><% if(m.isObligation()){%>*<% }%></span></label>
				<input name="<%=m.getName()%>" type="text" placeholder="<%=m.getDescription()%>" <% if(m.isObligation()){%>required<% }%>/>
				<%
			}
		}catch(Exception e1){
		%><br><div>Il n'y a pas de métadonnées auxquelles vous avez accès.</div><%
		}
		%>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider" />
		</p>
	</form>
</div>

<% }else if (entityName.equals("corpus")){ %>
<div id="createForm" class="">
	<h3>Création de corpus</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			Dans cette partie vous avez la possibilité de créer des corpus. 
			Pour rappel, un corpus est un thème abordé par un site.<br><br> 
			Vous devez simplement renseigner le nom du corpus et les métadonnées qui lui sont rattachées.	
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Create" id="add_corpus" name="corpus">
		<input type="hidden"  name="entity"  value="corpus">
		<input type="hidden"  name="fatherId"  value="<%=fatherId%>">
		<input type="hidden"  name="fatherEntityName"  value="<%=fatherEntityName%>">
		<label for="name"><span>Nom</span></label>
		<input name="name" type="text" size="40" placeholder="Nom du corpus" autofocus required/>
		 <% 
		try {
			for (Metadata m : remoteRequest.getMetadatas(e)){
				%>
				<label for="<%=m.getName()%>"><span><%=m.getName()%><% if(m.isObligation()){%>*<% }%></span></label>
				<input name="<%=m.getName()%>" type="text" placeholder="<%=m.getDescription()%>" <% if(m.isObligation()){%>required<% }%>/>
				<%
			}
		}catch(Exception e1){
		%><br><div>Il n'y a pas de métadonnées auxquelles vous avez accès.</div><%
		}
		%>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form>
</div>
<%}else if (entityName.equals("session")){%>
<div id="createForm" class="">
	<h3>Création de session</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			Dans cette partie vous avez la possibilité de créer des session. 
			Pour rappel, une session correspond à un tournage.<br><br> 
			Vous devez simplement renseigner le nom de la session et les métadonnées qui lui sont rattachées.	
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Create" id="add_session" name="session">
		<input type="hidden"  name="entity"  value="session">
		<input type="hidden"  name="fatherId"  value="<%=fatherId%>">
		<input type="hidden"  name="fatherEntityName"  value="<%=fatherEntityName%>">
		<label for="name"><span>Nom</span></label>
		<input name="name" type="text" size="40" placeholder="Nom de la session" autofocus required/>
		 <% 
		try {
			for (Metadata m : remoteRequest.getMetadatas(e)){
				%>
				<label for="<%=m.getName()%>"><span><%=m.getName()%><% if(m.isObligation()){%>*<% }%></span></label>
				<input name="<%=m.getName()%>" type="text" placeholder="<%=m.getDescription()%>" <% if(m.isObligation()){%>required<% }%>/>
				<%
			}
		}catch(Exception e1){
		%><br><div>Il n'y a pas de métadonnées auxquelles vous avez accès.</div><%
		}
		%>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form><br>
</div>
<%}else if (entityName.equals("view")){%>
<div id="createForm" class="">
	<h3>Création de vue</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			Dans cette partie vous avez la possibilité de créer des vues. 
			Pour rappel, une vue est une prise de vue, l'angle d'une caméra(Par exemple : de face, de coté, de profil)<br><br> 
			Vous devez simplement renseigner le nom de la vue et les métadonnées qui lui sont rattachées.	
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Create" id="add_view" name="view">
		<input type="hidden"  name="entity"  value="view">
		<input type="hidden"  name="fatherId"  value="<%=fatherId%>">
		<input type="hidden"  name="fatherEntityName"  value="<%=fatherEntityName%>">
		<label for="name"><span>Nom</span></label>
		<input name="name" type="text" size="40" placeholder="Nom de la vue" autofocus required/>
		 <% 
		try {
			for (Metadata m : remoteRequest.getMetadatas(e)){
				%>
				<label for="<%=m.getName()%>"><span><%=m.getName()%><% if(m.isObligation()){%>*<% }%></span></label>
				<input name="<%=m.getName()%>" type="text" placeholder="<%=m.getDescription()%>" <% if(m.isObligation()){%>required<% }%>/>
				<%
			}
		}catch(Exception e1){
		%><br><div>Il n'y a pas de métadonnées auxquelles vous avez accès.</div><%
		}
		%>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form><br>
</div>
<%} else if (entityName.equals("video")){%>
<div id="createForm" class="">
	<h3>Création de vidéo</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			Dans cette partie vous avez la possibilité de créer des vidéos. 
			Pour rappel, une vidéo peut avoir différentes résolutions et différents formats.
			Elle possède un accès publique, restreint ou privé.
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Create" id="add_video" name="video" enctype="multipart/form-data"><br>
		<input type="hidden"  name="entity"  value="video">
		<input type="hidden"  name="fatherId"  value="<%=fatherId%>">
		<input type="hidden"  name="fatherEntityName"  value="<%=fatherEntityName%>">
		<label for="name_video"><span>Nom</span></label>
		<input name="name" type="text" placeholder="Nom de la vidéo" autofocus required/>
		<br>
		<label for="privacy"><span>Privauté</span></label>
		<select name="privacy">
		  <option value="0">Privée</option>
		  <option value="1">Restreinte</option>
		  <option value="2">Publique</option>
		</select>
		<div>
		<label for="videofile"><span>Fichier</span></label>
			<input style="margin-right:5px;" name="videofile" type="file" />
			<input class="video_file" name="format_video" type="text" placeholder="Format" title="Format" required/>
			<input class="video_file" name="resolution_video" type="text" placeholder="Qualité" title="Résolution" required/>
		</div>
		 <% 
		try {
			for (Metadata m : remoteRequest.getMetadatas(e)){
				%>
				<label for="<%=m.getName()%>"><span><%=m.getName()%><% if(m.isObligation()){%>*<% }%></span></label>
				<input name="<%=m.getName()%>" type="text" placeholder="<%=m.getDescription()%>" <% if(m.isObligation()){%>required<% }%>/>
				<%
			}
		}catch(Exception e1){
		%><br><div>Il n'y a pas de métadonnées auxquelles vous avez accès.</div><%
		}
		%>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form>
</div>
<%} else if (entityName.equals("annotation")){%>
<div id="createForm" class="">
	<h3>Création d'annotation</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			This section allows you to upload an annotation for a video.
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Create" id="add_annotation" name="annotation" enctype="multipart/form-data"><br>
		<input type="hidden"  name="entity"  value="annotation">
		<input type="hidden"  name="fatherId"  value="<%=fatherId%>">
		<input type="hidden"  name="fatherEntityName"  value="<%=fatherEntityName%>">
		<label for="name_video"><span>Nom</span></label>
		<input name="name" type="text" placeholder="Nom de l'annotation" autofocus required/>
		<br>
		<label for="privacy"><span>Privauté</span></label>
		<select name="privacy">
		  <option value="0">Privée</option>
		  <option value="1">Restrainte</option>
		  <option value="2">Publique</option>
		</select>
		<label for="annotationfile"><span>Fichier</span></label>
		<input name="annotationfile" type="file" />
		 <% 
		try {
			for (Metadata m : remoteRequest.getMetadatas(e)){
				%>
				<label for="<%=m.getName()%>"><span><%=m.getName()%><% if(m.isObligation()){%>*<% }%></span></label>
				<input name="<%=m.getName()%>" type="text" placeholder="<%=m.getDescription()%>" <% if(m.isObligation()){%>required<% }%>/>
				<%
			}
		}catch(Exception e1){
		%><br><div>Il n'y a pas de métadonnées auxquelles vous avez accès.</div><%
		}
		%>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form>
</div>
<%} else if (entityName.equals("links")){%>
<div id="createForm" class="">
    <h3>Création de liens vidéo</h3>
    <div style="margin-bottom: 5px" class="description">
        <p>
            Dans cette partie vous avez la possibilité de rajouter des liens vers les vidéos. 
        </p>
    </div>
    <div class="space"></div>
    <form class="form" method="post" action="Create" id="add_video" name="video" enctype="multipart/form-data"><br>
        <input type="hidden"  name="entity"  value="video">
        <input type="hidden"  name="fatherId"  value="<%=fatherId%>">
        <input type="hidden"  name="fatherEntityName"  value="<%=fatherEntityName%>">
        <label for="videofile"><span>Fichier</span></label>
        <input name="videofile" type="file" />
		<% 
		try {
			for (Metadata m : remoteRequest.getMetadatas(e)){
				%>
				<label for="<%=m.getName()%>"><span><%=m.getName()%><% if(m.isObligation()){%>*<% }%></span></label>
				<input name="<%=m.getName()%>" type="text" placeholder="<%=m.getDescription()%>" <% if(m.isObligation()){%>required<% }%>/>
				<%
			}
		}catch(Exception e1){
		%><br><div>Il n'y a pas de métadonnées auxquelles vous avez accès.</div><%
		}
		%>
        <p class="validate">
            <input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
        </p>
    </form>
</div>

<%} else if (entityName.equals("user")){%>
<div id="createForm" class="">
    <h3>Création d'utilisateur</h3>
    <div style="margin-bottom: 5px" class="description">
        <p>
            Création d'utilisateur. 
        </p>
    </div>
    <div class="space"></div>
    <form class="form" method="post" action="Create" id="add_user" name="user"><br>
        <label for="name"><span>Nom</span></label>
        <input name="name" type="text" placeholder="Nom" autofocus required/>
        <label for="forename"><span>Prénom</span></label>
        <input name="forename" type="text" placeholder="Prénom" required/>
        <label for="mail"><span>Prénom</span></label>
        <input name="mail" type="email" placeholder="Martin@irit.fr" required/>
        <label for="role"><span>Droits</span></label>
        <select name="role">
          <option value="1">Gestionnaire de site</option>
          <option value="2">Gestionnaire de corpus</option>
          <option value="3">Utilisateur</option>
        </select>
        <p class="validate">
            <input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
        </p>
    </form>
</div>
<%} else if (entityName.equals("metadata")){%>
<div id="createForm" class="">
	<h3>Création de métadonnée</h3>
	<div style="margin-bottom: 5px" class="description">
		<p>
			Dans cette partie vous avez la possibilité de créer des métadonnées. 
			Seul l'administrateur possède les droits pour créer des métadonnées. 
			Une métadonnée permet de renseigner une entité, par exemple l'auteur,
			l'importance, la durée d'une video..<br><br> 
			Vous devez simplement renseigner le nom de la métadonnée, sa description,
			si elle est obligatoire ou non ainsi que sa privauté. Enfin vous devez cocher
			les entités auxquelles elle est rattachée.	
		</p>
	</div>
	<div class="space"></div>
	<form class="form" method="post" action="Create" id="add_metadata" name="metadata">
		<input type="hidden"  name="entity"  value="metadata">
		<label for="name_meta"><span>Nom</span></label>
		<input name="name_meta" type="text" placeholder="Nom de la métadonnée" autofocus required/>
		<label for="description_meta"><span>Description</span></label>
		<input name="description_meta" type="text" placeholder="A quoi elle sert" required/><br>
		<label for="obligation"><span>Obligation</span></label>
		<select name="obligation_meta">
		  <option value="True">Obligatoire</option>
		  <option value="False">Facultative</option>
		</select>
		<label for="private"><span>Privauté</span></label>
		<select name="private_meta" style="margin-bottom:15px;">
		  <option value="True">Privée</option>
		  <option value="False">Publique</option>
		</select>
		<label for="entities"><span>Choisir les entités :</span></label>
		<div id="conteneur_box" name="entities">
		<%
			String ts[] = {"Video","Fichier video","Annotation","Vue","Session","Corpus","Site"};
			for (String s : ts){ %>
					<input id="check_entities" type="checkbox" name="<% out.print(s+"_meta");%>" value="<%out.print(s);%>"/>
					<% out.print(s);if (!s.equals("Video")){ %> <br> <% }
			} %>
		</div>
		<p class="validate">
			<input class="validate_button" name="validate" type="submit" size="40" value="Valider"/>
		</p>
	</form>
</div>
<%}%>
