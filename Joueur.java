package tournoi;

public class Joueur {
	private int id;
	private String nom;
	private String prenom;
	private int age;
	private char sexe;
	private boolean nouveau;
	private int score;
	private boolean joue;
	private String niveau;
	
	public Joueur(int id, String nom, String prenom, int age, char sexe,
			boolean nouveau, String niveau){
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.sexe = sexe;
		this.nouveau = nouveau;
		this.score = 0;
		this.joue = false;
		this.niveau = niveau;
	}

}
