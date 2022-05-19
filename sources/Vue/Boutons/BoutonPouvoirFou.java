package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;

public class BoutonPouvoirFou extends Bouton {

    public BoutonPouvoirFou(ControleurMediateur ctrl, Jeu jeu) {
        super(ctrl, jeu);
    }

    @Override
    void action() {
        if (jeu.peutUtiliserPouvoirFou()) {
            ctrl.activerPouvoirFou();
            System.out.println("Pouvoir fou");
        }
    }

    // TODO ajouter appel pouvoir
}
