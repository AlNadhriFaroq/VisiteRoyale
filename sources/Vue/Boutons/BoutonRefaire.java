package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;

public class BoutonRefaire extends Bouton {

    public BoutonRefaire(ControleurMediateur ctrl, Jeu jeu) {
        super(ctrl, jeu);
    }

    @Override
    void action() {
        if (jeu.peutRefaire()) {
            ctrl.refaire();
            System.out.println("Refait");
        }
    }


}
