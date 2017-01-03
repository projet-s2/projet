package controleur;

import tournoi.Tournoi;
import vue.FenetrePrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by E154981H on 02/11/16.
 */
public class ResetControlleur  implements ActionListener {
    private Tournoi tournoi;
    private FenetrePrincipale vue;



    public ResetControlleur(FenetrePrincipale vue) {
        this.vue = vue;
        this.tournoi = vue.getTournoi();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tournoi.resetAll();
        vue.actualiserJoueurs();
    }
}
