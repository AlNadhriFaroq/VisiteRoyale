package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Modele.*;
import Vue.Composants.ComposantsJeu.*;
import Vue.Composants.ComposantsMenus.*;
import Vue.Fenetre;

import javax.swing.*;
import java.awt.event.*;

public class AdaptateurSouris extends MouseAdapter {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    public AdaptateurSouris(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof CarteVue) {
            CarteVue carteVue = (CarteVue) e.getSource();
            carteVue.redimensionner(carteVue.getHeight() + carteVue.getHeight() / 8);
            fenetre.getPanelJeu().repaint();
        } else if (e.getSource() instanceof BoutonMenu) {
            ((BoutonMenu) e.getSource()).setForeground(BoutonMenu.couleurFoncee);
            ((BoutonMenu) e.getSource()).setBackground(BoutonMenu.couleurClaire);
        } else if (e.getSource() instanceof BoutonSauvegarde) {
            ((BoutonSauvegarde) e.getSource()).setForeground(BoutonSauvegarde.couleurFoncee);
            ((BoutonSauvegarde) e.getSource()).setBackground(BoutonSauvegarde.couleurClaire);
        } else if (e.getSource() instanceof BoutonSupprimer) {
            ((BoutonSupprimer) e.getSource()).setForeground(BoutonSupprimer.couleurFoncee);
            ((BoutonSupprimer) e.getSource()).setBackground(BoutonSupprimer.couleurClaire);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof CarteVue) {
            CarteVue carteVue = (CarteVue) e.getSource();
            carteVue.redimensionner(carteVue.getHeight() - carteVue.getHeight() / 9);
            fenetre.getPanelJeu().repaint();
        } else if (e.getSource() instanceof BoutonMenu) {
            ((BoutonMenu) e.getSource()).setForeground(BoutonMenu.couleurClaire);
            ((BoutonMenu) e.getSource()).setBackground(BoutonMenu.couleurNormal);
        } else if (e.getSource() instanceof BoutonSauvegarde) {
            ((BoutonSauvegarde) e.getSource()).setForeground(BoutonSauvegarde.couleurClaire);
            ((BoutonSauvegarde) e.getSource()).setBackground(BoutonSauvegarde.couleurNormal);
        } else if (e.getSource() instanceof BoutonSupprimer) {
            ((BoutonSupprimer) e.getSource()).setForeground(BoutonSupprimer.couleurClaire);
            ((BoutonSupprimer) e.getSource()).setBackground(BoutonSupprimer.couleurNormal);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof CarteVue &&
                fenetre.getPanelJeu().getMainVue(prog.getJeu().getJoueurCourant()).contientCarteVue((CarteVue) e.getSource()) &&
                prog.getJeu().peutSelectionnerCarte(((CarteVue) e.getSource()).getCarte())) {
            ctrl.selectionnerCarte(((CarteVue) e.getSource()).getCarte());
        } else if (e.getSource() instanceof PionVue &&
                prog.getJeu().peutSelectionnerPion(((PionVue) e.getSource()).getPion())) {
            ctrl.selectionnerPion(((PionVue) e.getSource()).getPion());
        } else if (e.getSource() instanceof CaseVue &&
                prog.getJeu().getSelectionPions(0) != null) {
            if (((CaseVue) e.getSource()).getCase() < prog.getJeu().getPlateau().getPositionPion(prog.getJeu().getSelectionPions(0)) &&
                    prog.getJeu().peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT)))
                ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT));
            else if (((CaseVue) e.getSource()).getCase() > prog.getJeu().getPlateau().getPositionPion(prog.getJeu().getSelectionPions(0)) &&
                    prog.getJeu().peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE)))
                ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE));
        } else if (e.getSource() instanceof BoutonMenu) {
            ((BoutonMenu) e.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());
            ((BoutonMenu) e.getSource()).setForeground(BoutonMenu.couleurNormal);
            ((BoutonMenu) e.getSource()).setBackground(BoutonMenu.couleurFoncee);
        } else if (e.getSource() instanceof BoutonSauvegarde) {
            ((BoutonSauvegarde) e.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());
            ((BoutonSauvegarde) e.getSource()).setForeground(BoutonSauvegarde.couleurNormal);
            ((BoutonSauvegarde) e.getSource()).setBackground(BoutonSauvegarde.couleurFoncee);
        } else if (e.getSource() instanceof BoutonSupprimer) {
            ((BoutonSupprimer) e.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());
            ((BoutonSupprimer) e.getSource()).setForeground(BoutonSupprimer.couleurNormal);
            ((BoutonSupprimer) e.getSource()).setBackground(BoutonSupprimer.couleurFoncee);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof BoutonMenu) {
            ((BoutonMenu) e.getSource()).setBorder(BorderFactory.createRaisedBevelBorder());
            ((BoutonMenu) e.getSource()).setForeground(BoutonMenu.couleurClaire);
            ((BoutonMenu) e.getSource()).setBackground(BoutonMenu.couleurNormal);
        } else if (e.getSource() instanceof BoutonSauvegarde) {
            ((BoutonSauvegarde) e.getSource()).setBorder(BorderFactory.createRaisedBevelBorder());
            ((BoutonSauvegarde) e.getSource()).setForeground(BoutonSauvegarde.couleurClaire);
            ((BoutonSauvegarde) e.getSource()).setBackground(BoutonSauvegarde.couleurNormal);
        } else if (e.getSource() instanceof BoutonSupprimer) {
            ((BoutonSupprimer) e.getSource()).setBorder(BorderFactory.createRaisedBevelBorder());
            ((BoutonSupprimer) e.getSource()).setForeground(BoutonSupprimer.couleurClaire);
            ((BoutonSupprimer) e.getSource()).setBackground(BoutonSupprimer.couleurNormal);
        }
    }
}
