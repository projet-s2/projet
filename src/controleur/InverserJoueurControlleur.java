package controleur;

import tournoi.Tournoi;
import vue.FenetrePrincipale;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Pomme on 27/06/2016.
 */
public class InverserJoueurControlleur implements ActionListener{

    private Tournoi tournoi;
    private FenetrePrincipale vue;
    String joueurCourant;

    /**
     * constructeur de controleur de changement de joueurs sur les terrains
     * @param t le tournoi
     * @param v la fenetre principale
     */
    public InverserJoueurControlleur(Tournoi t, FenetrePrincipale v, String jC){
        tournoi = t;
        vue = v;
        joueurCourant = jC;
    }

    /**
     * pour choisir un joueur à remplacer
     * @param e on sélectionne un joueur dans le menu déroulant
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String joueur2 = (String)((JComboBox)e.getSource()).getSelectedItem();
        int idJ2 = tournoi.chercherJoueur(joueur2);
        int idJ1 = tournoi.chercherJoueur(joueurCourant);
        System.out.println("j1 :\t"+joueurCourant +"\t"+idJ1);
        System.out.println("j2 :\t"+joueur2 +"\t"+idJ2);
       // if(!tournoi.changerJoueurs(idJ1, idJ2))
            JOptionPane.showMessageDialog(vue,"Erreur");
        vue.setVerif(0);
        vue.actualiserJoueurs();
        //vue.actualiserTerrains();
    }
}
