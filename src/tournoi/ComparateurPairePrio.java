package tournoi;

import java.util.Comparator;

/**
 * Created by E154981H on 07/11/16.
 */
public class ComparateurPairePrio implements Comparator<Paire> {

    /*
    *
    * Permet de comparer des joueur pour les ranger par ordre croissant du nombre de matches joués
    *
     */
    @Override
    public int compare(Paire p1, Paire p2) {
        if(p1.prio()>p2.prio()){return 1;}
        else{
            if(p1.prio()<p2.prio()){return -1;}
            else{
                return 0;

            }
        }
    }
}
