package Vue.Boutons;

import Modele.Jeu;

import javax.swing.*;

public abstract class BoutonPouvoir extends JButton {
    Jeu jeu;

    public BoutonPouvoir(Jeu jeu){
        this.jeu = jeu;
        this.addActionListener(ActionEvent -> {action();});
    }

    abstract void action();
}
