package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Programme;
import Vue.ComponentsMenus.BoutonSauvegarde;
import Vue.ComponentsMenus.BoutonSupprimer;
import Vue.InterfaceGraphique;
import Vue.PanelsEtats.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurBoutons implements ActionListener {
    ControleurMediateur ctrl;
    InterfaceGraphique vue;
    Programme prog;

    public AdaptateurBoutons(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        this.ctrl = ctrl;
        this.vue = vue;
        this.prog = prog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vue.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.jouer1vs1)))
            ctrl.nouvellePartie(false, false);
        else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.jouerVsIA)))
            ctrl.nouvellePartie(true, false);
        else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.sauvegardes)) ||
                e.getSource().equals(vue.getPanelMenuJeu().getBouton(PanelMenuJeu.sauvegardes)))
            ctrl.ouvrirMenuSauvegardes();
        else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.options)) ||
                e.getSource().equals(vue.getPanelMenuJeu().getBouton(PanelMenuJeu.options)))
            ctrl.ouvrirMenuOptions();
        else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.tutoriel)) ||
                e.getSource().equals(vue.getPanelMenuJeu().getBouton(PanelMenuJeu.tutoriel)))
            ctrl.ouvrirTutoriel();
        else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.credits)))
            ctrl.ouvrirCredits();
        else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.quitter)))
            ctrl.quitter();
        else if (e.getSource().equals(vue.getPanelMenuJeu().getBouton(PanelMenuJeu.reprendre)))
            ctrl.reprendrePartie();
        else if (e.getSource().equals(vue.getPanelEnJeu().getPanelFinPartie().getBoutonNouvellePartie()) ||
                e.getSource().equals(vue.getPanelMenuJeu().getBouton(PanelMenuJeu.nouvellePartie)))
            ctrl.nouvellePartie(prog.getJoueurEstIA(Jeu.JOUEUR_VRT), prog.getJoueurEstIA(Jeu.JOUEUR_RGE));
        else if (e.getSource().equals(vue.getPanelEnJeu().getPanelFinPartie().getBoutonRetour()) ||
                e.getSource().equals(vue.getPanelMenuJeu().getBouton(PanelMenuJeu.retour)) ||
                e.getSource().equals(vue.getPanelMenuSauvegardes().getBoutonRetour()) ||
                e.getSource().equals(vue.getPanelMenuOptions().getBoutonRetour()) ||
                e.getSource().equals(vue.getPanelTutoriel().getBoutonRetour()) ||
                e.getSource().equals(vue.getPanelCredits().getBoutonRetour()))
            ctrl.retourMenuPrecedant();
        else if (e.getSource().equals(vue.getPanelEnJeu().getPanelChoixJoueur().getBoutonGauche()) ||
                e.getSource().equals(vue.getPanelEnJeu().getPanelChoixJoueur().getBoutonDroite()))
            ctrl.definirJoueurQuiCommence();
        else if (e.getSource().equals(vue.getPanelEnJeu().getPanelJeu().getBoutonPause()))
            ctrl.ouvrirMenuJeu();
        else if (e.getSource().equals(vue.getPanelEnJeu().getPanelJeu().getBoutonAnnuler()) &&
                prog.getJeu().peutAnnuler())
            ctrl.annuler();
        else if (e.getSource().equals(vue.getPanelEnJeu().getPanelJeu().getBoutonRefaire()) &&
                prog.getJeu().peutRefaire())
            ctrl.refaire();
        else if (e.getSource().equals(vue.getPanelEnJeu().getPanelJeu().getBoutonFinTour()) &&
                prog.getJeu().peutFinirTour())
            ctrl.finirTour();
        else if (e.getSource().equals(vue.getPanelEnJeu().getPanelJeu().getBoutonPouvoirSor()) &&
                prog.getJeu().peutUtiliserPouvoirSorcier())
            ctrl.activerPouvoirSor();
        else if (e.getSource().equals(vue.getPanelEnJeu().getPanelJeu().getBoutonPouvoirFou()) &&
                prog.getJeu().peutUtiliserPouvoirFou())
            ctrl.activerPouvoirFou();
        else if (e.getSource() instanceof BoutonSupprimer) {
            for (int i = 0; i < prog.getSauvegardes().length; i++)
                if (e.getSource().equals(vue.getPanelMenuSauvegardes().getBoutonSupprimer(i)))
                    ctrl.supprimerSauvegarde(i);
        } else if (e.getSource() instanceof BoutonSauvegarde) {
            if (prog.getJeu().getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE) {
                for (int i = 0; i < prog.getSauvegardes().length; i++)
                    if (e.getSource().equals(vue.getPanelMenuSauvegardes().getBoutonSauvegarde(i)))
                        ctrl.chargerSauvegarde(i);
            } else {
                for (int i = 0; i < Programme.NB_SAUVEGARDES; i++)
                    if (e.getSource().equals(vue.getPanelMenuSauvegardes().getBoutonSauvegarde(i)))
                        ctrl.sauvegarderPartie(i);
            }
        }
    }
}
