package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.Fenetre;

import java.awt.event.*;

public class AdaptateurComposant extends ComponentAdapter {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    public AdaptateurComposant(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (e.getSource().equals(fenetre)) {
            fenetre.redimensionner();
            fenetre.mettreAJour();
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {
        if (e.getSource().equals(fenetre))
            fenetre.mettreAJour();
    }
}
