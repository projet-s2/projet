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
	private JMenuItem enregistrerI;
	private JMenuItem enregistrerSousI;
	private JMenuItem exporterI;
	
	public Menu(final Tournoi t){
		super();
		//On cr�er le menu fichier
	    JMenu menuFichier = new JMenu("Fichier");

	    //On creer le bouton nouveau tournoi
	    JMenuItem nouveauTournoi = new JMenuItem("Nouveau...");
	    nouveauTournoi.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    menuFichier.add(nouveauTournoi);
	    this.add(menuFichier);

	    //Ouvrir un fichier
	    JMenuItem ouvrir = new JMenuItem("Ouvrir...");
	    ouvrir.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    menuFichier.add(ouvrir);
	    menuFichier.addSeparator();

	    //On creer le bouton enregister
	    JMenuItem enregistrer = new JMenuItem("Enregistrer");
	    enregistrer.setEnabled(false);
	    enregistrer.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    menuFichier.add(enregistrer);
	    this.enregistrerI = enregistrer;
	    
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
	    			t.save(dial.getDirectory(),dial.getFile());
	    		}catch(NullPointerException e1){
	    			
	    		}
	    	}
	    });
	    menuFichier.add(enregistrerSous);
	    this.enregistrerSousI = enregistrerSous;
	    
	    
	  //On creer le bouton exporter
	    JMenuItem exporter = new JMenuItem("Exporter le classement...");
	    exporter.setAccelerator(KeyStroke.getKeyStroke('G', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	    menuFichier.add(exporter);
	    this.exporterI = exporter;
	    
	}
	public void enableSave(){
		this.enregistrerI.setEnabled(true);
		this.enregistrerSousI.setEnabled(true);
		this.exporterI.setEnabled(true);
	}

}
