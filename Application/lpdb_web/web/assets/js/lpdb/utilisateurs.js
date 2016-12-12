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

function modify(){
	var params = getParam("editForm");
	var url = "/LPDB_WEB/rest/utilisateur/editUtilisateur";
	jQuery.post(url, params)
	.always(function(data) {
		if (data.hasOwnProperty("status")) {
			if (data.status == 500) {
				alert("Mauvais pseudo");
			} 
		} else { /* Response 200 */
			document.location.href = "/LPDB_WEB/annonces.html";
		}									
	});
}

function createUser(idForm, callback){
	var params = getParam(idForm);
	var url = "/LPDB_WEB/rest/utilisateur/addUtilisateur";
	jQuery.post(url, params)
	.always(function(data) {
		if (data.hasOwnProperty("status")) {
			if (data.status == 500) {
				alert("Mauvais pseudo");
				createCookie("connected", false, 0);
				callback();
			} 
		} else { /* Response 200 */
			createCookie("connected", true, 0);			
			createCookie("userConnected", params.PSEUDONYME, 0);
			callback();
			$("#"+idForm)[0].reset();
			//document.location.href = "/LPDB_WEB/index.html#";
		}									
	});
}

function connect(idForm, callback){
	var params = getParam(idForm);
	var url = "/LPDB_WEB/rest/utilisateur/connexion";
	jQuery.post(url, params)
	.always(function(data) {
		if (data.hasOwnProperty("status")) {
			if (data.status == 404 ) {
				alert("Mauvais pseudo");
				createCookie("connected", false, 0);
				callback();
			} else if (data.status == 500) {
				alert("internal error");
				createCookie("connected", false, 0);
				callback();
			} else if (data.status == 200) { 
				createCookie("connected", true, 0);
				createCookie("userConnected", params.PSEUDONYME, 0);
				callback();
				$("#"+idForm)[0].reset();
				//document.location.href = "/LPDB_WEB/index.html";
			}
		}								
	});
}

function setUserInfo(idTable){
	var pseudo = readCookie("userConnected");
	var url = "/LPDB_WEB/rest/utilisateur/getUtilisateurInfo/" + pseudo;
	jQuery.get(url, function(data) {
		if (data.hasOwnProperty("status")) {
			if (data.status == 500) {
				alert("internal error");
			}
		} else {
			var innerHTML = "";
			for (var key in data) {
				var capKey = key.charAt(0).toUpperCase() + key.slice(1);
				
				 innerHTML += "<tr class=\"coord\">" + 
								"<td bgcolor=\"F2F2F2\"> " + capKey + ": </td>" + 
								"<td contenteditable='false' bgcolor=\"F2F2F2\"> " + data[key] + " </td>" + 
							  "</tr>";
			}	
			
			document.getElementById(idTable).innerHTML = innerHTML;
			
		}						
	});	
}
