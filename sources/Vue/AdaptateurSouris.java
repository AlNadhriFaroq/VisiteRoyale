package Vue;

import Controleur.ControleurMediateur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
    JeuVue jeuVue;
    ControleurMediateur ctrl;

    AdaptateurSouris(JeuVue jeuVue, ControleurMediateur ctrl) {
        this.jeuVue = jeuVue;
        this.ctrl = ctrl;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ctrl.clicSouris(e.getX(), e.getY());
    }
}
