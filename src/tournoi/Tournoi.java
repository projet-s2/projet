package tournoi;

import java.util.ArrayList;

import java.io.*;
import java.util.regex.*;

import exception.*;


/**Tournoi est la classe représentant un Tournoi.
 *
 * @author OUAKRIM Yanis, RICHARD Nicolas, ORHON Paul, RIALET Yohann, NIVELAIS Quentin
 *
 * @version 0.1
 */

public class Tournoi{

	private ArrayList<Joueur> nouveauxJoueurs;
	private ArrayList<Joueur> nouveauxJoueursClasses;
	private ArrayList<Joueur> anciensJoueurs;
	private ArrayList<Joueur> anciensJoueursClasses;
	private ArrayList terrains;
	private ArrayList paires;
	private int nbrTerrains;
	private String nom;

	/**
	 * Constructeur d'un tournoi
	 * @param nbrTerrains le nompbre de terrains disponibles pour le tournoi
	 * @param leNom le nom du tournoi
	 * @throws NomVideException s'il n'y a pas de nom
	 * @throws NbTerrainNeg si le nombre des terrains est <=0
     */
	public Tournoi(int nbrTerrains, String leNom) throws NomVideException, NbTerrainNeg{
		if(leNom.equals("")){
			throw new NomVideException("Nom vide");
		}
		else if(nbrTerrains < 1){
			throw new NbTerrainNeg("Nombre de terrain n�gatif");
		}
		this.nouveauxJoueurs= new ArrayList<Joueur>();
		this.anciensJoueurs= new ArrayList<Joueur>();
		this.terrains= new ArrayList();
		this.paires= new ArrayList();
		this.nbrTerrains= nbrTerrains;
		this.nom = leNom;
		initialiserTerrains();
	}
	public Tournoi(int nbrTerrains) throws NomVideException, NbTerrainNeg{
		this(nbrTerrains, "Sans titre");
	}

	/** Retourne le joueur avec son id
	 * @return
	 *
	 * @return le joueur avec son id
	 */

	public Joueur getJoueur(int Lid){
		Joueur j = null;
		for(int i = 0; i<nouveauxJoueurs.size();i++){
			if(Lid==(this.nouveauxJoueurs.get(i)).getId()){
				j = this.nouveauxJoueurs.get(i);
			}
		}
		for(int i = 0; i<anciensJoueurs.size();i++){
			if(Lid==(this.anciensJoueurs.get(i)).getId()){
				j = this.anciensJoueurs.get(i);
			}
		}
		return j;
	}



/** Retourne la liste de nouveaux joueurs
	*
	* @return la liste des nouveaux adhérents joueurs
	*
	*/
	public ArrayList<Joueur> getNouveauxJoueurs() {
		return this.nouveauxJoueurs;
	}

	/** Retourne la liste des anciens joueurs
		*
		* @return la liste des anciens adhérents joueurs
		*
		*/
	public ArrayList<Joueur> getAnciensJoueurs() {
		return this.anciensJoueurs;
	}

	/** Retourne la liste des terrains
		*
		* @return la liste des terrains
		*
		*/
	public ArrayList getTerrains() {
		return this.terrains;
	}

	/** Initialise les terrains disponibles
	 *
	*/
	public void initialiserTerrains(){
		for (int i=0; i<this.nbrTerrains; i++){
			this.terrains.add(new Terrain(i+1));
		}
	}

	/** Retourne le nombre de terrains
		*
		* @return le nombre de terrains disponibles
		*
		*/
	public int getNbrTerrains() {
		return this.nbrTerrains;
	}

	/** Ajoute un joueur à la liste des joueurs d'un tournoi
		*
		* @param joueur Le joueur que l'on souhaite ajouter
		*
		*/
	public void ajouterJoueur(Joueur joueur){
		if (joueur.getNouveau()){
			this.nouveauxJoueurs.add(joueur);}
		else{
			this.anciensJoueurs.add(joueur);
		}
	}

	public void supprimerJoueur(Joueur joueur)
	{
		if (joueur.getNouveau())
		{
			this.nouveauxJoueurs.remove(joueur);
		}
			else {
			this.anciensJoueurs.remove(joueur);
		}
	}

