package Vue.Boutons;

import Modele.Jeu;

public class BoutonFinirTour extends BoutonPouvoir{
    public BoutonFinirTour(Jeu jeu){
        super(jeu);
    }

    @Override
    void action() {
        System.out.println("FIN DE TOUR !!!");
    }

}
