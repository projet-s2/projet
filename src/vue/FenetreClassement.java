package vue;

import controleur.*;
import tournoi.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FenetreClassement extends JFrame
{
    private Tournoi tournoi;
    private JScrollPane panJoueurs;
    private DefaultTableModel listeJoueursModele;
    private JTable listeJoueurs;
    private JComboBox categorie;


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

        listeJoueursModele = new DefaultTableModel(title, 0)
        {
            //bien redefinir les types des colones pour que l'autosort marche
            public Class getColumnClass(int column)
            {
                switch (column)
                {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };

        listeJoueurs = new JTable(listeJoueursModele);
        listeJoueurs.setAutoCreateRowSorter(true);
        panJoueurs = new JScrollPane(listeJoueurs);

        joueurs.add(panJoueurs);



        JPanel a = new JPanel();
        categorie = new JComboBox(new String[]{"Tous", "Nouveaux", "Anciens"});
        categorie.setSelectedIndex(0);
        categorie.addActionListener(new AfficherClassementControleur(this, categorie));
        a.add(categorie);
        joueurs.add(a);

        afficherJoueur("Tous");


        this.setContentPane(joueurs);
        this.pack();
        this.setVisible(true);
        this.setTitle(titre);
        int tailleX = 600, tailleY = 200;
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-tailleX)/2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-tailleY)/2);
        this.setSize(tailleX,tailleY);
        this.setResizable(false);
    }

    public void afficherJoueur(String s)
    {
        listeJoueursModele.setRowCount(0);


        if(s.equals("Tous"))
        {
            ArrayList<Joueur> listeAllJoueurs = tournoi.getNouveauxJoueurs();
            Collections.sort(listeAllJoueurs, new ComparateurJoueurScore());
            for (Joueur j : listeAllJoueurs)
            {
                ajouterJoueurTable(j);
            }
        }

        if(s.equals("Nouveaux"))
        {
            ArrayList<Joueur> listeJoueurNouveau = tournoi.getNouveauxJoueurs();
            Collections.sort(listeJoueurNouveau, new ComparateurJoueurScore());
            for (Joueur j : listeJoueurNouveau)
            {
                ajouterJoueurTable(j);
            }
        }

        if(s.equals("Anciens"))
        {
            ArrayList<Joueur> listeJoueurAncien = tournoi.getAnciensJoueurs();
            Collections.sort(listeJoueurAncien, new ComparateurJoueurScore());
            for (Joueur j : listeJoueurAncien)
            {
                ajouterJoueurTable(j);
            }
        }
    }

    public void ajouterJoueurTable(Joueur j)
    {
        String ancien = "Ancien";
        if(j.getNouveau())
        {
            ancien = "Nouveau";
        }
        Object[] tJ = {j.getNom(), j.getPrenom(), j.getScore(), ancien};
        this.listeJoueursModele.addRow(tJ);
    }




}