	/** Appelée pour démarrer un tour,
		* Comprend un mélange des listes de joueurs,
		* La création des paires,
		* La création des matchs à partir des paires
		* @throws TournoiVideException s'il n'y a pas de joueurs
		*
		*/
	public void demarrerTour() throws TournoiVideException{
		trierAnciensJoueurs(0, this.anciensJoueurs.size()-1);
		trierNouveauxJoueurs(0, this.nouveauxJoueurs.size()-1);
		this.creerPaires();
		for (int i=0; i<this.terrains.size(); i++){
			((Terrain)this.terrains.get(i)).setMatch(null);
		}
		this.attribuerMatchs();

	}

	/**
	 * Algorithme de tri rapide des nouveaux joueurs en fonction du score
	 * @param gauche borne inférieure
	 * @param droite borne supérieure
     */
	private void trierNouveauxJoueurs(int gauche, int droite){
		int pivot;
		Joueur tmp;
		if(droite > gauche){
			pivot = (gauche+droite)/2;
			tmp = (this.nouveauxJoueurs.get(gauche));
			this.nouveauxJoueurs.set(gauche, (this.nouveauxJoueurs.get(pivot))) ;
			this.nouveauxJoueurs.set(pivot, tmp) ;
			pivot = gauche;
			for(int i = gauche+1; i<=droite;i++){
				if((this.nouveauxJoueurs.get(i)).getNbMatchJoues() < (this.nouveauxJoueurs.get(gauche)).getNbMatchJoues()){
					pivot++;
					tmp = this.nouveauxJoueurs.get(i);
					this.nouveauxJoueurs.set(i, ( this.nouveauxJoueurs.get(pivot)) );
					this.nouveauxJoueurs.set(pivot, tmp);
				}
			}
			tmp = this.nouveauxJoueurs.get(pivot);
			this.nouveauxJoueurs.set(pivot, ( this.nouveauxJoueurs.get(gauche)));
			this.nouveauxJoueurs.set(gauche, tmp);
			trierNouveauxJoueurs(gauche, pivot-1);
			trierNouveauxJoueurs(pivot+1, droite);
		}
	}
	/**
	 * Algorithme de tri rapide des anciens joueurs en fonction du score
	 * @param gauche borne inférieure
	 * @param droite borne supérieure
	 */
	private void trierAnciensJoueurs(int gauche, int droite){
		int pivot;
		Joueur tmp;
		if(droite > gauche){
			pivot = (gauche+droite)/2;
			tmp = ( this.anciensJoueurs.get(gauche));
			this.anciensJoueurs.set(gauche, (this.anciensJoueurs.get(pivot))) ;
			this.anciensJoueurs.set(pivot, tmp) ;
			pivot = gauche;
			for(int i = gauche+1; i<=droite;i++){
				if((this.anciensJoueurs.get(i)).getNbMatchJoues() < (this.anciensJoueurs.get(gauche)).getNbMatchJoues()){
					pivot++;
					tmp =  this.anciensJoueurs.get(i);
					this.anciensJoueurs.set(i, (this.anciensJoueurs.get(pivot)) );
					this.anciensJoueurs.set(pivot, tmp);
				}
			}
			tmp =  this.anciensJoueurs.get(pivot);
			this.anciensJoueurs.set(pivot, (this.anciensJoueurs.get(gauche)));
			this.anciensJoueurs.set(gauche, tmp);
			trierAnciensJoueurs(gauche, pivot-1);
			trierAnciensJoueurs(pivot+1, droite);
		}
	}
	/** Appelée pour créer la liste des paires d'un tournoi
	 	* On attribue un partenaire uniquement aux joueurs actifs (ceux qui sont disponibles pour jouer)
		* On cherche d'abord à faire jouer ceux qui ont le moins participé
		* On cherche ensuite à faire jouer les joueurs qui n'ont pas joué au tour d'avant (les prios)
		* On fait ensuite jouer les autres joueurs
		* @throws TournoiVideException  s'il n'y a pas de joueurs
		*/
	public void creerPaires() throws TournoiVideException{
		//On parcourt les deux listes de joueurs et on crée les paires en conséquence
		int tailleMin, tailleMax;
		Joueur joueur;
		//On récupères les listes des nouveaux joueurs actifs et des anciens joueurs actif
		if(this.nouveauxJoueurs.size() == 0 && this.anciensJoueurs.size() == 0){
			throw new TournoiVideException("Il n'y a pas de joueurs dans le tournoi");
		}
		ArrayList nouveauxJoueursActifs = new ArrayList();
		ArrayList anciensJoueursActifs = new ArrayList();
		for (int i=0;i<this.nouveauxJoueurs.size();i++){
			if((this.nouveauxJoueurs.get(i)).peutJouer()){
				nouveauxJoueursActifs.add(this.nouveauxJoueurs.get(i));
			}
		}
		for (int i=0;i<this.anciensJoueurs.size();i++){
			if((this.anciensJoueurs.get(i)).peutJouer()){
				anciensJoueursActifs.add(this.anciensJoueurs.get(i));
			}
		}
		this.paires = new ArrayList();
		//On vérifie si le nombre d'anciens est supérieur au nombre de nouveaux
		if (anciensJoueursActifs.size()>=nouveauxJoueursActifs.size()){
			tailleMin=nouveauxJoueursActifs.size();
			tailleMax=anciensJoueursActifs.size();
			//On cherche à créer le maximum de paires ancien/nouveau avec les joueurs qui n'ont pas joué (prios);
			for (int j=0; j<tailleMin; j++)
			// On parcourt les nouveaux joueurs
			{
				joueur = ((Joueur)nouveauxJoueursActifs.get(j));
				//On vérifie que le nouveau joueur est prioritaire et qu'il ne joue pas
				if(joueur.getPrio() && (!joueur.getDansPaire())){
					for (int i=0; i<tailleMax; i++){
						//On cherche un ancien joueur compatible qui ne joue pas
						if (joueur.estCompatibleAvec(((Joueur)anciensJoueursActifs.get(i))) && (!((Joueur)anciensJoueursActifs.get(i)).getDansPaire()) && (((Joueur)anciensJoueursActifs.get(i)).getPrio())){
							//Si on trouve un partenaire possible, on les met ensemble et on les rend non disponibles
							this.paires.add(new Paire(joueur,((Joueur)anciensJoueursActifs.get(i)),i,i));
							joueur.ajouterAnciensPart(((Joueur)anciensJoueursActifs.get(i)));
							((Joueur)anciensJoueursActifs.get(i)).ajouterAnciensPart(joueur);
							joueur.setDansPaire(true);
							((Joueur)anciensJoueursActifs.get(i)).setDansPaire(true);
							break;
						}
					}
				}
			}
			//On cherche à créer le maximum de paires ancien/nouveau avec les joueurs restants;
			for (int j=0; j<tailleMin; j++)
			// On parcourt les nouveaux joueurs
			{
				joueur = ((Joueur)nouveauxJoueursActifs.get(j));
				for (int i=0; i<tailleMax; i++){
					//On cherche un ancien joueur compatible qui ne joue pas
					if (joueur.estCompatibleAvec(((Joueur)anciensJoueursActifs.get(i))) && (!((Joueur)anciensJoueursActifs.get(i)).getDansPaire())){
						//Si on trouve un partenaire possible, on les met ensemble et on les rend non disponibles
						this.paires.add(new Paire(joueur,((Joueur)anciensJoueursActifs.get(i)),i,i));
						joueur.ajouterAnciensPart(((Joueur)anciensJoueursActifs.get(i)));
						((Joueur)anciensJoueursActifs.get(i)).ajouterAnciensPart(joueur);
						joueur.setDansPaire(true);
						((Joueur)anciensJoueursActifs.get(i)).setDansPaire(true);
						break;
					}
				}
			}
		}
		else{
			tailleMin=anciensJoueursActifs.size();
			tailleMax=nouveauxJoueursActifs.size();
			//On cherche à créer le maximum de paires ancien/nouveau avec les joueurs qui n'ont pas joué (prios);
			for (int j=0; j<tailleMin; j++)
			// On parcourt les anciens joueurs
			{
				joueur = ((Joueur)anciensJoueursActifs.get(j));
				//On vérifie que le nouveau joueur est prioritaire et qu'il ne joue pas
				if(joueur.getPrio() && (!joueur.getDansPaire())){
					for (int i=0; i<tailleMax; i++){
						//On cherche un ancien joueur compatible qui ne joue pas
						if (joueur.estCompatibleAvec(((Joueur)nouveauxJoueursActifs.get(i))) && (!((Joueur)nouveauxJoueursActifs.get(i)).getDansPaire()) && (((Joueur)nouveauxJoueursActifs.get(i)).getPrio())){
							//Si on trouve un partenaire possible, on les met ensemble et on les rend non disponibles
							this.paires.add(new Paire(joueur,((Joueur)nouveauxJoueursActifs.get(i)),i,i));
							joueur.ajouterAnciensPart(((Joueur)nouveauxJoueursActifs.get(i)));
							((Joueur)nouveauxJoueursActifs.get(i)).ajouterAnciensPart(joueur);
							joueur.setDansPaire(true);
							((Joueur)nouveauxJoueursActifs.get(i)).setDansPaire(true);
							break;
						}
					}
				}
			}
			//On cherche à créer le maximum de paires ancien/nouveau avec les joueurs restants;
			for (int j=0; j<tailleMin; j++)
			// On parcourt les anciens joueurs
			{
				joueur = ((Joueur)anciensJoueursActifs.get(j));
				for (int i=0; i<tailleMax; i++){
					//On cherche un ancien joueur compatible qui ne joue pas
					if (joueur.estCompatibleAvec(((Joueur)nouveauxJoueursActifs.get(i))) && (!((Joueur)nouveauxJoueursActifs.get(i)).getDansPaire())){
						//Si on trouve un partenaire possible, on les met ensemble et on les rend non disponibles
						this.paires.add(new Paire(joueur,((Joueur)nouveauxJoueursActifs.get(i)),i,i));
						joueur.ajouterAnciensPart(((Joueur)nouveauxJoueursActifs.get(i)));
						((Joueur)nouveauxJoueursActifs.get(i)).ajouterAnciensPart(joueur);
						joueur.setDansPaire(true);
						((Joueur)nouveauxJoueursActifs.get(i)).setDansPaire(true);
						break;
					}
				}
			}
		}
	}

