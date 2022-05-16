package Vue;

import java.awt.*;

public class AnimationDefausse extends AnimationPanel{

    private final PlateauFrame frame;

    public AnimationDefausse(CarteVue carteVue, Point debut, Point dest, PlateauFrame frame) {
        super(carteVue, debut, dest);
        this.frame = frame;
    }

    @Override
    void FinAnimation(){
        //TODO Retirer les cartes à défausser
    }
}
