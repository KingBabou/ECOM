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

import java.util.List;
import java.util.Date;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Beans.UtilisateurBean;
import Sessions.UtilisateurRemote;
import Sessions.UtilisateurServiceBean;

@Path("/utilisateur")
public class UtilisateurWS {

	@Context
	private UriInfo contexte;

	@EJB
	private UtilisateurRemote utilisateurRemote = new UtilisateurServiceBean();

	public UtilisateurWS() {}

	@PUT
	@Consumes("application/json")
	public void putJSON(String json) {

	}

	@GET
	@Path("/getJson")
	@Produces("application/json")
	public String getJson() {
		return "{msg: \"UtilisateurWS\"}";
	}

	@GET
	@Path("/getUtilisateurs")
	@Produces("application/json")
	public String getUtilisateurs() throws Exception {
		List<UtilisateurBean> users = this.utilisateurRemote.findAll();
		String str = "[";
		for (int i = 0 ; i < users.size(); i++) {
			str += users.get(i).toString();
			if (i < users.size() - 1) str += ",";
		}
		str += "]";
		return str;
	}
	
	@GET
	@Path("/getUtilisateur/{id}")
	@Produces("application/json")
	public String getUtilisateur(@PathParam("id") int id) throws Exception {
		UtilisateurBean user = this.utilisateurRemote.findUser(id);
		if(user != null) return user.toString();
		else return "";
	}
	
	@GET
	@Path("/getUtilisateurByPseudo/{pseudo}")
	@Produces("application/json")
	public String getUtilisateurByPseudo(@PathParam("pseudo") String pseudo) throws Exception {
		UtilisateurBean user = this.utilisateurRemote.findUserByPseudo(pseudo);
		if(user != null) return user.toString();
		else return "";
	}
	
	@GET
	@Path("/getUtilisateurInfo/{pseudo}")
	@Produces("application/json")
	public String getUtilisateurInfo(@PathParam("pseudo") String pseudo) throws Exception {
		UtilisateurBean user = this.utilisateurRemote.findUserByPseudo(pseudo);
		if(user != null) {
			return "{" +
					"\"nom\":\"" + user.getNom() + "\"," +
					"\"prenom\":\"" + user.getPrenom() + "\"," +
					"\"adresse\":\"" + user.getAdresse() + "\"" +			
					"}";
		} else {
			return "";
		}
	}

	@GET
	@Path("/getPseudo/{id}")
	@Produces("application/json")
	public String getPseudo(@PathParam("id") int id) throws Exception {
		UtilisateurBean user = this.utilisateurRemote.find(id);
		if(user != null) return "{\"pseudo\":\"" + user.getPseudonyme() + "\"}";
		else return "";
	}

	@POST
	@Path("/connexion")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response connexion(@FormParam("PSEUDONYME") String pseudonyme,
							  @FormParam("MDP") String mdp) throws Exception {
		UtilisateurBean utilisateur = this.utilisateurRemote.findUserByPseudo(pseudonyme);
		if (utilisateur != null && mdp.equals(utilisateur.getMdp())) {
			/*Socket pingSocket = null;
			PrintWriter out = null;
			
			try {
				pingSocket = new Socket("172.17.0.4", 4242);
				out = new PrintWriter(pingSocket.getOutputStream(), true);
			} catch (IOException e) {
				return Response.status(500).entity("La base de données OpenTSDB ne répond pas.").build();
			}
			
			out.println("put connexions "+ new Date().getTime() + " 1 " + pseudonyme + "=1");
			out.close();
			pingSocket.close();*/
			return Response.status(200).entity("Bienvenue " + pseudonyme).build();
		}
		return Response.status(404).entity("Mauvais pseudonyme et / ou mot de passe").build();
	}

	@POST
	@Path("/addUtilisateur")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response addUtilisateur(@FormParam("NOM") String nom,
								   @FormParam("PRENOM") String prenom,
								   @FormParam("PSEUDONYME") String pseudonyme,
								   @FormParam("ADRESSE") String adresse,
								   @FormParam("MDP") String mdp) throws Exception {
		boolean nouveau_pseudonyme_disponible = this.utilisateurRemote.pseudoIsAvailable(pseudonyme);
		if (nouveau_pseudonyme_disponible) {
			int id = this.utilisateurRemote.getLastId();
			UtilisateurBean utilisateur = new UtilisateurBean(id, nom, prenom, pseudonyme, adresse, mdp, false);
			this.utilisateurRemote.create(utilisateur);
			return Response.status(200).entity(utilisateur.toString()).build();
		}
		return Response.status(500).entity("Le pseudonyme : '" + pseudonyme + "' est déjà pris. Veuillez en saisir un autre.").build();
	}

	@POST
	@Path("/deleteUtilisateur")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response deleteUtilisateur(@FormParam("PSEUDONYME") String pseudonyme) throws Exception {
		UtilisateurBean utilisateur = this.utilisateurRemote.find(pseudonyme);
		this.utilisateurRemote.remove(utilisateur);
		return Response.status(200).entity(utilisateur.toString()).build();
	}

	@POST
	@Path("/editUtilisateur")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response editUtilisateur(@FormParam("PSEUDONYME") String pseudonyme,
									  @FormParam("NEW_NOM") String new_nom,
									  @FormParam("NEW_PRENOM") String new_prenom,
									  @FormParam("NEW_PSEUDONYME") String new_pseudonyme,
									  @FormParam("NEW_ADRESSE") String new_adresse,
									  @FormParam("NEW_MDP") String new_mdp) throws Exception {
		boolean nouveau_pseudonyme_disponible = (pseudonyme.equals(new_pseudonyme)) ? true : this.utilisateurRemote.pseudoIsAvailable(new_pseudonyme);
		if (nouveau_pseudonyme_disponible) {
			UtilisateurBean utilisateur = this.utilisateurRemote.find(pseudonyme);
			utilisateur.setNom(new_nom);
			utilisateur.setPrenom(new_prenom);
			utilisateur.setPseudonyme(new_pseudonyme);
			utilisateur.setAdresse(new_adresse);
			utilisateur.setMdp(new_mdp);
			this.utilisateurRemote.edit(utilisateur);
			return Response.status(200).entity(utilisateur.toString()).build();
		}
		return Response.status(500).entity("Le pseudonyme : '" + new_pseudonyme + "' est déjà pris. Veuillez en saisir un autre.").build();
	}

}
