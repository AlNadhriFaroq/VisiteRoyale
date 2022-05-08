package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;

abstract class InterfaceUtilisateur implements Observateur, Runnable {
    protected Jeu jeu;
    protected ControleurMediateur ctrl;

    protected InterfaceUtilisateur(Jeu jeu, ControleurMediateur ctrl) {
        this.jeu = jeu;
        this.ctrl = ctrl;
        jeu.ajouterObservateur(this);
    }

    public void basculerPleinEcran() {
        return;
    }
}
