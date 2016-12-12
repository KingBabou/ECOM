var nbLines = 10;
var currentIndex = 0;

function getParam(idForm) {
	var x = document.getElementById(idForm);
	var params = "{";
	var i;
	for (i = 0; i < x.length-1; i++) {
		params = params + "\"" + x.elements[i].name + "\":\"" + x.elements[i].value + "\"";
		if(i < x.length-2) params = params + ","; 
	}
	params = params + "}";

	return JSON.parse(params);
}

function supprimerAnnonce(formId){
	var params = getParam(formId);
	var url = "/LPDB_WEB/rest/annonce/deleteAnnonce";
	jQuery.post(url, params)
	.always(function(data) {
		console.log(data);
		if (data.hasOwnProperty("status")) {
			if (data.status == 500) {
				alert("Erreur");
			} 
		} else { /* Response 200 */
			loadAnnonces();
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
			
			var htmlText = "<tr>";
			htmlText += "<td><img src=\"" + val.image + "\"/></td>";
			//htmlText += "<td>" + val.titre + "</td>";
			htmlText += "<td><a href=\"#my_modalAnnonce\" data-toggle=\"modal\" class=\"annonce\" data-identite=" + val.id + " data-price=" + val.prix + " data-localite=" + val.localite + " data-date=" + val.creation + " data-vendeur=" + val.pseudo + " data-description=" + val.description + ">" + val.titre + "</a></td>";
			htmlText += "<td>" + val.prix + "</td>";
			htmlText += "<td>" + val.localite + "</td>";
			htmlText += "<td>" + val.creation + "</td>";
			htmlText += "<td> <a href=\"\"> delete</a></td>";
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
			htmlText += "<td><a href=\"#my_modalAnnonce\" data-toggle=\"modal\" class=\"annonce\" data-identite=" + val.id + " data-price=" + val.prix + " data-localite=" + val.localite + " data-date=" + val.creation + " data-vendeur=" + val.pseudo + " data-description=" + val.description + ">" + val.titre + "</a></td>";
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
