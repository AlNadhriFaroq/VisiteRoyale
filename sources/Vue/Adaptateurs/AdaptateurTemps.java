package Vue.Adaptateurs;

import Controleur.ControleurMediateur;

import java.awt.event.*;

public class AdaptateurTemps implements ActionListener {
    ControleurMediateur ctrl;

    public AdaptateurTemps(ControleurMediateur ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ctrl.tictac();
    }
}
