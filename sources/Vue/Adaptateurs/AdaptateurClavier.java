package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Vue.InterfaceUtilisateur;

import java.awt.event.*;

public class AdaptateurClavier extends KeyAdapter {
    ControleurMediateur ctrl;
    InterfaceUtilisateur vue;

    public AdaptateurClavier(ControleurMediateur ctrl, InterfaceUtilisateur vue) {
        this.ctrl = ctrl;
        this.vue = vue;
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
                ctrl.ouvrirMenuJeu();
                break;
            case KeyEvent.VK_ESCAPE:
                vue.basculerPleinEcran();
                break;
            case KeyEvent.VK_N:
                ctrl.nouvellePartie(true, true);
            case KeyEvent.VK_Q:
            case KeyEvent.VK_A:
                ctrl.quitter();
                break;
        }
    }
}
