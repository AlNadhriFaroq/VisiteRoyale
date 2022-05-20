package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class PanelMenuOptions extends JPanel implements Observateur {
    ControleurMediateur ctrl;
    Programme prog;

    public PanelMenuOptions(ControleurMediateur ctrl, Programme prog) {
        this.ctrl = ctrl;
        this.prog = prog;

        setBackground(new Color(92, 252, 56, 255));
        JLabel texte = new JLabel("Options");
        add(texte, BorderLayout.NORTH);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_MENU_OPTIONS)
            repaint();
    }
}
