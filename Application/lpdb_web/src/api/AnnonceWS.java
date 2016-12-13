package api;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URISyntaxException; 

import java.util.List;
import java.util.Date;

import Beans.AnnonceBean;
import Beans.Type;
import Sessions.AnnonceRemote;
import Sessions.AnnonceServiceBean;
import Sessions.UtilisateurServiceBean;
import Sessions.UtilisateurRemote;

@Path("/annonce")
public class AnnonceWS {

	@Context
	private UriInfo contexte;

	@EJB
	private AnnonceRemote annonceRemote = new AnnonceServiceBean();

	public AnnonceWS() {}

	@PUT
	@Consumes("application/xml")
	public void putXML(String xml) {

	}

	@GET
	@Path("/getJson")
	@Produces("application/json")
	public String getJson() {
		return "{msg: \"AnnonceWS\"}";
	}

	@GET
	@Path("/getAnnonces")
	@Produces("application/json")
	public String getAnnonces() throws Exception {
		List<AnnonceBean> annonces = this.annonceRemote.findAll();
		String str = "[";
		for (int i = 0 ; i < annonces.size(); i++) {
			str += annonces.get(i).toString();
			if (i < annonces.size() - 1) str += ",";
		}
		str += "]";
		return str;
	}

	@GET
	@Path("/getAnnonces/{number}/{firstLine}")
	@Produces("application/json")
	public String getAnnoncesPaged (
			@PathParam("number") int nbAnnonces,
			@PathParam("firstLine") int beginIndex) throws Exception {

		List<AnnonceBean> annonces = this.annonceRemote.findAll();

		if(beginIndex > annonces.size()){
			return "[]";
		}
		
		String str = "[";
		int max = (beginIndex + nbAnnonces > annonces.size())? annonces.size() : beginIndex + nbAnnonces;
		for (int i = beginIndex; i < max; i++) {
			AnnonceBean annonce = annonces.get(i);
			String pseudo = this.annonceRemote.getPseudoUtilisateur(annonce.getIdUtilisateur());
			
			str += "{ \"id\":" + annonce.getId() +
				", \"pseudo\":\"" + pseudo + "\"" + 
				", \"titre\":\"" + annonce.getTitre() + "\"" +
				", \"prix\":\"" + annonce.getPrix() + "\"" +
				", \"description\":\"" + annonce.getDescription() + "\"" +
				", \"creation\":\"" + annonce.getCreation() + "\"" +
				", \"type\":\"" + annonce.getType() + "\"}";
				
			if (i < max - 1) str += ",";
		}
		str += "]";
		return str;

	}

	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	@Path("/addAnnonce")
	public Response addAnnonce(@FormParam("ID_UTILISATEUR") String id_utilisateur,
							   @FormParam("TITRE") String titre,
							   @FormParam("PRIX") String prix,
							   @FormParam("DESCRIPTION") String description,
							   @FormParam("TYPE") String type) throws Exception {
		int id = this.annonceRemote.getLastId();
		AnnonceBean annonce = new AnnonceBean(id, (int)Integer.valueOf(id_utilisateur), titre, (double)Double.valueOf(prix),
			description, Type.getType(type).toString());
		this.annonceRemote.create(annonce);
		return Response.status(200).entity(annonce.toString()).build();
	}

	@POST
	@Path("/deleteAnnonce")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response deleteAnnonce(@FormParam("ID") String id) throws Exception {
		AnnonceBean annonce = this.annonceRemote.find(Integer.valueOf(id));
		this.annonceRemote.remove(annonce);
		return Response.status(200).entity(annonce.toString()).build();
	}

	@POST
	@Path("/editAnnonce")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response editAnnonce(@FormParam("ID") String id,
							    @FormParam("TITRE") String titre,
							    @FormParam("PRIX") String prix,
							    @FormParam("DESCRIPTION") String description,
							    @FormParam("TYPE") String type) throws Exception {
		AnnonceBean annonce = this.annonceRemote.find(Integer.valueOf(id));
		annonce.setTitre(titre);
		annonce.setPrix((double)Double.valueOf(prix));
		annonce.setDescription(description);
		annonce.setType(Type.getType(type).toString());
		this.annonceRemote.edit(annonce);
		return Response.status(200).entity(annonce.toString()).build();
	}

	@POST
	@Path("/rechercheAnnonce")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public String rechercheAnnonce(@FormParam("RECHERCHE") String recherche) throws Exception {
		List<AnnonceBean> annonces = this.annonceRemote.recherche(recherche);
		if (annonces != null) {
			if (!annonces.isEmpty()) {
				String str = "[";
				for (int i = 0 ; i < annonces.size(); i++) {
					str += annonces.get(i).toString();
					if (i < annonces.size() - 1) str += ",";
				}
				str += "]";
				return str;
			}
		}
		return "[]";
	}

}
