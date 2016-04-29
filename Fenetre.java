package graphique;

import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

public class Fenetre extends JFrame {

	public Fenetre(String titre) {
		super(titre);

		//On inclue le lookandfeel, pour changement de l'apparence de l'interface
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Les declarations de base
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setTitle(titre);
		this.setLocationRelativeTo(null);
		this.setSize(900, 300);

		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);

		//Notre onglet pour l'acceuil
		JPanel acceuil = new JPanel();
		JLabel acceuilTitre = new JLabel("Acceuil");
		acceuil.add(new JLabel("La on donne des info sur le fonctionnement de l'appli"));

		//Notre onglet pour les joueur (Le jpanel joueurContent ser au contenue du pannel (en gros lees bouton) le pannel joueur regroupe joueurContent + la jtextarea pour display des info)
		JPanel joueur = new JPanel();
		joueur.setLayout(new BorderLayout());
		joueur.setBackground(Color.white);
		JPanel joueurContent = new JPanel();
		joueurContent.setLayout(new GridLayout(4,1));
		//Le Jpannel pour le nom du joueur
		JPanel nomduJoueur = new JPanel();
		nomduJoueur.setLayout(new GridLayout(1,2));
		nomduJoueur.add(new JLabel("Nom du joueur : "));
		nomduJoueur.add(new JTextField());
		nomduJoueur.setBackground(Color.white);
		joueurContent.add(nomduJoueur);
		//La notre choix avec liste deroulante et le bouton pour ajouté le joueur et la combobox pour le sexe du joueur
		String[] choixSexe = { "Homme", "Femme", "Les deux"};
		joueurContent.add(new JComboBox(choixSexe));
		String[] choixLvl = { "Debutant", "Intermediaire", "Proffessionnel"};
		joueurContent.add(new JComboBox(choixLvl));
		joueurContent.add(new JButton("Ajouté joueur"));
		joueur.add(joueurContent, BorderLayout.CENTER);
		joueur.add(new JTextArea("Albert : Niveaux debutant, homme \r\n Janne : Niveaux Intermediaire, femme \r\n etc ..."), BorderLayout.WEST);

		//Pareil que pour les joueur, terrain = le pannel en gros, terrainContent = les bouton du pannel
		JPanel terrain = new JPanel();
		terrain.setLayout(new BorderLayout());
		terrain.setBackground(Color.white);
		JPanel terrainContent = new JPanel();
		terrainContent.setLayout(new GridLayout(2,1));
		//Pareil que pour joueur, ajouté le label a cote de la textBox
		JPanel nombreTerrain = new JPanel();
		nombreTerrain.setLayout(new GridLayout(1,2));
		nombreTerrain.add(new JLabel("Nombre de terrain : "));
		nombreTerrain.add(new JTextField());
		nombreTerrain.setBackground(Color.white);
		terrainContent.add(nombreTerrain);
		terrainContent.add(new JButton("Refraichir le nombre de terrain"));
		terrain.add(terrainContent, BorderLayout.CENTER);
		terrain.add(new JTextArea("Il y a actuellement 15 terrain"), BorderLayout.WEST);

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
		onglets.addTab("Acceuil", acceuil);
		onglets.addTab("Joueur", joueur);
		onglets.addTab("Terrain", terrain);
		onglets.addTab("Score", score);
		onglets.addTab("Paire", paire);

		onglets.setOpaque(true);


		JPanel principal = new JPanel();
		principal.setLayout(new BorderLayout());
		principal.add(onglets, BorderLayout.CENTER);
		this.setContentPane(principal);
		this.setVisible(true);
	}

}
