package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;

public class BoutonAnnuler extends Bouton {

    public BoutonAnnuler(ControleurMediateur ctrl, Jeu jeu) {
        super(ctrl, jeu);
    }

    @Override
    void action() {
        if (jeu.peutAnnuler()) {
            ctrl.annuler();
            System.out.println("Annule");
        }
    }
}
