package vue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import tournoi.*;
import javax.swing.*;

import java.awt.Toolkit;
public class Menu extends JMenuBar{
	public Menu(final Tournoi t){
		super();
		//On cr�er le menu fichier
	    JMenu menuFichier = new JMenu("Fichier");

	    //On creer le bouton nouveau tournoi
	    JMenuItem nouveauTournoi = new JMenuItem("Nouveau tournoi");
	    nouveauTournoi.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    menuFichier.add(nouveauTournoi);
	    this.add(menuFichier);
	    menuFichier.addSeparator();
	    //On creer le bouton enregister sous
	    JMenuItem enregistrerSous = new JMenuItem("Enregistrer sous...");
	    enregistrerSous.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    enregistrerSous.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent e){
	    		//On affiche le s�l�ctionneur de r�pertoire
	    		Frame fr = new Frame("Choississez un répertoire");
	    		FileDialog dial = new FileDialog(fr, "Nouveau Tournoi", FileDialog.SAVE);
	    		dial.setVisible(true);
	    		fr.setVisible(false);
	    		t.save(dial.getDirectory(),dial.getFile());
	    	}
	    });
	    menuFichier.add(enregistrerSous);
	    menuFichier.add(new JMenuItem("Ouvrir un tournoi..."));
	    
	}

}