	/** Algorithme de tri rapide adapté pour ranger les performances des paires
	 *
	 * @param gauche L'indice minimal de la liste à trier
	 *
	 * @param droite L'indice maximal de la liste à trier
	 *
	 */

	private void trierPaires(int gauche, int droite){
		int pivot;
		Paire tmp;
		if(droite > gauche){
			pivot = (gauche+droite)/2;
			tmp = ((Paire) this.paires.get(gauche));
			this.paires.set(gauche, ((Paire) this.paires.get(pivot))) ;
			this.paires.set(pivot, tmp) ;
			pivot = gauche;
			for(int i = gauche+1; i<=droite;i++){
				if(((Paire)this.paires.get(i)).getPerf() < ((Paire)this.paires.get(gauche)).getPerf()){
					pivot++;
					tmp = (Paire) this.paires.get(i);
					this.paires.set(i, ((Paire) this.paires.get(pivot)) );
					this.paires.set(pivot, tmp);
				}
			}
			tmp = (Paire) this.paires.get(pivot);
			this.paires.set(pivot, ((Paire) this.paires.get(gauche)));
			this.paires.set(gauche, tmp);
			trierPaires(gauche, pivot-1);
			trierPaires(pivot+1, droite);
		}
	}
	/* On attribue les match à des paires
	 * Ce qui se passe : les paires les plus nulles sont les premières à avoir un terrain d'attribué
	 * Ce qu'on veut faire : chaque paire a la même probabilité de se voir attribuer un terrain
	 * Pour cela
	 * On créée une liste de matchs avec les paires rangées par niveau
	 * On mélange la liste de matchs
	 * On attribue les terrains aux matchs prio
	 * On attribue les terrains aux autres matchs
	 */
	private void attribuerMatchs(){
		//trierPaires(0, this.paires.size()-1);
		//On créer une liste de matchs avec les paires couplées par niveau
		int i;
		ArrayList<Match> matchs = new ArrayList<Match>();
		for(i=0;i<((int)(Math.floor(this.paires.size()/2)));i+=2){
			matchs.add(new Match((Paire) this.paires.get(i),(Paire) this.paires.get(i+1)));
		}

		//On mélange cette liste
		ArrayList<Match> melange = new ArrayList<Match>();
		int indice;
		while(matchs.size() > 0)
		{
			indice = (int) Math.round(Math.random()*(matchs.size()-1));
			Match element = matchs.get(indice);
			melange.add(element);
			matchs.remove(element);
		}
		matchs = melange;


		//On parcourt les terrains et on leur attribue des matchs prios
		int p=0;
		for (i=0; i<Math.min(this.terrains.size(),matchs.size()); i++){
			if((matchs.get(i)).estPrio()){
				( matchs.get(i)).pairesJouent(true);
				((Terrain)this.terrains.get(p)).setMatch( matchs.get(i));
				p++;
			}
		}

		//On parcourt les matchs restants et on leur attribue les terrains restants
		for (i=0; i<Math.min(this.terrains.size(),matchs.size()); i++){
			if(!( matchs.get(i)).estPrio()){
				( matchs.get(i)).pairesJouent(true);
				((Terrain)this.terrains.get(p)).setMatch( matchs.get(i));
				p++;
			}
		}

		//On affiche les matchs pour voir si tout est en ordre
		String res= "";
		for(int i1=0; i1<Math.min(this.terrains.size(),matchs.size());i1++){
			res+=((Terrain)this.terrains.get(i1)).getMatch().toString()+"\n";
		}
		//On parcourt les anciens joueurs et on rend prioritaires ceux qui ne jouent pas
		for(int i1=0; i1<this.anciensJoueurs.size();i1++){
			( this.anciensJoueurs.get(i1)).setPrio(!( this.anciensJoueurs.get(i1)).getJoue());
		}
		for(int i1=0; i1<this.nouveauxJoueurs.size();i1++){
			( this.nouveauxJoueurs.get(i1)).setPrio(!( this.nouveauxJoueurs.get(i1)).getJoue());
		}
	}

