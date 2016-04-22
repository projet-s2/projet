package app;
import tournoi.*;
import java.util.Scanner;

import liste.Liste;
public class App {

	private static Scanner sca;

	public static void main(String[] args) {
		sca = new Scanner(System.in);
		int nbTerrains;
		
		do{
			System.out.println("Entrez un nombre de terrains");
			String nbTerrainsS = sca.nextLine();
			nbTerrains=0;
			try{
				nbrTerrains =Integer.parseInt(nbrTerrainsS);
			}
			catch(NumberFormatException e){
			System.out.println("Il faut saisir un nombre !");
			}
		
		}while(nbrTerrains<=0);
	
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
		t.demarrerTour();
		t.finirTour();
		t.demarrerTour();
		t.finirTour();
		t.demarrerTour();
		t.finirTour();
		t.demarrerTour();
		t.finirTour();
		t.demarrerTour();
		t.finirTour();
		t.demarrerTour();
		t.finirTour();
		t.demarrerTour();
		t.finirTour();
		t.demarrerTour();
		t.finirTour();
		System.out.println("Classement anciens");
		Liste classem = t.getClassementAnciens();
		for(int i=0;i<classem.size();i++){
			System.out.println((i+1)+" "+((Joueur)classem.get(i)).toString()+" Score : " +((Joueur)classem.get(i)).getScore());
		}
		System.out.println("\nClassement nouveaux");
		classem = t.getClassementAnciens();
		for(int i=0;i<classem.size();i++){
			System.out.println((i+1)+" "+((Joueur)classem.get(i)).toString()+" Score : " +((Joueur)classem.get(i)).getScore());
		}
		//

	}

}
