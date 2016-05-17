package vue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tournoi.*;
import javax.swing.*;
public class Menu extends JMenuBar{
	public Menu(Tournoi t){
		super();
		//On créer le menu fichier
	    JMenu menuFichier = new JMenu("Fichier");
	    
	    //On creer le bouton enregister sous
	    JMenuItem enregistrerSous = new JMenuItem("Enregistrer sous...");
	    enregistrerSous.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent e){
	    		//On affiche le séléctionneur de répertoire
	    		Frame fr = new Frame("Choississez un rÃ©pertoire");
	    		FileDialog dial = new FileDialog(fr, "Nouveau Tournoi", FileDialog.SAVE);
	    		dial.setVisible(true);
	    		fr.setVisible(false);
	    		t.save(dial.getDirectory(),dial.getFile());
	    	}
	    });
	    menuFichier.add(enregistrerSous);
	    menuFichier.add(new JMenuItem("Ouvrir un tournoi..."));
	    
	    //On creer le bouton nouveau tournoi
	    menuFichier.add(new JMenuItem("Nouveau tournoi"));
	    this.add(menuFichier);
	}

}
