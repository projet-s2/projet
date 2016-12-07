package tournoi;

import java.util.ArrayList;

/**
 * Created by E154981H on 07/12/16.
 */
public class Tour {
    private ArrayList<Terrain> matches;

    public Tour(ArrayList<Terrain> matches) {
        this.matches = matches;
    }

    public ArrayList<Terrain> getMatches() {
        return matches;
    }
}
