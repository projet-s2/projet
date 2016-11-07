package vue;

import controleur.*;
import tournoi.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FenetreClassement extends JFrame
{
    private Tournoi tournoi;
    private JScrollPane panJoueurs;
    private DefaultTableModel listeJoueursModele;
    private JTable listeJoueurs;
    private JComboBox categorie;
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
        joueurs.setLayout(new GridLayout(1, 2));
        String  title[] = {"Nom", "Prénom", "Score","Ancienneté"};
        listeJoueursModele = new DefaultTableModel(title,0);
        listeJoueurs = new JTable(listeJoueursModele);
        listeJoueurs.setAutoCreateRowSorter(true);
        panJoueurs = new JScrollPane(listeJoueurs);
        joueurs.add(panJoueurs);

        JPanel a = new JPanel();
        categorie = new JComboBox(new String[]{"Tous", "Nouveaux", "Anciens"});
        categorie.setSelectedIndex(0);
        //categorie.addActionListener(new AfficherClassementControleur(this, categorie));
        a.add(categorie);
        joueurs.add(a);

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