	/** Appelée pour finir un tour et mettre à jour les scores
		*
		*
		*
		*/
	public void finirTour(){
		//On vérifie le score des équipes pour chaque terrain
  		for (int i=0; i<this.terrains.size(); i++){
  			//Il faut vérifier qu'un match a bien eu lieu dur le terrain
  			if (((Terrain)this.terrains.get(i)).getMatch()!=null){
				//On détermine les vainqueurs de chaque match
				((Terrain)this.terrains.get(i)).getMatch().modifierScores();
  			}
  		}
		//On remet tous les joueurs en attente d'une paire
		for (int i=0; i<this.anciensJoueurs.size(); i++){
			(this.anciensJoueurs.get(i)).setDansPaire(false);
			(this.anciensJoueurs.get(i)).setJoue(false);
		}
		for (int i=0; i<this.nouveauxJoueurs.size(); i++){
			(this.nouveauxJoueurs.get(i)).setDansPaire(false);
			(this.nouveauxJoueurs.get(i)).setJoue(false);
		}
	}


	/**
	 * pour rentrer les scores d'un match
	 * @param numTerrain le terrain sur lequel s'est déroulé le match
	 * @param scoreP1 le score de la première paire
	 * @param scoreP2 le score de la seconde paire
     */
	public void setScore(int numTerrain, int scoreP1, int scoreP2){
		((Terrain)this.terrains.get(numTerrain)).getMatch().getPaire1().setScore(scoreP1);
		((Terrain)this.terrains.get(numTerrain)).getMatch().getPaire2().setScore(scoreP2);
	}
	public void setScoreManuel(Paire paire,Paire paire2, int scoreP1, int scoreP2){
		this.paires.add(paire);
		this.paires.add(paire2);
		Match match = new Match(paire,paire2);
		match.getPaire1().setScore(scoreP1);
		match.getPaire2().setScore(scoreP2);
		match.determinerVainqueur(scoreP1,scoreP2);
		match.modifierScores();

	}


