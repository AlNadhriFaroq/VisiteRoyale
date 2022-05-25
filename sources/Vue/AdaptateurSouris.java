package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Pion;
import Modele.Type;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
    JeuVue jeuVue;
    ControleurMediateur ctrl;

    AdaptateurSouris(JeuVue jeuVue, ControleurMediateur ctrl) {
        this.jeuVue = jeuVue;
        this.ctrl = ctrl;
    }

    public int positionPion(Type type){
        switch (type.toString()) {
            case "R":
                return this.jeuVue.jeu.getPlateau().getPositionPion(Pion.ROI);
            case "S":
                return this.jeuVue.jeu.getPlateau().getPositionPion(Pion.SOR);
            case "F":
                return this.jeuVue.jeu.getPlateau().getPositionPion(Pion.FOU);
            default:
                throw new RuntimeException("Modele.Type.texteEnType() : Texte entré invalide.");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int pos;
        if((e.getX() >= this.jeuVue.terrain.getX()) && (e.getX() <= this.jeuVue.terrain.getWidth() + this.jeuVue.terrain.getX()) && (e.getY()>= this.jeuVue.terrain.getY()) && (e.getY()<= this.jeuVue.terrain.getHeight()+this.jeuVue.terrain.getY())){
            if((this.jeuVue.getTypeJoueur(this.jeuVue.jeu.getJoueurCourant())).toString()!="G") {
                pos = positionPion(this.jeuVue.getTypeJoueur(this.jeuVue.jeu.getJoueurCourant()));

                if (this.jeuVue.jeu.peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE)) && !this.jeuVue.cartesJoueesEstVide(this.jeuVue.jeu.getJoueurCourant())) {
                    if (e.getX() > pos*(this.jeuVue.terrain.getWidth()/17) + this.jeuVue.terrain.getX() + this.jeuVue.terrain.getWidth()/17) {
                        ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE));
                        this.jeuVue.defausserJeu(this.jeuVue.jeu.getJoueurCourant());
                        this.jeuVue.terrain.majPositions();
                    }
                }
                if (this.jeuVue.jeu.peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT)) && !this.jeuVue.cartesJoueesEstVide(this.jeuVue.jeu.getJoueurCourant())) {

                    if (e.getX() < pos*(this.jeuVue.terrain.getWidth()/17) + this.jeuVue.terrain.getX()) {
                        ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT));
                        this.jeuVue.defausserJeu(this.jeuVue.jeu.getJoueurCourant());
                        this.jeuVue.terrain.majPositions();
                    }
                }
            }


            if(this.jeuVue.jeu.getSelectionPions(0) == Pion.SOR){
                System.out.println("Ici");
            }
            /* if type != garde:
                if e.getx() < position du type:
                    deplacer vers joueur vert
                sinon:
                    deplacer vers joueur rouge
             */
        }
        ctrl.clicSouris(e.getX(), e.getY());
    }
}
