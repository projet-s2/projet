package vue;

import controleur.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FenetreClassement extends JFrame
{
    private Tournoi tournoi;
    private JScrollPane panJoueurs;
    private DefaultTableModel listeJoueursModele;
    private JTable listeJoueurs;
    String[] joueurs;


    /**
     * constructeur de la fenêtre classement
     * @param titre le titre à donner à la fenêtre
     * @param t le tournoi dans lequel on veut voir le classement
     */
    public FenetreClassement(String titre, Tournoi t)
    {
        this.tournoi = t;

        JPanel joueurs = new JPanel();
        String  title[] = {"ID","Nom", "Prénom", "Score","Ancienneté"};
        listeJoueursModele = new DefaultTableModel(title,0);
        listeJoueurs = new JTable(listeJoueursModele);
        listeJoueurs.setAutoCreateRowSorter(true);
        panJoueurs = new JScrollPane(listeJoueurs);
        joueurs.add(panJoueurs);






        this.setContentPane(joueurs);
        this.pack();
        this.setVisible(true);
        this.setTitle(titre);
        int tailleX = 600, tailleY = 200;
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-tailleX)/2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-tailleY)/2);
        this.setSize(tailleX,tailleY);
        this.setResizable(false);
    }
}
