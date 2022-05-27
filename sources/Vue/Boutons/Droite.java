package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Pion;
import Vue.JeuVue;

public class Droite extends Bouton {
    JeuVue frame;

    public Droite(ControleurMediateur ctrl, JeuVue frame, Jeu jeu) {
        super(ctrl, jeu);
        this.frame = frame;
    }

    @Override
    void action() {
        if (jeu.peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE)) && !this.frame.cartesJoueesEstVide(this.jeu.getJoueurCourant())) {
            ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE));
        }
    }
}
