package tournoi;

import java.util.Comparator;

/**
 * Created by E154981H on 09/11/16.
 */
public class ComparateurJoueurScore implements Comparator<Joueur> {

    /*
    *
    * Permet de comparer les joueurs en fonction de leurs scores
    *
     */
    @Override
    public int compare(Joueur j1, Joueur pj2) {
        if (j1.getScore() < pj2.getScore()) {
            return 1;
        } else {
            if (j1.getScore() > pj2.getScore()) {
                return -1;
            } else {
                return 0;

            }
        }

    }
}


