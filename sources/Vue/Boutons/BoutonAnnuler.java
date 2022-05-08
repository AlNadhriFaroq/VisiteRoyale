package Vue.Boutons;

import Modele.Jeu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BoutonAnnuler extends JButton {
    Jeu jeu;

    public BoutonAnnuler(Jeu jeu){
        this.jeu = jeu;
        //this.addActionListener(ActionEvent -> {action();});
    }

    // TODO ajouter appel pour annuler
    //void action();
}
