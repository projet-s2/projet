package controleur;

import exception.ImportExportException;
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
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e4) {
                JOptionPane.showMessageDialog(null, "Fichier erroné", "Erreur", JOptionPane.ERROR_MESSAGE);
                e4.printStackTrace();
            } catch (ImportExportException e4) {
                JOptionPane.showMessageDialog(null, "Fichier erroné : " + e4.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }

    /** Retourne la liste des joueurs qu'on peut trouver dans le CSV
     * @param fileDirectory le chemin pour accèder au CSV
     * @return une ArrayList avec tous les joueurs
     */
    private ArrayList<Joueur> csvReader(String fileDirectory) throws java.io.IOException, ImportExportException {
        ArrayList listeRetour = new ArrayList<Joueur>();

        BufferedReader br = new BufferedReader(new FileReader(fileDirectory));

        ArrayList<String> resultatRead = new ArrayList<>();
        br.readLine();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if (!line.isEmpty())  //On vérifie que la ligne n'est pas vide
                resultatRead.add(line);
        }

        br.close();

        String joueurCourant[];
        boolean sexe, nouveau;
        int age;
        int niveau;

        String nom, prenom;
        for (String ligne : resultatRead) {
            // Ordre d'une ligne du fichier CSV
            // [0] : prenom / [1] : nom / [2] : sexe (0 : femme / 1 : homme)
            // [3] ancienneté (0 : Ancien/ 1 : Nouveau)
            // [4] âge (0 : vide /1 : "-18 ans" /2 : "18-35 ans" / 3 : "35+ ans")
            // [5] : niveau  (0 : vide /1 : "Débutant" / 2 : "Intermédiaire" / 3 : "Confirmé")
            joueurCourant = ligne.split(",",-1); // le "-1" sert à récupérer une chaine même si elle est vide

            prenom = joueurCourant[0];
            nom = joueurCourant[1];

            //Lecture du sexe
            if (!joueurCourant[2].equals("Femme") && !joueurCourant[2].equals("Homme")) //Si la troisième valeur n'est ni Homme ni Femme
                throw new ImportExportException("Problème avec un genre");
            sexe = (joueurCourant[2].equals("Homme")); // Si la troisième valeur est Homme, sexe = true, sinon sexe = false

            //Lecture de l'ancienneté
            if (!joueurCourant[3].equals("Ancien") && !joueurCourant[3].equals("Nouveau")) //Si la quatrième valeur n'est ni Ancien ni Nouveau
                throw new ImportExportException("Problème avec l'ancienneté");
            nouveau = (joueurCourant[3].equals("Nouveau")); // Si la quatrième valeur est Nouveau, nouveau = true, sinon nouveau = false

            age = 0; //Utilse si l'âge est indéfini

            //Lecture de l'âge
            //Si la cinquième valeur n'est ni -18 ni 18-35 ni 35+ ni une chaine vide
            if (!joueurCourant[4].isEmpty()) {
                if (!joueurCourant[4].equals("-18 ans") && !joueurCourant[4].equals("18-35 ans") && !joueurCourant[4].equals("35+ ans")) {
                    throw new ImportExportException("Problème avec l'âge");
                }
                if (joueurCourant[4].equals("-18 ans"))
                     age = 1;
                else if (joueurCourant[4].equals("18-35 ans"))
                    age = 2;
                else if (joueurCourant[4].equals("35+ ans"))
                    age = 3;
            }

            niveau = 0; // Utile si le niveau est indéfini

            //Lecture du niveau
            //Si la sixième valeur n'est ni débutant ni confirmé ni intermédiaire ni une chaine vide
            if (!joueurCourant[5].isEmpty()) {
                if (!joueurCourant[5].equals("Débutant") && !joueurCourant[5].equals("Confirmé") && !joueurCourant[5].equals("Intermédiaire")) //Si la troisième valeur n'est ni Ancien ni Nouveau
                    throw new ImportExportException("Problème avec le niveau");
                if (joueurCourant[5].equals("Débutant"))
                    niveau = 1;
                else if (joueurCourant[5].equals("Confirmé"))
                    niveau = 2;
                else if (joueurCourant[5].equals("Intermédiaire"))
                    niveau = 3;
            }

            listeRetour.add(new Joueur(Joueur.nbJoueursCrees, nom, prenom, age, sexe, nouveau, niveau, true));
        }
        return listeRetour;
    }
}
