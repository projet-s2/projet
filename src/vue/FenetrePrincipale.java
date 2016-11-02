package vue;

import controleur.*;
import exception.TournoiVideException;
import tournoi.Joueur;
import tournoi.Terrain;
import tournoi.Tournoi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FenetrePrincipale extends JFrame {

	//La fen�tre principale � un tournoi surlequel elle peut agir
	private Tournoi tournoi;
	private JScrollPane panJoueurs;
	private DefaultTableModel listeJoueursModele;
	private JTable listeJoueurs;
	String[] joueurs;

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
		String  title[] = {"ID","Nom", "Prénom", "Score"};
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


		//Bouton pour importer des joueurs
		JButton ImporterJoueurs = new JButton("Importer...");
		ImporterJoueurs.addActionListener(new ImporterJoueursControlleur(tournoi,this));
		joueurs.add(ImporterJoueurs);

		//Bouton pour exporter les joueurs
		JButton ExporterJoueurs = new JButton("Exporter...");
		ExporterJoueurs.addActionListener(new ExporterJoueursControlleur(tournoi));
		joueurs.add(ExporterJoueurs);

		//Bouton Ajout match (ajout manuel d'un score entre deux joueurs :
		JButton newMatch = new JButton("Nouveau match");
		newMatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetreAjoutMatch();
			}
		});
		joueurs.add(newMatch);

		//Bouton reset tout les scores a zero
		JButton reset = new JButton("reset scores");
		reset.addActionListener(new ResetControlleur(this));
		joueurs.add(reset);






		//On ajoute tous les onglets
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
	public void fenetreAjoutMatch(){
		new FenetreAjoutMatch("Entrer nouveau Match",tournoi,this);
	}



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

		}
		//On rentre les joueurs nouveaux dans les cases restantes
		for(int i = 0; i < classN.size(); i++){
			Joueur j = (Joueur)classN.get(i);
			listeJoueurs.setValueAt(j.getId(),i+classA.size(),0);
			listeJoueurs.setValueAt(j.getNom(),i+classA.size(),1);
			listeJoueurs.setValueAt(j.getPrenom(),i+classA.size(),2);
			listeJoueurs.setValueAt(""+j.getScore(),i+classA.size(),3);

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
}
