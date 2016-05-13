package vue;

import java.awt.*;

import javax.swing.*;

public class NouveauTournoi extends JFrame {
	public NouveauTournoi(){
		//On créer la fenetre
				
		//On initialise le content pane ainsi que sa structure
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		
		//on créer éléments
		JTextField nom = new JTextField();
		JTextField nbTerrains = new JTextField();
		JLabel nomLabel = new JLabel("Nom : ");
		JLabel nbTerrainsLabel = new JLabel("Nombre de terrains : ");
		
		//On les ajoute à des panels de position
		JPanel etiquettes = new JPanel();
		etiquettes.setLayout(new GridLayout(2,1));
		etiquettes.add(nomLabel);
		etiquettes.add(nbTerrainsLabel);
		
		JPanel champsTexte = new JPanel();
		champsTexte.setLayout(new GridLayout(2,1));
		JPanel nomCentre = new JPanel();
		nomCentre.setLayout(new BoxLayout(nomCentre, BoxLayout.X_AXIS));
		nomCentre.add(nom);
		champsTexte.add(nomCentre);
		champsTexte.add(nbTerrains);
		
		//On les ajoute au content pane
		container.add(etiquettes, BorderLayout.WEST);
		container.add(champsTexte, BorderLayout.CENTER);
		
		//On ajoute le content pane à la fenêtre
		this.setContentPane(container);
		
		//affichage final
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setTitle("Nouveau Tournoi");
		this.setLocationRelativeTo(null);
		this.setSize(400, 250);
	}
}
