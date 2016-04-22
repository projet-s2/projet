package aplication;

import java.util.Scanner;

import tournoi.*;

public class AplicationTest {

	public static void main(String[] args) {
		
		Scanner sc =new Scanner(System.in);
		int nbr=0;
		do{
			System.out.println("saisir un nombre de terrain");
			String nbrS=sc.nextLine();
			nbr=0;
			try{
				nbr =Integer.parseInt(nbrS);
			}
			catch(NumberFormatException e){
			System.out.println("Il faut saisir un nombre !");
			}
		}while(nbr<=0);
			
		Tournoi t=new Tournoi(nbr);

		Joueur j1=new Joueur(1, true);
		t.ajouterjoueur(j1);
		Joueur j2=new Joueur(2, false);
		t.ajouterjoueur(j2);
		Joueur j3=new Joueur(3, true);
		t.ajouterjoueur(j3);
		Joueur j4=new Joueur(4, false);
		t.ajouterjoueur(j4);
		
		t.initialiserTerrains();
		
		t.demarrerTour();
		//debug
		System.out.println("test");
		t.finirTour();
		System.out.println(t.toString());
	}
}
