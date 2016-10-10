package vue;

import com.sun.xml.internal.bind.v2.TODO;
import controleur.AjouterJoueurControlleur;
import controleur.AjouterMatchControlleur;
import tournoi.Joueur;
import tournoi.Tournoi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by lea on 09/10/16.
 */
public class FenetreAjoutMatch extends JFrame {

    private Tournoi tournoi;
    FenetrePrincipale vue;

    private JTextField nom;
    private JTextField prenom;


    /**
     * constructeur de la fenêtre d'ajout d'un joueur
     * @param titre le titre à donner à la fenêtre
     * @param tournoi le tournoi dans lequel on veut ajouter un joueur
     * @param vue la vue qui crée la fenêtre
     */
    public FenetreAjoutMatch(String titre, Tournoi tournoi, FenetrePrincipale vue){

        JPanel corePanel = new JPanel();
        corePanel.setLayout(new BorderLayout());

        //gridLayout avec les joueurs et leurs socres
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(2,2));

        //menus déroulants des joueurs
        ArrayList<Joueur> classA = tournoi.getAnciensJoueurs();
        ArrayList<Joueur> classN = tournoi.getNouveauxJoueurs();
        String[] joueurs= new String[classA.size()+classN.size()];// voir combiens de gens le tournois acceuille pour adapter
        //On rentre les joueurs anciens dans les X premières cases
        for(int i =0; i < classA.size(); i++){
            Joueur j = classA.get(i);
            joueurs[i] = "" + j.getPrenom() + "    " +j.getNom();

        }
        //On rentre les joueurs nouveaux dans les cases restantes
        for(int i = 0; i < classN.size(); i++){
            Joueur j = (Joueur)classN.get(i);
            joueurs[classA.size()+i] = "" + j.getPrenom() + "    " +j.getNom();

        }


        JComboBox joueur1 = new JComboBox(joueurs);
        JComboBox joueur2 = new JComboBox(joueurs);
        JComboBox joueur3 = new JComboBox(joueurs);
        JComboBox joueur4 = new JComboBox(joueurs);
        JPanel j1 = new JPanel();
        JPanel j2 = new JPanel();
        j1.add(joueur1);
        j2.add(joueur2);
        j1.add(joueur3);
        j2.add(joueur4);
        playerPanel.add(j1);
        playerPanel.add(j2);

        //entrage des scores
        JSpinner score1 = new JSpinner();
        JLabel l1= new JLabel("Score :");
        JSpinner score2 = new JSpinner();
        JLabel l2= new JLabel("Score ;");
        JPanel s1 = new JPanel();
        JPanel s2 = new JPanel();
        s1.add(l1);
        s2.add(l2);
        s1.add(score1);
        s2.add(score2);

        playerPanel.add(s1);
        playerPanel.add(s2);



        corePanel.add(playerPanel,BorderLayout.CENTER);


        ///bouton valider en bas
        JButton valider = new JButton("Valider");
        valider.addActionListener(new AjouterMatchControlleur(this));
        JPanel sud = new JPanel();
        sud.add(valider);
        // TODO: 09/10/16 action listener du bouton valider pour actualiser les scores 

        corePanel.add(sud,BorderLayout.SOUTH);


        this.setContentPane(corePanel);
        this.pack();
        this.setVisible(true);
        this.setTitle(titre);
        int tailleX = 600, tailleY = 200;
        this.setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-tailleX)/2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-tailleY)/2);
        this.setSize(tailleX,tailleY);
        this.setResizable(false);

    }
}
