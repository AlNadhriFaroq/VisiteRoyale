package Vue.Boutons;

import Modele.Jeu;

import java.awt.event.ActionEvent;

public class BoutonPouvoirSorcier extends BoutonPouvoir {

    public BoutonPouvoirSorcier(Jeu jeu) {
        super(jeu);
    }

    @Override
    void action() {
        System.out.println("POUVOIR SORCIER");

    }

    // TODO ajouter appel pouvoir
}
