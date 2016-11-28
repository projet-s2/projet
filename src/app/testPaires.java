package app;

import exception.NbTerrainNeg;
import exception.NomVideException;
import tournoi.Joueur;
import tournoi.Tournoi;
import vue.FenetrePrincipale;
import vue.NouveauTournoi;

/**
 * Created by E154981H on 21/11/16.
 */
public class testPaires {
    public static void main(String[] args) {


        try {
            Tournoi tourn = new Tournoi(3,"truc");
            Joueur joueur1 = new Joueur(0, "a", "aa", 1, false, true, 1, true);
            Joueur joueur2 = new Joueur(1, "b", "bb", 1, true, true, 1, true);
            Joueur joueur3 = new Joueur(2, "c", "cc", 1, false, false, 1, true);
            Joueur joueur4 = new Joueur(3, "d", "dd", 3, true, false, 0, true);
            Joueur joueur5 = new Joueur(4, "e", "ee", 0, false, true, 3, true);
            Joueur joueur6 = new Joueur(5, "f", "ff", 1, true, false, 2, true);
            Joueur joueur7 = new Joueur(6, "g", "gg", 2, true, true, 3, true);
            Joueur joueur8 = new Joueur(7, "h", "hh", 2, true, false, 3, true);
            tourn.ajouterJoueur(joueur1);
            tourn.ajouterJoueur(joueur2);
            tourn.ajouterJoueur(joueur3);
            tourn.ajouterJoueur(joueur4);
            tourn.ajouterJoueur(joueur5);
            tourn.ajouterJoueur(joueur6);
            tourn.ajouterJoueur(joueur7);
            tourn.ajouterJoueur(joueur8);
            System.out.println("Tour 1 :");
            tourn.nouveauTour();
            System.out.println("Tour 2 :");
            tourn.nouveauTour();
            System.out.println("Tour 3 :");

        } catch (NomVideException e) {
            e.printStackTrace();
        } catch (NbTerrainNeg nbTerrainNeg) {
            nbTerrainNeg.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
