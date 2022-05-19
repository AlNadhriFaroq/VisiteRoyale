package Vue;

import java.awt.*;

public class AnimationCarte extends AnimationPanel {

    private final JeuVue frame;

    public AnimationCarte(CarteVue carteVue, Point debut, Point dest, JeuVue frame) {
        super(carteVue, debut, dest);
        this.frame = frame;
    }

    @Override
    void FinAnimation() {
        //TODO Recupérer Etat du Jeu pour savoir si une carte est jouée.
    }
}
