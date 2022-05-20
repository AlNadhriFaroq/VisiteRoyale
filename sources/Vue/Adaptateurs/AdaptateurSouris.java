package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.ComponentsJeu.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
    ControleurMediateur ctrl;
    Programme prog;

    public AdaptateurSouris(ControleurMediateur ctrl, Programme prog) {
        this.ctrl = ctrl;
        this.prog = prog;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof CarteVue && prog.getJeu().peutSelectionnerCarte(((CarteVue) e.getSource()).getCarte()))
            ctrl.selectionnerCarte(((CarteVue) e.getSource()).getCarte());
        else if (e.getSource() instanceof PionVue && prog.getJeu().peutSelectionnerPion(((PionVue) e.getSource()).getPion()))
            ctrl.selectionnerPion(((PionVue) e.getSource()).getPion());
    }
}
