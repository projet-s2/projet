package controleur;

import exception.ImportExportException;
import tournoi.Joueur;
import tournoi.Tournoi;
import vue.FenetrePrincipale;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.*;

/** La classe ImporterJoueursControlleur permet d'importer tous les joueurs contenus dans un fichier CSV
 * respectant cet ordre à chaque ligne :
 * [0] : id / [1] : nom / [2] : prenom / [3] : age / [4] : sexe (0 : homme / 1 : femme)
 * [5] : nouveau (0 : ancien / 1 : nouveau) / [6] : niveau  (0 : débutant / 1 : Intérmédiaire / 2 : confirmé)
 * [7] : peutJouer
 * @author DERNONCOURT Cyril , DROUARD Antoine, LE BERT Lea, MARTINEAU Lucas
 */
public class ImporterJoueursControlleur implements ActionListener {

    private Tournoi tournoi;
    private FenetrePrincipale vue;

    /** Constructeur de la classe ImporterJoueursControlleur
     *
     * @param tournoi le tournoi où importer les joueurs
     * @param vue la vue qu'il faut rafraichir lors de l'ajout des joueurs
     */
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
                ArrayList<Joueur> listeJoueur = tournoi.csvReader(cvsFile);
                for (Joueur j: listeJoueur) {
                    if (!tournoi.getAnciensJoueurs().contains(j) && !tournoi.getNouveauxJoueurs().contains(j)) {
                        tournoi.ajouterJoueur(j);
                        vue.ajouterJoueurTable();
                    }
                }
            } catch (java.io.FileNotFoundException e2) {
                JOptionPane.showMessageDialog(null, "Le fichier demandé n'a pas été trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e4) {
                JOptionPane.showMessageDialog(null, "Fichier erroné", "Erreur", JOptionPane.ERROR_MESSAGE);
                e4.printStackTrace();
            } catch (ImportExportException e4) {
                JOptionPane.showMessageDialog(null, "Fichier erroné : " + e4.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }


}
