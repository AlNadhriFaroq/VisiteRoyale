package Vue.Boutons;

import Modele.Jeu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BoutonRefaire extends JButton {
    Jeu jeu;

    public BoutonRefaire(Jeu jeu){
        this.jeu = jeu;
        //this.addActionListener(ActionEvent -> {action();});
    }

    // TODO ajouter appel pour refaire
    //void action();
}
