package tournoi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @classe Chronometre
 *
 * @extends JPanel
 *
 * @description Classe qui définit un chronometre
 *
 */
public class Chrono extends JPanel {

    /**
     * serialVersionUID : numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * f : Font appliqué au texte
     */
    private static Font f = new Font("Book Antiqua", Font.BOLD, 25);

    /**
     * timer : timer servant à décrémenter le chronometre
     */
    private Timer timer;

    /**
     * couleur : couleur de fond du chronometre
     */
    private Color couleur = Color.orange;

    /**
     * tempsRestant : temps restant
     */
    private int tempsRestant;

    /**
     * temps : temps initial
     */
    private int temps;

    //* actif : si le chrono est en cours */
    private boolean actif = false;

    /**
     * Construction du chronometre
     *
     * @param N : le nombre de secondes initial
     */
    public Chrono(int N) {
        timer = createTimer();
        timer.start();
        setOpaque(false);
        setPreferredSize(new Dimension(144, 144));
        this.setTempsRestant(N);
        this.setTemps(N);
    }

    public Chrono(int N, Color couleur) {
        this.couleur = couleur;
        timer = createTimer();
        timer.start();
        setOpaque(false);
        setPreferredSize(new Dimension(144, 144));
        this.setTempsRestant(N);
        this.setTemps(N);
    }

    /**
     * Permet de démarrer le chronometre
     */
    public void start() {
        timer.start();
        actif = true;
    }

    /**
     * Permet d'arreter le chronometre
     */
    public void stop() {
        timer.stop();
        actif = false;
    }

    /**
     * Methode qui crée un Timer
     *
     * @return le timer
     */
    private Timer createTimer() {
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (tempsRestant > 0) {
                    tempsRestant--;
                    repaint();
                } else {
                    timer.stop();
                }
            }
        };
        return new Timer(1000, action);
    }

    /**
     * Redéfinition de la méthode paintCompnent
     */
    public void paintComponent(Graphics g) {
        this.drawCircle(g, 70, 70, 60);
    }

    /**
     * Fonction qui permet de dessiner le chronometre
     *
     * @param cg      : element graphique
     * @param xCenter : abscice du centre du cercle
     * @param yCenter : ordonnee du centre du cercle
     * @param r       : rayon du cercle
     */
    public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
        cg.setColor(Color.white);
        cg.fillOval(xCenter - r, yCenter - r, 2 * r, 2 * r);
        if(tempsRestant>30) {
            this.couleur = Color.orange;
            cg.setColor(couleur);
        } else {
            this.couleur = Color.red;
            cg.setColor(couleur);
        }
        cg.fillArc(xCenter - r, yCenter - r, 2 * r, 2 * r, 90, -(360 - tempsRestant * 360 / temps));
        cg.setColor(Color.black);
        cg.setFont(f);

        int minutes = this.tempsRestant / 60;
        int secondes = this.tempsRestant - (60 * minutes);
        if (secondes > 9) {
            cg.drawString(minutes + ":" + secondes, 40, 80);
        } else {
            cg.drawString(minutes + ":" + "0" + secondes, 40, 80);
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getTempsRestant() {
        return tempsRestant;
    }

    public void setTempsRestant(int tempsRestant) {
        this.tempsRestant = tempsRestant;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean b) {
        this.actif = b;
    }
}