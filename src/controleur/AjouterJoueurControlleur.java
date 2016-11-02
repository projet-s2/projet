package controleur;

import java.util.regex.Pattern;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FenetreAjoutJoueur;

import javax.swing.*;

public class AjouterJoueurControlleur implements ActionListener {
	
	private FenetreAjoutJoueur vue;

	/**
	 * constructeur du controleur
	 * @param vue la fenetre principale
     */
	public AjouterJoueurControlleur(FenetreAjoutJoueur vue){
		this.vue = vue;
	}

	/**
	 * pour ajouter un joueur (voir ajouterJoueur dans FenetrePrincipale)
	 * @param e un clique sur le bouton ajouter un joueur (ou raccourci via menu)
     */
	public void actionPerformed(ActionEvent e) {
		if (verifier()){
			vue.ajouterJoueur();
			JOptionPane.showMessageDialog(vue,"Joueur ajouté !");
		}

	}

	/**
	 * pour vérifier si les informations entrées sont valables
	 * @return vrai si on peut ajouter le joueur, faux s'il y a un problème
     */
	public boolean verifier(){
		// On vérifie qu'il y a un nom et un prénom
		if (vue.getNom().getText().equals("") || vue.getPrenom().getText().equals("")){
			JOptionPane.showMessageDialog(vue,"Merci d'indiquer un nom et un prenom");
			return false;
		}
		//Le nom et le prénom sont composés de lettres non accentuées et éventuellement de tirets, espaces ou apostrophes
		String reg = "([[a-zA-Z][-][ ']]*)*";
		if ( !vue.getNom().getText().matches(reg) || !vue.getPrenom().getText().matches(reg) ){
			JOptionPane.showMessageDialog(vue,"Le nom et le prénom doivent être composé de lettres non accentuées\n avec éventuellement des tirets -, espaces ou apostrophes '");
			return false;
		}
	return true;
	}
}
