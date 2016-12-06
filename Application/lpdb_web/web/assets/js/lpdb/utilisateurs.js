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
		console.log(data);
		if (data.hasOwnProperty("status")) {
			if (data.status == 500) {
				alert("Mauvais pseudo");
			} 
		} else { /* Response 200 */
			document.location.href = "/LPDB_WEB/annonces.html";
		}									
	});
}

function createUser(idForm){
	var params = getParam(idForm);
	var url = "/LPDB_WEB/rest/utilisateur/addUtilisateur";
	jQuery.post(url, params)
	.always(function(data) {
		console.log(data);
		if (data.hasOwnProperty("status")) {
			if (data.status == 500) {
				alert("Mauvais pseudo");
			} 
		} else { /* Response 200 */
			document.location.href = "/LPDB_WEB/index.html#";
		}									
	});
}

function connect(idForm){
	var params = getParam(idForm);
	var url = "/LPDB_WEB/rest/utilisateur/connexion";
	jQuery.post(url, params)
	.always(function(data) {
		console.log(data);
		if (data.hasOwnProperty("status")) {
			if (data.status == 404) {
				alert("Mauvais pseudo");
			} else { /* Response 200 */
				createCookie("connected", true, 1);
				document.location.href = "/LPDB_WEB/index.html";
			}
		}								
	});
}
