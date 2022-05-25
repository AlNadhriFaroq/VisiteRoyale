package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Global.Audios;
import Global.Configuration;
import Global.Images;
import IA.IA;
import Modele.Programme;
import Vue.InterfaceGraphique;

import java.awt.event.*;

public class AdaptateurItem implements ItemListener {
    ControleurMediateur ctrl;
    InterfaceGraphique vue;
    Programme prog;

    public AdaptateurItem(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        this.ctrl = ctrl;
        this.vue = vue;
        this.prog = prog;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(vue.getPanelMenuOptions().getMusique())) {
            Audios.MUSIQUE_MENU.arreter();
            Audios.setMusiqueMenu((String) e.getItem());
            Audios.MUSIQUE_MENU.boucler();
            //vue.getPanelMenuOptions().repaint();
        } else if (e.getSource().equals(vue.getPanelMenuOptions().getNiveau())) {
            Configuration.instance().ecrire("NiveauDifficulteIA", Integer.toString(IA.texteEnIA((String) e.getItem())));
           // vue.getPanelMenuOptions().repaint();
        } else if (e.getSource().equals(vue.getPanelMenuOptions().getTexture())) {
            Images.setTexture((String) e.getItem());
            //vue.getPanelMenuOptions().repaint();
        }
    }
}
