package tournoi;

public class Paire {
	private Joueur joueur1;
	private Joueur joueur2;
	private int id;
	private int tour;
	private int score;

	public Paire(Joueur j1, Joueur j2, int id, int tour){
		this.joueur1 = j1;
		this.joueur2 = j2;
		this.id = id;
		this.tour = tour;
	}

	public Joueur getJoueur1(){
		return this.joueur1;
	}

	public Joueur getJoueur2(){
		return this.joueur2;
	}

	public int getId(){
		return this.id;
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
		return this.joueur1.toString() + " " + this.joueur2.toString();
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

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
