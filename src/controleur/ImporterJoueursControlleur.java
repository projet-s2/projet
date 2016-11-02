package controleur;

import tournoi.Joueur;
import tournoi.Tournoi;
import vue.FenetrePrincipale;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.*;

/** La classe ImporterJoueursControlleur permet d'importer tous les joueurs contenus dans un fichier CSV
 * respectant cet ordre à chaque ligne :
 * [0] : id / [1] : nom / [2] : prenom / [3] : age / [4] : sexe (0 : homme / 1 : femme)
 * [5] : nouveau (0 : ancien / 1 : nouveau) / [6] : niveau  (0 : débutant / 1 : Intérmédiaire / 2 : confirmé)
 * [7] : peutJouer
 * @author DERNONCOURT Cyril , DROUARD Antoine, LE BERT Lea, MARTINEAU Lucas
 */
public class ImporterJoueursControlleur implements ActionListener {

    private Tournoi tournoi;
    private FenetrePrincipale vue;

    /** Constructeur de la classe ImporterJoueursControlleur
     *
     * @param tournoi le tournoi où importer les joueurs
     * @param vue la vue qu'il faut rafraichir lors de l'ajout des joueurs
     */
    public ImporterJoueursControlleur(Tournoi tournoi , FenetrePrincipale vue) {
        this.tournoi = tournoi;
        this.vue = vue;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Frame fr = new Frame("Choississez un répertoire");
        FileDialog dial = new FileDialog(fr, "Importer un fichier", FileDialog.LOAD);
        dial.setFile("*.csv");
        dial.setVisible(true);
        fr.setVisible(false);
        if (dial.getFile() != null) {
            String cvsFile = dial.getDirectory().concat(dial.getFile());
            try {
                ArrayList<Joueur> listeJoueur = csvReader(cvsFile);
                for (Joueur j: listeJoueur) {
                    if (!tournoi.getAnciensJoueurs().contains(j) && !tournoi.getNouveauxJoueurs().contains(j)) {
                        tournoi.ajouterJoueur(j);
                        vue.ajouterJoueurTable();
                    }
                }
            } catch (java.io.FileNotFoundException e2) {
                JOptionPane.showMessageDialog(null, "Le fichier demandé n'a pas été trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (java.io.IOException e3) {
                e3.printStackTrace();
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e4) {
                JOptionPane.showMessageDialog(null, "Fichier erroné", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e6) {
                e6.printStackTrace();
            }

        }

    }

    /** Retourne la liste des joueurs qu'on peut trouver dans le CSV
     * @param fileDirectory le chemin pour accèder au CSV
     * @return une ArrayList avec tous les joueurs
     */
    private ArrayList<Joueur> csvReader(String fileDirectory) throws java.io.IOException{
        ArrayList listeRetour = new ArrayList<Joueur>();

        BufferedReader br = new BufferedReader(new FileReader(fileDirectory));

        ArrayList<String> resultatRead = new ArrayList<>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if (!line.isEmpty())  //On vérifie que la ligne n'est pas vide
                resultatRead.add(line);
        }

        br.close();

        String joueurCourant[];
        boolean sexe, nouveau, peutJouer;
        int id, age, niveau;
        String nom, prenom;
        for (String ligne : resultatRead) {
            /* [0] : id / [1] : nom / [2] : prenom / [3] : age / [4] : sexe (0 : homme / 1 : femme)
               [5] : nouveau (0 : ancien / 1 : nouveau) / [6] : niveau  (0 : débutant / 1 : Intérmédiaire / 2 : confirmé)
               [7] : peutJouer
            */
            joueurCourant = ligne.split(",");

            id = Integer.parseInt(joueurCourant[0]);
            nom = joueurCourant[1];
            prenom = joueurCourant[2];
            age = Integer.parseInt(joueurCourant[3]);

            //Si joueurCourant[4] == 0, alors sexe = false (homme); sinon femmme
            sexe = Integer.parseInt(joueurCourant[4]) != 0;
            //Si joueurCourant[5] == 0, alors nouveau = false (ancien); sinon nouveau
            nouveau = Integer.parseInt(joueurCourant[5]) != 0;
            niveau = Integer.parseInt(joueurCourant[6]);
            peutJouer = Integer.parseInt(joueurCourant[7]) != 0;
            listeRetour.add(new Joueur(id, nom, prenom, age, sexe, nouveau, niveau, peutJouer));
        }
        return listeRetour;
    }
}
