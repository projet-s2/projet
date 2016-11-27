package tournoi;

import java.util.ArrayList;

/**Joueur est la classe représentant un joueur du tournoi.
 *
 * @author OUAKRIM Yanis, RICHARD Nicolas, ORHON Paul, RIALET Yohann, NIVELAIS Quentin
 *
 * @version 0.1
 */
public class Joueur {

	private int id;
	private String nom;
	private String prenom;
	// 0 : indéfini
	// 1 : -18 jeune
	// 2 : 18-35 senior
	// 3 : 35+ veteran
	private int age;
	// 0 : femme
	// 1 : homme
	private boolean sexe;
	private boolean nouveau;
	private int score;
	private boolean joue;
	private boolean dansPaire;
	private int perf;
	// 0 : indéfini
	// 1 : Débutant
	// 2 : Intermédiaire
	// 3 : Confirmé
	private int niveau;
	private ArrayList<Joueur> anciensPart;
	private boolean prio;
	private int nbMatchJoues;
	private boolean peutJouer;

	//Savoir combien on a créé de joueurs permet de donner un identifiant facilement
	public static int nbJoueursCrees = 0;

	/** Constructeur de la classe Joueur
		*
		* @param id l'id du joueur
		* @param nom le nom du joueur
		* @param prenom le prénom du joueur
		* @param age l'âge du joueur (0 : -18 jeune / 1 : 18-35 senior / 2 : 35+ veteran)
		* @param sexe le sexe du joueur (0 : homme / 1 : femme)
		* @param nouveau (0 : joueur ancien / 1 : joueur nouveau)
		* @param niveau le niveau du joueur (0 : débutant / 1 : intermédiaire / 2 : confirmé)
		* @param peutJouer si le joueur peut jouer
		*
		*/
	public Joueur(int id, String nom, String prenom, int age, boolean sexe,
			boolean nouveau, int niveau, boolean peutJouer){
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.sexe = sexe;
		this.nouveau = nouveau;
		this.score = 0;
		this.joue = false;
		this.setDansPaire(false);
		this.niveau = niveau;
		this.perf = this.calculerPerf();
		//on calcule la performance en fonction de l'age, du sexe et du niveau
		
		this.prio = true;
		this.anciensPart = new ArrayList<>();
		this.nbMatchJoues = 0;
		this.peutJouer = peutJouer;

		nbJoueursCrees++;
	}
	// Tests
		public Joueur(int id, boolean sexe, boolean nouv){
			this(id, "Bon", "Jean", (20+id), sexe, nouv, (id%3),true);
		}

	/**
	 * Pour calculer un indice de performance d'un joueur en fonction de son âge, son niveau et son sexe
	 * La performance est calculée en fonction du niveau, du sexe ainsi que de l'âge du joueur
	 * @return son indice de performance
     */
	public int calculerPerf(){
		int perf = 0;

		if (niveau == 0) //Si le niveau n'est pas défini on considère que le joueur est de niveau intermédiaire
			perf += 2;
		else if (niveau == 1 || niveau == 2 || niveau == 3) // sinon on lui ajoute son niveau en indice
			perf += niveau;
		if (sexe) //Si le joueur est un homme on augmente son indice
			perf += 1;

		if (age == 0) //Si l'âge n'est pas défini on considère qu'il a entre 18 et 35 ans
						// (on trouve sur internet une moyenne d'environ 26 ans pour les joueurs de badminton)
			perf += 2;
		else if (age == 1) //Si le joueur a moins de 18 ans, on lui ajoute le moins de point à son indice
			perf += 1;
		else if (age == 2) //Si le joueur a entre 18 et 35 ans on lui ajoute le plus de points à son indice
			perf += 3;
		else if (age == 3) //Si le joueur a plus de 35 ans on lui ajoute un nombre moyen de point à son indice
			perf += 2;

		return perf;
	}
	

