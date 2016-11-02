package controleur;

import tournoi.Joueur;
import tournoi.Paire;
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
    private JSpinner sp_score1;
    private JSpinner sp_score2;
    private FenetreAjoutMatch vue;
    private Tournoi tournoi;
    private int score1;
    private int score2;
    private Paire paire1;
    private Paire paire2;


    /**
     * constructeur du controleur
     * @param vue la fenetre principale
     */
    public AjouterMatchControlleur(FenetreAjoutMatch vue,JSpinner s1,JSpinner s2){
        this.vue = vue;
        this.sp_score1 = s1;
        this.sp_score2 = s2;
        this.tournoi = vue.getTournoi();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        score1 = (int)sp_score1.getValue();
        score2 = (int)sp_score2.getValue();

        if (verifier()) {
            String j1 = ((String) vue.getJoueur1().getSelectedItem());
            String j2 = ((String) vue.getJoueur2().getSelectedItem());
            String j3 = ((String) vue.getJoueur3().getSelectedItem());
            String j4 = ((String) vue.getJoueur4().getSelectedItem());

            Joueur j11 = tournoi.getJoueur(tournoi.chercherJoueur(j1));
            Joueur j12 = tournoi.getJoueur(tournoi.chercherJoueur(j2));
            Joueur j21 = tournoi.getJoueur(tournoi.chercherJoueur(j3));
            Joueur j22 = tournoi.getJoueur(tournoi.chercherJoueur(j4));


            paire1 = new Paire(j11, j12);
            paire2 = new Paire(j21,j22);

           
            tournoi.setScoreManuel(paire1, paire2, score1, score2);
            vue.getVue().actualiserJoueurs();
            //fermeture de la fenètre
            vue.dispose();

        }
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
