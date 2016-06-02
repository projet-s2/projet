package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import controleur.AjouterJoueurControlleur;

public class FenetreAjoutJoueur extends JFrame {

	private Tournoi tournoi;

	private JTextField nom;
	private JTextField prenom;
	private JComboBox niveau;
	private JComboBox anciennete;
	private JSpinner age;
	private JRadioButton fem;
	private JRadioButton hom;
	private JRadioButton deb;
	private JRadioButton anc;

	public FenetreAjoutJoueur(String titre, Tournoi tournoi){

		this.tournoi = tournoi;

		nom = new JTextField();
		prenom = new JTextField();
		niveau = new JComboBox(new String[]{"Debutant","Intermediaire", "Confirme"});
		anciennete = new JComboBox(new String[]{"Nouveau","Ancien"});
		age = new JSpinner();
		fem = new JRadioButton("Femme");
		hom = new JRadioButton("Homme");
		hom.setSelected(true);
		ButtonGroup grSexe = new ButtonGroup();
		grSexe.add(hom);
		grSexe.add(fem);
		deb = new JRadioButton("Debutant");
		anc = new JRadioButton("Ancien");
		deb.setSelected(true);
		ButtonGroup grAnc = new ButtonGroup();
		grAnc.add(deb);
		grAnc.add(anc);
		
		JButton ajouter = new JButton("Ajouter le joueur");
		ajouter.addActionListener(new AjouterJoueurControlleur(this));
		
		JPanel corePanel = new JPanel();
		corePanel.setLayout(new BorderLayout());
		
		JPanel gauche = new JPanel();
		
		gauche.setLayout(new GridLayout(3,2));
		gauche.add(new JLabel("Nom :"));
		gauche.add(nom);
		
		JPanel panelSexe = new JPanel();
		panelSexe.setLayout(new GridLayout(2,1));
		panelSexe.add(hom);
		panelSexe.add(fem);
		
		gauche.add(new JLabel("Sexe :"));
		gauche.add(panelSexe);
		
		gauche.add(new JLabel("Niveau"));
		gauche.add(niveau);
		
		corePanel.add(gauche,BorderLayout.WEST);
		
		JPanel droite = new JPanel();
		
		droite.setLayout(new GridLayout(3,2));
		droite.add(new JLabel("Prenom :"));
		droite.add(prenom);
		
		JPanel panelAnc = new JPanel();
		panelAnc.setLayout(new GridLayout(2,1));
		panelAnc.add(deb);
		panelAnc.add(anc);
		
		droite.add(new JLabel("Anciennete :"));
		droite.add(panelAnc);
		
		droite.add(new JLabel("Age :"));
		droite.add(age);
		
		corePanel.add(droite,BorderLayout.EAST);
		
		corePanel.add(ajouter,BorderLayout.SOUTH);
		
		this.setContentPane(corePanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setTitle(titre);
		this.setLocation(0,0);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/4, Toolkit.getDefaultToolkit().getScreenSize().height/2);
		this.setVisible(true);
	}
	
	public JTextField getNom() {
		return nom;
	}


	public JTextField getPrenom() {
		return prenom;
	}


	public JComboBox getNiveau() {
		return niveau;
	}


	public JComboBox getAnciennete() {
		return anciennete;
	}


	public JSpinner getAge() {
		return age;
	}


	public JRadioButton getFem() {
		return fem;
	}


	public JRadioButton getHom() {
		return hom;
	}


	public JRadioButton getDeb() {
		return deb;
	}


	public JRadioButton getAnc() {
		return anc;
	}


	public void ajouterJoueur(){
		int id = Joueur.nbJoueursCrees, age = this.age.getValue();
		String nom = this.nom.getText(), prenom = this.prenom.getText();
		tournoi.ajouterJoueur(new Joueur(id, nom, prenom, age, sexe, nouveau, niveau, true));
	}

	

	
	public static void main(String[] args){
		new FenetreAjoutJoueur("COUCOu");
	}

}
