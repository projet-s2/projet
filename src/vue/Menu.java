package vue;

import exception.TournoiVideException;
import tournoi.Tournoi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Menu extends JMenuBar{
	//Il n'est pas possible de proceder à certaines actions tant que le tournoi n'a pas été crée ou affiché
	private ArrayList<JMenuItem> aAutoriser;
	
	public Menu(final Tournoi t, final FenetrePrincipale fen){
		super();
		//On cr�er le menu fichier
	    JMenu menuFichier = new JMenu("Fichier");
	    JMenu menuEdition = new JMenu("Édition");
	    //On inctancie la liste des item à auatoriser
	    this.aAutoriser = new ArrayList<JMenuItem>();
	    //On creer le bouton nouveau tournoi
	    JMenuItem nouveauTournoi = new JMenuItem("Nouveau...");
	    nouveauTournoi.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    nouveauTournoi.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent e){
	    		FenetrePrincipale fen = new FenetrePrincipale("Match Point");
	    		NouveauTournoi tourn = new NouveauTournoi(fen);
	    	}
	    });
	    menuFichier.add(nouveauTournoi);
	    this.add(menuFichier);
	    this.add(menuEdition);

	    //Ouvrir un fichier
	    JMenuItem ouvrir = new JMenuItem("Ouvrir...");
	    ouvrir.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    menuFichier.add(ouvrir);
	    menuFichier.addSeparator();

	    //On creer le bouton enregister
	    JMenuItem enregistrer = new JMenuItem("Enregistrer");
	    enregistrer.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    menuFichier.add(enregistrer);
	    this.aAutoriser.add(enregistrer);
	    enregistrer.setEnabled(false);
	    
	    //On creer le bouton enregister sous
	    JMenuItem enregistrerSous = new JMenuItem("Enregistrer sous...");
	    enregistrerSous.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()+KeyEvent.SHIFT_MASK));
	    enregistrerSous.setEnabled(false);
	    enregistrerSous.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent e){
	    		//On affiche le s�l�ctionneur de r�pertoire
	    		Frame fr = new Frame("Choississez un r�pertoire");
	    		FileDialog dial = new FileDialog(fr, "Nouveau Tournoi", FileDialog.SAVE);
	    		dial.setVisible(true);
	    		fr.setVisible(false);
	    		try{
	    			//t.save(dial.getDirectory(),dial.getFile());
	    		}catch(NullPointerException e1){
	    			
	    		}
	    	}
	    });
	    menuFichier.add(enregistrerSous);
	    this.aAutoriser.add(enregistrerSous);
	    
	    
	  //On creer le bouton exporter
	    JMenuItem exporter = new JMenuItem("Exporter le classement...");
	    exporter.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    menuFichier.add(exporter);
	    exporter.setEnabled(false);
	    this.aAutoriser.add(exporter);
	    
	   //Dans le menu édition


	    
	    JMenuItem ajouterJoueur = new JMenuItem("Nouveau joueur");
	    ajouterJoueur.setAccelerator(KeyStroke.getKeyStroke('J', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    ajouterJoueur.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent e){
				fen.fenetreAjout();
	    	}
				
	    });
	    ajouterJoueur.setEnabled(false);
	    menuEdition.add(ajouterJoueur);
	    this.aAutoriser.add(ajouterJoueur);
	    
	}
	public void enableSave(){
		for(int i = 0; i<this.aAutoriser.size();i++){
			((JMenuItem)this.aAutoriser.get(i)).setEnabled(true);
		}
	}

}
