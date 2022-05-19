package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;

public class BoutonPouvoirSorcier extends Bouton {

    public BoutonPouvoirSorcier(ControleurMediateur ctrl, Jeu jeu) {
        super(ctrl, jeu);
    }

    @Override
    void action() {
        if (jeu.peutUtiliserPouvoirSorcier()) {
            ctrl.activerPouvoirSor();
            System.out.println("Pouvoir Sorcier");
        }
    }

    // TODO ajouter appel pouvoir
}
