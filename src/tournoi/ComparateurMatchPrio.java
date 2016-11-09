package tournoi;

import java.util.Comparator;

/**
 * Created by E154981H on 09/11/16.
 */
public class ComparateurMatchPrio implements Comparator<Match> {

    /*
    *
    * Permet de comparer des matches pour les ranger par ordre croissant du nombre de matches joués
    *
     */
    @Override
    public int compare(Match p1, Match p2) {
        if (p1.prio() > p2.prio()) {
            return 1;
        } else {
            if (p1.prio() < p2.prio()) {
                return -1;
            } else {
                return 0;

            }
        }

    }
}


