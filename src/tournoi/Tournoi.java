package tournoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.*;

import exception.*;


/**Tournoi est la classe représentant un Tournoi.
 *
 * @author OUAKRIM Yanis, RICHARD Nicolas, ORHON Paul, RIALET Yohann, NIVELAIS Quentin
 *
 * @version 0.1
 */

public class Tournoi {

	private ArrayList<Joueur> nouveauxJoueurs;
	private ArrayList<Joueur> nouveauxJoueursClasses;
	private ArrayList<Joueur> anciensJoueurs;
	private ArrayList<Joueur> anciensJoueursClasses;
	private ArrayList<Terrain> terrains;
	private ArrayList<Paire> paires;
	private int nbrTerrains;
	private String nom;

	/**
	 * Constructeur d'un tournoi
	 *
	 * @param nbrTerrains le nompbre de terrains disponibles pour le tournoi
	 * @param leNom       le nom du tournoi
	 * @throws NomVideException s'il n'y a pas de nom
	 * @throws NbTerrainNeg     si le nombre des terrains est <=0
	 */
	public Tournoi(int nbrTerrains, String leNom) throws NomVideException, NbTerrainNeg {
		if (leNom.equals("")) {
			throw new NomVideException("Nom vide");
		} else if (nbrTerrains < 1) {
			throw new NbTerrainNeg("Nombre de terrain n�gatif");
		}
		this.nouveauxJoueurs = new ArrayList<Joueur>();
		this.anciensJoueurs = new ArrayList<Joueur>();
		this.terrains = new ArrayList<>();
		this.paires = new ArrayList<>();
		this.nbrTerrains = nbrTerrains;
		this.nom = leNom;
		initialiserTerrains();
	}

	public Boolean tournoisVide(){
		return this.nouveauxJoueurs.size() == 0 && this.anciensJoueurs.size() == 0;

	}

	public Tournoi(int nbrTerrains) throws NomVideException, NbTerrainNeg {
		this(nbrTerrains, "Sans titre");
	}

	/**
	 * Retourne le joueur avec son id
	 *
	 * @return le joueur avec son id
	 */

	public Joueur getJoueur(int Lid) {
		Joueur j = null;
		for (int i = 0; i < nouveauxJoueurs.size(); i++) {
			if (Lid == (this.nouveauxJoueurs.get(i)).getId()) {
				j = this.nouveauxJoueurs.get(i);
			}
		}
		for (int i = 0; i < anciensJoueurs.size(); i++) {
			if (Lid == (this.anciensJoueurs.get(i)).getId()) {
				j = this.anciensJoueurs.get(i);
			}
		}
		return j;
	}



	/**
	 * Retourne la liste de nouveaux joueurs
	 *
	 * @return la liste des nouveaux adhérents joueurs
	 */
	public ArrayList<Joueur> getNouveauxJoueurs() {
		return this.nouveauxJoueurs;
	}

	/**
	 * Retourne la liste des anciens joueurs
	 *
	 * @return la liste des anciens adhérents joueurs
	 */
	public ArrayList<Joueur> getAnciensJoueurs() {
		return this.anciensJoueurs;
	}

	/**
	 * Retourne la liste des anciens joueurs
	 *
	 * @return la liste des anciens adhérents joueurs
	 */
	public ArrayList<Joueur> getAllJoueurs() {
		ArrayList<Joueur> player = this.getNouveauxJoueurs();
		player.addAll(this.getAnciensJoueurs());
		return player;
	}


	/**
	 * Retourne la liste des terrains
	 *
	 * @return la liste des terrains
	 */
	public ArrayList getTerrains() {
		return this.terrains;
	}
	/**
	 * Retourne la liste des terrains
	 * @param i l'index du terrain voulu
	 *
	 * @return un terrain a un index
	 */
	public Terrain getTerrain(int i) {
		return this.terrains.get(i);
	}

