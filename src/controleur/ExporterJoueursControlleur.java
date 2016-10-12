package controleur;

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
        System.out.println(dial.getDirectory().concat(dial.getFile()));
        try {
            /* On crée un BufferedWriter (FileWriter avec la possibilité de créer une nouvelle ligne)
                qui va créer un fichier du nom qu'à choisi l'utilisateur et écrire dans celui ci
            */
            BufferedWriter fichier = new BufferedWriter(new FileWriter (dial.getDirectory().concat(dial.getFile()) ));
           // for ( : ) {
                
            //}
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}