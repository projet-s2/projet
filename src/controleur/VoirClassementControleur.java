package controleur;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.FenetreClassement;
import tournoi.*;

/** La classe VoirClassementControleur permet d'ouvrir une fenêtre contenant le classement
 * @author DERNONCOURT Cyril , DROUARD Antoine, LE BERT Lea, MARTINEAU Lucas
 */

public class VoirClassementControleur implements ActionListener
{
    private tournoi.Tournoi tournoi;

    /**
     * Constructeur de la classe VoirClassementControleur
     *
     * @param tournoi le tournoi où importer les joueurs
     */
    public VoirClassementControleur(Tournoi tournoi)
    {
        this.tournoi = tournoi;
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        new FenetreClassement("Classement", tournoi);
    }

}