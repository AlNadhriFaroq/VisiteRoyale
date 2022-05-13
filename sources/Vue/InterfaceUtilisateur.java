package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Programme;
import Patterns.Observateur;

abstract class InterfaceUtilisateur implements Observateur, Runnable {
    protected Programme prog;
    protected ControleurMediateur ctrl;

    protected InterfaceUtilisateur(Programme prog, ControleurMediateur ctrl) {
        this.prog = prog;
        this.ctrl = ctrl;
        prog.ajouterObservateur(this);
    }

    protected InterfaceUtilisateur() {
    }

    public void basculerPleinEcran() {
    }
}
