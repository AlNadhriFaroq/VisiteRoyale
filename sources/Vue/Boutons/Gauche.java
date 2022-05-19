package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;

public class Gauche extends Bouton {

    public Gauche(ControleurMediateur ctrl, Jeu jeu) {
        super(ctrl, jeu);
    }

    @Override
    void action() {
        if (jeu.peutSelectionnerDirection(Jeu.JOUEUR_VRT)) {
            ctrl.selectionnerDirection(Jeu.JOUEUR_VRT);
            System.out.println("Vers vert");
        }
    }
}
