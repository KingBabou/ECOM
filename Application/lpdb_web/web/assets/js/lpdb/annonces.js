var nbLines = 10;
var currentIndex = 0;

function getParamAnnonce(idForm) {
	var x = document.getElementById(idForm);
	var arrStr = [];
	for (var i = 0; i < x.length; i++) {

		if ((x.elements[i].nodeName == "INPUT" || x.elements[i].nodeName == "TEXTAREA" ) && x.elements[i].name != ""){
			arrStr.push("\"" + x.elements[i].name + "\":\"" + x.elements[i].value + "\"");
		}
	}
	return JSON.parse("{" + arrStr.join(",") + "}");
}

function deleteAnnonce(formId, callback){
	var params = getParamAnnonce(formId);
	var url = "/LPDB_WEB/rest/annonce/deleteAnnonce";
	jQuery.post(url, params)
	.always(function(data) {
		if (data.hasOwnProperty("status")) {
			if (data.status == 500) {
				alert("Erreur");
			} 
		} else { /* Response 200 */
			callback();
			loadAnnonces("list", currentIndex + nbLines);
		}									
	});
}

function loadUserAnnonces(idTable){
	var pseudo = readCookie("userConnected");
	if (pseudo == null) { return; }
	var url = "/LPDB_WEB/rest/annonce/getAnnonces?pseudo=" + pseudo;
	
	jQuery.get(url, function(data) {

		currentIndex = data.length <= nbLines ? -nbLines : index;
		if(data.length == nbLines + 1) data.splice(nbLines, 1);

		var listAnnonces = document.getElementById(idTable);

		var htmlData = $.map(data, function(val) {
			val["image"] = "";
			val["localite"] = "Grenoble";
			
			var idForm = "formDelete" + val.id;
			
			var htmlText = "<tr>";
			htmlText += "<td><img src=\"" + val.image + "\"/></td>";
			//htmlText += "<td>" + val.titre + "</td>";
			htmlText += "<td><a href=\"#my_modalAnnonce\" data-toggle=\"modal\" class=\"annonce\" data-identite=" + val.id + " data-price=" + val.prix + " data-localite=" + val.localite + " data-date=" + val.creation + " data-vendeur=" + val.pseudo + " data-description=" + val.description + ">" + val.titre + "</a></td>";
			htmlText += "<td>" + val.prix + "</td>";
			htmlText += "<td>" + val.localite + "</td>";
			htmlText += "<td>" + val.creation + "</td>";
			htmlText += "<td> <form id='" + idForm  + "' method='POST' action= \"javascript:deleteAnnonce('" + idForm + "', function(){loadUserAnnonces('"+ idTable +"')});\">"
			htmlText += "<input type='hidden' name='ID' value=" + val.id + "></input>";
			htmlText += "<button type='submit'> Supprimer </button>";
			htmlText += "</form></td>";
			htmlText += "</tr>";
			return htmlText;
		}).join('');

		listAnnonces.innerHTML = htmlData;
		

		var headers = ["Image", "Titre", "Prix", "Localité", "Date de création", "options"];
		var header = document.getElementById(idTable).createTHead();
		var rowHeader = header.insertRow(0);
	
		for(var i=0; i < headers.length; i++){
			rowHeader.insertCell(i).innerHTML = "<b>" + headers[i] + "</b>";
		}
	});
	
}

function loadAnnonces(idList, index){
	
	var url = "/LPDB_WEB/rest/annonce/getAnnonces/" + (nbLines+1) + "/" + index;
	
	jQuery.get(url, function(data) {
		currentIndex = data.length <= nbLines ? -nbLines : index;
		if(data.length == nbLines + 1) data.splice(nbLines, 1);

		var listAnnonces = document.getElementById(idList.replace("#",""));

		var htmlData = $.map(data, function(val) {
			val["image"] = "";
			val["localite"] = "Grenoble";
			
			var htmlText = "<tr>";
			htmlText += "<td><img src=\"" + val.image + "\"/></td>";
			//htmlText += "<td>" + val.titre + "</td>";
			htmlText += "<td><a href=\"#my_modalAnnonce\" data-toggle=\"modal\" class=\"annonce\" data-titre=" + val.titre + " data-identite=" + val.id + " data-price=" + val.prix + " data-localite=" + val.localite + " data-date=" + val.creation + " data-vendeur=" + val.pseudo + " data-description=" + val.description + ">" + val.titre + "</a></td>";
			htmlText += "<td>" + val.prix + "</td>";
			htmlText += "<td>" + val.localite + "</td>";
			htmlText += "<td>" + val.creation + "</td>";
			htmlText += "</tr>";
			return htmlText;
		}).join('');

		listAnnonces.innerHTML = htmlData;
		

		var headers = ["Image", "Titre", "Prix", "Localité", "Date de création"];
		var header = document.getElementById(idList.replace("#","")).createTHead();
		var rowHeader = header.insertRow(0);
	
		for(var i=0; i < headers.length; i++){
			rowHeader.insertCell(i).innerHTML = "<b>" + headers[i] + "</b>";
		}
		
	});
}

function addAnnonce(formId, callback){
	var params = getParamAnnonce(formId);

	if(readCookie("userConnected") == null) alert("utilisateur non connecté");
	
	params["PSEUDO"] = readCookie("userConnected");
	var url = "/LPDB_WEB/rest/annonce/addAnnonce";
	jQuery.post(url, params)
	.always(function(data) {
		if (data.hasOwnProperty("status")) {
			if (data.status == 500) {
				alert("Erreur");
			} 
		} else { // Response 200 
			callback();			
			loadAnnonces("list", currentIndex + nbLines);
		}									
	});
}


function generateComboTypes(idCombo){
	
	var url = "/LPDB_WEB/rest/annonce/getTypes";
	
	jQuery.get(url, function(data) {
		if (data.hasOwnProperty("status")) {
			if (data.status == 500) {
				alert("Erreur");
			} 
		} else { /* Response 200 */
			$('#hType').val(data.types[0]);
			$(idCombo).empty();
			data.types.forEach(function(type) { $(idCombo).append("<option>" + type + "</option>")});
		}		
	});	
}
