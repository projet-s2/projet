package tournoi;

import liste.Liste;

/**Tournoi est la classe représentant un Tournoi.
 *
 * @author OUAKRIM Yanis, RICHARD Nicolas, ORHON Paul, RIALET Yohann, NIVELAIS Quentin
 *
 * @version 0.1
 */

public class Tournoi{

	private Liste nouveauxJoueurs;
	private Liste nouveauxJoueursClasses;
	private Liste anciensJoueurs;
	private Liste anciensJoueursClasses;
	private Liste terrains;
	private Liste paires;
	private int nbrTerrains;
/** Constructeur de la classe tournoi
	*
	* @param nbrTerrains le nombre de terrains disponibles pour le tournoi
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
	* @return la liste des nouveaux adhérents joueurs
	*
	*/
	public Liste getNouveauxJoueurs() {
		return this.nouveauxJoueurs;
	}

	/** Retourne la liste des anciens joueurs
		*
		* @return la liste des anciens adhérents joueurs
		*
		*/
	public Liste getAnciensJoueurs() {
		return this.anciensJoueurs;
	}

	/** Retourne la liste des terrains
		*
		* @return la liste des terrains
		*
		*/
	public Liste getTerrains() {
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
	public void ajouterjoueur(Joueur joueur){
		if (joueur.getNouveau()){
			this.nouveauxJoueurs.add(joueur);}
		else{
			this.anciensJoueurs.add(joueur);
		}
	}

	/** Appelée pour démarrer un tour,
		* Comprend un mélange des listes de joueurs,
		* La création des paires,
		* La création des matchs à partir des paires
		*
		*/
	public void demarrerTour(){
		this.anciensJoueurs = this.anciensJoueurs.melangerListe();
		this.nouveauxJoueurs = this.nouveauxJoueurs.melangerListe();
		this.creerPaires();
		for (int i=0; i<this.nbrTerrains; i++){
			((Terrain)this.terrains.get(i)).setMatch(null);
		}
		this.attribuerMatchs();
		
	}

	/** Appelée pour créer la liste des paires d'un tournoi 
	 	* On attribue un partenaire uniquement aux joueurs actifs (ceux qui sont disponibles pour jouer)
		* On cherche d'abord à faire jouer ceux qui ont le moins participé
		* On cherche ensuite à faire jouer les joueurs qui n'ont pas joué au tour d'avant (les prios)
		* On fait ensuite jouer les autres joueurs
		*/
	private void creerPaires(){
		//On parcourt les deux listes de joueurs et on crée les paires en conséquence
		int tailleMin, tailleMax;
		Joueur joueur;
		//On récupères les listes des nouveaux joueurs actifs et des anciens joueurs actif
		Liste nouveauxJoueursActifs = new Liste();
		Liste anciensJoueursActifs = new Liste();
		for (int i=0;i<this.nouveauxJoueurs.size();i++){
			if(((Joueur)this.nouveauxJoueurs.get(i)).peutJouer()){
				nouveauxJoueursActifs.add((Joueur)this.nouveauxJoueurs.get(i));
			}
		}
		for (int i=0;i<this.anciensJoueurs.size();i++){
			if(((Joueur)this.anciensJoueurs.get(i)).peutJouer()){
				anciensJoueursActifs.add((Joueur)this.anciensJoueurs.get(i));
			}
		}
		this.paires = new Liste();
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
			for (int j=0; j<tailleMin; j++)
			{
				joueur = ((Joueur)anciensJoueursActifs.get(j));
				for (int i=0; i<tailleMax; i++){
					if (joueur.estCompatibleAvec(((Joueur)nouveauxJoueursActifs.get(i))) && (!((Joueur) nouveauxJoueursActifs.get(i)).getDansPaire())){
						this.paires.add(new Paire(joueur,((Joueur)nouveauxJoueursActifs.get(i)),i,i));
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
<<<<<<< HEAD
	/* On attribue les match à des paires
	 * Ce qui se passe : les paires les plus nulles sont les premières à avoir un terrain d'attribué
	 * Ce qu'on veut faire : chaque paire a la même probabilité de se voir attribuer un terrain
	 * Pour cela
	 * On créée une liste de matches avec les paires couplées par niveau
	 * On mélange cette liste
	 * On attribue les terrains
=======
	
	/** Attribue un match pour un maximum de paires
	 * 
>>>>>>> origin/master
	 */
	private void attribuerMatchs(){
		Paire paire1,paire2;
		trierPaires(0, this.paires.size()-1);
		//On parcourt les terrains et on leur attribue des matchs que l'on crée à partir des paires prio
		int j=0;
		int i;
		for (i=0; i<Math.floor(Math.min(this.nbrTerrains,this.paires.size()/2)); i++){
			if(((Paire)this.paires.get(j)).estPrio()){
				paire1=((Paire)this.paires.get(j));
				paire1.joueursJouent(true);
				j++;
				paire2=((Paire)this.paires.get(j));
				paire2.joueursJouent(true);
				j++;
				((Terrain)this.terrains.get(i)).setMatch(new Match(i+1, paire1, paire2));
			}else{
				i--;
				j++;
			}
			if(j>=(this.paires.size()-2)){
				break;
			}
		}
		//On parcourt les terrains et on leur attribue des matchs que l'on crée à partir des paires restantes
		j=0;
		for (int p=i; i<Math.floor(Math.min(this.nbrTerrains,this.paires.size()/2)); i++){
			if(!((Paire)this.paires.get(j)).estPrio()){
				paire1=((Paire)this.paires.get(j));
				paire1.joueursJouent(true);
				j++;
				paire2=((Paire)this.paires.get(j));
				paire2.joueursJouent(true);
				j++;
				((Terrain)this.terrains.get(i)).setMatch(new Match(i+1, paire1, paire2));
			}else{
				i--;
				j++;
			}
		}
		String res= "";
		for(int i1=0; i1<Math.floor(Math.min(this.nbrTerrains,this.paires.size()/2));i1++){
			res +=((Terrain)this.terrains.get(i1)).getMatch().toString()+"\n";
		}
		System.out.println(res);
		//On parcourt les anciens joueurs et on rend prioritaires ceux qui ne jouent pas
		for(int i1=0; i1<this.anciensJoueurs.size();i1++){
			((Joueur) this.anciensJoueurs.get(i1)).setPrio(!((Joueur) this.anciensJoueurs.get(i1)).getJoue());
		}
		for(int i1=0; i1<this.nouveauxJoueurs.size();i1++){
			((Joueur) this.nouveauxJoueurs.get(i1)).setPrio(!((Joueur) this.nouveauxJoueurs.get(i1)).getJoue());
		}
	}

	/** Appelée pour finir un tour et mettre à jour les scores
		*
		*
		*
		*/
	public void finirTour(){
		//On demande le score des équipes pour chaque terrain
		for (int i=0; i<this.nbrTerrains; i++){
			//Il faut vérifier qu'un match a bien eu lieu dur le terrain
			if (((Terrain)this.terrains.get(i)).getMatch()!=null){
				//Il faudrait demander de rentrer les scores 
				((Terrain)this.terrains.get(i)).getMatch().getPaire1().setScore(0);
				((Terrain)this.terrains.get(i)).getMatch().getPaire1().ajouterMatchJoue();
				((Terrain)this.terrains.get(i)).getMatch().getPaire2().setScore(0);
				((Terrain)this.terrains.get(i)).getMatch().getPaire2().ajouterMatchJoue();
				//On détermine les vainqueurs de chaque match
				((Terrain)this.terrains.get(i)).getMatch().setVainqueur(((Terrain)this.terrains.get(i)).getMatch().determinerVainqueur());
				//On modifie le score des joueurs en conséquence
				((Terrain)this.terrains.get(i)).getMatch().modifierScores();
			}

		}

		//On remet tous les joueurs en attente d'une paire
		for (int i=0; i<this.anciensJoueurs.size(); i++){
			((Joueur)this.anciensJoueurs.get(i)).setDansPaire(false);
		}
		for (int i=0; i<this.nouveauxJoueurs.size(); i++){
			((Joueur)this.nouveauxJoueurs.get(i)).setDansPaire(false);
		}
	}
	
	/**
	 * Tri rapide pour classer les joueurs selon leur score
	 */
	public void calculerClassementAnciens(int gauche, int droite){
		int pivot;
		Joueur tmp;
		this.anciensJoueursClasses = this.anciensJoueurs;
		if(droite > gauche){
			pivot = (gauche+droite)/2;
			tmp = ((Joueur) this.anciensJoueursClasses.get(gauche));
			this.anciensJoueursClasses.set(gauche, ((Joueur) this.anciensJoueursClasses.get(pivot))) ;
			this.anciensJoueursClasses.set(pivot, tmp) ;
			pivot = gauche;
			for(int i = gauche+1; i<=droite;i++){
				if(((Joueur)this.anciensJoueursClasses.get(i)).getScore() < ((Joueur)this.anciensJoueursClasses.get(gauche)).getScore()){
					pivot++;
					tmp = (Joueur) this.anciensJoueursClasses.get(i);
					this.anciensJoueursClasses.set(i, ((Joueur) this.anciensJoueursClasses.get(pivot)) );
					this.anciensJoueursClasses.set(pivot, tmp);
				}
			}
			tmp = (Joueur) this.anciensJoueursClasses.get(pivot);
			this.anciensJoueursClasses.set(pivot, ((Joueur) this.anciensJoueursClasses.get(gauche)));
			this.anciensJoueursClasses.set(gauche, tmp);
			calculerClassementAnciens(gauche, pivot-1);
			calculerClassementAnciens(pivot+1, droite);
		}
	}
	public Liste getClassementAnciens(){
		calculerClassementAnciens(0, this.anciensJoueurs.size()-1);
		Liste classem = this.anciensJoueursClasses;
		Liste classem2 = new Liste();
		for(int i=classem.size()-1;i>0;i--){
			classem2.add((Joueur)classem.get(i));
		}
		return classem2;
	}
	
	/**
	 * Tri rapide pour classer les joueurs selon leur score
	 */
	public void calculerClassementNouveaux(int gauche, int droite){
		int pivot;
		Joueur tmp;
		this.nouveauxJoueursClasses = this.nouveauxJoueurs;
		if(droite > gauche){
			pivot = (gauche+droite)/2;
			tmp = ((Joueur) this.nouveauxJoueursClasses.get(gauche));
			this.nouveauxJoueursClasses.set(gauche, ((Joueur) this.nouveauxJoueursClasses.get(pivot))) ;
			this.nouveauxJoueursClasses.set(pivot, tmp) ;
			pivot = gauche;
			for(int i = gauche+1; i<=droite;i++){
				if(((Joueur)this.nouveauxJoueursClasses.get(i)).getScore() < ((Joueur)this.nouveauxJoueursClasses.get(gauche)).getScore()){
					pivot++;
					tmp = (Joueur) this.nouveauxJoueursClasses.get(i);
					this.nouveauxJoueursClasses.set(i, ((Joueur) this.nouveauxJoueursClasses.get(pivot)) );
					this.nouveauxJoueursClasses.set(pivot, tmp);
				}
			}
			tmp = (Joueur) this.nouveauxJoueursClasses.get(pivot);
			this.nouveauxJoueursClasses.set(pivot, ((Joueur) this.nouveauxJoueursClasses.get(gauche)));
			this.nouveauxJoueursClasses.set(gauche, tmp);
			calculerClassementNouveaux(gauche, pivot-1);
			calculerClassementNouveaux(pivot+1, droite);
		}
	}
	
	/** Renvoie le classement des nouveaux joueurs
	 * @return le classement des nouveaux adhérents joueurs
	 */
	public Liste getClassementNouveaux(){
		calculerClassementNouveaux(0, this.nouveauxJoueurs.size()-1);
		Liste classem = this.nouveauxJoueursClasses;
		Liste classem2 = new Liste();
		for(int i=classem.size()-1;i>0;i--){
			classem2.add((Joueur)classem.get(i));
		}
		return classem2;
	}
	
	/** Redéfinition la méthode public toString
	 * @return L'état de toutes les paires à un moment donné
	 */
	public String toString(){
		String res= "";
		for(int i=0; i<this.paires.size();i++){
			res +=((Paire)this.paires.get(i)).toString()+"\n";
		}
		return res;
	}

}
