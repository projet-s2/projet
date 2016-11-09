package tournoi;

import java.util.ArrayList;

import java.io.*;
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
		this.terrains = new ArrayList();
		this.paires = new ArrayList();
		this.nbrTerrains = nbrTerrains;
		this.nom = leNom;
		initialiserTerrains();
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
	 * Retourne la liste des terrains
	 *
	 * @return la liste des terrains
	 */
	public ArrayList getTerrains() {
		return this.terrains;
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
			((Terrain) this.terrains.get(i)).setMatch(null);
		}
		this.attribuerMatchs();

	}

	/**
	 *Tri des nouveau joueurs par scores
	 */
	private void trierNouveauxJoueurs() {
		Collections.sort(this.nouveauxJoueurs,new ComparateurJoueurScore());
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
	public void creerPaires() throws TournoiVideException {
		//On met tout les joueurs comme n'appartenant pas a une paire
		this.viderGetDansPaire();
		//On parcourt les deux listes de joueurs et on crée les paires en conséquence
		int tailleMin, tailleMax;
		Joueur joueur;
		//On récupères les listes des nouveaux joueurs actifs et des anciens joueurs actif
		if (this.nouveauxJoueurs.size() == 0 && this.anciensJoueurs.size() == 0) {
			throw new TournoiVideException("Il n'y a pas de joueurs dans le tournoi");
		}
		ArrayList<Joueur> nouveauxJoueursActifs = new ArrayList();
		ArrayList<Joueur> anciensJoueursActifs = new ArrayList();
		for (int i = 0; i < this.nouveauxJoueurs.size(); i++) {
			if ((this.nouveauxJoueurs.get(i)).peutJouer()) {
				nouveauxJoueursActifs.add(this.nouveauxJoueurs.get(i));
			}
		}
		for (int i = 0; i < this.anciensJoueurs.size(); i++) {
			if ((this.anciensJoueurs.get(i)).peutJouer()) {
				anciensJoueursActifs.add(this.anciensJoueurs.get(i));
			}
		}
		this.paires = new ArrayList();
		//en triant les listes en fonction du nombres de match joué en parcourant les liste on prendra en premier des
		// les joueur avec le moins de matchs a leurs actif
		Collections.sort(anciensJoueursActifs, new ComparateurjoueurParNbMatches());
		Collections.sort(nouveauxJoueursActifs, new ComparateurjoueurParNbMatches());


		//On vérifie si le nombre d'anciens est supérieur au nombre de nouveaux
		if (anciensJoueursActifs.size() >= nouveauxJoueursActifs.size()) {
			tailleMin = nouveauxJoueursActifs.size();
			tailleMax = anciensJoueursActifs.size();
			//On cherche à créer le maximum de paires ancien/nouveau avec les joueurs qui n'ont pas joué (prios);
			for (int j = 0; j < tailleMin; j++)
			// On parcourt les nouveaux joueurs
			{
				joueur =  nouveauxJoueursActifs.get(j);
				//On vérifie que le nouveau joueur est prioritaire et qu'il n'a pas deja été attribué a une paire
				if (joueur.getPrio() && (!joueur.getDansPaire())) {
					for (int i = 0; i < tailleMax; i++) {
						//On cherche un ancien joueur compatible qui ne joue pas
						if (joueur.estCompatibleAvec(( anciensJoueursActifs.get(i))) && (!(anciensJoueursActifs.get(i)).getDansPaire())) {
							//Si on trouve un partenaire possible, on les met ensemble et on les rend non disponibles
							this.paires.add(new Paire(joueur, (anciensJoueursActifs.get(i)), i, i));
							//todo est ce qu'il ne vaut pas mieux ajouter les joururs dans la liste respesctive des anciens joueur selements a la validation du match
									joueur.ajouterAnciensPart((anciensJoueursActifs.get(i)));
									(anciensJoueursActifs.get(i)).ajouterAnciensPart(joueur);
							joueur.setDansPaire(true);
							(anciensJoueursActifs.get(i)).setDansPaire(true);
							//todo au lieu d'associer directement le premier joueur compatible faire une liste de joueur compatibles faire eun comparateur de perf et associer les joueur de manière a ce que la somme de leur perf soit proche de 4.5 (la perf max est 7 et la perf  min est 2)
						}
					}
				}
			}
			//On cherche à créer le maximum de paires ancien/nouveau avec les joueurs restants qui n'ont encore jamais joués ensembles;
			for (int j = 0; j < tailleMin; j++)
			// On parcourt les nouveaux joueurs
			{
				joueur = (nouveauxJoueursActifs.get(j));
				for (int i = 0; i < tailleMax; i++) {
					//On cherche un ancien joueur compatible qui ne joue pas
					if (joueur.estCompatibleAvec(( anciensJoueursActifs.get(i))) && (!(anciensJoueursActifs.get(i)).getDansPaire()) && !joueur.getDansPaire()) {
						//Si on trouve un partenaire possible, on les met ensemble et on les rend non disponibles
						this.paires.add(new Paire(joueur, (anciensJoueursActifs.get(i)), i, i));
						joueur.ajouterAnciensPart((anciensJoueursActifs.get(i)));
						(anciensJoueursActifs.get(i)).ajouterAnciensPart(joueur);
						joueur.setDansPaire(true);
						(anciensJoueursActifs.get(i)).setDansPaire(true);
						break;
					}
				}
			}
			//On remplis avec des joueurs même si ils ont joués ensembles
			for (int j = 0; j < tailleMin; j++)
			// On parcourt les nouveaux joueurs
			{
				joueur = (nouveauxJoueursActifs.get(j));
				for (int i = 0; i < tailleMax; i++) {
					//On cherche un ancien joueur  qui ne joue pas
					if ( (!(anciensJoueursActifs.get(i)).getDansPaire()) && !joueur.getDansPaire()) {
						//Si on trouve un partenaire possible, on les met ensemble et on les rend non disponibles
						this.paires.add(new Paire(joueur, (anciensJoueursActifs.get(i)), i, i));
						joueur.ajouterAnciensPart((anciensJoueursActifs.get(i)));
						(anciensJoueursActifs.get(i)).ajouterAnciensPart(joueur);
						joueur.setDansPaire(true);
						(anciensJoueursActifs.get(i)).setDansPaire(true);
						break;
					}
				}
			}
		} else {
			tailleMin = anciensJoueursActifs.size();
			tailleMax = nouveauxJoueursActifs.size();
			//todo la meme chose dans l'autre sens (en prenant comme base les anciens joueru au lieu des nouveau

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
		//todo prise en compte de qui a deja jouer avec qui
		for (i = 0; i < ((int) (Math.floor(this.paires.size() / 2))); i += 2) {
			matchs.add(new Match((Paire) this.paires.get(i), (Paire) this.paires.get(i + 1)));
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
			res += ((Terrain) this.terrains.get(i1)).getMatch().toString() + "\n";
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
	 * Appelée pour finir un tour et mettre à jour les scores
	 */
	public void finirTour() {
		//On vérifie le score des équipes pour chaque terrain
		for (int i = 0; i < this.terrains.size(); i++) {
			//Il faut vérifier qu'un match a bien eu lieu dur le terrain
			if (((Terrain) this.terrains.get(i)).getMatch() != null) {
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
		((Terrain) this.terrains.get(numTerrain)).getMatch().getPaire1().setScore(scoreP1);
		((Terrain) this.terrains.get(numTerrain)).getMatch().getPaire2().setScore(scoreP2);
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
		this.trierNouveauxJoueurs();
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
			res += ((Paire) this.paires.get(i)).toString() + "\n";
		}
		return res;
	}


	public void parserMPF(String str) {
		Pattern pattern = Pattern.compile("Hugo");
		Matcher matcher = pattern.matcher("Hugo Eti�vant");
	}

	/**
	 * pour intervertir facilement deux joueurs qui jouent déjà
	 *
	 * @param idJ1 l'id du premier joueur
	 * @param idJ2 l'id du second joueur
	 * @return true si l'opération est un succès, false sinon
	 */
	public boolean changerJoueurs(int idJ1, int idJ2) {
		Joueur joueurChange2 = null;
		//On cherche d'abord le joueur qui va prendre la place du premier
		for (int i = 0; i < anciensJoueurs.size(); i++) {
			Joueur tmp = anciensJoueurs.get(i);
			if (tmp.getId() == idJ2) {
				joueurChange2 = tmp;
				break;
			}
		}
		if (joueurChange2 == null) {
			for (int i = 0; i < nouveauxJoueurs.size(); i++) {
				Joueur tmp = nouveauxJoueurs.get(i);
				if (tmp.getId() == idJ2) {
					joueurChange2 = tmp;
					break;
				}
			}
		}
		//Précaution si on ne trouve pas le deuxieme joueur
		if (joueurChange2 == null)
			return false;

		//Différents cas si le joueur 2 joue ou non

		//Si le deuxieme jouer ne joue pas c'est simple
		if (!joueurChange2.getJoue()) {
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
			int posJ1 = 0, posJ2 = 0;
			Paire paireJ1 = null, paireJ2 = null;
			// On cherche le joueur 1
			for (int i = 0; i < terrains.size(); i++) {
				Match m = ((Terrain) terrains.get(i)).getMatch();
				Joueur j1 = m.getPaire1().getJoueur1();
				Joueur j2 = m.getPaire1().getJoueur2();
				Joueur j3 = m.getPaire2().getJoueur1();
				Joueur j4 = m.getPaire2().getJoueur2();
				if (j1.getId() == idJ1) {
					paireJ1 = m.getPaire1();
					posJ1 = 1;
					break;
				} else if (j2.getId() == idJ1) {
					paireJ1 = m.getPaire1();
					posJ1 = 2;
					break;
				} else if (j3.getId() == idJ1) {
					paireJ1 = m.getPaire2();
					posJ1 = 1;
					break;
				} else if (j4.getId() == idJ1) {
					paireJ1 = m.getPaire2();
					posJ1 = 2;
					break;
				}
			}
			//Si on a pas trouvé le joueur 1 rien ne sert de continuer
			if (posJ1 == 0)
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
					posJ2 = 1;
					break;
				} else if (j2.getId() == idJ2) {
					paireJ2 = m.getPaire1();
					posJ2 = 2;
					break;
				} else if (j3.getId() == idJ2) {
					paireJ2 = m.getPaire2();
					posJ2 = 1;
					break;
				} else if (j4.getId() == idJ2) {
					paireJ2 = m.getPaire2();
					posJ2 = 2;
					break;
				}
			}
			//Ce cas est normalement impossible mais on ne sait jamais
			if (posJ2 == 0)
				return false;
			// Une fois les paramètres trouvés on lance la méthode adaptée
			paireJ1.intervertir(posJ1, paireJ2, posJ2);
		}

		return true;
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


}

