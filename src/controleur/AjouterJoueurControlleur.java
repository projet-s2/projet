package controleur;

import java.util.regex.Pattern;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FenetreAjoutJoueur;

import javax.swing.*;

public class AjouterJoueurControlleur implements ActionListener {
	
	private FenetreAjoutJoueur vue;

	public AjouterJoueurControlleur(FenetreAjoutJoueur vue){
		this.vue = vue;
	}
	public void actionPerformed(ActionEvent e) {
		if (verifier()){
			vue.ajouterJoueur();
			JOptionPane.showMessageDialog(vue,"Joueur ajouté !");
		}

	}

	
	public boolean verifier(){
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
		//Verification sur l'age
		if ((int)vue.getAge().getValue()<=0){
			JOptionPane.showMessageDialog(vue,"L'age doit etre un entier positif");
			return false;
		}
	return true;
	}
}
