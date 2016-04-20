package aplication;

import java.util.Scanner;

import tournoi.*;

public class Aplication {

	public static void main(String[] args) {
		
		Scanner sc =new Scanner(System.in);
		String nbrS=sc.nextLine();
		int nbr=0;
		try{
			nbr =Integer.parseInt(nbrS);

			//debug
			System.out.println(nbrS+"   "+nbr);
			}
			catch(NumberFormatException e){
			System.out.println(nbrS+" ne peut pas être convertir en entier");
			}
			
		Tournoi t=new Tournoi(nbr);
		t.initialiserTerrains();
		t.demarrerTour();
		//debug
		System.out.println("test");
		t.finirTour();
	}
}
