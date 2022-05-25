package Vue;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Patterns.Observateur;

public abstract class InterfaceUtilisateur implements Observateur, Runnable {
    protected ControleurMediateur ctrl;
    protected Programme prog;

    protected InterfaceUtilisateur(ControleurMediateur ctrl, Programme prog) {
        this.ctrl = ctrl;
        this.prog = prog;
        prog.ajouterObservateur(this);
    }

    public void basculerPleinEcran() {
    }
}
