package controleur;

import tournoi.Tournoi;
import vue.FenetrePrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by E155301F on 12/10/16.
 */
public class ExporterJoueursControlleur implements ActionListener {

    private Tournoi tournoi;
    private FenetrePrincipale vue;

    public ExporterJoueursControlleur(Tournoi tournoi, FenetrePrincipale vue) {
        this.tournoi = tournoi;
        this.vue = vue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
