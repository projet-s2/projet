package controleur;

import vue.FenetreAjoutJoueur;
import vue.FenetreAjoutMatch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lea on 09/10/16.
 */
public class AjouterMatchControlleur implements ActionListener {

    private FenetreAjoutMatch vue;

    /**
     * constructeur du controleur
     * @param vue la fenetre principale
     */
    public AjouterMatchControlleur(FenetreAjoutMatch vue){
        this.vue = vue;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
