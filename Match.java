package tournoi;

public class Match {
	private int idMatch;
	private Paire paire1;
	private Paire paire2;
	private Paire vainqueur;
	private int score1;
	private int score2;
	private Terrain terrain;
	
	public Match(int lIdMatch, Paire laPaire1, Paire laPaire2, Terrain leTerrain){
		this.paire1 = laPaire1;
		this.paire2 = laPaire2;
		this.idMatch = lIdMatch;
		this.terrain = leTerrain;
		this.setScore(0,0);
	}
	
	public Paire getPaire1(){
		return this.paire1;
	}
	
	public Paire getPaire2(){
		return this.paire2;
	}
	
	public int getIdMatch(){
		return this.idMatch;
	}
	
	public Terrain getTerrain(){
		return this.terrain;
	}
	
	public int getScore1(){
		return this.score1;
	}
	
	public int getScore2(){
		return this.score2;
	}
	
	public void setPaire1(Paire p1){
		this.paire1 = p1;
	}
	
	public void setPaire2(Paire p2){
		this.paire2 = p2;
	}
	
	public void setIdMatch(int lIdMatch){
		this.idMatch = lIdMatch;
	}
	
	public void setTerrain(Terrain leTerrain){
		this.terrain = leTerrain;
	}
	
	public void setVainqueur(Paire leGagnant){
		this.vainqueur = leGagnant;
	}
	
	public void setScore(int leScore1, int leScore2){
		this.score1 = leScore1;
		this.score2 = leScore2;
	}
}
