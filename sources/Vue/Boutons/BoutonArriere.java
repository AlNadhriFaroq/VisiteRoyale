package Vue.Boutons;

import Modele.Jeu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BoutonArriere extends JButton {

    Jeu j;

    public BoutonArriere(Jeu j){
        this.j = j;

    //this.addActionListener(ActionEvent -> {action();});
    }
    // TODO ajouter appel pour Revenir en Arriere
    //void action();
}
