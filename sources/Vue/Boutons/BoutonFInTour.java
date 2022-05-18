package Vue.Boutons;

import Modele.Jeu;

public class BoutonFInTour extends BoutonPouvoir{
    public BoutonFInTour(Jeu jeu){
        super(jeu);
    }

    @Override
    void action() {
        System.out.println("FIN DE TOUR !!!");
    }

}
