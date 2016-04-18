package tournoi;

public class Paire {
	private Joueur joueur1;
	private Joueur joueur2;
	private int idPaire;
	private int tour;
	
	public Paire newPaire(Joueur j1, Joueur j2, int lIdPaire, int leTour){
		this.joueur1 = j1;
		this.joueur2 = j2;
		this.idPaire = lIdPaire;
		this.tour = leTour;
	}
	
	public Joueur getJoueur1(){
		return this.joueur1;
	}
	
	public Joueur getJoueur2(){
		return this.joueur2;
	}
	
	public int getIdPaire(){
		return this.idPaire;
	}
	
	public int getTour(){
		return this.tour;
	}
	
	public void setJoueur1(Joueur j1){
		this.joueur1 = j1;
	}
	
	public void setJoueur2(Joueur j2){
		this.joueur2 = j2;
	}
	
	public void setTour(int leNumeroTour){
		this.tour = leNumeroTour;
	}
	
	public String toString(){
		return this.joueur1 + " " + this.joueur2;
	}
	
	 
	
}
