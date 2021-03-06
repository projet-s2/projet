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
		*
		* @return paire1 si la première paire gagne
		* @return paire2 si la deuxième paire gagne
		*
		*/
	public Paire determinerVainqueur(){
		if(this.paire1.getScore()>this.paire2.getScore()){
			return(this.paire1);
		}else if (this.paire1.getScore()<this.paire2.getScore()){
			return(this.paire2);
		}
		else{
			//égalité
			return null;
		}
	}

	/** Modifie les scores de la paire gagnante et de la perdante
		*
		* (1 pt : défaite |
	 	* 2 pt : égalité |
		* 3 pt : victoire)
		*
		*/
		public void modifierScores(){
			// On regarde le vainqueur et on modifie en conséquence
				if (this.determinerVainqueur()==null){
					this.paire1.getJoueur1().setScore(this.paire1.getJoueur1().getScore()+2);
					this.paire1.getJoueur2().setScore(this.paire1.getJoueur2().getScore()+2);
					this.paire2.getJoueur1().setScore(this.paire2.getJoueur1().getScore()+2);
					this.paire2.getJoueur2().setScore(this.paire2.getJoueur2().getScore()+2);

				}
				else{
					if (this.determinerVainqueur().equals(this.paire1)){
						this.paire1.getJoueur1().setScore(this.paire1.getJoueur1().getScore()+3);
						this.paire1.getJoueur2().setScore(this.paire1.getJoueur2().getScore()+3);
						this.paire2.getJoueur1().setScore(this.paire2.getJoueur1().getScore()+1);
						this.paire2.getJoueur2().setScore(this.paire2.getJoueur2().getScore()+1);
					}
					else {
						this.paire1.getJoueur1().setScore(this.paire1.getJoueur1().getScore()+1);
						this.paire1.getJoueur2().setScore(this.paire1.getJoueur2().getScore()+1);
						this.paire2.getJoueur1().setScore(this.paire2.getJoueur1().getScore()+3);
						this.paire2.getJoueur2().setScore(this.paire2.getJoueur2().getScore()+3);
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
