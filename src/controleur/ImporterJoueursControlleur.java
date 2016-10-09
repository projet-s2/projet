package controleur;

import tournoi.Joueur;
import tournoi.Tournoi;
import vue.FenetrePrincipale;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Created by Martineau on 04/10/2016.
 */
public class ImporterJoueursControlleur implements ActionListener {

    Tournoi tournoi;
    FenetrePrincipale vue;

    public ImporterJoueursControlleur(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    public ImporterJoueursControlleur(Tournoi tournoi , FenetrePrincipale vue) {
        this.tournoi = tournoi;
        this.vue = vue;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Frame fr = new Frame("Choississez un répertoire");
        FileDialog dial = new FileDialog(fr, "Importer un fichier", FileDialog.LOAD);
        dial.setFile("*.csv");
        dial.setVisible(true);
        fr.setVisible(false);
        if (dial.getFile() != null) {
            String cvsFile = dial.getDirectory().concat(dial.getFile());
            try {
                ArrayList<Joueur> listeJoueur = cvsReader(cvsFile);
                for (Joueur j: listeJoueur) {
                    if (!tournoi.getAnciensJoueurs().contains(j) && !tournoi.getNouveauxJoueurs().contains(j)) {
                        tournoi.ajouterJoueur(j);
                        vue.ajouterJoueurTable();
                    }
                }
            } catch (java.io.FileNotFoundException e2) {
                JOptionPane.showMessageDialog(null, "Le fichier demandé n'a pas été trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (java.io.IOException e3) {
                e3.printStackTrace();
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e4) {
                JOptionPane.showMessageDialog(null, "Fichier erroné", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e6) {
                e6.printStackTrace();
            }

        }

    }

    /** Retourne la liste des joueurs qu'on peut trouver dans le CSV
     * @param fileDirectory le chemin pour accèder au CSV
     * @return une ArrayList avec tous les joueurs
     */
    private ArrayList<Joueur> cvsReader(String fileDirectory) throws java.io.FileNotFoundException, java.io.IOException{
        ArrayList listeRetour = new ArrayList<Joueur>();

        FileReader fReader = new FileReader(fileDirectory);
        BufferedReader br = new BufferedReader(fReader);

        ArrayList<String> resultatRead = new ArrayList<String>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            resultatRead.add(line);
        }

        br.close();
        fReader.close();

        String joueurCourant[];
        boolean sexe, nouveau, peutJouer;
        int id, age, niveau;
        String nom, prenom;
        for (int i = 0; i < resultatRead.size(); i++) {
            /* [0] : id / [1] : nom / [2] : prenom / [3] : age / [4] : sexe (0 : homme / 1 : femme)
               [5] : nouveau (0 : ancien / 1 : nouveau) / [6] : niveau  (0 : débutant / 1 : Intérmédiaire / 2 : confirmé)
               [7] : peutJouer
            */
            joueurCourant = resultatRead.get(i).split(",");

            id = Integer.parseInt(joueurCourant[0]);
            nom = joueurCourant[1];
            prenom = joueurCourant[2];
            age = Integer.parseInt(joueurCourant[3]);

            //Si joueurCourant[4] == 0, alors sexe = false (homme); sinon femmme
            sexe = Integer.parseInt(joueurCourant[4]) == 0 ? false : true;
            //Si joueurCourant[5] == 0, alors nouveau = false (ancien); sinon nouveau
            nouveau = Integer.parseInt(joueurCourant[5]) == 0 ? false : true;
            niveau = Integer.parseInt(joueurCourant[6]);
            peutJouer = Integer.parseInt(joueurCourant[7]) == 0 ? false : true;
            listeRetour.add(new Joueur(id, nom, prenom, age, sexe, nouveau, niveau, peutJouer));
        }
        return listeRetour;
    }
}
