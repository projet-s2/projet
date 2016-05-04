package app;

import tournoi.*;
import vue.*;
import java.util.Scanner;
import liste.Liste;

public class App {

	private static Scanner sca;

	public static void main(String[] args) {
		Fenetre fen = new Fenetre("Match Point");
		Tournoi t = new Tournoi(26);
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
