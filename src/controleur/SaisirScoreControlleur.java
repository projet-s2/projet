package controleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import tournoi.Tournoi;
import vue.FenetrePrincipale;

public class SaisirScoreControlleur implements ActionListener{
	
	private JTextField scoreP1;
	private JTextField scoreP2;
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
     * @param terr le JPanel associé
     */
	public SaisirScoreControlleur(JTextField jt1, JTextField jt2, FenetrePrincipale v, Tournoi t, int numTer, JPanel terr){
		scoreP1 = jt1;	//passer en int directement
		scoreP2 = jt2;
		tournoi = t;
		vue = v;
		numeroTerrain = numTer;
		terrain = terr;
		maj = false;
	}

	/**
	 * pour essayer d'entrer un score
	 * @param e un clic sur le bouton valider scores
     */
	public void actionPerformed(ActionEvent e){
		if (verifier())
			tournoi.setScore(numeroTerrain, Integer.parseInt(scoreP1.getText()), Integer.parseInt(scoreP2.getText()));
		tournoi.setScore(numeroTerrain, Integer.parseInt(scoreP1.getText()), Integer.parseInt(scoreP2.getText()));
		terrain.setFocusable(false);
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
		try {
			int test = Integer.parseInt(scoreP1.getText());
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
			int test = Integer.parseInt(scoreP2.getText());
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
