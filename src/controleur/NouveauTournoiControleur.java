package controleur;

import java.awt.Frame;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import exception.*;
import liste.Liste;
import tournoi.*;
import vue.*;

public class NouveauTournoiControleur implements ActionListener {
	private JSpinner nbTerrains;
	private JTextField nom;
	private FenetrePrincipale fenetre;
	private NouveauTournoi nouveauTournoiFen;
	public NouveauTournoiControleur(FenetrePrincipale f, JTextField leNom, JSpinner leNbTerrains, NouveauTournoi nt){
		this.nbTerrains = leNbTerrains;
		this.nom = leNom;
		this.nouveauTournoiFen = nt;
		this.fenetre = f;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		//On créer un nouveau tournoi
		try{
			Tournoi t = new Tournoi((int) this.nbTerrains.getValue(), this.nom.getText());
			//On assigne un tournoi à la fenetre principale
			fenetre.setTournoi(t);
			//Fermeture de la fenetre
			this.nouveauTournoiFen.dispose();
		}
		catch(NomVideException e1){
			JOptionPane.showMessageDialog(null, "Entrez un nom de tournoi", "Erreur", JOptionPane.ERROR_MESSAGE);
		} 
		catch(NbTerrainNeg e1){
			JOptionPane.showMessageDialog(null, "Entrez un nombre de terrain positif", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
		
		
		

	}
}
