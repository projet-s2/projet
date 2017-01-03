package controleur;

import javax.swing.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import vue.FenetrePrincipale;
import tournoi.*;

/**
 * Created by anton on 05/11/16.
 */
public  class ComboBoxSwapControlleur implements ItemListener {

    private FenetrePrincipale vue;
    private ArrayList<JComboBox> boxTab;
    private JComboBox boxActuel;
    private Tournoi tournoi;

    public ComboBoxSwapControlleur(FenetrePrincipale v, JComboBox b) {
        this.vue = v;
        this.boxTab = vue.getBoxTerrains();
        this.boxActuel = b;
        this.tournoi = vue.getTournoi();
    }

    public void itemStateChanged(ItemEvent e) {
        /*
         Quand on selectionne un joueur, on parcours le tableau pour chercher si le joueur choisit est selectionné,
         si il l'est on remplace le joueur selectionné par ce JComboBox par notre actuel.
         */
        String previous = "ComboBox SwapControlleur";
        if(e.getStateChange() == ItemEvent.DESELECTED) {
            previous = boxActuel.getSelectedItem().toString();
        }
        if(e.getStateChange() == ItemEvent.SELECTED) {
            for(int i=0;i<boxTab.size();i++) {
                System.out.println(i);
                if (boxTab.get(i).getSelectedItem().equals(boxActuel.getSelectedItem())) {
                    System.out.println("boucle: " + i);
                    boxTab.get(i).setSelectedItem(tournoi.getJoueur(tournoi.chercherJoueur(previous)));
                }
            }
        }
    }
}
