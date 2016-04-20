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
	private int perf;
	/*
		Débutant
		Intermédiaire
		Confirmé
	 */
	private String niveau;
	private Liste anciensPart;

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

	public void setJoue(boolean bool){
		this.joue=bool;
	}

	public void setScore(int score){
		this.score=score;
	}

	public Liste getAnciensPart(){
		return this.anciensPart;
	}

	public boolean aJoueAvec(Joueur j1){
		boolean res = false;
		for (int i=0;i<this.getAnciensPart().size() ; i++) {
			if(((Joueur) this.getAnciensPart().get(i)).equals(Joueur j1)) {
				res =true;
			}
		}
		return res;
	}
	public boolean estCompatibleAvec(Joueur joueur){
		boolean res =true;
		//On vérifie si les joueurs ont déjà joué ensemble
		if(!this.aJoueAvec(joueur)){
			res = false;
		}
		return res;
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
