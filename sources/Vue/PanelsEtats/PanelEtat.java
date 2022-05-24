package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.Programme;
import Patterns.Observateur;
import Vue.InterfaceGraphique;

import javax.swing.*;
import java.awt.*;

public abstract class PanelEtat extends JPanel implements Observateur {
    ControleurMediateur ctrl;
    InterfaceGraphique vue;
    Programme prog;
    Image imgFond;
    Graphics2D dessin;

    public PanelEtat(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        this.ctrl = ctrl;
        this.vue = vue;
        this.prog = prog;
        prog.ajouterObservateur(this);
        imgFond = Images.FOND_MENU;
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dessin = (Graphics2D) g;
        dessin.drawImage(imgFond, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void mettreAJour() {
        repaint();
    }
}
