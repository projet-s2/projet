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

	public int getId(){
		return this.id;
	}

	public String getNom(){
		return this.nom;
	}

	public String getPrenom(){
		return this.prenom;
	}

	public int getAge(){
		return this.age;
	}

	public char getSexe(){
		return this.sexe;
	}

	public int getScore(){
		return this.score;
	}

	public boolean getNouveau(){
		return this.nouveau;
	}

	public boolean getJoue(){
		return this.joue;
	}

	public String getNiveau(){
		return this.niveau;
	}

	public String toString(){
		return (""+this.id+" "+this.prenom+" "+this.nom);
	}
}
