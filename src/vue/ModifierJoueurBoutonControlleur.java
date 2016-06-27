package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ModifierJoueurBoutonControlleur implements ActionListener {

	private FenetreModifierJoueur fmj;
	
	public ModifierJoueurBoutonControlleur(FenetreModifierJoueur fmj){
		this.fmj = fmj;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		fmj.modifierJoueur();
		
	}
}