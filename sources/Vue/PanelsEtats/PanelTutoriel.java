package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.Bouton;
import Vue.InterfaceGraphique;

import javax.swing.*;
import java.awt.*;

public class PanelTutoriel extends Panel {
    Bouton boutonRetour;

    public PanelTutoriel(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        JLabel texte = new JLabel("Tutoriel");
        boutonRetour = new Bouton("Retour");

        boutonRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        add(texte, BorderLayout.NORTH);
        add(boutonRetour);
    }

    public Bouton getBoutonRetour() {
        return boutonRetour;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_TUTORIEL)
            repaint();
    }
}
