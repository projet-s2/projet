
package tournoi;

/**Paire est la classe représentant une paire de joueurs.
 *
 * @author OUAKRIM Yanis, RICHARD Nicolas, ORHON Paul, RIALET Yohann, NIVELAIS Quentin
 *
 * @version 0.1
 */
public class Paire {
	private Joueur joueur1;
	private Joueur joueur2;
	private int id;
	private int tour;
	private int score;
	private int perf;

	/** Constructeur de la classe Paire
		*
		* @param j1 le premier joueur de la paire
		* @param j2 le deuxième joueur de la paire
		* @param id l'id de la paire
		* @param tour le tour de jeu de la paire
		*
		*/
	public Paire(Joueur j1, Joueur j2, int id, int tour){
		this.joueur1 = j1;
		this.joueur2 = j2;
		this.id = id;
		this.tour = tour;
		this.perf = j1.getPerf()+j2.getPerf();
		this.joueursJouent(false);
	}
	public Paire(Joueur j1, Joueur j2){
		this(j1, j2, 0, 0);
	}

	/** Retourne la performance de la paire (celles des joueurs additionnées)
		*
		* @return La performance de la paire
		*/
	public int getPerf(){
		return this.perf;
	}
	
	/** Retourne le premier joueur de la paire
		*
		* @return joueur1 le premier joueur de la paire
		*/
	public Joueur getJoueur1(){
		return this.joueur1;
	}

	/** Retourne le deuxième joueur de la paire
		*
		* @return joueur2 le deuxième joueur de la paire
		*/
	public Joueur getJoueur2(){
		return this.joueur2;
	}

	/** Retourne l'id de la paire
		*
		* @return id l'id de la paire
		*/
	public int getId(){
		return this.id;
	}

	/** Retourne le tour de la paire
		*
		* @return tour le tour de la paire
		*/
	public int getTour(){
		return this.tour;
	}

	/** Redéfinition de l'attribut "joueur1"
		*
		* @param j1 le premier joueur de la paire
		*/
	public void setJoueur1(Joueur j1){
		this.joueur1 = j1;
	}

	/** Redéfinition de l'attribut "joueur2"
		*
		* @param j2 le deuxième joueur de la paire
		*/
	public void setJoueur2(Joueur j2){
		this.joueur2 = j2;
	}

	/** Redéfinition de l'attribut "tour"
		*
		* @param leNumeroTour le tour de la paire
		*/
	public void setTour(int leNumeroTour){
		this.tour = leNumeroTour;
	}

	/** Redéfinition de la méthode toString()
		*
		* @return txt l'affichage d'une paire
		*/
	public String toString(){
		String txt = this.joueur1.toString() + " et " + this.joueur2.toString() + " Perf : " + this.perf;
		return txt;
	}

	public void joueursJouent(boolean j){
		this.joueur1.setJoue(j);
		this.joueur2.setJoue(j);
	}
	public boolean getJoueursJouent(){
		return (this.joueur1.getJoue() && this.joueur2.getJoue());
	}
	
	public void ajouterMatchJoue(){
		this.joueur1.ajouterMatchJoue();
		this.joueur2.ajouterMatchJoue();
	}

	/** Retourne le score de la paire
		*
		* @return score le score de la paire
		*/
	public int getScore() {
		return this.score;
	}

	/** Redéfinition de l'attribut "score"
		*
		* @param score le score de la paire
		*/
	public void setScore(int score) {
		this.score = score;
	}

	public boolean estPrio(){
		return (this.joueur1.getPrio() || this.joueur2.getPrio());
	}
	
	/** Redéfinition de la méthode equals()
		*
		* @param o l'objet à comparer
		* @return true si les deux paires sont égales
		*/
	public boolean equals(Object o){
		if (o instanceof Paire){
			return (this.joueur1.equals(((Paire)o).joueur1)
					&& this.joueur2.equals(((Paire)o).joueur2));
		}
		else {
			return false;
		}
	}
}