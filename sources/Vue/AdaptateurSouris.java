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
                throw new RuntimeException("Vue.AdaptateurSouris.positionPion() : Pion invalide");
        }
    }

    public int positionGarde(String name){
        switch (name){
            case "GV":
                return this.jeuVue.jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
            case "GR":
                return this.jeuVue.jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
            default:
                throw new RuntimeException("Vue.AdaptaeurSouris.positionGarde() : Garde invalide.");
        }

    }

    public int choixGarde(int x) {
        if ((x >= this.jeuVue.terrain.getX() + this.jeuVue.jeu.getPlateau().getPositionPion(Pion.GAR_VRT) * this.jeuVue.terrain.getWidth() / 17) && (x <= this.jeuVue.terrain.getX() + this.jeuVue.jeu.getPlateau().getPositionPion(Pion.GAR_VRT) * this.jeuVue.terrain.getWidth() / 17 + this.jeuVue.terrain.getWidth() / 17)) {
            return 0;
        }
        if ((x >= this.jeuVue.terrain.getX() + this.jeuVue.jeu.getPlateau().getPositionPion(Pion.GAR_RGE) * this.jeuVue.terrain.getWidth() / 17) && (x <= this.jeuVue.terrain.getX() + this.jeuVue.jeu.getPlateau().getPositionPion(Pion.GAR_RGE) * this.jeuVue.terrain.getWidth() / 17 + this.jeuVue.terrain.getWidth() / 17)){
            return 1;
        }
        return 2;


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int pos;
        if((e.getX() >= this.jeuVue.terrain.getX()) && (e.getX() <= this.jeuVue.terrain.getWidth() + this.jeuVue.terrain.getX()) && (e.getY()>= this.jeuVue.terrain.getY()) && (e.getY()<= this.jeuVue.terrain.getHeight()+this.jeuVue.terrain.getY())){
            if((this.jeuVue.getTypeJoueur(this.jeuVue.jeu.getJoueurCourant())).toString()=="G" && this.jeuVue.jeu.getSelectionPions(0)==null){
                if (this.choixGarde(e.getX())==0) {
                    this.ctrl.selectionnerPion(Pion.GAR_VRT);
                    System.out.println(this.jeuVue.jeu.getSelectionPions(0));
                }
                if (this.choixGarde(e.getX())==1) {
                    this.ctrl.selectionnerPion(Pion.GAR_RGE);
                    System.out.println(this.jeuVue.jeu.getSelectionPions(0));
                }
                if (this.choixGarde(e.getX())==2) {
                    System.out.println("Aucun");
                }
            }

            if((this.jeuVue.getTypeJoueur(this.jeuVue.jeu.getJoueurCourant())).toString()=="G" && this.jeuVue.jeu.getSelectionPions(0)!=null){
                pos = positionGarde(this.jeuVue.jeu.getSelectionPions(0).toString());

                if(this.jeuVue.jeu.peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE)) && !this.jeuVue.cartesJoueesEstVide(this.jeuVue.jeu.getJoueurCourant())){
                    if(e.getX()>pos*(this.jeuVue.terrain.getWidth()/17) + this.jeuVue.terrain.getX()+this.jeuVue.terrain.getWidth()/17){
                        ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE));
                    }
                }

                if (this.jeuVue.jeu.peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT)) && !this.jeuVue.cartesJoueesEstVide(this.jeuVue.jeu.getJoueurCourant())) {

                    if (e.getX() < pos*(this.jeuVue.terrain.getWidth()/17) + this.jeuVue.terrain.getX()) {
                        ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT));
                    }
                }


            }


            if((this.jeuVue.getTypeJoueur(this.jeuVue.jeu.getJoueurCourant())).toString()!="G") {
                pos = positionPion(this.jeuVue.getTypeJoueur(this.jeuVue.jeu.getJoueurCourant()));

                if (this.jeuVue.jeu.peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE)) && !this.jeuVue.cartesJoueesEstVide(this.jeuVue.jeu.getJoueurCourant())) {
                    if (e.getX() > pos*(this.jeuVue.terrain.getWidth()/17) + this.jeuVue.terrain.getX() + this.jeuVue.terrain.getWidth()/17) {
                        ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_RGE));
                    }
                }
                if (this.jeuVue.jeu.peutSelectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT)) && !this.jeuVue.cartesJoueesEstVide(this.jeuVue.jeu.getJoueurCourant())) {

                    if (e.getX() < pos*(this.jeuVue.terrain.getWidth()/17) + this.jeuVue.terrain.getX()) {
                        ctrl.selectionnerDirection(Jeu.getDirectionJoueur(Jeu.JOUEUR_VRT));
                    }
                }
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