	/**
	 * Initialise les terrains disponibles
	 */
	public void initialiserTerrains() {
		for (int i = 0; i < this.nbrTerrains; i++) {
			this.terrains.add(new Terrain(i + 1));
		}
	}

	/**
	 * Retourne le nombre de terrains
	 *
	 * @return le nombre de terrains disponibles
	 */
	public int getNbrTerrains() {
		return this.nbrTerrains;
	}

	/**
	 * Ajoute un joueur à la liste des joueurs d'un tournoi
	 *
	 * @param joueur Le joueur que l'on souhaite ajouter
	 */
	public void ajouterJoueur(Joueur joueur) {
		if (joueur.getNouveau()) {
			this.nouveauxJoueurs.add(joueur);
		} else {
			this.anciensJoueurs.add(joueur);
		}
	}

	public void supprimerJoueur(Joueur joueur) {
		if (joueur.getNouveau()) {
			this.nouveauxJoueurs.remove(joueur);
		} else {
			this.anciensJoueurs.remove(joueur);
		}
	}

	/**
	 * Appelée pour démarrer un tour,
	 * Comprend un mélange des listes de joueurs,
	 * La création des paires,
	 * La création des matchs à partir des paires
	 *
	 * @throws TournoiVideException s'il n'y a pas de joueurs
	 */
	public void demarrerTour() throws TournoiVideException {
		trierAnciensJoueurs();
		trierNouveauxJoueurs();
		this.creerPaires();
		for (int i = 0; i < this.terrains.size(); i++) {
			(this.terrains.get(i)).setMatch(null);
		}
		this.attribuerMatchs();

	}

	/**
	 *Tri des nouveau joueurs par scores
	 */
	private void trierNouveauxJoueurs() {
		Collections.sort(this.nouveauxJoueurs, new ComparateurJoueurScore());
	}

	/**
	 * Tri des Anciens joueurs en fonction du score
	 *

	 */
	private void trierAnciensJoueurs() {
		Collections.sort(this.anciensJoueurs,new ComparateurJoueurScore());
	}

