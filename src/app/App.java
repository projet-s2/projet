package app;

import vue.*;

public class App {

	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		FenetrePrincipale fen = new FenetrePrincipale("Match Point");
		NouveauTournoi tourn = new NouveauTournoi(fen);
	}

}
