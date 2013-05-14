<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Wanda - Home</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
		<link rel="stylesheet" media="screen" type="text/css" title="Design" href="css/home_style.css" />
		<link rel="stylesheet" media="screen" type="text/css" title="Design" href="css/menu_style.css" />
		<link rel="stylesheet" media="screen" type="text/css" title="Design" href="css/arborescence_style.css" />
		
		<script type="text/javascript" src="js/script.js"></script>
		<script type="text/javascript" src="js/script_displayDiv.js"></script>
		<script	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
		<script type="text/javascript">
	$(document).ready(function() {
		$('#MainBack').load('html-content-file.html');
	});

	function getXhr() {
		var xhr = null;
		if (window.XMLHttpRequest) //Firefox et autres
			xhr = new XMLHttpRequest();
		else if (window.ActiveXObject) { // Internet Explorer 
			try {
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}
		} else { // XMLHttpRequest non supporté par le navigateur 
			alert("Le navigateur ne supporte pas les objetsXMLHTTPRequest...");
			xhr = false;
		}
		return xhr
	}
	
	function change_div(name,id) {
		var xhr = getXhr(); // On défini ce qu'on va faire quand on aura la réponse
		xhr.onreadystatechange = function() { // si on a tout reçu et que le serveur est ok
			if (xhr.readyState == 4 && xhr.status == 200) {
				document.getElementById('home_page').innerHTML = xhr.responseText;
			}
		};
		alert(name+"?id="+id);
		xhr.open("GET", name+"?id="+id, true);
		xhr.send(null);
	}
	
</script>
	</head>
	
	<body>
		<%@ page import="fr.irit.wanda.entities.*"%>
		<%@ page import="fr.irit.wanda.servlet.ClientConfiguration"%>
		<%@ page import="fr.irit.wanda.dao.*"%>
		<%@ page import="java.util.Collection"%>
		<%@ page import="java.io.*"%>
	
		<p id="top"></p>
		<div id="main_container">
		
			<div class="dotted_line"></div>
			<div id="header_container">
				<img src="img/logo_app.png" alt="Wanda" title="Wanda" width="350" height="100" style="margin-left: 29px; float: left;">
				<div id="search_tool">
					<form method="get" action="/search" id="search">
						<input id="search_barre" name="search_barre" type="text" size="40" placeholder="Search..." />
					</form>
				</div>
			</div>
			<div class="dotted_line"></div>
			<div id="content">
				<div id="contenu">
					<div id="home_page" class="caller">Ex his quidam aeternitati
						se commendari posse per statuas aestimantes eas ardenter adfectant
						quasi plus praemii de figmentis aereis sensu carentibus adepturi,
						quam ex conscientia honeste recteque factorum, easque auro curant
						inbracteari, quod Acilio Glabrioni delatum est primo, cum consiliis
						armisque regem superasset Antiochum. quam autem sit pulchrum exigua
						haec spernentem et minima ad ascensus verae gloriae tendere longos
						et arduos, ut memorat vates Ascraeus, Censorius Cato monstravit.
						qui interrogatus quam ob rem inter multos... statuam non haberet
						malo inquit ambigere bonos quam ob rem id non meruerim, quam quod
						est gravius cur inpetraverim mussitare. Eminuit autem inter humilia
						supergressa iam impotentia fines mediocrium delictorum nefanda
						Clematii cuiusdam Alexandrini nobilis mors repentina; cuius socrus
						cum misceri sibi generum, flagrans eius amore, non impetraret, ut
						ferebatur, per palatii pseudothyrum introducta, oblato pretioso
						reginae monili id adsecuta est, ut ad Honoratum tum comitem
						orientis formula missa letali omnino scelere nullo contactus idem
						Clematius nec hiscere nec loqui permissus occideretur. Mox dicta
						finierat, multitudo omnis ad, quae imperator voluit, promptior
						laudato consilio consensit in pacem ea ratione maxime percita, quod
						norat expeditionibus crebris fortunam eius in malis tantum
						civilibus vigilasse, cum autem bella moverentur externa, accidisse
						plerumque luctuosa, icto post haec foedere gentium ritu perfectaque
						sollemnitate imperator Mediolanum ad hiberna discessit.</div>
	
			</div>
			</div>		
			<div id="sidebar">
				<%
					ClientConfiguration ccfg = new ClientConfiguration("benjamin.babic@hotmail.fr");
					out.print(ccfg.remoteRequest.printHierarchy());
				%>
			</div>
			<div class="clear"></div>
			<div class="dotted_line"></div>
			<div id="copyright">Copyright © Wanda 2013.</div>
		</div>
		<div id="footer">
			<div id="footer-menu">
				<ul>
					<li><a title="Home" href="Home.jsp" class="color1">Home</a></li>
					<li><a title="About us" href="" class="color1">About us</a></li>
					<li><a title="Top page" href="#top" class="color1">Top page</a></li>
				</ul>
			</div>
			<div id="time">
				<script>
					dT();
				</script>
			</div>
		</div>
	</body>
</html>