package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.Fenetre;

import java.awt.event.*;

public class AdaptateurClavier extends KeyAdapter {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    public AdaptateurClavier(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
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
                fenetre.mettreAJourPleinEcran();
                break;
            case KeyEvent.VK_N:
                ctrl.nouvellePartie(true, true);
                break;
            case KeyEvent.VK_Q:
            case KeyEvent.VK_A:
                ctrl.quitter();
                break;
        }
    }
}
