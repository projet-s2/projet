package tournoi;
import java.math.*;
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

	public Liste getNouveauxJoueurs() {
		return this.nouveauxJoueurs;
	}

	public Liste getAnciensJoueurs() {
		return this.anciensJoueurs;
	}

	public Liste getTerrains() {
		return this.terrains;
	}
	
	public void initialiserTerrains(){
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
	
	private void creerPaires(){
		//On parcourt les deux listes de joueurs et on crée les paires en conséquence
		for (int i=0; i<Math.min(this.anciensJoueurs.size(),this.nouveauxJoueurs.size()); i++){
				this.paires.add(new Paire(((Joueur)this.anciensJoueurs.get(i)),((Joueur)this.nouveauxJoueurs.get(i)),i,i));
				((Joueur)this.anciensJoueurs.get(i)).setJoue(true);
				((Joueur)this.nouveauxJoueurs.get(i)).setJoue(true);
		}
	}
	
	private void attribuerMatchs(){
		Paire paire1,paire2;
		//On parcourt les terrains et on leur attribue des matchs que l'on crée à partir des paires
		for (int i=0; i<this.nbrTerrains; i++){
			paire1=((Paire)this.paires.get(2*i));
			paire2=((Paire)this.paires.get(2*i+1));
			((Terrain)this.terrains.get(i)).setMatch(new Match(i+1, paire1, paire2));
		}
	}
	
	public void finirTour(){
		//On demande le score des équipes pour chaque terrain
		for (int i=0; i<this.nbrTerrains; i++){
			((Terrain)this.terrains.get(i)).getMatch().getPaire1().setScore(0);
			((Terrain)this.terrains.get(i)).getMatch().getPaire2().setScore(0);
			//On détermine les vainqueurs de chaque match
			((Terrain)this.terrains.get(i)).getMatch().setVainqueur(((Terrain)this.terrains.get(i)).getMatch().determinerVainqueur());
			//On modifie le score des joueurs en conséquence
			((Terrain)this.terrains.get(i)).getMatch().modifierScores();


			}
	}
	
}
