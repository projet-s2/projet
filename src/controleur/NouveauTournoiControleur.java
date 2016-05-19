package controleur;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSpinner;
import javax.swing.JTextField;

import liste.Liste;
import tournoi.Joueur;
import tournoi.Tournoi;
import vue.FenetrePrincipale;
import vue.NouveauTournoi;

public class NouveauTournoiControleur implements ActionListener {
	private JSpinner nbTerrains;
	private JTextField nom;
	private FenetrePrincipale fenetre;
	private NouveauTournoi nouveauTournoiFen;
	public NouveauTournoiControleur(FenetrePrincipale f, JTextField leNom, JSpinner leNbTerrains, NouveauTournoi nt){
		this.nbTerrains = leNbTerrains;
		this.nom = leNom;
		this.nouveauTournoiFen = nt;
		this.fenetre = f;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		//On créer un nouveau tournoi
		Tournoi t = new Tournoi((int) this.nbTerrains.getValue(), this.nom.getText());
		//On assigne un tournoi à la fenetre principale
		fenetre.setTournoi(t);
		//Fermeture de la fenetre
		this.nouveauTournoiFen.dispose();
		
		//pour la simulation console - A retirer
		boolean nouv = true;
		boolean sexe = true;
		for(int id=0;id<100;id++){
			if(Math.random()>0.5){
				nouv =false;
			}else{
				nouv =true;
			}
			if(Math.random()>0.5){
				sexe =false;
			}else{
				sexe =true;
			}
			t.ajouterjoueur(new Joueur(id, sexe, nouv));

		}

		t.initialiserTerrains();
		for(int i=0;i<5;i++){
			t.demarrerTour();
			t.finirTour();
		}
		System.out.println("Classement anciens");
		Liste classem = t.getClassementAnciens();
		for(int j=0;j<classem.size();j++){
			System.out.println((j+1)+" "+((Joueur)classem.get(j)).toString()+" Score : " +((Joueur)classem.get(j)).getScore());
		}
		System.out.println("\nClassement nouveaux");
		classem = t.getClassementNouveaux();
		for(int j=0;j<classem.size();j++){
			System.out.println((j+1)+" "+((Joueur)classem.get(j)).toString()+" Score : " +((Joueur)classem.get(j)).getScore());
		}
		
		

	}
}
