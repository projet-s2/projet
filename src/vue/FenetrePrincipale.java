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

import tournoi.*;

public class FenetrePrincipale extends JFrame {
	
	//La fen�tre principale � un tournoi surlequel elle peut agir
	private Tournoi tournoi;
	private JScrollPane panJoueurs;
	private JTable listeJoueurs;
	
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
	    this.setJMenuBar(new Menu(tournoi));
	    
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
	    String  title[] = {"Prenom", "Nom", "Score"};
	    listeJoueurs = new JTable(data, title);
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
		JPanel listeTerrains = new JPanel();
		listeTerrains.setLayout(new GridLayout((int)Math.floor(this.tournoi.getNbrTerrains()/((int) Math.floor(this.getBounds().width/250))), (int) Math.floor(this.getBounds().width/250), 10, 10));
		//On parcours les terrains pour les afficher
		for(int i = 0; i<this.tournoi.getNbrTerrains();i++){
			listeTerrains.add(nouvelAffichageTerrain(i));
		}
		JScrollPane terrains = new JScrollPane(listeTerrains);

		//Notre onglet pour les score a l'issue d'un match
		JPanel score = new JPanel();
		score.setLayout(new BorderLayout());
		//Le scoreContent pour les info par apport au deux joueur
		JPanel scoreContent = new JPanel();
		scoreContent.setLayout(new GridLayout(5,2));
		//Le jpannel pour le nom des joueur (les 4, deux pour chaques paire)
		String[] choixScoreName = { "Liste", "Des", "Joueur"};
		JPanel scoreName1 = new JPanel();
		scoreName1.setLayout(new GridLayout(1,2));
		scoreName1.add(new JLabel("Pair 1 - joueur 1: "));
		scoreName1.add(new JComboBox(choixScoreName));
		JPanel scoreName2 = new JPanel();
		scoreName2.setLayout(new GridLayout(1,2));
		scoreName2.add(new JLabel("Pair 1 - joueur 2: "));
		scoreName2.add(new JComboBox(choixScoreName));
		JPanel scoreName3 = new JPanel();
		scoreName3.setLayout(new GridLayout(1,2));
		scoreName3.add(new JLabel("Pair 2 - joueur 1: "));
		scoreName3.add(new JComboBox(choixScoreName));
		JPanel scoreName4 = new JPanel();
		scoreName4.setLayout(new GridLayout(1,2));
		scoreName4.add(new JLabel("Pair 2 - joueur 2: "));
		scoreName4.add(new JComboBox(choixScoreName));
		//On repasse sur le JPanel content
		scoreContent.add(scoreName1);
		scoreContent.add(scoreName2);
		scoreContent.add(new JLabel("Score final pair 1"));
		scoreContent.add(new JTextArea());
		scoreContent.add(scoreName3);
		scoreContent.add(scoreName4);
		scoreContent.add(new JLabel("Score final pair 2"));
		scoreContent.add(new JTextArea());
		scoreContent.add(new JButton("Ajouté score"));
		score.add(scoreContent, BorderLayout.CENTER);
		score.add(new JTextArea("Albert - Jenne v Albert - Jenne : \r\n 21 vs 13 \r\n\r\n Albert - Jenne v Albert - Jenne : \r\n 21 vs 13"), BorderLayout.WEST);



		//Notre onglet pour les paire
		JPanel paire = new JPanel();
		paire.setLayout(new BorderLayout());
		//Le jpannel pour la progressBar
		JPanel pairePourcent = new JPanel();
		pairePourcent.setBackground(Color.gray);
		pairePourcent.setLayout(new FlowLayout(FlowLayout. CENTER));
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		pairePourcent.add(progressBar);
		pairePourcent.add(new JLabel("Creation des paire non lancé"));
		paire.add(new JButton("Creation des paire"), BorderLayout.CENTER);
		paire.add(new JTextArea("Affichage des paire generé"), BorderLayout.WEST);
		paire.add(pairePourcent, BorderLayout.SOUTH);

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

	private void fenetreAjout(){
		new FenetreAjoutJoueur("Ajouter un joueur",tournoi,this);
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
		terr.setBackground(Color.orange);
		terr.setPreferredSize(new Dimension(200,300));
		try{
			terr.add(new JLabel(((Terrain) this.tournoi.getTerrains().get(i)).getMatch().getPaire1().getJoueur1().toString()));
		}catch(Exception e){
			terr.add(new JLabel("Pas de j1 sur le terr :  "+(i+1)));
			
		}
		JPanel terrain = new JPanel();
		terrain.add(terr);
		return terrain;
	}

}
