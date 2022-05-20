package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Programme;
import Vue.InterfaceGraphique;

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
        if (e.getSource().equals(vue.getPanelMenuPrincipal().getBoutonJouer1vs1())) {
            ctrl.nouvellePartie(false, false);
        } else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBoutonJouerVsIA())) {
            ctrl.nouvellePartie(true, false);
        } else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBoutonSauvegardes()) || e.getSource().equals(vue.getPanelMenuJeu().getBoutonSauvegardes())) {
            ctrl.ouvrirMenuSauvegardes();
        } else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBoutonOptions()) || e.getSource().equals(vue.getPanelMenuJeu().getBoutonOptions())) {
            ctrl.ouvrirMenuOptions();
        } else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBoutonTutoriel()) || e.getSource().equals(vue.getPanelMenuJeu().getBoutonTutoriel())) {
            ctrl.ouvrirTutoriel();
        } else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBoutonCredits())) {
            ctrl.ouvrirCredits();
        } else if (e.getSource().equals(vue.getPanelMenuPrincipal().getBoutonQuitter())) {
            ctrl.quitter();
        } else if (e.getSource().equals(vue.getPanelMenuJeu().getBoutonReprendre())) {
            ctrl.reprendrePartie();
        } else if (e.getSource().equals(vue.getPanelMenuJeu().getBoutonNouvellePartie())) {
            ctrl.nouvellePartie(prog.getJoueurEstIA(Jeu.JOUEUR_VRT), prog.getJoueurEstIA(Jeu.JOUEUR_RGE));
        } else if (e.getSource().equals(vue.getPanelMenuJeu().getBoutonRetour())) {
            ctrl.retourMenuPrecedant();
        }
    }
}
