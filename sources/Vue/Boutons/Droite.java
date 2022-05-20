package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Pion;

public class Droite extends Bouton {

    public Droite(ControleurMediateur ctrl, Jeu jeu) {
        super(ctrl, jeu);
    }

    @Override
    void action() {
        if (jeu.peutSelectionnerDirection(Jeu.JOUEUR_RGE)) {
            ctrl.selectionnerDirection(Jeu.JOUEUR_RGE);
            System.out.println("Position " + jeu.getPlateau().getPositionPion(Pion.SOR));
        }
    }
}
