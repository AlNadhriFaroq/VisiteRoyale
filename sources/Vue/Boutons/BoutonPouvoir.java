package Vue.Boutons;

import Modele.Jeu;

import javax.swing.*;

public abstract class BoutonPouvoir extends JButton {

    Jeu j;
    public BoutonPouvoir(Jeu j){

        this.addActionListener(ActionEvent -> {action();});

    }

    abstract void action();
}
