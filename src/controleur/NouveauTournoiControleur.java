package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import exception.*;
import tournoi.*;
import vue.*;

public class NouveauTournoiControleur implements ActionListener {
	private JSpinner nbTerrains;
	private JTextField nom;
	private FenetrePrincipale fenetre;
	private NouveauTournoi nouveauTournoiFen;

	/**
	 * constructeur du controleur de la fenêtre de lancement
	 * @param f la fenêtre principale associée
	 * @param leNom le champs de saisie du nom du tournoi
	 * @param leNbTerrains l'outil de sélection du nombre de terrains
     * @param nt la fenêtre de lancement associée
     */
	public NouveauTournoiControleur(FenetrePrincipale f, JTextField leNom, JSpinner leNbTerrains, NouveauTournoi nt){
		this.nbTerrains = leNbTerrains;
		this.nom = leNom;
		this.nouveauTournoiFen = nt;
		this.fenetre = f;

	}

	/**
	 * pour créer un tournoi
	 * @param e un clic sur me bouton de lancement du tournoi
     */
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
		//On vérifie qu'on peut créer un tournoi avec les informations indiquées
		catch(NomVideException e1){
			JOptionPane.showMessageDialog(null, "Entrez un nom de tournoi", "Erreur", JOptionPane.ERROR_MESSAGE);
		} 
		catch(NbTerrainNeg e1){
			JOptionPane.showMessageDialog(null, "Entrez un nombre de terrain positif", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
		
		
		

	}
}
