package controleur;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import tournoi.Chrono;

/**
 * Created by anton on 05/11/16.
 */
public class ChronometreStartControlleur implements ActionListener {
    private Chrono temps;
    private JButton bouton;

    /**
     * constructeur de la fenêtre classement
     * @param titre le titre à donner à la fenêtre
     * @param t le tournoi dans lequel on veut voir le classement
     */
    public ChronometreStartControlleur(Chrono t, JButton b)
    {
        this.temps = t;
        this.bouton = b;
    }

    public void actionPerformed(ActionEvent e) {
        if(temps.getActif()) {
            temps.stop();
            bouton.setText("Lancer");
        } else {
            temps.start();
            bouton.setText("Pauser");
        }
    }
}
