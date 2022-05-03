package Vue;

import Controleur.ControleurMediateur;

import java.awt.event.*;

public class AdaptateurTemps implements ActionListener {
    ControleurMediateur ctrl;

    AdaptateurTemps(ControleurMediateur ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ctrl.tictac();
    }
}
