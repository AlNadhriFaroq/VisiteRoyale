package Vue;

import Controleur.ControleurMediateur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
    JeuVue prtGrph;
    ControleurMediateur ctrl;

    AdaptateurSouris(JeuVue prtGrph, ControleurMediateur ctrl) {
        this.prtGrph = prtGrph;
        this.ctrl = ctrl;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ctrl.clicSouris(e.getX(), e.getY());
    }
}
