package app;

import exception.NbTerrainNeg;
import exception.NomVideException;
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


        } catch (NomVideException e) {
            e.printStackTrace();
        } catch (NbTerrainNeg nbTerrainNeg) {
            nbTerrainNeg.printStackTrace();
        }
    }
}
