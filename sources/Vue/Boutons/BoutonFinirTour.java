package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Vue.JeuVue;

public class BoutonFinirTour extends Bouton {
    JeuVue frame;

    public BoutonFinirTour(ControleurMediateur ctrl, Jeu jeu, JeuVue frame) {
        super(ctrl, jeu);
        this.frame = frame;
    }

    @Override
    void action() {
        if (jeu.peutFinirTour()) {
/*
            this.frame.defausserJeu(jeu.getJoueurCourant());
            this.frame.piocher(jeu.getJoueurCourant());
            this.frame.updateMains();
 */

            ctrl.finirTour();
            System.out.println("Fin tour");
        }
    }
}
