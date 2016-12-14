package controleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import tournoi.Tournoi;
import vue.FenetrePrincipale;

public class SaisirScoreControlleur implements ActionListener{
	
	private JSpinner scoreP1;
	private JSpinner scoreP2;
	private int score1;
	private int score2;
	private Tournoi tournoi;
	private int numeroTerrain;
	private FenetrePrincipale vue;
	private JPanel terrain;
	private boolean maj;

	/**
	 * constructeur du controleur pour entrer les scores sur un terrain
	 * @param jt1 le champ de saisie du score de la première paire
	 * @param jt2 le champ de saisie du score de la seconde paire
	 * @param v la fenetre principale
	 * @param t le tournoi
	 * @param numTer le numéro de terrain
     *
     */
	public SaisirScoreControlleur(JSpinner jt1, JSpinner jt2, FenetrePrincipale v, Tournoi t, int numTer){
		scoreP1 = jt1;
		scoreP2 = jt2;
		tournoi = t;
		vue = v;
		numeroTerrain = numTer;
		maj = false;
	}

	/**
	 * pour essayer d'entrer un score
	 * @param e un clic sur le bouton valider scores
     */
	public void actionPerformed(ActionEvent e){

		 this.score1 =(int) scoreP1.getValue();
		 this.score2 =(int) scoreP2.getValue();


		if (verifier()) {
			tournoi.setScore(numeroTerrain, score1, score2);


		}
		vue.actualiserJoueurs();
		//fermeture de la fenètre

		if (!maj){
			maj = true;
			vue.rentrerVerif();
		}
	}

	/**
	 * pour vérifier si on peut entrer les scores
	 * @return true si les scores sont valables false sinon
     */
	private boolean verifier(){
		if(this.tournoi.terrainVide(this.numeroTerrain)){
			JOptionPane.showMessageDialog(vue, "Il manque un joueur");
			return false;
		}



		try {
			int test = score1;
			if(test<0){
				JOptionPane.showMessageDialog(vue, "Vous devez entrer un entier positif.");
				return false;
			}
				
		}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(vue, "Vous devez entrer un entier positif.");
			return false;
		}
		try {
			int test = score1;
			if(test<0){
				JOptionPane.showMessageDialog(vue, "Vous devez entrer un entier positif.");
				return false;
			}

		}
		catch (NumberFormatException e){
			JOptionPane.showMessageDialog(vue, "Vous devez entrer un entier positif.");
			return false;
		}
		
		return true;
	}

}
