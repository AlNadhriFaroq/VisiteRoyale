package Vue.Boutons;

import Modele.Jeu;

public class BoutonPouvoirFou extends BoutonPouvoir {

    public BoutonPouvoirFou(Jeu jeu) {
        super(jeu);
    }

    @Override
    void action() {
        System.out.println("POUVOIR FOU");

    }

    // TODO ajouter appel pouvoir
}
