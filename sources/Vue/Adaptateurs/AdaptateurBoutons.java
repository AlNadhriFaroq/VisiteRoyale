package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.*;
import Vue.Fenetre;
import Vue.Composants.ComposantsMenus.*;
import Vue.PanelsEtats.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurBoutons implements ActionListener {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    public AdaptateurBoutons(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(fenetre.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.jouer1vs1)))
            ctrl.nouvellePartie(false, false);
        else if (e.getSource().equals(fenetre.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.jouerVsIA)))
            ctrl.nouvellePartie(true, false);
        else if (e.getSource().equals(fenetre.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.sauvegardes)) ||
                e.getSource().equals(fenetre.getPanelMenuJeu().getBouton(PanelMenuJeu.sauvegardes)))
            ctrl.ouvrirMenuSauvegardes();
        else if (e.getSource().equals(fenetre.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.options)) ||
                e.getSource().equals(fenetre.getPanelMenuJeu().getBouton(PanelMenuJeu.options)))
            ctrl.ouvrirMenuOptions();
        else if (e.getSource().equals(fenetre.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.tutoriel)) ||
                e.getSource().equals(fenetre.getPanelMenuJeu().getBouton(PanelMenuJeu.tutoriel)))
            ctrl.ouvrirTutoriel();
        else if (e.getSource().equals(fenetre.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.credits)))
            ctrl.ouvrirCredits();
        else if (e.getSource().equals(fenetre.getPanelMenuPrincipal().getBouton(PanelMenuPrincipal.quitter)))
            ctrl.quitter();
        else if (e.getSource().equals(fenetre.getPanelMenuJeu().getBouton(PanelMenuJeu.reprendre)))
            ctrl.reprendrePartie();
        else if (e.getSource().equals(fenetre.getPanelFinPartie().getBoutonNouvellePartie()) ||
                e.getSource().equals(fenetre.getPanelMenuJeu().getBouton(PanelMenuJeu.nouvellePartie)))
            ctrl.nouvellePartie(prog.getJoueurEstIA(Jeu.JOUEUR_VRT), prog.getJoueurEstIA(Jeu.JOUEUR_RGE));
        else if (e.getSource().equals(fenetre.getPanelFinPartie().getBoutonRetour()) ||
                e.getSource().equals(fenetre.getPanelMenuJeu().getBouton(PanelMenuJeu.retour)) ||
                e.getSource().equals(fenetre.getPanelMenuSauvegardes().getBoutonRetour()) ||
                e.getSource().equals(fenetre.getPanelMenuOptions().getBoutonRetour()) ||
                e.getSource().equals(fenetre.getPanelTutoriel().getBoutonRetour()) ||
                e.getSource().equals(fenetre.getPanelCredits().getBoutonRetour()))
            ctrl.retourMenuPrecedant();
        else if (e.getSource().equals(fenetre.getPanelChoixJoueur().getBoutonGauche()) ||
                e.getSource().equals(fenetre.getPanelChoixJoueur().getBoutonDroite()))
            ctrl.definirJoueurQuiCommence();
        else if (e.getSource().equals(fenetre.getPanelJeu().getBoutonPause()))
            ctrl.ouvrirMenuJeu();
        else if (e.getSource().equals(fenetre.getPanelJeu().getBoutonAnnuler()) &&
                prog.peutAnnuler())
            ctrl.annuler();
        else if (e.getSource().equals(fenetre.getPanelJeu().getBoutonRefaire()) &&
                prog.peutRefaire())
            ctrl.refaire();
        else if (e.getSource().equals(fenetre.getPanelJeu().getBoutonFinTour()) &&
                prog.getJeu().peutFinirTour())
            ctrl.finirTour();
        else if (e.getSource().equals(fenetre.getPanelJeu().getBoutonPouvoirSor()) &&
                prog.getJeu().peutUtiliserPouvoirSorcier())
            ctrl.activerPouvoirSor();
        else if (e.getSource().equals(fenetre.getPanelJeu().getBoutonPouvoirFou()) &&
                prog.getJeu().peutUtiliserPouvoirFou())
            ctrl.activerPouvoirFou();
        else if (e.getSource() instanceof BoutonSupprimer) {
            for (int i = 0; i < prog.getSauvegardes().length; i++)
                if (e.getSource().equals(fenetre.getPanelMenuSauvegardes().getBoutonSupprimer(i)))
                    ctrl.supprimerSauvegarde(i);
        } else if (e.getSource() instanceof BoutonSauvegarde) {
            if (prog.getJeu().getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE) {
                for (int i = 0; i < prog.getSauvegardes().length; i++)
                    if (e.getSource().equals(fenetre.getPanelMenuSauvegardes().getBoutonSauvegarde(i)))
                        ctrl.chargerSauvegarde(i);
            } else {
                for (int i = 0; i < Programme.NB_SAUVEGARDES; i++)
                    if (e.getSource().equals(fenetre.getPanelMenuSauvegardes().getBoutonSauvegarde(i)))
                        ctrl.sauvegarderPartie(i);
            }
        } else if (e.getSource().equals(fenetre.getPanelMenuOptions().getPleinEcran())) {
            Configuration.instance().ecrire("PleinEcran", Boolean.toString(fenetre.getPanelMenuOptions().getPleinEcran().isSelected()));
            fenetre.mettreAJourPleinEcran();
        } else if (e.getSource().equals(fenetre.getPanelMenuOptions().getMainCachee())) {
            Configuration.instance().ecrire("MainCachee", Boolean.toString(fenetre.getPanelMenuOptions().getMainCachee().isSelected()));
        }
    }
}
