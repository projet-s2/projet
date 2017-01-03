package controleur;

import tournoi.Joueur;
import tournoi.Tournoi;
import vue.FenetrePrincipale;
import vue.FenetreStatJoueur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by E154981H on 03/11/16.
 */
public class StatutJoueurControlleur implements ActionListener {
    private Tournoi tournoi;
    private FenetreStatJoueur vue;
    private Joueur joueur;


    public StatutJoueurControlleur(FenetreStatJoueur vue) {
        this.vue = vue;
        this.tournoi = vue.getTournoi();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.joueur = tournoi.getJoueur(tournoi.chercherJoueur((String)vue.getJoueur().getSelectedItem()));
        tournoi.statusJoueur(joueur);
        vue.getVue().actualiserJoueurs();
        this.vue.dispose();
    }
}
