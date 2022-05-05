package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;

public interface InterfaceUtilisateur extends Observateur {
    public void basculerPleinEcran();
}
