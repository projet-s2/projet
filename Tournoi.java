package tournoi;

import liste.Liste;

public class Tournoi{

	private Liste nouveauxJoueurs;
	private Liste anciensJoueurs;
	private Liste terrains;
	private Liste paires;
	private int nbrTerrains;

	public Tournoi(int nbrTerrains){
		this.nouveauxJoueurs= new Liste();
		this.anciensJoueurs= new Liste();
		this.terrains= new Liste();
		this.paires= new Liste();
		this.nbrTerrains= nbrTerrains;
	}

	public Liste getNewJoueur() {
		return this.nouveauxJoueurs;
	}

	public void ajouterJoueur(Joueur joueur) {
		if (joueur.getNouveau()==false){
			anciensJoueurs.add(joueur);
		}
		else {
			nouveauxJoueurs.add(joueur);
		}
	}

	public Liste getAncienJoueur() {
		return this.anciensJoueurs;
	}

	public Liste getTerrain() {
		return this.terrains;
	}

	public void initialiserTerrain(){
		for (int i=0; i<this.nbrTerrains; i++){
			this.terrains.add(new Terrain(i+1));
		}
	}

	public int getNbrTerrains() {
		return this.nbrTerrains;
	}

	public void ajouterjoueur(Joueur joueur){
		if (joueur.getNouveau()){
			this.nouveauxJoueurs.add(joueur);}
		else{
			this.anciensJoueurs.add(joueur);
		}
	}

	public void demarrerTour(){
		this.creerPaires();
		this.attribuerMatchs();
	}

	public void creerPaires(){
		//On parcourt les deux listes de joueurs et on crée les paires en conséquence

	}

	public void attribuerMatchs(){
		Paire paire1,paire2;
		//On parcourt les terrains et on leur attribue des matchs que l'on crée à partir des paires
		for (int i=0; i<this.nbrTerrains; i++){
			//paire 1 =???
			//paire 2 =???
			((Terrain)this.terrains.get(i)).setMatch(new Match(i+1, paire1, paire2));
		}
	}

	public void finirTour(){
		//On demande le score des équipes pour chaque terrain
		//On détermine les vainqueurs de chaque match
		//On modifie le score des joueurs en conséquence
	}

}
