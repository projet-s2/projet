package controleur;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import tournoi.Chrono;

/**
 * Created by anton on 05/11/16.
 */
public class ChronometreRestartControlleur implements ActionListener {
    private Chrono temps;
    private JButton start;

    /**
     * constructeur de la fenêtre classement
     * @param titre le titre à donner à la fenêtre
     * @param t le tournoi dans lequel on veut voir le classement
     */
    public ChronometreRestartControlleur(Chrono t, JButton b)
    {
        this.temps = t;
        this.start = b;
    }

    public void actionPerformed(ActionEvent e) {
        if(temps.getActif()) {
            temps.stop();
            temps.setTempsRestant(300);
            start.setText("Lancer");
        } else {
            temps.setTempsRestant(300);
            temps.repaint();
        }
    }
}
