package app;
import tournoi.*;
import java.util.Scanner;
public class App {

	private static Scanner sca;

	public static void main(String[] args) {
		System.out.println("Entrez un nombre de terrains");
		sca = new Scanner(System.in);
		int nbTerrains = sca.nextInt();
		Tournoi t = new Tournoi(nbTerrains);
		boolean nouv = true;
		for(int id=0;id<100;id++){
			if(Math.random()>0.5){
				nouv =false;
			}else{
				nouv =true;
			}
			t.ajouterjoueur(new Joueur(id, nouv));
		
		}
		t.initialiserTerrains();
		t.demarrerTour();
		t.finirTour();
	}

}
