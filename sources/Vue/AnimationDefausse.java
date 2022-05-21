package Vue;

import java.awt.*;

public class AnimationDefausse extends AnimationPanel {

    private final JeuVue frame;

    public AnimationDefausse(CarteVue carteVue, Point debut, Point dest, JeuVue frame) {
        super(carteVue, debut, dest, new Dimension(frame.carteW, frame.carteH), 25);
        this.frame = frame;
    }

    @Override
    void FinAnimation() {
        //TODO Retirer les cartes à défausser
    }
}