	/** Retourne l'id d'un joueur
	 *
	 * @return id l'id du joueur
	 */
	public int getId(){
		return this.id;
	}
	
	
	public boolean isPeutJouer() {
		return peutJouer;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setSexe(boolean sexe) {
		this.sexe = sexe;
	}
	public void setNouveau(boolean nouveau) {
		this.nouveau = nouveau;
	}
	public void setPerf(int perf) {
		this.perf = perf;
	}
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	/**
	 * pour ajouter un joueur dans les partenaires
	 * @param j le joueur à ajouter dans les partenaires
     */
	public void ajouterAnciensPart(Joueur j){
		this.anciensPart.add(j);
	}
	/** Retourne le nom d'un joueur
	 *
	 * @return nom le nom du joueur
	 */
	public String getNom(){
		return this.nom;
	}

	/** Retourne le prénom d'un joueur
	 *
	 * @return prenom le prénom du joueur
	 */
	public String getPrenom(){
		return this.prenom;
	}

	/** Retourne l'âge d'un joueur
	 *
	 * @return age l'âge du joueur
	 */
	public int getAge(){
		return this.age;
	}

	/** Retourne le sexe d'un joueur
	 *
	 * @return sexe le sexe du joueur
	 */
	public boolean getSexe(){
		return this.sexe;
	}

	/** Retourne le score d'un joueur
	 *
	 * @return score le score du joueur
	 */
	public int getScore(){
		return this.score;
	}

	/** Retourne l'ancienneté d'un joueur
	 *
	 * @return nouveau 0 : joueur ancien / 1 : joueur nouveau
	 */
	public boolean getNouveau(){
		return this.nouveau;
	}

	/** Retourne si le joueur joue ou non
	 *
	 * @return joue 0 : ne joue pas / 1 : joue
	 */
	public boolean getJoue(){
		return this.joue;
	}

	/**
	 *
	 * @return la priorité du joueur
     */
	public boolean getPrio(){
		return this.prio;
	}

	/**
	 * pour changer la priorité d'un joueur
	 * @param pr vrai s'il est prioritaire faux sinon
     */
	public void setPrio(boolean pr){
		this.prio = pr;
	}
	/** Retourne le niveau d'un joueur
	 *
	 * @return niveau le niveau du joueur
	 */
	public int getNiveau(){
		return this.niveau;
	}
	
	/** Retourne l'indice de performance du joueur
	 * @return l'indice de performance du joueur
	 */
	public int getPerf(){
		return this.perf;
	}
	
	/** Redéfinition de la méthode toString()
	 *
	 * @return txt l'affichage d'un joueur
	 */
	@Override
	public String toString(){
		String res = "";
		res += this.id + "/" + this.prenom + "/" + this.nom + "/";

		if (age == 0) res += "âge indéfini/";
		else if (age == 1) res += "-18/";
		else if (age == 2) res += "18-35/";
		else if (age == 3) res += "+35/";

		if(this.sexe) res += "homme/" ;
		else res += "femme/" ;

		if(this.nouveau) res+= "nouveau/";
		else res += "ancien/";

		if(this.joue) res += "joue/";
		else res += "ne joue pas/";

		res += "score:" + this.score + "/" + "perf:" + this.perf + "/";

		if (niveau == 0) res += "niveau indéfini/";
		else if (niveau == 1) res += "Débutant/";
		else if (niveau == 2) res += "Intermédiaire/";
		else if (niveau == 3) res += "Confirmé/";

		if (this.peutJouer) res += "peut joueur";
		else res += "ne peut pas jouer";
		return res;
	}
	
	/** Retourne si le joueur peut jouer ou non
	*
	* @return actif 0 : le joueur ne peut pas jouer / 1 : le joueur peut jouer
	*/
	public boolean peutJouer() {
		return peutJouer;
	}

	/** Retourne si le joueur peut jouer ou non
	 *
	 * @return non : le joueur ne peut pas jouer / oui : le joueur peut jouer
	 */
	public String statut() {
		return (peutJouer) ? "Oui" : "Non";

	}

	/** Redéfinit l'attribut "peutJouer"
	*
	* @param peutJouer : le joueur ne joue pas / 1 : le joueur joue
	*/
	public void setPeutJouer(boolean peutJouer) {
		this.peutJouer = peutJouer;
	}
	
	/** Redéfinit l'attribut "joue"
	 *
	 * @param bool 0 : le joueur ne joue pas / 1 : le joueur joue
	 */
	public void setJoue(boolean bool){
		this.joue=bool;
		if(bool){
			this.nbMatchJoues++;
		}
	}

	/** Redéfinit l'attribut "score"
	 *
	 * @param score le score d'un joueur
	 */
	public void setScore(int score){
		this.score=score;
	}

	/** Retourne tous les anciens partenaires d'un joueur
	 *
	 * @return anciensPart la liste de tous les anciens partenaires d'un joueur
	 */
	public ArrayList<Joueur> getAnciensPart(){
		return this.anciensPart;
	}

	/** Retourne si le joueur a déjà joué avec un autre (en paramètre)
	 *
	 * @param j1 le joueur à tester
	 * @return res 0 : ils n'ont jamais joué ensemble / 1 : ils ont joué ensemble
	 */
	private boolean aJoueAvec(Joueur j1){
		boolean res = false;
		for (int i=0;i<this.getAnciensPart().size() ; i++) {
			if((this.getAnciensPart().get(i)).equals(j1)) {
				res =true;
			}
		}
		return res;
	}

	/** Retourne si le joueur est compatible avec un autre (en paramètre)
	 *
	 * @param joueur le joueur à tester
	 * @return booléen 0 : n'est pas compatible / 1 : est compatible
	 */
	public boolean estCompatibleAvec(Joueur joueur){
		//On vérifie si les joueurs ont déjà joué ensemble
		return !this.aJoueAvec(joueur);
	}

	/** Redéfinition de la méthode equals()
	 *
	 * @param o l'objet à comparer
	 * @return booléen 0 : ils ne sont pas égaux / 1 : ils sont égaux
	 */
	@Override
	public boolean equals(Object o){
		if (o instanceof Joueur){
			return (this.id==((Joueur)o).id
					&& this.nom.equals(((Joueur)o).nom)
					&& this.prenom.equals(((Joueur)o).prenom));
		}
		else return false;
	}
	
	/** Retourne si le joueur est dans une paire 
	 * 
	 * @return si le joueur est dans paire
	 */
	public boolean getDansPaire() {
		return this.dansPaire;
	}
	
	/** Redéfinition de l'attribut "dansPaire"
	 * 
	 * @param dansPaire
	 */
	public void setDansPaire(boolean dansPaire) {
		this.dansPaire = dansPaire;
	}
	
	/** Retourne le nombre de match joués
	 * @return le nombre de matchs joués
	 */
	public int getNbMatchJoues() {
		return nbMatchJoues;
	}
	/** Ajoute un Match aux matchs joués d'un joueur
	 * 
	 * 
	 */
	public void ajouterMatchJoue() {
		this.nbMatchJoues++;
	}
	
	public String readyToBeSaved(){
		String anc ="";
		for(int i = 0;i<this.anciensPart.size();i++){
			anc += "\n<id>"+((Joueur) this.anciensPart.get(i)).getId()+"</id>";
		}
		anc=anc.replaceAll("(?m)^", "\t");
		anc=anc.replaceAll("(?m)^", "\t");
		return "\n<joueur> \n"
				+ "	<id>"+this.id+"</id>\n"
				+ "	<nom>"+this.nom+"</nom>\n"
				+ "	<prenom>"+this.prenom+"</prenom>\n"
				+ "	<age>"+this.age+"</age>\n"
				+ "	<sexe>"+this.sexe+"</sexe>\n"
				+ "	<nouveau>"+this.nouveau+"</nouveau>\n"
				+ "	<score>"+this.score+"</score>\n"
				+ "	<joue>"+this.joue+"</joue>\n"
				+ "	<dansPaire>"+this.dansPaire+"</dansPaire>\n"
				+ "	<perf>"+this.perf+"</perf>\n"
				+ "	<niveau>"+this.niveau+"</niveau>\n"
				+ "	<anciensPart>"+anc+"\n	</anciensPart>\n"
				+ "	<prio>"+this.prio+"</prio>\n"
				+ "	<nbMatchJoues>"+this.nbMatchJoues+"</nbMatchJoues>\n"
				+ "	<peutJouer>"+this.peutJouer+"</peutJouer>\n"
			+ "</joueur>";
	}
	public void modifierJoueur(String nom2, String prenom2, int age2, boolean sexe2, boolean nouveau2,
			int niveau2) {
		this.setNom(nom2);
		this.setPrenom(prenom2);
		this.setAge(age2);
		this.setSexe(sexe2);
		this.setNouveau(nouveau2);
		this.setNiveau(niveau2);
	}
	

}
