package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Global.Audios;
import Modele.Programme;
import Vue.InterfaceGraphique;

import javax.swing.*;
import javax.swing.event.*;

public class AdaptateurChange implements ChangeListener {
    ControleurMediateur ctrl;
    InterfaceGraphique vue;
    Programme prog;

    public AdaptateurChange(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        this.ctrl = ctrl;
        this.vue = vue;
        this.prog = prog;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(vue.getPanelMenuOptions().getVolumeMusique())) {
            ctrl.changerVolume(((JSlider) e.getSource()).getValue(), Audios.MUSIQUE);
        } else if (e.getSource().equals(vue.getPanelMenuOptions().getVolumeSons())) {
            ctrl.changerVolume(((JSlider) e.getSource()).getValue(), Audios.SONS);
        }
    }
}
