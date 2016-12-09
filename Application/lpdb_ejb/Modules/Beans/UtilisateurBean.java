package Beans;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "UTILISATEUR")
public final class UtilisateurBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "NOM", nullable = false)
	private String nom;

	@Column(name = "PRENOM", nullable = false)
	private String prenom;

	@Column(name = "PSEUDONYME", nullable = false)
	private String pseudonyme;

	@Column(name = "ADRESSE", nullable = false)
	private String adresse;

	@Column(name = "MDP", nullable = false)
	private String mdp;

	@Column(name = "ADMINISTRATEUR", nullable = false)
	private boolean administrateur;

	public UtilisateurBean() {}

	public UtilisateurBean(int id, String nom, String prenom, String pseudonyme,
			String adresse, String mdp, boolean administrateur) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.pseudonyme = pseudonyme;
		this.adresse = adresse;
		this.mdp = mdp;
		this.administrateur = administrateur;
	}

	public int getId() {return this.id;}
	private void setId(int id) {this.id = id;}

	public String getNom() {return this.nom;}
	public void setNom(String nom) {this.nom = nom;}

	public String getPrenom() {return this.prenom;}
	public void setPrenom(String prenom) {this.prenom = prenom;}

	public String getPseudonyme() {return this.pseudonyme;}
	public void setPseudonyme(String pseudonyme) {this.pseudonyme = pseudonyme;}

	public String getAdresse() {return this.adresse;}
	public void setAdresse(String adresse) {this.adresse = adresse;}

	public String getMdp() {return this.mdp;}
	public void setMdp(String mdp) {this.mdp = mdp;}

	public boolean getAdministrateur() {return this.administrateur;}
	public void setMdp(boolean administrateur) {this.administrateur = administrateur;}
	
	@Override
	public String toString() {
		return "{" + "\"type\":\"Utilisateur\"" +
				", \"id\":" + this.id +
				", \"nom\":\"" + this.nom + "\"" +
				", \"prenom\":\"" + this.prenom + "\"" +
				", \"pseudonyme\":\"" + this.pseudonyme + "\"" +
				", \"adresse\":\"" + this.adresse + "\"" +
				", \"mdp\":\"" + this.mdp + "\"" +
				", \"administrateur\":\"" + this.administrateur + "\"}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UtilisateurBean)) return false;

		UtilisateurBean utilisateur = (UtilisateurBean)o;

		if (this.id != utilisateur.id) return false;
		if (!this.nom.equals(utilisateur.nom)) return false;
		if (!this.prenom.equals(utilisateur.prenom)) return false;
		if (!this.pseudonyme.equals(utilisateur.pseudonyme)) return false;
		if (!this.adresse.equals(utilisateur.adresse)) return false;
		if (!this.mdp.equals(utilisateur.mdp)) return false;
		if (this.administrateur != utilisateur.administrateur) return false;

		return true;
	}
	
}
