package Beans;

public enum Type {

	IMMOBILIER("Immobilier"),
	VOITURE("Voiture"),
	CUISINE("Cuisine"),
	INFORMATIQUE("Informatique"),
	JARDINAGE("Jardinage"),
	SERVICE("Service"),
	JOUET("Jouet"),
	ANIMAL("Animal"),
	EMPLOI("Emploi"),
	DECORATION("Decoration"),
	VETEMENT("Vetement"),
	JEUVIDEO("Jeu-video"),
	CD("CD"),
	LIVRE("Livre"),
	AUTRE("Autre");

	private String nom = "";

	Type(String nom) {
		this.nom = nom;
	}

	public String toString() {return this.nom;}

	public static Type getType(String nom) {
		if (nom == null) return AUTRE;

		Type[] types = values();
		for(int i = 0; i < types.length; i++)
			if(types[i].toString().equals(nom)) return types[i];
		return AUTRE;

	}

}
