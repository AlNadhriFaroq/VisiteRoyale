package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Programme;
import Vue.InterfaceGraphique;

import java.awt.*;

public class PanelEnJeu extends PanelEtat {
    private final CardLayout panels;

    private final PanelJeu panelJeu;
    private final PanelChoixJoueur panelChoixJoueur;
    private final PanelFinPartie panelFinPartie;

    public PanelEnJeu(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl , vue, prog);

        panels = new CardLayout();
        setLayout(panels);

        panelJeu = new PanelJeu(ctrl, vue, prog);
        panelChoixJoueur = new PanelChoixJoueur(ctrl, vue, prog);
        panelFinPartie = new PanelFinPartie(ctrl, vue, prog);

        add(panelJeu, "jeu");
        add(panelChoixJoueur, "choixJoueur");
        add(panelFinPartie, "finPartie");
    }

    public PanelJeu getPanelJeu() {
        return panelJeu;
    }

    public PanelChoixJoueur getPanelChoixJoueur() {
        return panelChoixJoueur;
    }

    public PanelFinPartie getPanelFinPartie() {
        return panelFinPartie;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_EN_JEU) {
            repaint();
            if (prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_JOUEUR)
                panels.show(this, "choixJoueur");
            else if (prog.getJeu().getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE)
                panels.show(this, "finPartie");
            else
                panels.show(this, "jeu");
        }
    }
}
