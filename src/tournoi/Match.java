package tournoi;

/**Match est la classe représentant un match du tournoi.
 *
 * @author OUAKRIM Yanis, RICHARD Nicolas, ORHON Paul, RIALET Yohann, NIVELAIS Quentin
 *
 * @version 0.1
 */
public class Match {
	private Paire paire1;
	private Paire paire2;
	private Paire vainqueur;

	//les scores effectifs des joueurs
	private int score1;
	private int score2;

	/** Constructeur de la classe Match
		*
		* @param laPaire1 la première paire du match
		* @param laPaire2 la deuxième paire du match
		*
		*/
	public Match(Paire laPaire1, Paire laPaire2){
		this.paire1 = laPaire1;
		this.paire2 = laPaire2;
		this.vainqueur=null;
	}

	/** Retourne la première paire du match
		*
		* @return la première paire du match
		*
		*/
	public Paire getPaire1(){
		return this.paire1;
	}

	/** Retourne la deuxième paire du match
		*
		* @return la deuxième paire du match
		*
		*/
	public Paire getPaire2(){
		return this.paire2;
	}

	/** Redéfinit l'attribut "paire1"
		*
		* @param p1 la première paire du match
		*
		*/
	public void setPaire1(Paire p1){
		this.paire1 = p1;
	}

	/** Redéfinit l'attribut "paire2"
		*
		* @param p2 la deuxième paire du match
		*
		*/
	public void setPaire2(Paire p2){
		this.paire2 = p2;
	}

	/** Redéfinit l'attribut "vainqueur"
		*
		* @param leGagnant le gagnant du match
		*
		*/
	public void setVainqueur(Paire leGagnant){
		this.vainqueur = leGagnant;
	}

	/** Retourne le vainqueur d'un match
		*
		* @return le vainqueur d'un match
		*
		*/
	public Paire getVainqueur(){
		return this.vainqueur;
	}

	/** Détermine le vainqueur d'un match
	  * @param s1 le score de la paire 1
	  * @param s2 le score de la paire 2
		*
		*/
	public void determinerVainqueur(int s1,int s2){
		this.score1 = s1;
		this.score2 = s2;
		if(s1>s2){
			System.out.println("s1>s2");
			this.vainqueur = paire1;
		}else if (s1<s2){
			this.vainqueur = paire2;
		}
		else{
			//égalité
			this.vainqueur = null;
		}
	}

	/** Modifie les scores de la paire gagnante et de la perdante
		*
		* le gagnant gagne la différence entre lui et le perdant
	 	* @param s1 score de la paire 1
	 	* @param s2 score de la paire 2
		*
		*/
		public void modifierScores(int s1,int s2){
			this.determinerVainqueur(s1,s2);
			// On regarde le vainqueur et on modifie en conséquence
				if (this.vainqueur==null){//egalitee
					paire1.setScore(0);
					paire2.setScore(0);

				}
				else{
					if (this.vainqueur.equals(this.paire1)){
						int diff = score1-score2;
						paire1.setScore(diff);
						paire2.setScore(0-diff);
					}
					else {
						int diff = score2-score1;
						paire1.setScore(0-diff);
						paire2.setScore(diff);
					}
				}
			}

	/** Redéfinition de la méthode toString()
		*
		* @return txt l'affichage d'un match
		*
		*/
	@Override
	public String toString(){
		String txt = this.paire1.toString() + " jouent contre "+this.paire2.toString();
		return txt;
	}
	public boolean getPairesJouent(){
		return (paire1.getJoueursJouent() && paire2.getJoueursJouent());
	}

	/**
	 * modifie si les paires du match jouent
	 * @param j vrai si les paires jouent faux sinon
     */
	public void pairesJouent(boolean j){
		paire1.joueursJouent(j);
		paire2.joueursJouent(j);
	}

	/**
	 *
	 * @return vrai si une des paires est prio faux sinon
     */
	public boolean estPrio(){
		return (paire1.estPrio() && paire2.estPrio());
	}
	
	public String readyToBeSaved(){
		return "";
	}

}
