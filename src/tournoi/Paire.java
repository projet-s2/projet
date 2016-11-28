
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
	private boolean dansMatch;


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
		this.dansMatch = false;
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
	@Override
	public String toString(){
		String txt = this.joueur1.toString() + " et " + this.joueur2.toString() + " Perf : " + this.perf;
		return txt;
	}

	public boolean isDansMatch() {
		return dansMatch;
	}

	public void setDansMatch(boolean dansMatch) {
		this.dansMatch = dansMatch;
	}

	/**
	 * modifie si les joueurs de la paire jouent
	 * @param j vrai s'ils jouent faux sinon
     */
	public void joueursJouent(boolean j){
		this.joueur1.setJoue(j);
		this.joueur2.setJoue(j);

	}

	/**
	 *
	 * @return vrai si les deux joueurs jouent, faux sinon
     */
	public boolean getJoueursJouent(){
		return (this.joueur1.getJoue() && this.joueur2.getJoue());
	}


	/** Facilite l'inversion de deux joueurs dans des paires différentes
	 *
	 * @param joueur1 1 si c'est le premier joueur, autre si c'est le second
	 * @param autre l'autre paire avec laquelle échanger un joueur
	 * @param joueur2 1 si c'est le premier joueur, autre si c'est le second
     */
	public void intervertir(int joueur1, Paire autre, int joueur2){

		// Les joueurs originaux ne jouent plus ensemble
		this.joueur1.getAnciensPart().remove(this.joueur2);
		this.joueur2.getAnciensPart().remove(this.joueur1);
		autre.joueur1.getAnciensPart().remove(this.joueur2);
		autre.joueur2.getAnciensPart().remove(this.joueur1);

		if (joueur1==1){
			Joueur tmp1 = this.getJoueur1();
			if (joueur2==1){
				this.setJoueur1(autre.getJoueur1());
				autre.setJoueur1(tmp1);
			}
			else{
				this.setJoueur2(autre.getJoueur1());
				autre.setJoueur2(tmp1);
			}
		}
		else{
			Joueur tmp1 = this.getJoueur2();
			if (joueur2==1){
				this.setJoueur1(autre.getJoueur1());
				autre.setJoueur1(tmp1);
			}
			else{
				this.setJoueur2(autre.getJoueur1());
				autre.setJoueur2(tmp1);
			}
		}
		// On remet à jour les partenaires
		this.joueur1.ajouterAnciensPart(this.joueur2);
		this.joueur2.ajouterAnciensPart(this.joueur1);
		autre.joueur1.ajouterAnciensPart(this.joueur2);
		autre.joueur2.ajouterAnciensPart(this.joueur1);
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
		* et mise a jour des scores des jouerus de la paire
		* @param score le score de la paire
		*/
	public void setScore(int score) {
		this.score = score;
		//on incrémente le score a partir du score que le joueur avais deja a la base
		this.joueur1.setScore(score+this.joueur1.getScore());
		this.joueur2.setScore(score+this.joueur2.getScore());
	}


	/**
	 *
	 * @return un int qui estime la prioritée de la paire
	 * +1 par membres prio
     */
	public int prio(){
		int prio = 0;
		if ( this.joueur1.getPrio()) {prio++;}
		if ( this.joueur2.getPrio()) {prio++;}


		return prio;
	}
	
	/** Redéfinition de la méthode equals()
		*
		* @param o l'objet à comparer
		* @return true si les deux paires sont égales false sinon
		*/
	@Override
	public boolean equals(Object o){
		if (o instanceof Paire){
			return (this.joueur1.equals(((Paire)o).joueur1)
					&& this.joueur2.equals(((Paire)o).joueur2));
		}
		else {
			return false;
		}
	}

	/** Retourne si la paire est compatible avec un autre (en paramètre)
	 *
	 * @param paire2 la paire a tester
	 * @return booléen 0 : n'est pas compatible / 1 : est compatible
	 */
	public boolean estCompatibleAvec(Paire paire2){
		//On vérifie si les joueurs ont déjà joué ensemble
		return !this.joueur1.aJoueAvec(paire2.joueur1);
	}
}
