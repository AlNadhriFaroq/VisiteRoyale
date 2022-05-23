package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.InterfaceGraphique;

import java.awt.event.*;

public class AdaptateurClavier extends KeyAdapter {
    ControleurMediateur ctrl;
    InterfaceGraphique vue;
    Programme prog;

    public AdaptateurClavier(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        this.ctrl = ctrl;
        this.vue = vue;
        this.prog = prog;
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
                ctrl.changerPleinEcran();
                vue.mettreAJourPleinEcran();
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
