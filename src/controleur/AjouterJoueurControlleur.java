package controleur;

import java.util.regex.Pattern;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FenetreAjoutJoueur;

public class AjouterJoueurControlleur implements ActionListener {
	
	private FenetreAjoutJoueur vue;

	public AjouterJoueurControlleur(FenetreAjoutJoueur vue){
		this.vue = vue;
	}
	public void actionPerformed(ActionEvent e) {
		if (verifier()){
			vue.ajouterJoueur();
		}

	}

	
	public boolean verifier(){
		//reg = "([a-zA-Z]*\s*[-']*)*";
		if ( vue.getNom().getText().matches("([a-zA-Z]*\s*[-']*)*") && vue.getPrenom().getText().matches("([a-zA-Z]*\s*[-']*)*") ){
			return true;
			
		}
		
		return false;
	}
}
