package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class PanelMenuSauvegardes extends JPanel implements Observateur {
    ControleurMediateur ctrl;
    Programme prog;

    public PanelMenuSauvegardes(ControleurMediateur ctrl, Programme prog) {
        this.ctrl = ctrl;
        this.prog = prog;

        setBackground(new Color(252, 56, 56, 255));
        JLabel texte = new JLabel("Sauvegardes");
        add(texte, BorderLayout.NORTH);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_MENU_SAUVEGARDES)
            repaint();
    }
}
