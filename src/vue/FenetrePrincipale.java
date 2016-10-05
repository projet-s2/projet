package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import controleur.ImporterJoueursControlleur;
import controleur.InverserJoueurControlleur;
import liste.Liste;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controleur.ModifierJoueurControlleur;
import controleur.SaisirScoreControlleur;
import exception.TournoiVideException;
import tournoi.*;

public class FenetrePrincipale extends JFrame {

	//La fen�tre principale � un tournoi surlequel elle peut agir
	private Tournoi tournoi;
	private JScrollPane panJoueurs;
	private DefaultTableModel listeJoueursModele;
	private JTable listeJoueurs;
	private JPanel listeTerrains;
	private JPanel[] terrains;
	private JButton boutonFinir;
	String[] joueurs;
	public static Color couleurOK = Color.GRAY;
	public static Color couleurPasOK = Color.ORANGE;
	private int verif;

	/**
	 *
	 * @param titre le titre que l'on souhaite donner à la fenêtre
	 */
	public FenetrePrincipale(String titre) {
		super(titre);

		//On charge le look and feel du syst�me de l'utilisateur (� la place de GTK) auquel il est habitu�
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		//On assigne le menu � la fenetres
		this.setJMenuBar(new Menu(tournoi, this));

		//Les declarations de base
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setTitle(titre);
		this.setLocation(0,0);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		this.setVisible(true);
	}

	/**
	 *
	 * @param t le tournoi avec lequel la fenêtre va interagir
	 */
	public void setTournoi(Tournoi t){
		this.tournoi=t;
		this.setTitle("Match Point - "+t.getNom());
		((Menu) this.getJMenuBar()).enableSave();
		this.afficherTournoi();
	}

	/**
	 *
	 * @return le tournoi
	 */

	public Tournoi getTournoi(){
		return this.tournoi;
	}

