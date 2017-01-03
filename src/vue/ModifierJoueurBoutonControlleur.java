package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifierJoueurBoutonControlleur implements ActionListener {

	private FenetreModifierJoueur fmj;
	private int id;
	
	public ModifierJoueurBoutonControlleur(FenetreModifierJoueur fmj, int id)
	{
		this.fmj = fmj;
		this.id = id;
	}
		
	@Override
	public void actionPerformed(ActionEvent e)
	{
		fmj.modifierJoueur(this.id);
		
	}
}