package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Pion;
import Vue.JeuVue;

public class Gauche extends Bouton {
    JeuVue frame;

    public Gauche(ControleurMediateur ctrl,JeuVue frame, Jeu jeu) {
        super(ctrl, jeu );
        this.frame = frame;
    }

    @Override
    void action() {
        if (jeu.peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT))) {
            ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT));
            this.frame.defausserJeu(jeu.getJoueurCourant());
            this.frame.terrain.majPositions();
        }
    }
}
