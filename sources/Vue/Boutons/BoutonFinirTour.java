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
            ctrl.finirTour();
            System.out.println("Fin tour");
        }
    }
}
