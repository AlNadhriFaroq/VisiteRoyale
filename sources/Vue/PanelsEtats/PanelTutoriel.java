package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.Programme;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.ComponentsMenus.BoutonMenu;
import Vue.InterfaceGraphique;

import javax.swing.*;
import java.awt.*;

public class PanelTutoriel extends PanelEtat {
    BoutonMenu boutonMenuRetour;

    BoutonMenu boutonPrecedent;
    BoutonMenu boutonSuivant;

    public PanelTutoriel(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        setLayout(new GridBagLayout());

        JLabel texteTitre = new JLabel("Tutoriel");
        texteTitre.setFont(new Font(Font.DIALOG, 0, 30));

        boutonPrecedent = new BoutonMenu("");
        boutonPrecedent.setIcon(new ImageIcon(Images.TEXTE_PRECEDENT_SUIVANT.getScaledInstance(25, 25, 0)));

        JLabel textePage = new JLabel("0");

        boutonSuivant = new BoutonMenu("");
        boutonSuivant.setIcon(new ImageIcon(Images.TEXTE_PRECEDENT_SUIVANT.getScaledInstance(25, 25, 0)));

        boutonMenuRetour = new BoutonMenu("Retour");
        JPanel image = new JPanel();

        boutonMenuRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        add(texteTitre, new GridBagConstraints(0, 0, 4, 1, 1, 0.05, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(image, new GridBagConstraints(0, 1, 4, 1, 1, 0.9, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(boutonMenuRetour, new GridBagConstraints(0, 2, 1, 1, 0.33, 0.05, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(boutonPrecedent, new GridBagConstraints(1, 2, 1, 1, 0.28, 0.05, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(textePage, new GridBagConstraints(2, 2, 1, 1, 0.11, 0.05, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(boutonSuivant, new GridBagConstraints(3, 2, 1, 1, 0.28, 0.05, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    }

    public BoutonMenu getBoutonRetour() {
        return boutonMenuRetour;
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
