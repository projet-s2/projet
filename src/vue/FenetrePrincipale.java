package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import liste.Liste;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
	public void setTournoi(Tournoi t){
		this.tournoi=t;
		this.setTitle("Match Point - "+t.getNom());
		((Menu) this.getJMenuBar()).enableSave();
		this.afficherTournoi();
	}

	public void afficherTournoi(){
		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);

		//Notre onglet pour les joueur
		JPanel joueurs = new JPanel();
		String[]_ = new String[]{"","",""};
		 Object[][] data = {
				 _,
				 _,
				 _,
				 _,
				 _,
				 _,
				 _,
			    };

	    //Les titres des colonnes
	    String  title[] = {"Nom", "Prénom", "Score"};
	    listeJoueursModele = new DefaultTableModel(title,0);
	    listeJoueurs = new JTable(listeJoueursModele);
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

		//On veut afficher les terrains et les paires qui jouent dessus
		this.listeTerrains = new JPanel();
		listeTerrains.setLayout(new GridLayout((int)Math.floor(this.tournoi.getNbrTerrains()/((int) Math.floor(this.getBounds().width/400))), (int) Math.floor(this.getBounds().width/400), 10, 10));
		//On parcours les terrains pour les afficher
		for(int i = 0; i<this.tournoi.getNbrTerrains();i++){
			listeTerrains.add(nouvelAffichageTerrain(i));
		}
		JScrollPane terrains = new JScrollPane(listeTerrains);


		//On ajoute tout les onglet
		onglets.addTab("Terrains", terrains);
		onglets.addTab("Joueurs", joueurs);

		onglets.setOpaque(true);


		JPanel principal = new JPanel();
		principal.setLayout(new BorderLayout());
		principal.add(onglets, BorderLayout.CENTER);
		this.setContentPane(principal);
		this.setVisible(true);
	}

	public void fenetreAjout(){
		new FenetreAjoutJoueur("Ajouter un joueur",tournoi,this);
	}

	public void genererPaires() throws TournoiVideException{
		tournoi.demarrerTour();
		this.actualiserTerrains();
	}

	//Appelé quand on ajoute un joueur dans la fenêtre ajout joueur
	public void actualiserJoueurs(){
		String[] colonnes = {"Nom","Prenom","Score"};
		Liste classA = tournoi.getAnciensJoueurs();
		Liste classN = tournoi.getNouveauxJoueurs();
		int tailleTotale = classA.size()+classN.size();
		//Joueur[] joueurs = new Joueur[tailleTotale];
		String[][] data = new String[tailleTotale][3];
		//On rentre les joueurs anciens dans les X premières cases
		for(int i =0; i < classA.size(); i++){
			Joueur j = (Joueur)classA.get(i);
			System.out.println(i+"\t"+j);
			listeJoueurs.setValueAt(j.getNom(),i,0);
			listeJoueurs.setValueAt(j.getPrenom(),i,1);
			listeJoueurs.setValueAt(""+j.getScore(),i,2);
			data[i] = new String[]{j.getNom(),j.getPrenom(),""+j.getScore()};
		}
		//On rentre les joueurs nouveaux dans les cases restantes
		for(int i = 0; i < classN.size(); i++){
			Joueur j = (Joueur)classN.get(i);
			System.out.println(i+"\t"+j);
			listeJoueurs.setValueAt(j.getNom(),i+classA.size(),0);
			listeJoueurs.setValueAt(j.getPrenom(),i+classA.size(),1);
			listeJoueurs.setValueAt(""+j.getScore(),i+classA.size(),2);
			data[i+classA.size()] = new String[]{j.getNom(),j.getPrenom(),""+j.getScore()};
		}


		/*
		for(String[] s : data){
			System.out.println("\nUn joueur");
			for (String s2 : s)
				System.out.print("\t"+s2);

		}
		*/
		//listeJoueurs = new JTable(data, colonnes);
		//Nous ajoutons notre tableau à notre contentPane dans un scroll
		//Sinon les titres des colonnes ne s'afficheront pas !
		//listeJoueurs.setAutoCreateRowSorter(true);
		//panJoueurs = new JScrollPane(listeJoueurs);

		//panJoueurs = new JScrollPane(listeJoueurs);

	}


	public JPanel nouvelAffichageTerrain(int i){
		JPanel terr = new JPanel();

		JPanel paire1 = new JPanel();
		paire1.setLayout(new GridLayout(2,2));
		JTextArea p1j1 = new JTextArea();
		p1j1.setWrapStyleWord(true);
		p1j1.setLineWrap(true);
		p1j1.setEditable(false);
		JTextArea p1j2 = new JTextArea();
		p1j2.setWrapStyleWord(true);
		p1j2.setLineWrap(true);
		p1j2.setEditable(false);
		JTextField scoreP1 = new JTextField();
		scoreP1.setColumns(3);
		paire1.add(p1j1);
		paire1.add(p1j2);
		paire1.add(scoreP1);

		JPanel paire2 = new JPanel();
		paire2.setLayout(new GridLayout(2,2));
		JTextArea p2j1 = new JTextArea();
		p2j1.setWrapStyleWord(true);
		p2j1.setLineWrap(true);
		p2j1.setEditable(false);
		JTextArea p2j2 = new JTextArea();
		p2j2.setWrapStyleWord(true);
		p2j2.setLineWrap(true);
		p2j2.setEditable(false);
		JTextField scoreP2 = new JTextField();
		scoreP2.setColumns(3);
		paire2.add(p2j1);
		paire2.add(p2j2);
		paire2.add(scoreP2);

		JButton scoreBouton = new JButton("Valider Scores");
		scoreBouton.addActionListener(new SaisirScoreControlleur(scoreP1,scoreP2,this,this.tournoi,i));

		try{
			p1j1.setText( ((Terrain)tournoi.getTerrains().get(i)).getMatch().getPaire1().getJoueur1().toString());
			p1j2.setText( ((Terrain)tournoi.getTerrains().get(i)).getMatch().getPaire1().getJoueur2().toString());
			p2j1.setText( ((Terrain)tournoi.getTerrains().get(i)).getMatch().getPaire2().getJoueur1().toString());
			p2j2.setText( ((Terrain)tournoi.getTerrains().get(i)).getMatch().getPaire2().getJoueur2().toString());
		}catch(NullPointerException e){
			p1j1.setText("Pas assez de joueurs sur le terrain "+(i+1));
			p1j2.setText(" ");
			p2j1.setText("Pas assez de joueurs sur le terrain "+(i+1));
			p2j2.setText(" ");
			scoreBouton.setEnabled(false);

		}
		terr.setLayout(new BorderLayout());
		terr.add(paire1, BorderLayout.NORTH);
		terr.add(paire2, BorderLayout.SOUTH);
		terr.add(scoreBouton, BorderLayout.EAST);
		terr.setBackground(Color.orange);
		terr.setPreferredSize(new Dimension(200,300));

		JPanel terrain = new JPanel();
		terrain.add(terr);
		return terrain;
	}
	public void actualiserTerrains(){
		//On vide la liste des terrains
		this.listeTerrains.removeAll();
		this.listeTerrains.revalidate();
		this.listeTerrains.repaint();
		listeTerrains.setLayout(new GridLayout((int)Math.floor(this.tournoi.getNbrTerrains()/((int) Math.floor(this.getBounds().width/400))), (int) Math.floor(this.getBounds().width/400), 10, 10));
		//On parcours les terrains pour les afficher
		for(int i = 0; i<this.tournoi.getNbrTerrains();i++){
			listeTerrains.add(nouvelAffichageTerrain(i));
		}
	}
	public void actualiserScoresJoueurs(){

	};

	public void ajouterJoueurTable(){
		Object[]tJ = {"","",""};
		this.listeJoueursModele.addRow(tJ);
		this.actualiserJoueurs();
	}

}