	/**
	 * Algorithme de tri rapide des anciens joueurs en fonction du score
	 * @param gauche borne inférieure
	 * @param droite borne supérieure
	 */
	public void calculerClassementAnciens(int gauche, int droite){
		int pivot;
		Joueur tmp;
		this.anciensJoueursClasses = this.anciensJoueurs;
		if(droite > gauche){
			pivot = (gauche+droite)/2;
			tmp = ( this.anciensJoueursClasses.get(gauche));
			this.anciensJoueursClasses.set(gauche, (this.anciensJoueursClasses.get(pivot))) ;
			this.anciensJoueursClasses.set(pivot, tmp) ;
			pivot = gauche;
			for(int i = gauche+1; i<=droite;i++){
				if((this.anciensJoueursClasses.get(i)).getScore() < (this.anciensJoueursClasses.get(gauche)).getScore()){
					pivot++;
					tmp =  this.anciensJoueursClasses.get(i);
					this.anciensJoueursClasses.set(i, (this.anciensJoueursClasses.get(pivot)) );
					this.anciensJoueursClasses.set(pivot, tmp);
				}
			}
			tmp =this.anciensJoueursClasses.get(pivot);
			this.anciensJoueursClasses.set(pivot, (this.anciensJoueursClasses.get(gauche)));
			this.anciensJoueursClasses.set(gauche, tmp);
			calculerClassementAnciens(gauche, pivot-1);
			calculerClassementAnciens(pivot+1, droite);
		}
	}
	public ArrayList getClassementAnciens(){
		calculerClassementAnciens(0, this.anciensJoueurs.size()-1);
		ArrayList classem = this.anciensJoueursClasses;
		ArrayList classem2 = new ArrayList();
		for(int i=classem.size()-1;i>=0;i--){
			classem2.add((Joueur)classem.get(i));
		}
		return classem2;
	}

