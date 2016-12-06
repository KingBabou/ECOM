package Beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "ANNONCE")
public final class AnnonceBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "ID_UTILISATEUR")
	private int id_utilisateur;

	@Column(name = "TITRE", nullable = false)
	private String titre;

	@Column(name = "PRIX", nullable = false)
	private double prix;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION", nullable = false)
	private Date creation;

	@Column(name = "TYPE", nullable = false)
	private String type;

	public AnnonceBean() {}

	public AnnonceBean(int id, int id_ut, String titre, double prix, String description,
			String type) {
		this.id = id;
		this.id_utilisateur = id_ut;
		this.titre = titre;
		this.prix = prix;
		this.description = description;
		this.creation = new Date();
		this.type = type;
	}

	public int getId() {return this.id;}
	public void setId(int id) {this.id = id;}
	
	public int getIdUtilisateur() {return this.id_utilisateur;}
	public void setIdUtilisateur(int id_ut) {this.id_utilisateur = id_ut;}

	public String getTitre() {return this.titre;}
	public void setTitre(String titre) {this.titre = titre;}

	public double getPrix() {return this.prix;}
	public void setPrix(double prix) {this.prix = prix;}

	public String getDescription() {return this.description;}
	public void setDescription(String description) {this.description = description;}

	public Date getCreation() {return this.creation;}
	public void setCreation(Date creation) {this.creation = creation;}

	public String getType() {return this.type;}
	public void setType(String type) {this.type = type;}

	@Override
	public String toString() {
		return "{  \"id\":" + this.id +
				", \"id_utilisateur\":" + this.id_utilisateur +
				", \"titre\":\"" + this.titre + "\"" +
				", \"prix\":\"" + this.prix + "\"" +
				", \"description\":\"" + this.description + "\"" +
				", \"creation\":\"" + this.creation + "\"" +
				", \"type\":\"" + this.type + "\"}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AnnonceBean)) return false;

		AnnonceBean annonce = (AnnonceBean)o;

		if (this.id != annonce.id) return false;
		if (this.id_utilisateur != annonce.id_utilisateur) return false;
		if (!this.titre.equals(annonce.titre)) return false;
		if (!(this.prix == annonce.prix)) return false;
		if (!this.description.equals(annonce.description)) return false;
		if (!this.creation.equals(annonce.creation)) return false;

		return true;
	}
	
}

