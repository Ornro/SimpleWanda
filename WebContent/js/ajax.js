$(document).ready(function() {
	$('#MainBack').load('html-content-file.html');
});

function getXhr() {
	var xhr = null;
	if (window.XMLHttpRequest) // Firefox et autres
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
	return xhr;
}

function window_change(id) {
	var xhr = getXhr(); // On défini ce qu'on va faire quand on aura la réponse
	xhr.onreadystatechange = function() { // si on a tout reçu et que le
											// serveur est ok
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById('home_page').innerHTML = xhr.responseText;
		}
	};
	xhr.open("GET", id + '.jsp', true);
	xhr.send(null);
}