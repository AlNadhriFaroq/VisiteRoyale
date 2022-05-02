package Vue;

import Controleur.ControleurMediateur;

import java.awt.event.*;

public class AdaptateurClavier extends KeyAdapter {
    ControleurMediateur ctrl;

    AdaptateurClavier(ControleurMediateur ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_U:
                ctrl.toucheClavier("Annuler");
                break;
            case KeyEvent.VK_R:
                ctrl.toucheClavier("Refaire");
                break;
            case KeyEvent.VK_P:
                ctrl.toucheClavier("Pause");
                break;
            case KeyEvent.VK_I:
                ctrl.toucheClavier("IA");
                break;
            case KeyEvent.VK_ESCAPE:
                ctrl.toucheClavier("PleinEcran");
                break;
            case KeyEvent.VK_N:
                ctrl.toucheClavier("NouvellePartie");
            case KeyEvent.VK_Q:
            case KeyEvent.VK_A:
                ctrl.toucheClavier("Quitter");
                break;
        }
    }
}
