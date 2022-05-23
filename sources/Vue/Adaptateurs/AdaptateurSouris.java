package Vue.Adaptateurs;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Plateau;
import Modele.Programme;
import Vue.ComponentsJeu.*;
import Vue.InterfaceGraphique;
import Vue.PanelsEtats.PanelEnJeu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
    ControleurMediateur ctrl;
    InterfaceGraphique vue;
    Programme prog;

    public AdaptateurSouris(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        this.ctrl = ctrl;
        this.vue = vue;
        this.prog = prog;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof CarteVue &&
                ((PanelEnJeu) vue.getPanel(Programme.ETAT_EN_JEU)).getPanelJeu().getMainVue(prog.getJeu().getJoueurCourant()).contientCarteVue((CarteVue) e.getSource()) &&
                prog.getJeu().peutSelectionnerCarte(((CarteVue) e.getSource()).getCarte()))
            ctrl.selectionnerCarte(((CarteVue) e.getSource()).getCarte());
        else if (e.getSource() instanceof PionVue &&
                prog.getJeu().peutSelectionnerPion(((PionVue) e.getSource()).getPion()))
            ctrl.selectionnerPion(((PionVue) e.getSource()).getPion());
        else if (e.getSource() instanceof CaseVue &&
                prog.getJeu().getSelectionPions(0) != null)
            if (((CaseVue) e.getSource()).getCase() < prog.getJeu().getPlateau().getPositionPion(prog.getJeu().getSelectionPions(0)) &&
                    prog.getJeu().peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT)))
                ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT));
            else if (((CaseVue) e.getSource()).getCase() > prog.getJeu().getPlateau().getPositionPion(prog.getJeu().getSelectionPions(0)) &&
                    prog.getJeu().peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE)))
                ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE));
    }
}