	/**
	 * Appelée pour créer la liste des paires d'un tournoi
	 * On attribue un partenaire uniquement aux joueurs actifs (ceux qui sont disponibles pour jouer)
	 * On cherche d'abord à faire jouer ceux qui ont le moins participé
	 * On cherche ensuite à faire jouer les joueurs qui n'ont pas joué au tour d'avant (les prios)
	 * On fait ensuite jouer les autres joueurs
	 *
	 * @throws TournoiVideException s'il n'y a pas de joueurs
	 */
	private void creerPaires() throws TournoiVideException {
		//On met tout les joueurs comme n'appartenant pas a une paire
		this.viderGetDansPaire();
		//On parcourt les deux listes de joueurs et on crée les paires en conséquence
		int tailleMin, tailleMax;
		//On récupères les listes des nouveaux joueurs actifs et des anciens joueurs actif
		if (this.nouveauxJoueurs.size() == 0 && this.anciensJoueurs.size() == 0) {
			throw new TournoiVideException("Il n'y a pas de joueurs dans le tournoi");
		}
		ArrayList<Joueur> nouveauxJoueursActifs = new ArrayList<>();
		ArrayList<Joueur> anciensJoueursActifs = new ArrayList<>();
		for (Joueur nouveauJoueur : this.nouveauxJoueurs) {
			if (nouveauJoueur.peutJouer()) {
				nouveauxJoueursActifs.add(nouveauJoueur);
			}
		}
		for (Joueur ancienJoueur : this.anciensJoueurs) {
			if (ancienJoueur.peutJouer()) {
				anciensJoueursActifs.add(ancienJoueur);
			}
		}
		this.paires = new ArrayList<>();
		//en triant les listes en fonction du nombre de match joués en parcourant les listes on prendra en premier
		//les joueurs avec le moins de matchs à leurs actif
		Collections.sort(anciensJoueursActifs, new ComparateurjoueurParNbMatches());
		Collections.sort(nouveauxJoueursActifs, new ComparateurjoueurParNbMatches());

		ArrayList<Joueur> tailleMinList = new ArrayList<>();
		ArrayList<Joueur> tailleMaxList = new ArrayList<>();

		if (anciensJoueursActifs.size() >= nouveauxJoueursActifs.size()) {
			tailleMinList = nouveauxJoueursActifs;
			tailleMaxList = anciensJoueursActifs;
		} else {
			tailleMinList = anciensJoueursActifs;
			tailleMaxList = nouveauxJoueursActifs;
		}

		//On cherche à créer le maximum de paires ancien/nouveau avec les joueurs qui n'ont pas joué (prios);
		for (Joueur joueur1 : tailleMinList) {
			if (joueur1.getPrio() && !joueur1.getDansPaire()) {
				for (Joueur joueur2: tailleMaxList) {
					if (joueur1.estCompatibleAvec(joueur2) && !joueur2.getDansPaire()) {
						this.paires.add(new Paire(joueur1, joueur2));
						// TODO : ajouter les joueurs dans la liste respective des anciens joueur à la validation du match
						joueur1.setDansPaire(true);
						joueur2.setDansPaire(true);
						break;
					}
				}
			}
		}
		//On cherche à créer le maximum de paires ancien/nouveau avec les joueurs restants qui n'ont encore jamais joués ensembles;
		for (Joueur joueur1 : tailleMinList) {
			//Cette fois on ne vérifie pas s'il est prio
			if (!joueur1.getDansPaire()) {
				for (Joueur joueur2 : tailleMaxList) {
					if (joueur1.estCompatibleAvec(joueur2) && !joueur2.getDansPaire()) {
						this.paires.add(new Paire(joueur1, joueur2));
						// TODO : ajouter les joueurs dans la liste respective des anciens joueur à la validation du match
						joueur1.setDansPaire(true);
						joueur2.setDansPaire(true);
						break;
					}
				}
			}
		}
		//On remplit avec des joueurs même s'il ont déjà joué ensemble
		for (Joueur joueur1: tailleMinList) {
			for (Joueur joueur2 : tailleMaxList) {
				if (!joueur1.getDansPaire() && !joueur2.getDansPaire()) {
					this.paires.add(new Paire(joueur1, joueur2));
					// TODO : ajouter les joueurs dans la liste respective des anciens joueur à la validation du match
					joueur1.setDansPaire(true);
					joueur2.setDansPaire(true);
				}
			}
		}
	}

	/**
	 * Tris des paires par ordre de pref
	 */

