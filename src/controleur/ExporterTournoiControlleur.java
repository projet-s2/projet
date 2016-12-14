package controleur;

import tournoi.Joueur;
import tournoi.Tournoi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

/** La classe ExporterTournoiControlleur permet d'exporter un tournoi
 * @author DROUARD Antoine, DERNONCOURT Cyril, LE BERT Lea, MARTINEAU Lucas
 */
public class ExporterTournoiControlleur implements ActionListener {



    private final Tournoi tournoi;

    /** Constructeur de la classe ExporterTournoiControlleur
     *
     * @param tournoi le tournoi où trouver les tours joués
     */
    public ExporterTournoiControlleur(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }






}
