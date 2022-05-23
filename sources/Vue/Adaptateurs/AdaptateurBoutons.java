package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Programme;
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
        if (e.getSource().equals(((PanelMenuPrincipal) vue.getPanel(Programme.ETAT_MENU_PRINCIPAL)).getBoutonJouer1vs1()))
            ctrl.nouvellePartie(false, false);
        else if (e.getSource().equals(((PanelMenuPrincipal) vue.getPanel(Programme.ETAT_MENU_PRINCIPAL)).getBoutonJouerVsIA()))
            ctrl.nouvellePartie(true, false);
        else if (e.getSource().equals(((PanelMenuPrincipal) vue.getPanel(Programme.ETAT_MENU_PRINCIPAL)).getBoutonSauvegardes()) ||
                e.getSource().equals(((PanelMenuJeu) vue.getPanel(Programme.ETAT_MENU_JEU)).getBoutonSauvegardes()))
            ctrl.ouvrirMenuSauvegardes();
        else if (e.getSource().equals(((PanelMenuPrincipal) vue.getPanel(Programme.ETAT_MENU_PRINCIPAL)).getBoutonOptions()) ||
                e.getSource().equals(((PanelMenuJeu) vue.getPanel(Programme.ETAT_MENU_JEU)).getBoutonOptions()))
            ctrl.ouvrirMenuOptions();
        else if (e.getSource().equals(((PanelMenuPrincipal) vue.getPanel(Programme.ETAT_MENU_PRINCIPAL)).getBoutonTutoriel()) ||
                e.getSource().equals(((PanelMenuJeu) vue.getPanel(Programme.ETAT_MENU_JEU)).getBoutonTutoriel()))
            ctrl.ouvrirTutoriel();
        else if (e.getSource().equals(((PanelMenuPrincipal) vue.getPanel(Programme.ETAT_MENU_PRINCIPAL)).getBoutonCredits()))
            ctrl.ouvrirCredits();
        else if (e.getSource().equals(((PanelMenuPrincipal) vue.getPanel(Programme.ETAT_MENU_PRINCIPAL)).getBoutonQuitter()))
            ctrl.quitter();
        else if (e.getSource().equals(((PanelMenuJeu) vue.getPanel(Programme.ETAT_MENU_JEU)).getBoutonReprendre()))
            ctrl.reprendrePartie();
        else if (e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelFinPartie().getBoutonNouvellePartie()) ||
                e.getSource().equals(((PanelMenuJeu) vue.getPanel(Programme.ETAT_MENU_JEU)).getBoutonNouvellePartie()))
            ctrl.nouvellePartie(prog.getJoueurEstIA(Jeu.JOUEUR_VRT), prog.getJoueurEstIA(Jeu.JOUEUR_RGE));
        else if (e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelFinPartie().getBoutonRetour()) ||
                e.getSource().equals(((PanelMenuJeu) vue.getPanel(Programme.ETAT_MENU_JEU)).getBoutonRetour()) ||
                e.getSource().equals(((PanelMenuSauvegardes) vue.getPanel(Programme.ETAT_MENU_SAUVEGARDES)).getBoutonRetour()) ||
                e.getSource().equals(((PanelMenuOptions) vue.getPanel(Programme.ETAT_MENU_OPTIONS)).getBoutonRetour()) ||
                e.getSource().equals(((PanelTutoriel) vue.getPanel(Programme.ETAT_TUTORIEL)).getBoutonRetour()) ||
                e.getSource().equals(((PanelCredits) vue.getPanel(Programme.ETAT_CREDITS)).getBoutonRetour()))
            ctrl.retourMenuPrecedant();
        else if (e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelChoixJoueur().getBoutonGauche()) ||
                e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelChoixJoueur().getBoutonDroite()))
            ctrl.definirJoueurQuiCommence();
        else if (e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelJeu().getBoutonPause()))
            ctrl.ouvrirMenuJeu();
        else if (e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelJeu().getBoutonAnnuler()) &&
                prog.getJeu().peutAnnuler())
            ctrl.annuler();
        else if (e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelJeu().getBoutonRefaire()) &&
                prog.getJeu().peutRefaire())
            ctrl.refaire();
        else if (e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelJeu().getBoutonFinTour()) &&
                prog.getJeu().peutFinirTour())
            ctrl.finirTour();
        else if (e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelJeu().getBoutonPouvoirSor()) &&
                prog.getJeu().peutUtiliserPouvoirSorcier())
            ctrl.activerPouvoirSor();
        else if (e.getSource().equals(((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelJeu().getBoutonPouvoirFou()) &&
                prog.getJeu().peutUtiliserPouvoirFou())
            ctrl.activerPouvoirFou();
    }
}
