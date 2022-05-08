package Vue;

import Controleur.ControleurMediateur;

import java.awt.event.*;

public class AdaptateurClavier extends KeyAdapter {
    InterfaceUtilisateur vue;
    ControleurMediateur ctrl;

    AdaptateurClavier(InterfaceUtilisateur vue, ControleurMediateur ctrl) {
        this.vue = vue;
        this.ctrl = ctrl;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_U:
                ctrl.annuler();
                break;
            case KeyEvent.VK_R:
                ctrl.refaire();
                break;
            case KeyEvent.VK_P:
                //ctrl.pause();
                break;
            case KeyEvent.VK_I:
                ctrl.basculerIA(0, true);
                break;
            case KeyEvent.VK_ESCAPE:
                vue.basculerPleinEcran();
                break;
            case KeyEvent.VK_N:
                ctrl.nouvellePartie();
            case KeyEvent.VK_Q:
            case KeyEvent.VK_A:
                ctrl.quitter();
                break;
        }
    }
}
