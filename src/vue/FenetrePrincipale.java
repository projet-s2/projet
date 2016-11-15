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
public class FenetrePrincipale extends JFrame {

	//La fen�tre principale � un tournoi surlequel elle peut agir
	private Tournoi tournoi;
	private JScrollPane panJoueurs;
	private DefaultTableModel listeJoueursModele;
	private JTable listeJoueurs;
	String[] joueurs;
	private JPanel[] terrains;
	private BufferedImage image;

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
		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
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


		//pannel south qui contiens les boutons
		JPanel southpan = new JPanel();
		southpan.setLayout(new GridLayout(25, 1));
		//Ajout d'un joueur
		JButton ajouterJoueur = new JButton("Ajouter un joueur");
		ajouterJoueur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetreAjout();
			}
		});
		southpan.add(ajouterJoueur);



		//Bouton pour importer des joueurs
		JButton ImporterJoueurs = new JButton("Importer...");
		ImporterJoueurs.addActionListener(new ImporterJoueursControlleur(tournoi, this));
		southpan.add(ImporterJoueurs);


		//Bouton pour exporter les joueurs
		JButton ExporterJoueurs = new JButton("Exporter...");
		ExporterJoueurs.addActionListener(new ExporterJoueursControlleur(tournoi));
		southpan.add(ExporterJoueurs);

		//Bouton Ajout match (ajout manuel d'un score entre deux joueurs :
		JButton newMatch = new JButton("Nouveau match");
		newMatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetreAjoutMatch();
			}
		});
		southpan.add(newMatch);

		//Bouton reset tout les scores a zero
		JButton reset = new JButton("reset scores");
		reset.addActionListener(new ResetControlleur(this));
		southpan.add(reset);

		//Bouton pour faire sortir/entrer les jouers du tournoi sans les supprimer
		JButton status = new JButton("Présence/Absence");
		status.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { fenetreStatJoeur();
				}});
		southpan.add(status);

		//Bouton pour faire sortir/entrer les jouers du tournoi sans les supprimer
		JButton setPaires = new JButton("Nouveau Tour");
		//setPaires.addActionListener(//todo generer paires et matches);
		southpan.add(setPaires);

		//Bouton pour voir le classement
		JButton classement = new JButton("Classement");
		classement.addActionListener(new VoirClassementControleur(tournoi));
		southpan.add(classement);



		joueurs.add(southpan,BorderLayout.WEST);

		JPanel chrono = new JPanel();
		chrono.setLayout(new FlowLayout(FlowLayout.CENTER));
		Chrono chronometre = new Chrono(300);
		chrono.add(chronometre);
		chronometre.stop();

		JButton start;
		JButton restart;

		start = new JButton("Lancer");
		restart = new JButton("Redémarrer");
		chrono.add(start);
		chrono.add(restart);

		start.addActionListener(new ChronometreStartControlleur(chronometre, start));
		restart.addActionListener(new ChronometreRestartControlleur(chronometre, start));

		////Onglet Tournoi
		JPanel tournois = new JPanel();
		tournois.setLayout(new BorderLayout());
		JPanel panTour = new JPanel();



		panTour.setLayout(new GridLayout((int)Math.floor(this.tournoi.getNbrTerrains()/((int) Math.floor(this.getBounds().width/400))), (int) Math.floor(this.getBounds().width/400), 10, 10));
		//On parcours les terrains pour les afficher
		for(int i = 0; i<this.tournoi.getNbrTerrains();i++){
//			panTour.add(nouveauTerrain(i));
		}
		JScrollPane terrains = new JScrollPane(panTour);

		tournois.add(panTour, BorderLayout.CENTER);




		//On ajoute tous les onglets
		onglets.addTab("Joueurs", joueurs);
		onglets.addTab("Tournois", tournois);

		onglets.setOpaque(true);



		JPanel principal = new JPanel();
		principal.setLayout(new BorderLayout());
		principal.add(onglets, BorderLayout.CENTER);
		principal.add(chrono, BorderLayout.EAST);
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
		this.listeJoueursModele.removeRow(i-1);
		this.actualiserJoueurs();
		this.actualiserNoms();
	}


	public void setVerif(int verif) {
		this.verif = verif;
	}


	public JPanel nouveauTerrain(int i){
		JPanel terrain = new JPanel();
		terrain.setLayout(new BorderLayout());
		int o = i + 1;
		JLabel numTer = new JLabel("Terrain "+o);
		terrain.add(numTer,BorderLayout.NORTH);
		JComboBox j1 = new JComboBox(/*this.getTournoi().getTerrain(i).j1()*/);
		JComboBox j2 = new JComboBox(/*this.getTournoi().getTerrain(i).j2()*/);
		JComboBox j3 = new JComboBox(/*this.getTournoi().getTerrain(i).j3()*/);
		JComboBox j4 = new JComboBox(/*this.getTournoi().getTerrain(i).j4()*/);
		JPanel espace = new JPanel();
		ImageIcon icon = new ImageIcon(getClass().getResource("/resources/images/terrainS.png"));
		JLabel labelimg = new JLabel();
		labelimg.setIcon(icon);
		espace.add(labelimg);
		terrain.add(espace,BorderLayout.CENTER);
		JButton valider = new JButton("Valider");
		JPanel panSouth = new JPanel(new GridLayout(3,1));
		JPanel panSouthUn = new JPanel(new GridLayout(1, 3));
		panSouthUn.add(j1);
		panSouthUn.add(j2);
		JTextField score1 = new JTextField();
		panSouthUn.add(score1);
		JPanel panSouthDeux = new JPanel(new GridLayout(1, 3));
		panSouthDeux.add(j3);
		panSouthDeux.add(j4);
		JTextField score2 = new JTextField();
		panSouthDeux.add(score2);
		panSouth.add(panSouthUn);
		panSouth.add(panSouthDeux);
		panSouth.add(valider);
		terrain.add(panSouth,BorderLayout.SOUTH);

		return  terrain;}
}
