package controleur;

import tournoi.Joueur;
import tournoi.Tournoi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

/** La classe ExporterJoueursControlleur permet d'exporter les anciens ainsi que les nouveaux
 *  joueurs contenus dans la classe tournoi dans un fichier CSV
 * @author DERNONCOURT Cyril , DROUARD Antoine, LE BERT Lea, MARTINEAU Lucas
 */
public class ExporterJoueursControlleur implements ActionListener {

    private final Tournoi tournoi;

    /** Constructeur de la classe ExporterJoueursControlleur
     *
     * @param tournoi le tournoi où trouver les joueurs à exporter
     */
    public ExporterJoueursControlleur(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Si il y des joueurs dans le tournoi
        if (!(tournoi.getAnciensJoueurs().isEmpty() && tournoi.getNouveauxJoueurs().isEmpty())) {
            //Ouverture de la fenetre "enregistrer sous"
            Frame fr = new Frame("Choississez un répertoire");
            FileDialog dial = new FileDialog(fr, "Enregistrer sous", FileDialog.SAVE);
            dial.setFile(".csv"); //Pré-écrit l'extension .csv dans la fenêtre de dialogue
            dial.setVisible(true);
            fr.setVisible(false);
            try {
                if (dial.getDirectory() != null && dial.getFile() != null) {// Si l'utilisateur a bien spécifié un fichier où écrire
                    // On crée un BufferedWriter (FileWriter avec la possibilité de créer une nouvelle ligne)
                    // qui va créer un fichier du nom qu'à choisi l'utilisateur et écrire dans celui ci
                    BufferedWriter fichier = new BufferedWriter(new FileWriter(dial.getDirectory().concat(dial.getFile())));

                    //Première ligne (entête)
                    fichier.write("Prénom,Nom,Sexe,Ancienneté,Âge,Niveau");
                    fichier.newLine();

                    //On parcourt tous les anciensJoueurs de tournoi, on les découpe et on les écrit dans le fichier
                    for (Joueur j : tournoi.getAnciensJoueurs()) {
                        fichier.write(tournoi.decouperJoueur(j));
                        fichier.newLine();
                    }
                    //On parcourt tous les NoueauxJoueurs de tournoi, on les découpe et on les écrit dans le fichier
                    for (Joueur j : tournoi.getNouveauxJoueurs()) {
                        fichier.write(tournoi.decouperJoueur(j));
                        fichier.newLine();
                    }
                    fichier.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        else
            //S'il n'y a pas de joueurs dans le tournoi
            JOptionPane.showMessageDialog(null, "Il n'y a pas de joueurs à exporter", "Erreur", JOptionPane.ERROR_MESSAGE);
    }


}