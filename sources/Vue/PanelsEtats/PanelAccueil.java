package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.Programme;
import Vue.InterfaceGraphique;

import java.awt.*;

public class PanelAccueil extends Panel {

    public PanelAccueil(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        imgFond = Images.TEXTE_TITRE;

        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_ACCUEIL)
            repaint();
    }
}