	/**
	 * initialise l'affichage de la fenêtre
	 */
	public void afficherTournoi(){
		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
		this.joueurs= new String[]{"Pas de joueur"};

		//Notre onglet pour les joueur
		JPanel joueurs = new JPanel();
		String  title[] = {"ID","Nom", "Prénom", "Score","Ancienneté","Disponible"};
		listeJoueursModele = new DefaultTableModel(title,0);
		listeJoueurs = new JTable(listeJoueursModele);
		listeJoueurs.addMouseListener(new ModifierJoueurControlleur(this,listeJoueursModele,listeJoueurs));
		//Nous ajoutons notre tableau à notre contentPane dans un scroll
		//Sinon les titres des colonnes ne s'afficheront pas !
		listeJoueurs.setAutoCreateRowSorter(true);
		panJoueurs = new JScrollPane(listeJoueurs);
		joueurs.add(panJoueurs);
		//Ajout d'un joueur
		JButton ajouterJoueur = new JButton("Ajouter un joueur");
		ajouterJoueur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetreAjout();
			}
		});
		joueurs.add(ajouterJoueur);

		//Bouton importation de joueurs : Lucas~Potentiellement instable <3
		JButton ImporterJoueurs = new JButton("Importer...");
		ImporterJoueurs.addActionListener(new ImporterJoueursControlleur(tournoi));
		joueurs.add(ImporterJoueurs);

		//On veut afficher les terrains et les paires qui jouent dessus
		this.listeTerrains = new JPanel();
		listeTerrains.setLayout(new GridLayout((int)Math.floor(this.tournoi.getNbrTerrains()/((int) Math.floor(this.getBounds().width/400))), (int) Math.floor(this.getBounds().width/400), 10, 10));
		//On parcours les terrains pour les afficher
		for(int i = 0; i<this.tournoi.getNbrTerrains();i++){
			listeTerrains.add(nouvelAffichageTerrain(i));
		}
		JScrollPane terrains = new JScrollPane(listeTerrains);
		boutonFinir = new JButton("Finir tour");
		boutonFinir.setEnabled(false);
		boutonFinir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finirTour();
			}
		});
		listeTerrains.add(boutonFinir);


		//On ajoute tous les onglets
		onglets.addTab("Terrains", terrains);
		onglets.addTab("Joueurs", joueurs);

		onglets.setOpaque(true);


		JPanel principal = new JPanel();
		principal.setLayout(new BorderLayout());
		principal.add(onglets, BorderLayout.CENTER);
		this.setContentPane(principal);
		this.setVisible(true);
	}

	/**
	 * pour dire que l'on a rentré les résultats d'un match
	 */
	public void rentrerVerif(){
		verif--;
	}

	/**
	 * pour créer une fenêtre d'ajout de joueur
	 */
	public void fenetreAjout(){
		new FenetreAjoutJoueur("Ajouter un joueur",tournoi,this);
	}

	/**
	 * pour générer les paires et démarrer un tour
	 * @throws TournoiVideException s'il n'y a pas de joueurs
	 */
	public void genererPaires() throws TournoiVideException{
		verif = 0;
		tournoi.demarrerTour();
		this.actualiserTerrains();
		boutonFinir.setEnabled(true);

	}

	/**
	 * pour actualiser l'affichage de sjoueurs dans l'onglet joueur
	 */

	public void actualiserJoueurs(){
		ArrayList<Joueur> classA = tournoi.getAnciensJoueurs();
		ArrayList<Joueur> classN = tournoi.getNouveauxJoueurs();
		//On rentre les joueurs anciens dans les X premières cases
		for(int i =0; i < classA.size(); i++){
			Joueur j = (Joueur)classA.get(i);
			listeJoueurs.setValueAt(j.getId(),i,0);
			listeJoueurs.setValueAt(j.getNom(),i,1);
			listeJoueurs.setValueAt(j.getPrenom(),i,2);
			listeJoueurs.setValueAt(""+j.getScore(),i,3);
			listeJoueurs.setValueAt(j.getNouveau() ? "Nouveau" : "Ancien",i,4);
			listeJoueurs.setValueAt(j.peutJouer() ? "Oui" : "Non",i,5);
		}
		//On rentre les joueurs nouveaux dans les cases restantes
		for(int i = 0; i < classN.size(); i++){
			Joueur j = (Joueur)classN.get(i);
			listeJoueurs.setValueAt(j.getId(),i+classA.size(),0);
			listeJoueurs.setValueAt(j.getNom(),i+classA.size(),1);
			listeJoueurs.setValueAt(j.getPrenom(),i+classA.size(),2);
			listeJoueurs.setValueAt(""+j.getScore(),i+classA.size(),3);
			listeJoueurs.setValueAt(j.getNouveau() ? "Nouveau" : "Ancien",i+classA.size(),4);
			listeJoueurs.setValueAt(j.peutJouer() ? "Oui" : "Non",i+classA.size(),5);
		}
	}

	/**
	 * pour créer un JPanel à partir d'un numéro de terrain
	 * @param i le numéro de terrain
	 * @return un JPanel indiquant des informations relatives au terrain
	 */
	public JPanel nouvelAffichageTerrain(int i){
		JPanel terr = new JPanel();

		//On crée les différentes parties du JPanel

		JPanel paire1 = new JPanel();
		paire1.setLayout(new GridLayout(2,2));

		JComboBox p1j1 = new JComboBox(joueurs);
		JComboBox p1j2 = new JComboBox(joueurs);


		JTextField scoreP1 = new JTextField();
		scoreP1.setColumns(3);
		paire1.add(p1j1);
		paire1.add(p1j2);
		paire1.add(scoreP1);

		JComboBox p2j1 = new JComboBox(joueurs);
		JComboBox p2j2 = new JComboBox(joueurs);

		JPanel paire2 = new JPanel();
		paire2.setLayout(new GridLayout(2,2));

		JTextField scoreP2 = new JTextField();
		scoreP2.setColumns(3);
		paire2.add(p2j1);
		paire2.add(p2j2);
		paire2.add(scoreP2);

		//Le bouton pour valider les score
		JButton scoreBouton = new JButton("Valider Scores");
		scoreBouton.addActionListener(new SaisirScoreControlleur(scoreP1,scoreP2,this,this.tournoi,i,terr));

		try{
			//S'il ya un match sur le terrain, on indique les 4 joueurs
			Joueur j1 = ((Terrain)tournoi.getTerrains().get(i)).getMatch().getPaire1().getJoueur1();
			p1j1.setSelectedItem(j1.getNom()+" "+j1.getPrenom());
			p1j1.addActionListener(new InverserJoueurControlleur(tournoi,this,(String)p1j1.getSelectedItem()));
			Joueur j2 = ((Terrain)tournoi.getTerrains().get(i)).getMatch().getPaire1().getJoueur2();
			p1j2.setSelectedItem(j2.getNom()+" "+j2.getPrenom());
			p1j2.addActionListener(new InverserJoueurControlleur(tournoi,this,(String)p1j1.getSelectedItem()));
			Joueur j3 = ((Terrain)tournoi.getTerrains().get(i)).getMatch().getPaire2().getJoueur1();
			p2j1.setSelectedItem(j3.getNom()+" "+j3.getPrenom());
			p2j1.addActionListener(new InverserJoueurControlleur(tournoi,this,(String)p1j1.getSelectedItem()));
			Joueur j4 = ((Terrain)tournoi.getTerrains().get(i)).getMatch().getPaire2().getJoueur2();
			p2j2.setSelectedItem(j4.getNom()+" "+j4.getPrenom());
			p2j2.addActionListener(new InverserJoueurControlleur(tournoi,this,(String)p1j1.getSelectedItem()));
			terr.setBackground(couleurPasOK);
			//Il faudra entrer les scores dans un terrain d eplus
			verif++;
		}catch(NullPointerException e){
			//Sinon, on indique que le terrain est libre
			p1j1.setSelectedIndex(joueurs.length-1);
			p1j1.setEnabled(false);
			p1j2.setSelectedIndex(joueurs.length-1);
			p1j2.setEnabled(false);
			p2j1.setSelectedIndex(joueurs.length-1);
			p2j1.setEnabled(false);
			p2j2.setSelectedIndex(joueurs.length-1);
			p2j2.setEnabled(false);
			scoreBouton.setEnabled(false);
			terr.setBackground(couleurOK);

		}
		terr.setLayout(new BorderLayout());
		terr.add(paire1, BorderLayout.NORTH);
		terr.add(paire2, BorderLayout.SOUTH);
		terr.add(scoreBouton, BorderLayout.EAST);
		terr.setPreferredSize(new Dimension(200,300));

		JPanel terrain = new JPanel();
		terrain.add(terr);

		return terrain;
	}

	/**
	 * pour mettre à jour l'affichage des terrains
	 */
	public void actualiserTerrains(){
		//On vide la liste des terrains
		this.listeTerrains.removeAll();
		this.listeTerrains.revalidate();
		this.listeTerrains.repaint();
		listeTerrains.setLayout(new GridLayout((int)Math.floor(this.tournoi.getNbrTerrains()/((int) Math.floor(this.getBounds().width/400))), (int) Math.floor(this.getBounds().width/400), 10, 10));
		//On parcours les terrains pour les afficher
		terrains = new JPanel[tournoi.getNbrTerrains()];
		for(int i = 0; i<this.tournoi.getNbrTerrains();i++){
			terrains[i] = nouvelAffichageTerrain(i);
			listeTerrains.add(terrains[i]);
		}
		listeTerrains.add(boutonFinir);
	}

	/**
	 * pour terminer un tour et valider les scores
	 */
	public void finirTour(){
		if (verifFinir()){
			tournoi.finirTour();
			boutonFinir.setEnabled(false);
			actualiserJoueurs();
		}
		else{
			JOptionPane.showMessageDialog(this,"Vous devez valider tous les terrains avant de finir le tour.");
		}
	}

	/**
	 * on vérirife que tous les terrains on eu leurs scores rentrés
	 * @return vrai si on peut finir le tour faux sinon
	 */
	public boolean verifFinir(){
		return verif==0;
	}

	/**
	 * pour mettre à jour la liste des noms de joueurs
	 */
	public void actualiserNoms(){
		joueurs = new String[tournoi.getNouveauxJoueurs().size()+tournoi.getAnciensJoueurs().size()+1];
		for (int i = 0; i < tournoi.getNouveauxJoueurs().size(); i++){
			Joueur j = (Joueur)tournoi.getNouveauxJoueurs().get(i);
			joueurs[i] = j.getNom()+" "+j.getPrenom();
		}
		for (int i = 0;i<tournoi.getAnciensJoueurs().size();i++){
			Joueur j = (Joueur)tournoi.getAnciensJoueurs().get(i);
			joueurs[i+tournoi.getNouveauxJoueurs().size()] = j.getNom()+" "+j.getPrenom();
		}
		joueurs[tournoi.getNouveauxJoueurs().size()+tournoi.getAnciensJoueurs().size()] = "Pas de joueur";
	}

	/**
	 * pour insérer un joueur dans la liste des joueurs (onglet joueurs)
	 */
	public void ajouterJoueurTable(){
		Object[]tJ = {"","","","","",""};
		this.listeJoueursModele.addRow(tJ);
		this.actualiserJoueurs();
		this.actualiserNoms();
	}

	public void setVerif(int verif) {
		this.verif = verif;
	}
}
