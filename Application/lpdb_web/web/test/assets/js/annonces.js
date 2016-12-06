var nbLines = 1;
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
	var url = "http://localhost:8080/LPDB_WEB/rest/annonce/deleteAnnonce";
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

function loadAnnonces(idList, index){
	currentIndex = index;
	var url = string.format("http://localhost:8080/LPDB_WEB/rest/annonce/getAnnonces/{0}/{1}", nbLines, index);
	jQuery.get(url, function(data) {
		var listAnnonces = jQuery(idList);
		
		listAnnonces.empty();
		listAnnonces.html($.map(data, function(val) {
			val["image"] = "";
			val["localite"] = "Grenoble";
			
			var htmlText = "<tr>";
			htmlText += "<td><img src=\"" + val.image + "\"/></td>";
			htmlText += "<td>" + val.titre + "</td>";
			htmlText += "<td>" + val.prix + "</td>";
			htmlText += "<td>" + val.localite + "</td>";
			htmlText += "<td>" + val.creation + "</td>";
			htmlText += "</tr>";
			return htmlText;
		}).join(''));

		var headers = ["Image", "Titre", "Prix", "Localité", "Date de création"];
		var header = document.getElementById(idList.replace("#","")).createTHead();
		var rowHeader = header.insertRow(0);
	
		for(var i=0; i < headers.length; i++){
			rowHeader.insertCell(i).innerHTML = "<b>" + headers[i] + "</b>";
		}
		
		/*for(var i=0; i < data.length; i++) {
			var l = document.createElement("li");
			l.innerHTML = JSON.stringify(data[i]);
			
			var b = document.createElement("button");
			b.innerHTML = "editer";
			b.type = "submit";

			var supBtn = document.createElement("button");
			supBtn.innerHTML = "Supprimer";
			supBtn.type = "submit";
			
			var formEdit = document.createElement("form");
			formEdit.action = "http://localhost:8080/LPDB_WEB/editAnnonce.html";
			formEdit.method = "GET";

			var formSup = document.createElement("form");
			formSup.id = "formSup"+i;
			formSup.action = "javascript:supprimerAnnonce('" + formSup.id + "');";
			formSup.method = "POST";

			var input = document.createElement("input");
			input.type = "hidden";
			input.value = data[i].id;
			input.name = "ID";

			formEdit.append(b);
			formEdit.append(input);
			formSup.append(input);							
			formSup.append(supBtn);							
			l.append(formEdit);
			l.append(formSup);					
			listAnnonces.append(l);

		}*/
	});
}