	/**
	 * Algorithme de tri rapide des nouveaux joueurs en fonction du score
	 * @param gauche borne inférieure
	 * @param droite borne supérieure
	 */
	public void calculerClassementNouveaux(int gauche, int droite){
		int pivot;
		Joueur tmp;
		this.nouveauxJoueursClasses = this.nouveauxJoueurs;
		if(droite > gauche){
			pivot = (gauche+droite)/2;
			tmp = ( this.nouveauxJoueursClasses.get(gauche));
			this.nouveauxJoueursClasses.set(gauche, ( this.nouveauxJoueursClasses.get(pivot))) ;
			this.nouveauxJoueursClasses.set(pivot, tmp) ;
			pivot = gauche;
			for(int i = gauche+1; i<=droite;i++){
				if((this.nouveauxJoueursClasses.get(i)).getScore() < (this.nouveauxJoueursClasses.get(gauche)).getScore()){
					pivot++;
					tmp = (Joueur) this.nouveauxJoueursClasses.get(i);
					this.nouveauxJoueursClasses.set(i, ( this.nouveauxJoueursClasses.get(pivot)) );
					this.nouveauxJoueursClasses.set(pivot, tmp);
				}
			}
			tmp = this.nouveauxJoueursClasses.get(pivot);
			this.nouveauxJoueursClasses.set(pivot, ( this.nouveauxJoueursClasses.get(gauche)));
			this.nouveauxJoueursClasses.set(gauche, tmp);
			calculerClassementNouveaux(gauche, pivot-1);
			calculerClassementNouveaux(pivot+1, droite);
		}
	}

	/** Renvoie le classement des nouveaux joueurs
	 * @return le classement des nouveaux adhérents joueurs
	 */
	public ArrayList getClassementNouveaux(){
		calculerClassementNouveaux(0, this.nouveauxJoueurs.size()-1);
		ArrayList classem = this.nouveauxJoueursClasses;
		ArrayList classem2 = new ArrayList();
		for(int i=classem.size()-1;i>=0;i--){
			classem2.add((Joueur)classem.get(i));
		}
		return classem2;
	}

