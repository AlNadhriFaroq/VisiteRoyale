package Vue;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.Adaptateurs.*;

import javax.swing.*;

public class InterfaceGraphique extends InterfaceUtilisateur {
    private Fenetre fenetre;

    public InterfaceGraphique(ControleurMediateur ctrl, Programme prog) {
        super(ctrl, prog);
    }

    public void run() {
        /* Construction de la fenêtre */
        fenetre = new Fenetre(ctrl, prog);
        fenetre.demarrer();

        /* Retransmission des événements temporels au contrôleur */
        Timer timer = new Timer(16, new AdaptateurTemps(ctrl));
        timer.start();
    }

    @Override
    public void mettreAJour() {
        fenetre.mettreAJour();
    }
}
