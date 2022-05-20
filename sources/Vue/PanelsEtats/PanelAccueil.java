package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class PanelAccueil extends JPanel implements Observateur {
    ControleurMediateur ctrl;
    Programme prog;

    public PanelAccueil(ControleurMediateur ctrl, Programme prog) {
        this.ctrl = ctrl;
        this.prog = prog;

        setBackground(new Color(56, 91, 252, 255));

        JLabel texte = new JLabel("Ouverture en cours, veuillez patienter...");

        add(texte, BorderLayout.SOUTH);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_ACCUEIL)
            repaint();
    }
}
