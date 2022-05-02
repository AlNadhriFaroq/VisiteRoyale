package Vue;

import Controleur.ControleurMediateur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
    PartieGraphique prtGrph;
    ControleurMediateur ctrl;

    AdaptateurSouris(PartieGraphique prtGrph, ControleurMediateur ctrl) {
        this.prtGrph = prtGrph;
        this.ctrl = ctrl;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ctrl.clicSouris(e.getX(), e.getY());
    }
}
