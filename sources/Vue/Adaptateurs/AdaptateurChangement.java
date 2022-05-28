package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Global.Audios;
import Modele.Programme;
import Vue.Fenetre;

import javax.swing.*;
import javax.swing.event.*;

public class AdaptateurChangement implements ChangeListener {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    public AdaptateurChangement(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(fenetre.getPanelMenuOptions().getVolumeMusique())) {
            ctrl.changerVolume(((JSlider) e.getSource()).getValue(), Audios.MUSIQUE);
        } else if (e.getSource().equals(fenetre.getPanelMenuOptions().getVolumeSons())) {
            ctrl.changerVolume(((JSlider) e.getSource()).getValue(), Audios.SONS);
        }
    }
}
