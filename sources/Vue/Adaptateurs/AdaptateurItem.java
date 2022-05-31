package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Global.*;
import IA.IA;
import Modele.Programme;
import Vue.Fenetre;

import java.awt.event.*;

public class AdaptateurItem implements ItemListener {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    public AdaptateurItem(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(fenetre.getPanelMenuOptions().getMusique())) {
            Audios.MUSIQUE_MENU.arreter();
            Audios.setMusiqueMenu((String) e.getItem());
            Audios.MUSIQUE_MENU.boucler();
        } else if (e.getSource().equals(fenetre.getPanelMenuOptions().getNiveau())) {
            Configuration.instance().ecrire("NiveauDifficulteIA", Integer.toString(IA.texteEnIA((String) e.getItem())));
        } else if (e.getSource().equals(fenetre.getPanelMenuOptions().getTexture())) {
            Images.setTexture((String) e.getItem());
        }
    }
}
