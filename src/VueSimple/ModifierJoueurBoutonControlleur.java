package VueSimple;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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