	/** Redéfinition la méthode public toString
	 * @return L'état de toutes les paires à un moment donné
	 */
	@Override
	public String toString(){
		String res= "";
		for(int i=0; i<this.paires.size();i++){
			res +=((Paire)this.paires.get(i)).toString()+"\n";
		}
		return res;
	}
	/** Méthode permettant d'enregistrer un tournoi dans un fichier
	 *
	 * @return Booléen qui permet de savoir si le fichier à été enregistré avec succès
	 */
	public boolean save(String chemin, String nomFichier){
		FileOutputStream fop = null;
		File file;
		String content = this.readyToBeSaved();
		try {

			file = new File(chemin+nomFichier+".mpf");
			fop = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	/*On g�n�re une version textuelle du tournoi pour l'enregistrement
	 * @return Le tournoi sous forme de chaine pouvant être parsée
	 */
	public String readyToBeSaved(){
		String str = "";
		//On insère les anciens joueurs
		String anc = "";
		for(int i = 0;i<this.anciensJoueurs.size();i++){
			anc += (this.anciensJoueurs.get(i)).readyToBeSaved();
		}
		anc = anc.replaceAll("(?m)^", "\t");
		anc = "<ancienJoueurs>"+anc+"\n</anciensJoueurs>";

		//On ins�re les nouveaux joueurs
		String nouv = "";
		for(int i = 0;i<this.nouveauxJoueurs.size();i++){
			nouv += (this.nouveauxJoueurs.get(i)).readyToBeSaved();
		}
		nouv = nouv.replaceAll("(?m)^", "\t");
		nouv = "<nouveauxJoueurs>"+nouv+"\n</nouveauxJoueurs>";

		//On ins�re les terrains
		String terr = "";
		for(int i = 0;i<this.terrains.size();i++){
			terr += ((Terrain)this.terrains.get(i)).readyToBeSaved();
		}
		terr = terr.replaceAll("(?m)^", "\t");
		terr = "<terrains>"+terr+"\n</terrains>";
		str = "<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n"+anc+"\n"+nouv+"\n"+terr;
		return str;
	}

	public void load(){

	}
	public void parserMPF(String str){
		Pattern pattern = Pattern.compile("Hugo");
	    Matcher matcher = pattern.matcher("Hugo Eti�vant");
	}

	/**
	 * pour intervertir facilement deux joueurs qui jouent déjà
	 * @param idJ1 l'id du premier joueur
	 * @param idJ2 l'id du second joueur
     * @return true si l'opération est un succès, false sinon
     */
	public boolean changerJoueurs(int idJ1, int idJ2){
		Joueur joueurChange2=null;
		//On cherche d'abord le joueur qui va prendre la place du premier
		for(int i = 0; i<anciensJoueurs.size();i++){
			Joueur tmp = anciensJoueurs.get(i);
			if(tmp.getId()==idJ2){
				joueurChange2=tmp;
				break;
			}
		}
		if(joueurChange2==null) {
			for (int i = 0; i < nouveauxJoueurs.size(); i++) {
				Joueur tmp = nouveauxJoueurs.get(i);
				if (tmp.getId() == idJ2) {
					joueurChange2 = tmp;
					break;
				}
			}
		}
		//Précaution si on ne trouve pas le deuxieme joueur
		if(joueurChange2==null)
			return false;

		//Différents cas si le joueur 2 joue ou non

		//Si le deuxieme jouer ne joue pas c'est simple
		if(!joueurChange2.getJoue()) {
			//On cherche où joue le premier joueur
			for (int i = 0; i < terrains.size(); i++) {
				Match m = ((Terrain) terrains.get(i)).getMatch();
				Joueur j1 = m.getPaire1().getJoueur1();
				Joueur j2 = m.getPaire1().getJoueur2();
				Joueur j3 = m.getPaire2().getJoueur1();
				Joueur j4 = m.getPaire2().getJoueur2();
				if (j1.getId() == idJ1) {
					//On met le premier joueur sur le banc de touche et le second sur le terrain
					j1.setJoue(false);
					m.getPaire1().setJoueur1(joueurChange2);
					return true;
				} else if (j2.getId() == idJ1) {
					j2.setJoue(false);
					m.getPaire1().setJoueur2(joueurChange2);
					return true;
				} else if (j3.getId() == idJ1) {
					j3.setJoue(false);
					m.getPaire2().setJoueur1(joueurChange2);
					return true;
				} else if (j4.getId() == idJ1) {
					j4.setJoue(false);
					m.getPaire2().setJoueur2(joueurChange2);
					return true;
				}
			}
			//Si on n'a pas trouvé le joueur1 alors c'est un échec
			return false;
		}

		// Si les deux joueurs jouent, on les inverse dans leures paires
		else {
			//On aura besoin de la paire des joueurs et de leur position dans celle ci
			int posJ1=0, posJ2=0;
			Paire paireJ1=null, paireJ2=null;
			// On cherche le joueur 1
			for (int i = 0; i < terrains.size(); i++) {
				Match m = ((Terrain) terrains.get(i)).getMatch();
				Joueur j1 = m.getPaire1().getJoueur1();
				Joueur j2 = m.getPaire1().getJoueur2();
				Joueur j3 = m.getPaire2().getJoueur1();
				Joueur j4 = m.getPaire2().getJoueur2();
				if (j1.getId() == idJ1) {
					paireJ1 = m.getPaire1();
					posJ1=1;
					break;
				} else if (j2.getId() == idJ1) {
					paireJ1 = m.getPaire1();
					posJ1=2;
					break;
				} else if (j3.getId() == idJ1) {
					paireJ1 = m.getPaire2();
					posJ1=1;
					break;
				} else if (j4.getId() == idJ1) {
					paireJ1 = m.getPaire2();
					posJ1=2;
					break;
				}
			}
			//Si on a pas trouvé le joueur 1 rien ne sert de continuer
			if (posJ1==0)
				return false;
			// On cherche le joueur 2
			for (int i = 0; i < terrains.size(); i++) {
				Match m = ((Terrain) terrains.get(i)).getMatch();
				Joueur j1 = m.getPaire1().getJoueur1();
				Joueur j2 = m.getPaire1().getJoueur2();
				Joueur j3 = m.getPaire2().getJoueur1();
				Joueur j4 = m.getPaire2().getJoueur2();
				if (j1.getId() == idJ2) {
					paireJ2 = m.getPaire1();
					posJ2=1;
					break;
				} else if(j2.getId() == idJ2) {
					paireJ2 = m.getPaire1();
					posJ2=2;
					break;
				} else if (j3.getId() == idJ2) {
					paireJ2 = m.getPaire2();
					posJ2=1;
					break;
				} else if (j4.getId() == idJ2) {
					paireJ2 = m.getPaire2();
					posJ2=2;
					break;
				}
			}
			//Ce cas est normalement impossible mais on ne sait jamais
			if (posJ2==0)
				return false;
			// Une fois les paramètres trouvés on lance la méthode adaptée
			paireJ1.intervertir(posJ1,paireJ2,posJ2);
		}

		return true;
	}

	/**
	 * pour obtenir l'id d'un joueur à partir de son nom et de son prénom
	 * @param nomPrenom le nom plus un espace plus le prénom du joueur recherché
	 * @return l'id du joueur recherché ou -1 si non trouvé
     */
	public int chercherJoueur(String nomPrenom) {
		for (int i = 0; i<anciensJoueurs.size(); i++){
			Joueur j = anciensJoueurs.get(i);
			if ((j.getNom()+" "+j.getPrenom()).equals(nomPrenom))
				return j.getId();
		}
		for (int i = 0; i<nouveauxJoueurs.size(); i++){
			Joueur j = nouveauxJoueurs.get(i);
			if ((j.getNom()+" "+j.getPrenom()).equals(nomPrenom))
				return j.getId();
		}
		// Le joueur n'existe pas car les ID sont >=0
		return -1;
	}

	/**
	 * @return le nom du tournoi
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * @param nom le nouveua nom du tournoi
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void parseTournoi(String tournoiXML){

	}
	public void modifierJoueur(int id, String nom, String prenom, int age, boolean sexe,
			boolean nouveau, int niveau) {
		Joueur j = null;
		j = this.getJoueur(id);
		j.modifierJoueur(nom,prenom,age,sexe,nouveau,niveau);
	}
}
