package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FenetreAjoutJoueur;

public class FenetreAjouterControlleur implements ActionListener{
	
	public void actionPerformed(ActionEvent e){
		new FenetreAjoutJoueur("Ajouter un joueur");
	}
	
	
	
	
	

}
