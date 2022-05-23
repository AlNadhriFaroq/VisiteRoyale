package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.*;

import javax.swing.*;
import java.awt.*;

public class PanelMenuPrincipal extends Panel {
    Bouton boutonJouer1vs1;
    Bouton boutonJouerVsIA;
    Bouton boutonSauvegardes;
    Bouton boutonOptions;
    Bouton boutonTutoriel;
    Bouton boutonCredits;
    Bouton boutonQuitter;

    public PanelMenuPrincipal(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        boutonJouer1vs1 = new Bouton("Jouer 1vs1");
        boutonJouerVsIA = new Bouton("Joueur contre IA");
        boutonSauvegardes = new Bouton("Sauvegardes");
        boutonOptions = new Bouton("Options");
        boutonTutoriel = new Bouton("Tutoriel");
        boutonCredits = new Bouton("Crédits");
        boutonQuitter = new Bouton("Quitter");

        boutonJouer1vs1.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonJouerVsIA.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonSauvegardes.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonOptions.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonTutoriel.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonCredits.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonQuitter.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(new Color(0, 0, 0, 0));
        panelBoutons.setLayout(new GridLayout(7, 1, 0, 10));

        panelBoutons.add(boutonJouer1vs1);
        panelBoutons.add(boutonJouerVsIA);
        panelBoutons.add(boutonSauvegardes);
        panelBoutons.add(boutonOptions);
        panelBoutons.add(boutonTutoriel);
        panelBoutons.add(boutonCredits);
        panelBoutons.add(boutonQuitter);

        add(new Cadre(panelBoutons, 1, 10, 1, 1));
    }

    public Bouton getBoutonJouer1vs1() {
        return boutonJouer1vs1;
    }

    public Bouton getBoutonJouerVsIA() {
        return boutonJouerVsIA;
    }

    public Bouton getBoutonSauvegardes() {
        return boutonSauvegardes;
    }

    public Bouton getBoutonOptions() {
        return boutonOptions;
    }

    public Bouton getBoutonTutoriel() {
        return boutonTutoriel;
    }

    public Bouton getBoutonCredits() {
        return boutonCredits;
    }

    public Bouton getBoutonQuitter() {
        return boutonQuitter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_MENU_PRINCIPAL)
            repaint();
    }
}
