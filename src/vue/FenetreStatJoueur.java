package vue;

import controleur.AjouterMatchControlleur;
import controleur.StatutJoueurControlleur;
import tournoi.Joueur;
import tournoi.Tournoi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by E154981H on 03/11/16.
 */
public class FenetreStatJoueur extends JFrame {

    private FenetrePrincipale vue;
    private Tournoi tournoi;
    private JComboBox joueur;


    public FenetreStatJoueur(String titre,FenetrePrincipale vue)
    {
        super(titre);
        this.tournoi = vue.getTournoi();
        this.vue = vue;
        JPanel corePanel = new JPanel();
        corePanel.setLayout(new BoxLayout(corePanel, BoxLayout.Y_AXIS));

        //menus déroulants des joueurs
        ArrayList<Joueur> classA = tournoi.getAnciensJoueurs();
        ArrayList<Joueur> classN = tournoi.getNouveauxJoueurs();
        String[] joueurs = new String[classA.size() + classN.size()];// voir combiens de gens le tournois acceuille pour adapter
        //On rentre les joueurs anciens dans les X premières cases
        for (int i = 0; i < classA.size(); i++) {
            Joueur j = classA.get(i);
            joueurs[i] = "" + j.getNom() + " " + j.getPrenom();

        }
        //On rentre les joueurs nouveaux dans les cases restantes
        for (int i = 0; i < classN.size(); i++) {
            Joueur j = (Joueur) classN.get(i);
            joueurs[classA.size() + i] = "" + j.getNom() + " " + j.getPrenom();

        }

        joueur = new JComboBox(joueurs);
        corePanel.add(joueur);
        ///bouton valider en bas
        JButton valider = new JButton("Valider");
        corePanel.add(valider);

        valider.addActionListener(new StatutJoueurControlleur(this));

        this.setContentPane(corePanel);
        this.pack();
        this.setVisible(true);
        this.setTitle(titre);
        int tailleX = 600, tailleY = 200;
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - tailleX) / 2, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - tailleY) / 2);
        this.setSize(tailleX, tailleY);
        this.setResizable(false);


    }

    public FenetrePrincipale getVue() {
        return vue;
    }

    public Tournoi getTournoi() {
        return tournoi;

    }

    public JComboBox getJoueur() {
        return joueur;
    }
}
