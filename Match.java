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
	
	public void determinerVainqueur(){
		if(this.paire1.getScore()>this.paire2.getScore()){
			this.vainqueur=paire1;
		}else if (this.paire1.getScore()<this.paire2.getScore()){
			this.vainqueur=paire1;
		}
	}

}
