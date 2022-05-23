package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Pion;
import Vue.JeuVue;

public class Droite extends Bouton {
    JeuVue frame;

    public Droite(ControleurMediateur ctrl, JeuVue frame, Jeu jeu) {
        super(ctrl, jeu);
        this.frame = frame;
    }

    @Override
    void action() {
        if (jeu.peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE))); {
            ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE));
            this.frame.defausserJeu(jeu.getJoueurCourant());
            this.frame.terrain.majPositions();
            System.out.println("Position FOU " + jeu.getPlateau().getPositionPion(Pion.FOU)+" Position Frame FOU  "+ frame.terrain.getPosFou());

            System.out.println("Position SOR " + jeu.getPlateau().getPositionPion(Pion.SOR)+" Position Frame SOR  "+ frame.terrain.getPosSor());
        }
    }
}
