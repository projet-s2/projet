package vue;

import controleur.*;
import exception.TournoiVideException;
import tournoi.Joueur;
import tournoi.Terrain;
import tournoi.Tournoi;
import tournoi.Chrono;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class FenetrePrincipale extends JFrame {

	//La fen�tre principale � un tournoi surlequel elle peut agir
	private Tournoi tournoi;
	private JScrollPane panJoueurs;
	private DefaultTableModel listeJoueursModele;
	private JTable listeJoueurs;
	String[] joueurs;
	private JPanel[] terrains;
	private BufferedImage image;
	private ArrayList<JComboBox> boxTerrains = new ArrayList<>();
	private JPanel tournois;
	private JTabbedPane onglets;

	private int verif;

	/**
	 *
	 * @param titre le titre que l'on souhaite donner à la fenêtre
	 */
	public FenetrePrincipale(String titre) {
		super(titre);

		//On charge le look and feel du syst�me de l'utilisateur (� la place de GTK) auquel il est habitu�
		try {
			UIManager. setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities. updateComponentTreeUI(this);
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
		this.setLocation(0, 0);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		this.setVisible(true);
	}

	/**
	 *
	 * @param t le tournoi avec lequel la fenêtre va interagir
	 */
	public void setTournoi(Tournoi t){
		this.tournoi=t;
		this.setTitle("Match Point - " + t.getNom());
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
		onglets = new JTabbedPane(SwingConstants.TOP);
		this.joueurs= new String[]{"Pas de joueur"};

		//Notre onglet pour les joueur
		JPanel joueurs = new JPanel();
		joueurs.setLayout(new BorderLayout());
		String  title[] = {"ID","Nom", "Prénom", "Score","Present"};
		listeJoueursModele = new DefaultTableModel(title,0){
			@Override
			//bien redefinir les types des colones pour que l'autosort marche
			public Class getColumnClass(int column) {
				switch (column) {
					case 0:
						return Integer.class;
					case 1:
						return String.class;
					case 2:
						return String.class;
					case 3:
						return Integer.class;
					case 4:
						return String.class;
					default:
						return String.class;
				}
			}
		};
		listeJoueurs = new JTable(listeJoueursModele);
		//modif d'un joueur en cliquant sur le joueur
			listeJoueurs.addMouseListener(new ModifierJoueurControlleur(this,listeJoueursModele,listeJoueurs));
		//Nous ajoutons notre tableau à notre contentPane dans un scroll
		//Sinon les titres des colonnes ne s'afficheront pas !
		listeJoueurs.setAutoCreateRowSorter(true);
		panJoueurs = new JScrollPane(listeJoueurs);

		joueurs.add(panJoueurs,BorderLayout.CENTER);

		//panel west qui contiens les boutons
		JPanel westButtonPan = new JPanel(new GridBagLayout());
		GridBagConstraints gbcWest = new GridBagConstraints();
		/*gbcWest.weighty = 1;
		gbcWest.anchor = GridBagConstraints.NORTH;*/

		//Ajout du chronometre
		Chrono chronometre = new Chrono(300);
		gbcWest.gridx = 0;
		gbcWest.gridy = 0;
		westButtonPan.add(chronometre, gbcWest);
		chronometre.stop();

		//Ajout du bouton Lancer/Pauser
		JButton start;
		start = new JButton("Lancer");
		start.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 1;
		westButtonPan.add(start, gbcWest);
		start.addActionListener(new ChronometreStartControlleur(chronometre, start));

		//Ajout du bouton Redémarrer
		JButton restart;
		restart = new JButton("Redémarrer");
		restart.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 2;
		westButtonPan.add(restart, gbcWest);
		restart.addActionListener(new ChronometreRestartControlleur(chronometre, start));

		//Ajout d'un joueur
		JButton ajouterJoueur = new JButton("Ajouter un joueur");
		ajouterJoueur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetreAjout();
			}
		});
		ajouterJoueur.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 3;
		westButtonPan.add(ajouterJoueur, gbcWest);

		//Bouton pour importer des joueurs
		JButton ImporterJoueurs = new JButton("Importer...");
		ImporterJoueurs.addActionListener(new ImporterJoueursControlleur(tournoi, this));
		ImporterJoueurs.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 4;
		westButtonPan.add(ImporterJoueurs, gbcWest);

		//Bouton pour exporter les joueurs
		JButton ExporterJoueurs = new JButton("Exporter...");
		ExporterJoueurs.addActionListener(new ExporterJoueursControlleur(tournoi));
		ExporterJoueurs.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 5;
		westButtonPan.add(ExporterJoueurs, gbcWest);

		//Bouton Ajout match (ajout manuel d'un score entre deux joueurs :
		JButton newMatch = new JButton("Nouveau match");
		newMatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetreAjoutMatch();
			}
		});
		newMatch.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 6;
		westButtonPan.add(newMatch, gbcWest);

		//Bouton reset tout les scores a zero
		JButton reset = new JButton("reset scores");
		reset.addActionListener(new ResetControlleur(this));
		reset.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 7;
		westButtonPan.add(reset, gbcWest);

		//Bouton pour faire sortir/entrer les jouers du tournoi sans les supprimer
		JButton status = new JButton("Présence/Absence");
		status.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { fenetreStatJoeur();
				}});
		status.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 8;
		westButtonPan.add(status, gbcWest);

		//Bouton pour faire sortir/entrer les jouers du tournoi sans les supprimer
		JButton setPaires = new JButton("Nouveau Tour");
		setPaires.addActionListener(new NouveauTourControleur(this));
		setPaires.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 9;
		westButtonPan.add(setPaires, gbcWest);

		//Bouton pour voir le classement
		JButton classement = new JButton("Classement");
		classement.addActionListener(new VoirClassementControleur(tournoi));
		classement.setPreferredSize(new Dimension(140, 40));
		gbcWest.gridy = 10;
		westButtonPan.add(classement, gbcWest);

		westButtonPan.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));



		////Onglet Tournoi
		tournois = new JPanel();
		tournois.setLayout(new BorderLayout());

		//On ajoute tous les onglets
		onglets.addTab("Joueurs", joueurs);
		onglets.addTab("Tournois", tournois);

		onglets.setOpaque(true);



		JPanel principal = new JPanel();
		principal.setLayout(new BorderLayout());
		principal.add(onglets, BorderLayout.CENTER);
		principal.add(westButtonPan,BorderLayout.WEST);
		this.setContentPane(principal);
		this.setVisible(true);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
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
	public void fenetreAjoutMatch(){
		new FenetreAjoutMatch("Entrer nouveau Match",tournoi,this);
	}
	public void fenetreStatJoeur(){ new FenetreStatJoueur("Modifier diponibiliter Joueur",this);}




	/**
	 * pour actualiser l'affichage de sjoueurs dans l'onglet joueur
	 */

	public void actualiserJoueurs(){
		ArrayList<Joueur> classA = tournoi.getAnciensJoueurs();
		ArrayList<Joueur> classN = tournoi.getNouveauxJoueurs();
		//On rentre les joueurs anciens dans les X premières cases
		for(int i =0; i < classA.size(); i++){
			Joueur j = classA.get(i);
			listeJoueurs.setValueAt(j.getId(),i,0);
			listeJoueurs.setValueAt(j.getNom(),i,1);
			listeJoueurs.setValueAt(j.getPrenom(),i,2);
			listeJoueurs.setValueAt(""+j.getScore(),i,3);
			listeJoueurs.setValueAt(""+j.statut(),i,4);

		}
		//On rentre les joueurs nouveaux dans les cases restantes
		for(int i = 0; i < classN.size(); i++){
			Joueur j = (Joueur)classN.get(i);
			listeJoueurs.setValueAt(j.getId(),i+classA.size(),0);
			listeJoueurs.setValueAt(j.getNom(),i+classA.size(),1);
			listeJoueurs.setValueAt(j.getPrenom(),i+classA.size(),2);
			listeJoueurs.setValueAt(""+j.getScore(),i+classA.size(),3);
			listeJoueurs.setValueAt(""+j.statut(),i+classA.size(),4);

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
		Object[]tJ = {"","",""};
		this.listeJoueursModele.addRow(tJ);
		this.actualiserJoueurs();
		this.actualiserNoms();
	}


	/**
	 * pour supprimer un joueur dans la liste des joueurs (onglet joueurs)
	 */
	public void supprimerJoueurTable()
	{
		int i = listeJoueursModele.getRowCount();
		this.listeJoueursModele.removeRow(i - 1);
		this.actualiserJoueurs();
		this.actualiserNoms();
	}


	public void setVerif(int verif) {
		this.verif = verif;
	}

	public ArrayList<JComboBox> getBoxTerrains() { return this.boxTerrains; };

	public JPanel nouveauTerrain(int i){
		JPanel terrain = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		ArrayList<Joueur> joueurDansCombo = tournoi.getAllJoueurs();
		//joueurDansCombo.add(new Joueur(420, "", "", 42, true, true, 0, false));
		Collections.sort(joueurDansCombo, new Comparator<Joueur>() {
			@Override
			public int compare(Joueur j1, Joueur j2) {
				return j1.toString().compareToIgnoreCase(j2.toString());
			}
		});

		//Terrain n° X
		int o = i + 1;
		JLabel numTer = new JLabel("Terrain "+o);
		gbc.gridx = 0;
		gbc.gridy = 0;
		terrain.add(numTer, gbc);

		//Equipe 1 et leur score
		JComboBox j1 = new JComboBox();
		JComboBox j2 = new JComboBox();
		if(!this.tournoi.tournoisVide()) {
			j1 = new JComboBox(joueurDansCombo.toArray());
			j2 = new JComboBox(joueurDansCombo.toArray());
			try {
				if((i*2) <= tournoi.getAllJoueurs().size()) {
					j1.setSelectedItem(tournoi.getTerrain(i).getMatch().getPaire1().getJoueur1());//depuis terrain je recupere match qui donne les paires qui donne les joueurs
				} else {
					j1.setSelectedIndex(0);
				}
				if((i*2)+1 <= tournoi.getAllJoueurs().size()) {
					j2.setSelectedItem(tournoi.getTerrain(i).getMatch().getPaire1().getJoueur2());
				} else {
					j2.setSelectedIndex(0);
				}
			} catch(Exception e) {
				//System.out.println("moins de joueur que de terrain?");
			}
		}
		JSpinner score1 = new JSpinner();
		JPanel equipeUn = new JPanel(new GridBagLayout());
		j1.setPreferredSize(new Dimension(125, 25));
		j2.setPreferredSize(new Dimension(125, 25));
		score1.setPreferredSize(new Dimension(50, 30));
		GridBagConstraints gbcUn = new GridBagConstraints();
		gbcUn.gridx = 0;
		gbcUn.gridy = 0;
		equipeUn.add(j1, gbcUn);
		gbcUn.gridx = 1;
		equipeUn.add(j2, gbcUn);
		gbcUn.gridx = 2;
		equipeUn.add(score1, gbcUn);
		gbc.gridy = 1;
		terrain.add(equipeUn, gbc);

		//Le terrain
		JPanel espace = new JPanel();
		ImageIcon icon = new ImageIcon(getClass().getResource("/resources/images/terrainS.png"));
		JLabel labelimg = new JLabel();
		labelimg.setIcon(icon);
		espace.add(labelimg);
		gbc.gridy = 2;
		terrain.add(espace, gbc);

		//Equipe 2 et leur score
		JComboBox j3 = new JComboBox();
		JComboBox j4 = new JComboBox();
		if(!this.tournoi.tournoisVide()) {
			j3 = new JComboBox(joueurDansCombo.toArray());
			j4 = new JComboBox(joueurDansCombo.toArray());
			try {
				if((i*2)+2 <= tournoi.getAllJoueurs().size()) {
					j3.setSelectedItem(tournoi.getTerrain(i).getMatch().getPaire2().getJoueur1());
				} else {
					j3.setSelectedIndex(0);
				}
				if((i*2)+3 <= tournoi.getAllJoueurs().size()) {
					j4.setSelectedItem(tournoi.getTerrain(i).getMatch().getPaire2().getJoueur2());
				} else {
					j4.setSelectedIndex(0);
				}
			} catch(Exception e) {
				//System.out.println("moins de joueur que de terrain?");
			}
		}
		//controlleurs de l'échange de joueur
		j1.addItemListener( new SelectonAutreJoueurMatch(j1, this,1,i));
		j2.addItemListener( new SelectonAutreJoueurMatch(j2, this,2,i));
		j3.addItemListener( new SelectonAutreJoueurMatch(j3, this,3,i));
		j4.addItemListener( new SelectonAutreJoueurMatch(j4, this,4,i));
		JSpinner score2 = new JSpinner();
		JPanel equipeDeux = new JPanel(new GridBagLayout());
		j3.setPreferredSize(new Dimension(125, 25));
		j4.setPreferredSize(new Dimension(125, 25));
		score2.setPreferredSize(new Dimension(50, 30));
		GridBagConstraints gbcDeux = new GridBagConstraints();
		gbcDeux.gridx = 0;
		gbcDeux.gridy = 0;
		equipeDeux.add(j3);
		gbcDeux.gridx = 1;
		equipeDeux.add(j4);
		gbcDeux.gridx = 2;
		equipeDeux.add(score2);
		gbc.gridy = 3;
		terrain.add(equipeDeux, gbc);

		//Bouton valider
		JButton valider = new JButton("Valider");
		valider.addActionListener(new SaisirScoreControlleur(score1,score2,this,this.tournoi,i));
		gbc.gridy = 4;
		terrain.add(valider, gbc);

		//Ajout des JComboBox au tableau qui les regroupe
		this.boxTerrains.add(j1);
		this.boxTerrains.add(j2);
		this.boxTerrains.add(j3);
		this.boxTerrains.add(j4);
		/*j1.addItemListener(new ComboBoxSwapControlleur(this, j1));
		j2.addItemListener(new ComboBoxSwapControlleur(this, j2));
		j3.addItemListener(new ComboBoxSwapControlleur(this, j3));
		j4.addItemListener(new ComboBoxSwapControlleur(this, j4));*/

		return  terrain;}

	public void actualiserTerrains() {
		tournois = new JPanel();
		tournois.setLayout(new BorderLayout());
		JPanel panTour = new JPanel();

		panTour.setLayout(new GridLayout((int)Math.floor(this.tournoi.getNbrTerrains()/((int) Math.floor(this.getBounds().width/450))), (int) Math.floor(this.getBounds().width/450), 10, 10));
		//On parcours les terrains pour les afficher

		ArrayList<Joueur> joueursActifs = new ArrayList<>();

		//ArrayList des nouveaux joueurs actifs
		for (Joueur joueur : tournoi.getAllJoueurs()) {
			if (joueur.peutJouer()) {
				joueursActifs.add(joueur);
			}
		}

		if(this.tournoi.getNbrTerrains() > joueursActifs.size()/4) {
			for (int i = 0; i < joueursActifs.size()/4; i++) {
				panTour.add(nouveauTerrain(i));
			}
		} else {
			for (int i = 0; i < this.tournoi.getNbrTerrains(); i++) {
				panTour.add(nouveauTerrain(i));
			}
		}

		JScrollPane terrains = new JScrollPane(panTour);

		tournois.add(terrains, BorderLayout.CENTER);
		onglets.removeTabAt(1);
		onglets.addTab("Tournois", tournois);
		onglets.setSelectedIndex(1);
	}


}
