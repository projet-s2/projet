package tournoi;
import liste.Liste;

/**Joueur est la classe représentant un joueur du tournoi.
 *
 * @author OUAKRIM Yanis, RICHARD Nicolas, ORHON Paul, RIALET Yohann, NIVELAIS Quentin
 *
 * @version 0.1
 */
public class Joueur {
	private int id;
	private String nom;
	private String prenom;
	private int age;
	// 0 : homme
	// 1 : femme
	private boolean sexe;
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
	private boolean prio;


	/** Constructeur de la classe Joueur
		*
		* @param id l'id du joueur
		* @param nom le nom du joueur
		* @param prenom le prénom du joueur
		* @param age l'âge du joueur
		* @param sexe le sexe du joueur
		* @param nouveau 0 : joueur ancien / 1 : joueur nouveau
		* @param niveau le niveau du joueur ()
		*
		*/
	public Joueur(int id, String nom, String prenom, int age, boolean sexe,
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
		if(sexe){
			this.perf-=40;
		}
		this.prio = true;
		this.anciensPart = new Liste();
	}
	// Tests
		public Joueur(int id, boolean sexe, boolean nouv){
			this(id, "Bon", "Jean", (20+id), sexe, nouv, (id%3));
		}

	/** Retourne l'id d'un joueur
		*
		* @return id l'id du joueur
		*/
	public int getId(){
		return this.id;
	}
	public void ajouterAnciensPart(Joueur j){
		this.anciensPart.add(j);
	}
	/** Retourne le nom d'un joueur
		*
		* @return nom le nom du joueur
		*/
	public String getNom(){
		return this.nom;
	}

	/** Retourne le prénom d'un joueur
		*
		* @return prenom le prénom du joueur
		*/
	public String getPrenom(){
		return this.prenom;
	}

	/** Retourne l'âge d'un joueur
		*
		* @return age l'âge du joueur
		*/
	public int getAge(){
		return this.age;
	}

	/** Retourne le sexe d'un joueur
		*
		* @return sexe le sexe du joueur
		*/
	public boolean getSexe(){
		return this.sexe;
	}

	/** Retourne le score d'un joueur
		*
		* @return score le score du joueur
		*/
	public int getScore(){
		return this.score;
	}

	/** Retourne l'ancienneté d'un joueur
		*
		* @return nouveau 0 : joueur ancien / 1 : joueur nouveau
		*/
	public boolean getNouveau(){
		return this.nouveau;
	}

	/** Retourne si le joueur joue ou non
		*
		* @return joue 0 : ne joue pas / 1 : joue
		*/
	public boolean getJoue(){
		return this.joue;
	}

	public boolean getPrio(){
		return this.prio;
	}
	public void setPrio(boolean pr){
		this.prio = pr;
	}
	/** Retourne le niveau d'un joueur
		*
		* @return niveau le niveau du joueur
		*/
	public int getNiveau(){
		return this.niveau;
	}
	
	/** Retourne l'indice de performance du joueur
	 * @return l'indice de performance du joueur
	 */
	public int getPerf(){
		return this.perf;
	}
	
	/** Redéfinition de la méthode toString()
		*
		* @return txt l'affichage d'un joueur
		*/
	public String toString(){
		String sx="un homme";
		String prio="un homme";
		if(this.sexe){
			sx= "une femme";
		}
		if(this.prio){
			prio= "Oui";
		}
		String txt = this.prenom + "" + this.nom +" a "+ this.age+ " ans et est "+sx+ " Prio : " +prio;
		return txt;
	}

	/** Redéfinit l'attribut "joue"
		*
		* @param bool 0 : le joueur ne joue pas / 1 : le joueur joue
		*/
	public void setJoue(boolean bool){
		this.joue=bool;
	}

	/** Redéfinit l'attribut "score"
		*
		* @param score le score d'un joueur
		*/
	public void setScore(int score){
		this.score=score;
	}

	/** Retourne tous les anciens partenaires d'un joueur
		*
		* @return anciensPart la liste de tous les anciens partenaires d'un joueur
		*/
	public Liste getAnciensPart(){
		return this.anciensPart;
	}

	/** Retourne si le joueur a déjà joué avec un autre (en paramètre)
		*
		* @param j1 le joueur à tester
		* @return res 0 : ils n'ont jamais joué ensemble / 1 : ils ont joué ensemble
		*/
	private boolean aJoueAvec(Joueur j1){
		boolean res = false;
		for (int i=0;i<this.getAnciensPart().size() ; i++) {
			if(((Joueur) this.getAnciensPart().get(i)).equals(j1)) {
				res =true;
			}
		}
		return res;
	}

	/** Retourne si le joueur est compatible avec un autre (en paramètre)
		*
		* @param joueur le joueur à tester
		* @return booléen 0 : n'est pas compatible / 1 : est compatible
		*/
	public boolean estCompatibleAvec(Joueur joueur){
		//On vérifie si les joueurs ont déjà joué ensemble
		if(this.aJoueAvec(joueur)){
			return false;
		}
		return true;
	}

	/** Redéfinition de la méthode equals()
		*
		* @param o l'objet à comparer
		* @return booléen 0 : ils ne sont pas égaux / 1 : ils sont égaux
		*/
	public boolean equals(Object o){
		if (o instanceof Joueur){
			return (this.id==((Joueur)o).id
					&& this.nom.equals(((Joueur)o).nom)
					&& this.prenom.equals(((Joueur)o).prenom));
		}
		else return false;
	}

}
