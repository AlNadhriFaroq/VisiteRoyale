package Vue;

import java.awt.*;

public class AnimationCarte extends AnimationPanel{

    private final PlateauFrame frame;

    public AnimationCarte(CarteVue carteVue, Point debut, Point dest, PlateauFrame frame) {
        super(carteVue, debut, dest);
        this.frame = frame;
    }

    @Override
    void FinAnimation(){
        //TODO Recupérer Etat du Jeu pour savoir si une carte est jouée.
    }
}
