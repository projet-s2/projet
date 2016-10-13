package controleur;

import tournoi.Joueur;
import tournoi.Tournoi;
import vue.FenetrePrincipale;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
        //Ouverture de la fenetre "enregistrer sous"
        Frame fr = new Frame("Choississez un répertoire");
        FileDialog dial = new FileDialog(fr, "Importer un fichier", FileDialog.SAVE);
        dial.setFile(".csv"); //Pré-écrit l'extension .csv dans la fenêtre de dialogue
        dial.setVisible(true);
        fr.setVisible(false);
        try {
            /* On crée un BufferedWriter (FileWriter avec la possibilité de créer une nouvelle ligne)
                qui va créer un fichier du nom qu'à choisi l'utilisateur et écrire dans celui ci
            */
            BufferedWriter fichier = new BufferedWriter(new FileWriter (dial.getDirectory().concat(dial.getFile()) ));
            for (Joueur j : tournoi.getAnciensJoueurs()) {
                fichier.write(decouperJoueur(j));
                fichier.newLine();
            }

            for (Joueur j : tournoi.getNouveauxJoueurs()) {
                fichier.write(decouperJoueur(j));
                fichier.newLine();
            }
            fichier.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }
    /** Découpe un joueur pour obtenir une chaine de caractère correspondant à une ligne CSV
     * @param joueur le joueur à découper
     * @return La chaine de caractère correspondant à une ligne CSV représentant le joueur découpé
     */
    private String decouperJoueur(Joueur joueur) {
        /* [0] : id / [1] : nom / [2] : prenom / [3] : age / [4] : sexe (0 : homme / 1 : femme)
               [5] : nouveau (0 : ancien / 1 : nouveau) / [6] : niveau  (0 : débutant / 1 : Intérmédiaire / 2 : confirmé)
               [7] : peutJouer
            */

        StringBuilder chaine = new StringBuilder();
        chaine.append(Integer.toString(joueur.getId()) + ",");
        chaine.append(joueur.getNom() + ",");
        chaine.append(joueur.getPrenom() + ",");
        chaine.append(Integer.toString(joueur.getAge()) + ",");
        chaine.append(joueur.getSexe() ? "1" : "0" + ",");
        chaine.append(joueur.getNouveau() ? "1" : "0" + ",");
        chaine.append(Integer.toString(joueur.getNiveau()) + ",");
        chaine.append(joueur.peutJouer() ? "1" : "0");
        return chaine.toString();
    }
}