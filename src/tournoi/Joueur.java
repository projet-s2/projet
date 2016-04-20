package tournoi;
import liste.Liste;

public class Joueur {
	private int id;
	private String nom;
	private String prenom;
	private int age;
	private char sexe;
	private boolean nouveau;
	private int score;
	private boolean joue;
	private int perf;
	/*
		0 : Débutant
		1 : Intermédiaire
		2 : Confirmé
	 */
	private int niveau;
	private Liste anciensPart;

	public Joueur(int id, String nom, String prenom, int age, char sexe,
			boolean nouveau, int niveau){
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.sexe = sexe;
		this.nouveau = nouveau;
		this.score = 0;
		this.joue = false;
		this.niveau = niveau;
		this.perf = 0;
		//on calcule la performance en fonction de l'age
		this.perf = 80-age;
		this.perf+= niveau*10;
		this.anciensPart = new Liste();
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
	public int getPerf(){
		return this.perf;
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

	public int getNiveau(){
		return this.niveau;
	}

	public String toString(){
		return (""+this.id+" "+this.prenom+" "+this.nom);
	}

	public void setJoue(boolean bool){
		this.joue=bool;
	}

	public void setScore(int score){
		this.score=score;
	}

	public Liste getAnciensPart(){
		return this.anciensPart;
	}
	private boolean aJoueAvec(Joueur j1){
		boolean res = false;
		for (int i=0;i<this.getAnciensPart().size() ; i++) {
			if(((Joueur) this.getAnciensPart().get(i)).equals(j1)) {
				res =true;
			}
		}
		return res;
	}
	public boolean estCompatibleAvec(Joueur joueur){
		//On vérifie si les joueurs ont déjà joué ensemble
		if(this.aJoueAvec(joueur)){
			return false;
		}
		return true;
	}

	public boolean equals(Object o){
		if (o instanceof Joueur){
			return (this.id==((Joueur)o).id
					&& this.nom.equals(((Joueur)o).nom)
					&& this.prenom.equals(((Joueur)o).prenom));
		}
		else return false;
	}
}
