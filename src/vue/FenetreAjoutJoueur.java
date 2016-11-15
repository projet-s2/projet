package vue;

import controleur.AjouterJoueurControlleur;
import tournoi.Joueur;
import tournoi.Tournoi;

import javax.swing.*;
import java.awt.*;

public class FenetreAjoutJoueur extends JFrame {

	private Tournoi tournoi;
	FenetrePrincipale vue;

	private JTextField nom;
	private JTextField prenom;
	private JComboBox niveau;
	private JComboBox age;
	private JRadioButton fem;
	private JRadioButton hom;
	private JCheckBox nouv;

	/**
	 * constructeur de la fenêtre d'ajout d'un joueur
	 * @param titre le titre à donner à la fenêtre
	 * @param tournoi le tournoi dans lequel on veut ajouter un joueur
	 * @param vue la vue qui crée la fenêtre
     */
	public FenetreAjoutJoueur(String titre, Tournoi tournoi, FenetrePrincipale vue){

		this.tournoi = tournoi;
		this.vue = vue;

		//Les différents champs de saisie
		nom = new JTextField();
		prenom = new JTextField();
		//Il faut bien laisser les niveaux dans cet ordre pour correspondre avec l'ajout du joueur
		// (0 : Indéfini / 1 : Débutant / 2 : Intermédiaire / 3 : Confirmé)
		niveau = new JComboBox(new String[]{"Indéfini", "Débutant", "Intermédiaire", "Confirmé"});

		//Il faut bien laisser les âges dans cet ordre pour correspondre avec l'ajout du joueur
		// (0 : Indéfini / 1 : -18 jeune / 2 : 18-35 senior / 3 : 35+ veteran)
		age = new JComboBox(new String[]{"Indéfini", "-18 ans (Jeune)", "18-35 ans (Senior)", "35 ans et + (Veteran)"});
		fem = new JRadioButton("Femme");
		hom = new JRadioButton("Homme");
		hom.setSelected(true);
		ButtonGroup grSexe = new ButtonGroup();
		grSexe.add(hom);
		grSexe.add(fem);
		nouv = new JCheckBox("Nouveau");
		nouv.setSelected(true);
		
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
		
		gauche.add(new JLabel("Niveau :"));
		gauche.add(niveau);
		
		corePanel.add(gauche,BorderLayout.WEST);
		
		JPanel droite = new JPanel();
		
		droite.setLayout(new GridLayout(3,2));
		droite.add(new JLabel("Prenom :"));
		droite.add(prenom);
		
		JPanel panelAnc = new JPanel();
		panelAnc.setLayout(new GridLayout(2,1));
		panelAnc.add(nouv);
		
		droite.add(new JLabel("Anciennete :"));
		droite.add(panelAnc);
		
		droite.add(new JLabel("Age :"));
		droite.add(age);
		
		corePanel.add(droite,BorderLayout.EAST);
		
		corePanel.add(ajouter,BorderLayout.SOUTH);
		
		this.setContentPane(corePanel);
		this.pack();
		this.setVisible(true);
		this.setTitle(titre);
		int tailleX = 600, tailleY = 200;
		this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-tailleX)/2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-tailleY)/2);
		this.setSize(tailleX,tailleY);
		this.setResizable(false);
	}

	/**
	 *
	 * @return le champ de saisi du nom
     */
	public JTextField getNom() {
		return nom;
	}

	/**
	 *
	 * @param str la chaine de caractère à écrire dans le champ de saisie du nom
     */
	public void setNom(String str){
		this.nom.setText(str);
	}

	/**
	 *
	 * @returnle champ de saisie du prénom
     */
	public JTextField getPrenom() {
		return prenom;
	}

	/**
	 *
	 * @param str la chaine de caractère à écrire dans le champ de saisie du nom
     */
	public void setPrenom(String str){
		this.prenom.setText(str);
	}

	public JComboBox getNiveau() {
		return niveau;
	}

	/**
	 *
	 * @return l'outil de séléction de l'âge
     */
	public JComboBox getAge() {
		return age;
	}

	/**
	 * réinitialise l'age
	 */
	public void setAge(){
		this.age.setSelectedIndex(0);
	}

	public JRadioButton getFem() {
		return fem;
	}


	public JRadioButton getHom() {
		return hom;
	}

	/**
	 *
	 * @return la case qui indique l'ancienneté
     */
	public JCheckBox getNouv() {
		return nouv;
	}

	/**
	 * pour ajouter un joueur dans le tournoi et dans la liste de la fenetre principale
	 */
	public void ajouterJoueur(){
		int id = Joueur.nbJoueursCrees;
		int age = this.age.getSelectedIndex(); // 0 : -18 jeune / 1 : 18-35 senior / 2 : 35+ veteran
		String nom = this.nom.getText(), prenom = this.prenom.getText();
		boolean sexe = hom.isSelected();
		boolean nouveau = nouv.isSelected();
		int niveau = this.niveau.getSelectedIndex();
		Joueur j = new Joueur(id, nom, prenom, age, sexe, nouveau, niveau, true);
		this.setNom("");
		this.setPrenom("");
		this.setAge();
		tournoi.ajouterJoueur(j);
		vue.ajouterJoueurTable();

	}


}
