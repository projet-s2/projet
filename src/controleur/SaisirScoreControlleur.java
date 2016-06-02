package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import tournoi.Tournoi;
import vue.FenetrePrincipale;

public class SaisirScoreControlleur implements ActionListener{
	
	private JTextField scoreP1;
	private JTextField scoreP2;
	private Tournoi tournoi;
	private int numeroTerrain;
	private FenetrePrincipale vue;
	
	public SaisirScoreControlleur(JTextField jt1, JTextField jt2, FenetrePrincipale v, Tournoi t, int numTer){
		scoreP1 = jt1;
		scoreP2 = jt2;
		tournoi = t;
		vue = v;
		numeroTerrain = numTer;
	}
	
	public void actionPerformed(ActionEvent e){
		if (verifier())
			tournoi.setScore(numeroTerrain,Integer.parseInt(scoreP1.getText()),Integer.parseInt(scoreP2.getText()));
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
		}
		
		return true;
	}
}
