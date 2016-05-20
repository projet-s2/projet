package app;

import tournoi.*;
import vue.*;

import java.awt.FileDialog;
import java.awt.Frame;
import java.util.Locale;
import java.util.Scanner;
import liste.Liste;
public class App {
	private static Scanner sca;

	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		FenetrePrincipale fen = new FenetrePrincipale("Match Point");
		
		NouveauTournoi tourn = new NouveauTournoi(fen);
	}

}
