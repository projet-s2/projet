package aplication;

import tournoi.*;
import java.util.Scanner;

import liste.Liste;
public class App {

	private static Scanner sca;

	public static void main(String[] args) {
		sca = new Scanner(System.in);
		int nbTerrains;
		boolean reBoot= false;
		do{
			if (reBoot==true){
				System.out.println("Abruti, mets un truc au dessus de 0");
			}
			System.out.println("Entrez un nombre de terrains");
			String nbTerrainsS = sca.nextLine();
			nbTerrains=0;
			try{
				nbTerrains =Integer.parseInt(nbTerrainsS);
				reBoot= true;
			}
			catch(NumberFormatException e){
			System.out.println("Il faut saisir un nombre !");
			reBoot= false;
			}
		
		}while(nbTerrains<=0);
	
		Tournoi t = new Tournoi(nbTerrains);
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
		
		int nbTours;
		reBoot= false;
		System.out.println("Entrez un nombre de tours");
		do{
			if (reBoot==true){
				System.out.println("Abruti, mets un truc au dessus de 0");
			}
			String nbToursS = sca.nextLine();
			nbTours=-1;
			try{
				nbTours =Integer.parseInt(nbToursS);
				reBoot= true;
			}
			catch(NumberFormatException e){
			System.out.println("Il faut saisir un nombre !");
			reBoot= false;
			}
			
		}while(nbTours<=0);
		
		
		for (int i = 0; i<nbTours; i++)
		t.demarrerTour();
		//On doit pouvoir attendre avant de finir au cas où l'on souhaite modifier des paires
		t.finirTour();
		
		
		System.out.println("Classement anciens");
		Liste classem = t.getClassementAnciens();
		for(int i=0;i<classem.size();i++){
			System.out.println((i+1)+" "+((Joueur)classem.get(i)).toString()+" Score : " +((Joueur)classem.get(i)).getScore());
		}
		System.out.println("\nClassement nouveaux");
		classem = t.getClassementNouveaux();
		for(int i=0;i<classem.size();i++){
			System.out.println((i+1)+" "+((Joueur)classem.get(i)).toString()+" Score : " +((Joueur)classem.get(i)).getScore());
		}
		//

	}

}
