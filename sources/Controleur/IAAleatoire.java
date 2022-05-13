package Controleur;

import Modele.*;

import java.util.*;

class IAAleatoire extends IA {
    Random r;

    IAAleatoire(Jeu jeu) {
        super(jeu);
        r = new Random();
    }

    @Override
    public Coup calculerCoup() {
        List<Coup> coups = jeu.calculerListeCoup();

        if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_CARTE && !jeu.getActivationPouvoirSor() && !jeu.getActivationPouvoirFou() && jeu.getActivationPrivilegeRoi() == 0) {
            int taille = coups.size();
            for (int i = 0; i < taille; i++) {
                if (coups.get(i).getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU) {
                    coups.add(coups.get(i));
                    coups.add(coups.get(i));
                    coups.add(coups.get(i));
                    coups.add(coups.get(i));
                }
                if (coups.get(i).getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR) {
                    coups.add(coups.get(i));
                }
            }
        }

        if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_DIRECTION) {
            boolean b = false;
            int i = 0;
            while (i < coups.size() && !b) {
                if (coups.get(i).getDirection() == Jeu.getDirectionJoueur(jeu.getJoueurCourant()))
                    b = true;
                i++;
            }
            if (b) {
                i = 0;
                while (i < coups.size())
                    if (coups.get(i).getDirection() == -Jeu.getDirectionJoueur(jeu.getJoueurCourant()) && r.nextInt(10) <= 7)
                        coups.remove(i);
                    else
                        i++;
            }
        }

        Coup coup = coups.get(r.nextInt(coups.size()));

        switch (coup.getTypeCoup()) {
            case Coup.CHOISIR_CARTE:
                System.out.println(coup.getCarte());
                break;
            case Coup.CHOISIR_PION:
                System.out.println(coup.getPion());
                break;
            case Coup.CHOISIR_DIRECTION:
                System.out.println((coup.getDirection() == Plateau.DIRECTION_VRT ? "V" : "R"));
                break;
            case Coup.ACTIVER_POUVOIR_SOR:
                System.out.println("Sor");
                break;
            case Coup.ACTIVER_POUVOIR_FOU:
                System.out.println("Fou");
                break;
            case Coup.FINIR_TOUR:
                System.out.println("Fin tour");
                break;
        }

        return coup;
    }
}
