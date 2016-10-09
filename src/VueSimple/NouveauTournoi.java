package VueSimple;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controleur.NouveauTournoiControleur;

public class NouveauTournoi extends JDialog {
	public NouveauTournoi(FenetrePrincipale o){
		//On cr�er la fenetre
		super(o);
		//On initialise le content pane ainsi que sa structure
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		
		//on cr�er les �l�ments
		JTextField nom = new JTextField();
		JSpinner nbTerrains = new JSpinner();
		JLabel nomLabel = new JLabel("Nom : ", SwingConstants.RIGHT);
		JLabel nbTerrainsLabel = new JLabel("Nombre de terrains : ", SwingConstants.RIGHT);
		
		//On les ajoute � des panels de position
		JPanel etiquettes = new JPanel();
		etiquettes.setLayout(new GridLayout(2,1));
		etiquettes.add(nomLabel);
		etiquettes.add(nbTerrainsLabel);

		//On creer un panel de marge � droite
		JPanel margeDroite = new JPanel();
		margeDroite.setPreferredSize(new Dimension(60, 30));
		
		//On creer un panel de marge en haut
		JPanel margeHaut = new JPanel();
		margeHaut.setPreferredSize(new Dimension(30, 15));
		
		//On creer un panel de marge en bas
		JPanel margeBas = new JPanel();
		margeBas.setPreferredSize(new Dimension(30, 20));
		
		//On cr�er un panel avec les champs de texte
		JPanel champsTexte = new JPanel();
		champsTexte.setLayout(new GridLayout(2,1,0,5));
		champsTexte.add(nom);
		champsTexte.add(nbTerrains);
		
		//On les ajoute au contenaire du haut
		JPanel topContainer= new JPanel();
		topContainer.setLayout(new BorderLayout());
		etiquettes.setPreferredSize(new Dimension(150,30));
		topContainer.add(etiquettes, BorderLayout.WEST);
		topContainer.add(margeHaut, BorderLayout.NORTH);
		topContainer.add(margeDroite, BorderLayout.EAST);
		topContainer.add(margeBas, BorderLayout.SOUTH);
		topContainer.add(champsTexte, BorderLayout.CENTER);
		
		//On ajoute les boutons
		JPanel buttonContainer = new JPanel(new BorderLayout());
		JButton valider = new JButton("Valider");
		//On assigne un controleur au bouton pour g�n�rer la cr�ation du tournoi
		valider.addActionListener(new NouveauTournoiControleur(o, nom, nbTerrains, this));
		
		//On assigne un controleur au bouton annuler pour fermer la fenetre
		JButton annuler = new JButton("Annuler");
		final JDialog frameToClose = this;
		annuler.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frameToClose.dispose();
				
			}
		});
		
		JPanel boutonsAnVal = new JPanel();
		boutonsAnVal.setLayout(new BoxLayout(boutonsAnVal, BoxLayout.X_AXIS));
		boutonsAnVal.add(annuler);
		boutonsAnVal.add(valider);
		buttonContainer.add(boutonsAnVal, BorderLayout.EAST);
		buttonContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		//On ajoute le conteneur du haut au content pane
		container.add(topContainer, BorderLayout.NORTH);
		container.add(buttonContainer, BorderLayout.SOUTH);
		
		//On ajoute le content pane � la fen�tre
		this.setContentPane(container);
		
		//On pr�selectionne le bouton valider
		this.getRootPane().setDefaultButton(valider);
		
		//affichage final
		this.pack();
		this.setVisible(true);
		this.setTitle("Nouveau Tournoi");
		this.setLocationRelativeTo(null);
		this.setSize(410, 150);
		this.setResizable(false);

	}
}