	private void trierPaires() {
		Collections.sort(this.paires, new ComparateurPairePrio());
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
	private void attribuerMatchs() {
		trierPaires();
		//On créer une liste de matchs avec les paires couplées par niveau
		int i;
		ArrayList<Match> matchs = new ArrayList<Match>();
		// prise en compte de qui a deja jouer avec qui
		for(Paire paire1 : this.paires)
		{
			for(Paire paire2 : this.paires)
			{
				//les paires sont differentes, compatibles et libres
				if(paire1!=paire2 && paire1.estCompatible(paire2) && !paire2.isDansMatch()){
					matchs.add(new Match(paire1, paire2));
					paire1.setDansMatch(true);
					paire2.setDansMatch(true);
				}


			}
		}
		//on reparcours pour associer les dernières paires meme si elles ne tsont pas compatibles
		for(Paire paire1 : this.paires)
		{
			for(Paire paire2 : this.paires)
			{
				//les paires sont differentes, compatibles et libres
				if(paire1!=paire2 && !paire2.isDansMatch()){
					matchs.add(new Match(paire1, paire2));
					paire1.setDansMatch(true);
					paire2.setDansMatch(true);
				}


			}
		}
		//range les match en untilisant prio comme comparateur
		Collections.sort(matchs,new ComparateurMatchPrio());

		//on vide les terrains
		terrains.clear();
		//On parcourt les matchs et on leur attribue les terrains restants
		for( i=0;i<matchs.size()&&i<this.nbrTerrains;i++ ){
			terrains.add(i, new Terrain(i,matchs.get(i)));
		}


		//On affiche les matchs pour voir si tout est en ordre
		String res = "";
		for (int i1 = 0; i1 < Math.min(this.terrains.size(), matchs.size()); i1++) {
			res += this.terrains.get(i1).getMatch().toString() + "\n";
		}
		//On parcourt les anciens joueurs et on rend prioritaires ceux qui ne jouent pas
		for (int i1 = 0; i1 < this.anciensJoueurs.size(); i1++) {
			(this.anciensJoueurs.get(i1)).setPrio(!(this.anciensJoueurs.get(i1)).getJoue());
		}
		for (int i1 = 0; i1 < this.nouveauxJoueurs.size(); i1++) {
			(this.nouveauxJoueurs.get(i1)).setPrio(!(this.nouveauxJoueurs.get(i1)).getJoue());
		}
	}

	/**
	 * Lance les methodes de créations de paires
	 */
	public void nouveauTour() throws TournoiVideException {
		this.creerPaires();
		for (Paire paire: paires) {
			System.out.println(paire);
		}
		this.attribuerMatchs();

	}

	/**
	 * Appelée pour finir un tour et mettre à jour les scores
	 */
	public void finirTour() {
		//On vérifie le score des équipes pour chaque terrain
		for (int i = 0; i < this.terrains.size(); i++) {
			//Il faut vérifier qu'un match a bien eu lieu dur le terrain
			if (this.terrains.get(i).getMatch() != null) {
				//On détermine les vainqueurs de chaque match
				//((Terrain)this.terrains.get(i)).getMatch().modifierScores();
			}
		}
		//On remet tous les joueurs en attente d'une paire
		for (int i = 0; i < this.anciensJoueurs.size(); i++) {
			(this.anciensJoueurs.get(i)).setDansPaire(false);
			(this.anciensJoueurs.get(i)).setJoue(false);
		}
		for (int i = 0; i < this.nouveauxJoueurs.size(); i++) {
			(this.nouveauxJoueurs.get(i)).setDansPaire(false);
			(this.nouveauxJoueurs.get(i)).setJoue(false);
		}
	}


	/**
	 * pour rentrer les scores d'un match lors qu'il est cré de manière automatique
	 *
	 * @param numTerrain le terrain sur lequel s'est déroulé le match
	 * @param scoreP1    le score de la première paire
	 * @param scoreP2    le score de la seconde paire
	 */
	public void setScore(int numTerrain, int scoreP1, int scoreP2) {
		(this.terrains.get(numTerrain)).getMatch().getPaire1().setScore(scoreP1);
		( this.terrains.get(numTerrain)).getMatch().getPaire2().setScore(scoreP2);
		this.terrains.get(numTerrain).getMatch().getPaire1().getJoueur1().ajouterAnciensPart(this.terrains.get(numTerrain).getMatch().getPaire1().getJoueur2());
		this.terrains.get(numTerrain).getMatch().getPaire1().getJoueur2().ajouterAnciensPart(this.terrains.get(numTerrain).getMatch().getPaire1().getJoueur1());
		this.terrains.get(numTerrain).getMatch().getPaire2().getJoueur1().ajouterAnciensPart(this.terrains.get(numTerrain).getMatch().getPaire2().getJoueur2());
		this.terrains.get(numTerrain).getMatch().getPaire2().getJoueur2().ajouterAnciensPart(this.terrains.get(numTerrain).getMatch().getPaire2().getJoueur1());

	}

	/**
	 * pour rentrer les scores d'un match lorsqu'il est ajouté de manière manuelle
	 *
	 * @param paire   la première paire (celle de droite dans la vue)
	 * @param paire2  la deuxième paire (celle de gauche dans la vue)
	 * @param scoreP1 le score de la première paire
	 * @param scoreP2 le score de la seconde paire
	 */
	public void ajouterMatch(Paire paire, Paire paire2, int scoreP1, int scoreP2) {
		this.paires.add(paire);
		this.paires.add(paire2);
		//comme les joueurs ont joués ils ne sont pas prioritaires
		paire.getJoueur1().setPrio(false);
		paire.getJoueur2().setPrio(false);
		paire2.getJoueur1().setPrio(false);
		paire2.getJoueur2().setPrio(false);
		Match match = new Match(paire, paire2);
		match.modifierScores(scoreP1, scoreP2);

	}



	public ArrayList getClassementAnciens() {
		this.trierAnciensJoueurs();
		return this.anciensJoueurs;
	}


	/**
	 * Renvoie le classement des nouveaux joueurs
	 *
	 * @return le classement des nouveaux adhérents joueurs
	 */
	public ArrayList getClassementNouveaux() {
		trierNouveauxJoueurs();
		return this.nouveauxJoueurs;
	}

	/**
	 * Redéfinition la méthode public toString
	 *
	 * @return L'état de toutes les paires à un moment donné
	 */
	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < this.paires.size(); i++) {
			res += this.paires.get(i).toString() + "\n";
		}
		return res;
	}


	public void parserMPF(String str) {
		Pattern pattern = Pattern.compile("Hugo");
		Matcher matcher = pattern.matcher("Hugo Eti�vant");
	}

	/**
	 * pour intervertir facilement deux joueurs qui jouent déjà
	 * On selectionne le premier Joueur et on souhaite le remplacer par le deuxième. On regarde si le deuxième joue ou pas
	 *
	 * @param idJ1 l'id du premier joueur
	 * @param idJ2 l'id du second joueur
	 * @return true si l'opération est un succès, false sinon
	 */
	public Boolean changerJoueurs(int idJ1, int idJ2) {
		if(idJ1==idJ2){
			return false;
		}
		//On regarde si le joueur de remplacement est attribué a une paire
		if(this.getJoueur(idJ2).getDansPaire()){
			//si le deuxieme joeur est J1 dans sa paire
			if(this.getPaireContenant(idJ2).getJoueur1()==this.getJoueur(idJ2)){
				this.getPaireContenant(idJ2).setJoueur1(this.getJoueur(idJ1));
				//si le premier joueur etqi jouer 1
				if(this.getPaireContenant(idJ1).getJoueur1()==this.getJoueur(idJ1)){
					this.getPaireContenant(idJ1).setJoueur1(this.getJoueur(idJ2));
				}
				//si le premier joueur etai jouer 2
				if(this.getPaireContenant(idJ1).getJoueur2()==this.getJoueur(idJ1)){
					this.getPaireContenant(idJ1).setJoueur2(this.getJoueur(idJ2));
				}
			}
			//si  deuxieme joeur est J2 dans sa paire
			if(this.getPaireContenant(idJ2).getJoueur2()==this.getJoueur(idJ2)){
				this.getPaireContenant(idJ2).setJoueur2(this.getJoueur(idJ1));
				//si le premier joueur etai jouer 1
				if(this.getPaireContenant(idJ1).getJoueur1()==this.getJoueur(idJ1)){
					this.getPaireContenant(idJ1).setJoueur1(this.getJoueur(idJ2));
				}
				//si le premier joueur etai jouer 2
				if(this.getPaireContenant(idJ1).getJoueur2()==this.getJoueur(idJ1)){
					this.getPaireContenant(idJ1).setJoueur2(this.getJoueur(idJ2));
				}
			}
		}
		else{
			//si le premier joueur etqi jouer 1
			if(this.getPaireContenant(idJ1).getJoueur1()==this.getJoueur(idJ1)){
				this.getPaireContenant(idJ1).setJoueur1(this.getJoueur(idJ2));
				this.getJoueur(idJ2).setDansPaire(true);
				this.getJoueur(idJ1).setDansPaire(false);
			}
			//si le premier joueur etai jouer 2
			if(this.getPaireContenant(idJ1).getJoueur2()==this.getJoueur(idJ1)){
				this.getPaireContenant(idJ1).setJoueur2(this.getJoueur(idJ2));
				this.getJoueur(idJ2).setDansPaire(true);
				this.getJoueur(idJ1).setDansPaire(false);
			}

		}
		return true;

	}

	/**
	 * renvoie la paire contenant un joueur donné null sinon
	 * @param idJ l'id du  joueur

	 * @return null si le joueur n'est pas dans une paire la paire sinon
	 */
	public Paire getPaireContenant(int idJ){
		for (int i = 0; i < this.paires.size(); i++) {
			if (idJ == this.paires.get(i).getJoueur1().getId() || idJ == this.paires.get(i).getJoueur2().getId()) {
				return this.paires.get(i);
			}
		}
		return null;

	}

	/**
	 * pour obtenir l'id d'un joueur à partir de son nom et de son prénom
	 *
	 * @param nomPrenom le nom plus un espace plus le prénom du joueur recherché
	 * @return l'id du joueur recherché ou -1 si non trouvé
	 */
	public int chercherJoueur(String nomPrenom) {
		for (int i = 0; i < anciensJoueurs.size(); i++) {
			Joueur j = anciensJoueurs.get(i);
			if ((j.getNom() + " " + j.getPrenom()).equals(nomPrenom))
				return j.getId();
		}
		for (int i = 0; i < nouveauxJoueurs.size(); i++) {
			Joueur j = nouveauxJoueurs.get(i);
			if ((j.getNom() + " " + j.getPrenom()).equals(nomPrenom))
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

	public void parseTournoi(String tournoiXML) {

	}

	public void modifierJoueur(int id, String nom, String prenom, int age, boolean sexe,
							   boolean nouveau, int niveau) {
		Joueur j = null;
		j = this.getJoueur(id);
		j.modifierJoueur(nom, prenom, age, sexe, nouveau, niveau);
	}

	/**
	 * remet a zero toutes les données de jeux (matches paires scores ect)
	 */
	public void resetAll() {
		//clear historique des paires
		this.paires.clear();
		//clear scores
		for (int i = 0; i < anciensJoueurs.size(); i++) {
			Joueur j = anciensJoueurs.get(i);
			j.setScore(0);


		}
		for (int i = 0; i < nouveauxJoueurs.size(); i++) {
			Joueur j = nouveauxJoueurs.get(i);
			j.setScore(0);


		}
	}
	public void statusJoueur(Joueur j){
		j.setPeutJouer(!j.peutJouer());
	}

	/**
	 * @return le nouveau avec le meilleur score
	 */
	public Joueur meilleurNouveau(){
		return (Joueur)this.getClassementNouveaux().get(0);
		//todo revoir ces deux methodes
	}
	/**
	 * @return l' ancien avec le pire score
	 */
	public Joueur meilleurAncien(){
		return (Joueur)this.getClassementAnciens().get(this.getClassementAnciens().size()-1);
	}

	/**
	 * remet a faux le getdans paire des joueurs. Permet de liberer les joueurs pour l'algo des paires
	 */
	public void viderGetDansPaire(){
		for (int i = 0; i < anciensJoueurs.size(); i++) {
			anciensJoueurs.get(i).setDansPaire(false);

		}
		for (int i = 0; i < nouveauxJoueurs.size(); i++) {
			nouveauxJoueurs.get(i).setDansPaire(false);

		}
	}

	public ArrayList<Paire> getPaires() {
		return this.paires;
	}

}

