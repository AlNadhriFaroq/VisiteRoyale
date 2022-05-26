package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;

import javax.swing.*;

public abstract class Bouton extends JButton {
    Jeu jeu;
    ControleurMediateur ctrl;

    public Bouton(ControleurMediateur ctrl, Jeu jeu){
        this.jeu = jeu;
        this.ctrl = ctrl;
        this.addActionListener(ActionEvent -> {action();});
    }

    abstract void action();

}
