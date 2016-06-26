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

	public SaisirScoreControlleur(JTextField jt1, JTextField jt2, FenetrePrincipale v, Tournoi t, int numTer, JPanel terr){
		scoreP1 = jt1;
		scoreP2 = jt2;
		tournoi = t;
		vue = v;
		numeroTerrain = numTer;
		terrain = terr;
		maj = false;
	}
	
	public void actionPerformed(ActionEvent e){
		if (verifier())
			tournoi.setScore(numeroTerrain,Integer.parseInt(scoreP1.getText()),Integer.parseInt(scoreP2.getText()));
		terrain.setBackground(new Color(63, 14, 126));
		terrain.setFocusable(false);
		if (!maj){
			maj = true;
			vue.rentrerVerif();
		}
	}

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
