package IA;

import Modele.*;

import java.util.*;

public class IAAleatoirePonderee extends IAAleatoire {

    public IAAleatoirePonderee(Jeu jeu) {
        super(jeu);
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
        System.out.println(coup.toString());
        return coup;
    }
}
