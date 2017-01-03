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
	return true;
	}
}
