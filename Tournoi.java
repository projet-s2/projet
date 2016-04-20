package tournoi;
import java.math.*;
import liste.Liste;

/**Tournoi est la classe représentant un Tournoi.
 *
 * @author OUAKRIM Yanis, RICHARD Nicolas, ORHON Paul, RIALET Yohann, NIVELAIS Quentin
 *
 * @version 0.1
 */

public class Tournoi{

	private Liste nouveauxJoueurs;
	private Liste anciensJoueurs;
	private Liste terrains;
	private Liste paires;
	private int nbrTerrains;
/** Constructeur de la classe tournoi
	*
	* @param nbrTerrains
	*
	*/
	public Tournoi(int nbrTerrains){
		this.nouveauxJoueurs= new Liste();
		this.anciensJoueurs= new Liste();
		this.terrains= new Liste();
		this.paires= new Liste();
		this.nbrTerrains= nbrTerrains;
	}

/** Retourne la liste de nouveaux joueurs
	*
	* @return Une liste de Tournoi, qui correspond à celle de tous les nouveaux joueurs
	*
	*/
	public Liste getNouveauxJoueurs() {
		return this.nouveauxJoueurs;
	}

	/** Retourne la liste des anciens joueurs
		*
		* @return Une liste de Tournoi, qui correspond à celle de tous les anciens joueurs
		*
		*/
	public Liste getAnciensJoueurs() {
		return this.anciensJoueurs;
	}

	/** Retourne la liste des terrains
		*
		* @return Une liste de Tournoi, qui correspond à celle de tous les terrains
		*
		*/
	public Liste getTerrains() {
		return this.terrains;
	}

	/** Initialise tous les terrains
		*
		*
		*
		*/
	public void initialiserTerrains(){
		for (int i=0; i<this.nbrTerrains; i++){
			this.terrains.add(new Terrain(i+1));
		}
	}

	/** Retourne le nombre de terrains
		*
		* @return Un entier de Tournoi, correspondant au nombre de terrains
		*
		*/
	public int getNbrTerrains() {
		return this.nbrTerrains;
	}

	/** Ajoute un joueur à la liste des joueurs d'un tournoi
		*
		* @param joueur
		*
		*/
	public void ajouterjoueur(Joueur joueur){
		if (joueur.getNouveau()){
			this.nouveauxJoueurs.add(joueur);}
		else{
			this.anciensJoueurs.add(joueur);
		}
	}

	/** Appelée pour démarrer un tour
		*
		*
		*
		*/
	public void demarrerTour(){
		this.creerPaires();
		this.attribuerMatchs();
		//On remet tous les joueurs en attente d'une paire
		for (int i=0; i<this.anciensJoueurs.size(); i++){
			((Joueur)this.anciensJoueurs.get(i)).setJoue(false);
		}
		for (int i=0; i<this.nouveauxJoueurs.size(); i++){
			((Joueur)this.nouveauxJoueurs.get(i)).setJoue(false);
		}
	}

	/** Appelée pour créer la liste des paires d'un tournoi
		*
		*
		*
		*/
	private void creerPaires(){
		//On parcourt les deux listes de joueurs et on crée les paires en conséquence
		int tailleMin;
		Joueur joueur;
		if ( this.anciensJoueurs.size()>=this.nouveauxJoueurs.size() ){
			tailleMin=this.nouveauxJoueurs.size();
			for (int j=0; j<this.anciensJoueurs.size(); j++)
			{
				joueur = ((Joueur)this.anciensJoueurs.get(j));
				for (int i=0; i<tailleMin; i++){
					if (joueur.estCompatibleAvec(((Joueur)this.nouveauxJoueurs.get(i)))){
						this.paires.add(new Paire(joueur,((Joueur)this.nouveauxJoueurs.get(i)),i,i));
						joueur.setJoue(true);
						((Joueur)this.nouveauxJoueurs.get(i)).setJoue(true);
						break;
					}
				}
			}
		}
		else{
			tailleMin=this.anciensJoueurs.size();
			for (int j=0; j<this.nouveauxJoueurs.size(); j++)
			{
				joueur = ((Joueur)this.nouveauxJoueurs.get(j));
				for (int i=0; i<tailleMin; i++){
					if (joueur.estCompatibleAvec(((Joueur)this.anciensJoueurs.get(i)))){
						this.paires.add(new Paire(joueur,((Joueur)this.anciensJoueurs.get(i)),i,i));
						joueur.setJoue(true);
						((Joueur)this.anciensJoueurs.get(i)).setJoue(true);
						break;
					}
				}
			}
		}
	}

	/** Attribue un match à deux paires
		*
		*
		*
		*/
	private void attribuerMatchs(){
		Paire paire1,paire2;
		//On parcourt les terrains et on leur attribue des matchs que l'on crée à partir des paires
		for (int i=0; i<this.nbrTerrains; i++){
			paire1=((Paire)this.paires.get(2*i));
			paire2=((Paire)this.paires.get(2*i+1));
			((Terrain)this.terrains.get(i)).setMatch(new Match(i+1, paire1, paire2));
		}
	}

	/** Appelée pour finir un tour du tournoi
		*
		*
		*
		*/
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
