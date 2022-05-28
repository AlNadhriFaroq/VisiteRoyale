package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.Programme;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.Composants.ComposantsMenus.BoutonMenu;
import Vue.*;

import javax.swing.*;
import java.awt.*;

public class PanelTutoriel extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    BoutonMenu boutonPrecedent;
    BoutonMenu boutonSuivant;
    BoutonMenu boutonMenuRetour;

    public PanelTutoriel(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        /* Construction des composants */
        JLabel texteTitre = new JLabel("Tutoriel");
        texteTitre.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));

        JLabel textePage = new JLabel("0");

        JPanel image = new JPanel();

        boutonPrecedent = new BoutonMenu("");
        boutonPrecedent.setIcon(new ImageIcon(Images.TEXTE_PRECEDENT_SUIVANT.getScaledInstance(25, 25, 0)));

        boutonSuivant = new BoutonMenu("");
        boutonSuivant.setIcon(new ImageIcon(Images.TEXTE_PRECEDENT_SUIVANT.getScaledInstance(25, 25, 0)));

        boutonMenuRetour = new BoutonMenu("Retour");

        /* Disposition des composants dans le panel */
        add(texteTitre, new GBC(0, 0, 4, 1).setWeighty(5).setAnchor(GBC.LINE_START));
        add(image, new GBC(0, 1, 4, 1).setWeighty(90).setFill(GBC.BOTH));
        add(boutonMenuRetour, new GBC(0, 2).setWeight(33, 5).setAnchor(GBC.LINE_START));
        add(boutonPrecedent, new GBC(1, 2).setWeight(28, 5).setAnchor(GBC.LINE_END));
        add(textePage, new GBC(2, 2).setWeight(11, 5));
        add(boutonSuivant, new GBC(3, 2).setWeight(28, 5).setAnchor(GBC.LINE_START));

        /* Retransmission des événements au contrôleur */
        boutonMenuRetour.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonSuivant.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonPrecedent.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
    }

    public BoutonMenu getBoutonRetour() {
        return boutonMenuRetour;
    }

    public void mettreAJour() {
        //image.mettreAJour();
        repaint();
    }
}
