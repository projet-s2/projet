package tournoi;

public class Match {
	private int id;
	private Paire paire1;
	private Paire paire2;
	private Paire vainqueur;

	public Match(int id, Paire laPaire1, Paire laPaire2){
		this.paire1 = laPaire1;
		this.paire2 = laPaire2;
		this.id = id;
		this.vainqueur=null;
	}

	public Paire getPaire1(){
		return this.paire1;
	}

	public Paire getPaire2(){
		return this.paire2;
	}

	public int getId(){
		return this.id;
	}

	public void setPaire1(Paire p1){
		this.paire1 = p1;
	}

	public void setPaire2(Paire p2){
		this.paire2 = p2;
	}

	public void setId(int lIdMatch){
		this.id = lIdMatch;
	}

	public void setVainqueur(Paire leGagnant){
		this.vainqueur = leGagnant;
	}
	
	public Paire getVainqueur(){
		return this.vainqueur;
	}
	public Paire determinerVainqueur(){
		if(this.paire1.getScore()>this.paire2.getScore()){
			return(this.paire1);
		}else if (this.paire1.getScore()<this.paire2.getScore()){
			return(this.paire2);
		}
		else{
			return null;
		}
	}
	
	public void modifierScores(){
		if (this.determinerVainqueur().equals(this.paire2)){
			this.paire1.getJoueur1().setScore(this.paire1.getJoueur1().getScore()+1);
			this.paire1.getJoueur2().setScore(this.paire1.getJoueur2().getScore()+1);
			this.paire2.getJoueur1().setScore(this.paire2.getJoueur1().getScore()+3);
			this.paire2.getJoueur2().setScore(this.paire2.getJoueur2().getScore()+3);
		}
		else{
			if (this.determinerVainqueur().equals(this.paire1)){
				this.paire1.getJoueur1().setScore(this.paire1.getJoueur1().getScore()+3);
				this.paire1.getJoueur2().setScore(this.paire1.getJoueur2().getScore()+3);
				this.paire2.getJoueur1().setScore(this.paire2.getJoueur1().getScore()+1);
				this.paire2.getJoueur2().setScore(this.paire2.getJoueur2().getScore()+1);
			}
			else {
				this.paire1.getJoueur1().setScore(this.paire1.getJoueur1().getScore()+2);
				this.paire1.getJoueur2().setScore(this.paire1.getJoueur2().getScore()+2);
				this.paire2.getJoueur1().setScore(this.paire2.getJoueur1().getScore()+2);
				this.paire2.getJoueur2().setScore(this.paire2.getJoueur2().getScore()+2);
			}
		}
	}

}
