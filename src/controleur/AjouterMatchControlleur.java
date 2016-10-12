package controleur;

import tournoi.Tournoi;
import vue.FenetreAjoutJoueur;
import vue.FenetreAjoutMatch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lea on 09/10/16.
 */
public class AjouterMatchControlleur implements ActionListener {

    private FenetreAjoutMatch vue;
    private Tournoi tournoi;
    private int score1;
    private int score2;

    /**
     * constructeur du controleur
     * @param vue la fenetre principale
     */
    public AjouterMatchControlleur(FenetreAjoutMatch vue){
        this.vue = vue;
        score1 = (int)vue.getScore1().getValue();
        score2 = (int)vue.getScore2().getValue();
        tournoi = vue.getTournoi();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (verifier())
            tournoi.setScore(0, score1, score2);
         //ToDo créée des paires sur le tournoi

    }

    /**
     * pour vérifier si on peut entrer les scores
     * @return true si les scores sont valables false sinon
     */
    private boolean verifier(){
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
            int test = score2;
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
