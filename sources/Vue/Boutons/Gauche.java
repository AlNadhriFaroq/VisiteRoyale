package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Pion;

public class Gauche extends Bouton {

    public Gauche(ControleurMediateur ctrl, Jeu jeu) {
        super(ctrl, jeu);
    }

    @Override
    void action() {
        if (jeu.peutSelectionnerDirection(Jeu.JOUEUR_VRT)) {
            ctrl.selectionnerDirection(Jeu.JOUEUR_VRT);
            System.out.println("Position " + jeu.getPlateau().getPositionPion(Pion.SOR));
        }
    }
}
