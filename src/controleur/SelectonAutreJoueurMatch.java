package controleur;

import tournoi.Joueur;
import tournoi.Tournoi;
import vue.FenetrePrincipale;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Lea on 29/12/2016.
 */
public class SelectonAutreJoueurMatch implements ItemListener {
    private FenetrePrincipale vue;
    private Tournoi tournoi;
    private  JComboBox jselect;
    private Joueur jprec;//joueru precedament electionné
    private int nuJ;
    private int nuterr;


        public SelectonAutreJoueurMatch(JComboBox jselect, FenetrePrincipale vue,int nuJ,int nuterr){
            this.vue = vue;
            this.tournoi = vue.getTournoi();
            this.jselect = jselect;
            this.jprec = (Joueur) jselect.getSelectedItem();
            this.nuJ = nuJ;
            this.nuterr = nuterr;
        }
    @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Joueur jnouv = (Joueur) event.getItem();
                ////  verifier que jnouv n'est pas  ()joueur 420
                if(!(jnouv.getId()==420)){
                    this.tournoi.changerJoueurs(this.jprec,jnouv);
                }
                vue.actualiserTerrains();
            }
        }
}
