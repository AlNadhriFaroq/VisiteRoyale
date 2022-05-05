package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Coup implements Cloneable {
    public static final int DEPLACEMENT = 0;
    public static final int PRIVILEGEROI = 1;
    public static final int POUVOIRSORCIER = 2;
    public static final int POUVOIRFOU = 3;
    public static final int FINTOUR = 4;

    Jeu jeu;
    int joueur;
    int typeCoup;
    int typePasse;
    Carte[] cartes;
    int[] pions;
    int[] deplacements;
    int[] directions;
    int positionPasse;
    int nbCartesAPiocher;
    Paquet defaussePasse;
    Paquet defausseFutur;
    int indiceMelange;
    boolean couronne;

    public Coup(int joueur, int typeCoup, Carte[] cartes, int[] pions, int[] deplacements, int[] directions) {
        this.joueur = joueur;
        this.typeCoup = typeCoup;
        this.pions = pions;
        this.deplacements = deplacements;
        this.directions = directions;
        if (typeCoup == FINTOUR) {
            nbCartesAPiocher = Jeu.TAILLE_MAIN - jeu.getMain(joueur).getTaille();
            this.cartes = new Carte[nbCartesAPiocher];
            defaussePasse = new Paquet(Paquet.ORDONNE);
            defausseFutur = new Paquet(Paquet.ORDONNE);
        } else {
            this.cartes = cartes;
        }
        typePasse = jeu.getTypeCourant();
        indiceMelange = -1;
        couronne = false;
    }

    public void fixerJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public void executer() {
        switch (typeCoup) {
            case DEPLACEMENT:
                executerDeplacement();
                break;
            case PRIVILEGEROI:
                executerPrivilegeRoi();
                break;
            case POUVOIRSORCIER:
                executerPouvoirSorcier();
                break;
            case POUVOIRFOU:
                executerPouvoirFou();
                break;
            case FINTOUR:
                executerFinTour();
                break;
            default:
                System.err.println("Coup impossible !");
                break;
        }
    }

    public void desexecuter() {
        switch (typeCoup) {
            case DEPLACEMENT:
                desexecuterDeplacement();
                break;
            case PRIVILEGEROI:
                desexecuterPrivilegeRoi();
                break;
            case POUVOIRSORCIER:
                desexecuterPouvoirSorcier();
                break;
            case POUVOIRFOU:
                desexecuterPouvoirFou();
                break;
            case FINTOUR:
                desexecuterFinTour();
                break;
            default:
                System.err.println("Coup impossible !");
                break;
        }
    }

    void executerDeplacement() {
        for (int i = 0; i < pions.length; i++) {
            if (cartes[0].getType() == Type.TYPE_FOU && cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE) {
                positionPasse = jeu.getPositionPion(pions[0]);
                jeu.setPositionPion(pions[i], Plateau.FONTAINE);
            } else {
                jeu.setPositionPion(pions[i], deplacements[i], directions[i]);
            }
            jeu.getDefausse().ajouter(jeu.getMain(joueur).defausser(cartes[i]));
        }
        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(cartes[0].getType());
    }

    void desexecuterDeplacement() {
        for (int i = pions.length-1; i >= 0; i--) {
            if (cartes[0].getType() == Type.TYPE_FOU && cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE)
                jeu.setPositionPion(pions[i], positionPasse);
            else
                jeu.setPositionPion(pions[i], deplacements[i], -directions[i]);
            jeu.getMain(joueur).ajouter(jeu.getDefausse().piocher());
        }
        jeu.setTypeCourant(typePasse);
    }

    void executerPrivilegeRoi() {
        jeu.setPositionPion(Plateau.PION_ROI, 1, directions[0]);
        jeu.setPositionPion(Plateau.PION_GRD_VERT, 1, directions[0]);
        jeu.setPositionPion(Plateau.PION_GRD_ROUGE, 1, directions[0]);
        jeu.getDefausse().ajouter(jeu.getMain(joueur).defausser(cartes[0]));
        jeu.getDefausse().ajouter(jeu.getMain(joueur).defausser(cartes[1]));
        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(cartes[0].getType());
    }

    void desexecuterPrivilegeRoi() {
        jeu.setPositionPion(Plateau.PION_ROI, 1, -directions[0]);
        jeu.setPositionPion(Plateau.PION_GRD_VERT, 1, -directions[0]);
        jeu.setPositionPion(Plateau.PION_GRD_ROUGE, 1, -directions[0]);
        jeu.getMain(joueur).ajouter(jeu.getDefausse().piocher());
        jeu.getMain(joueur).ajouter(jeu.getDefausse().piocher());
        jeu.setTypeCourant(typePasse);
    }

    void executerPouvoirSorcier() {
        positionPasse = jeu.getPositionPion(pions[0]);
        jeu.setPositionPion(pions[0], jeu.getPositionPion(Plateau.PION_SRC));
        jeu.typeCourant = 6;
        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(Type.TYPE_FIN);
    }

    void desexecuterPouvoirSorcier() {
        jeu.setPositionPion(pions[0], positionPasse);
        jeu.setTypeCourant(typePasse);
    }

    void executerPouvoirFou() {
        if (cartes[0].getType() == Type.TYPE_FOU && cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE) {
            positionPasse = jeu.getPositionPion(pions[0]);
            jeu.setPositionPion(pions[0], Plateau.FONTAINE);
        } else {
            jeu.setPositionPion(pions[0], deplacements[0], directions[0]);
        }
        jeu.getDefausse().ajouter(jeu.getMain(joueur).defausser(cartes[0]));
        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(jeu.getTypePion(pions[0]));
    }

    void desexecuterPouvoirFou() {
        if (cartes[0].getType() == Type.TYPE_FOU && cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE)
            jeu.setPositionPion(pions[0], positionPasse);
        else
            jeu.setPositionPion(pions[0], deplacements[0], -directions[0]);
        jeu.getMain(joueur).ajouter(jeu.getDefausse().piocher());
        jeu.setTypeCourant(typePasse);
    }

    void executerFinTour() {
        if (joueur == Jeu.JOUEUR_VERT)
            jeu.setPositionCouronne(calculerDeplacementCouronne(), Plateau.VERS_VERT);
        else
            jeu.setPositionCouronne(calculerDeplacementCouronne(), Plateau.VERS_ROUGE);

        for (int i = 0; i < nbCartesAPiocher; i++) {
            if (jeu.getPioche().estVide() && (jeu.getFaceCouronne() == Jeton.GRANDE_CRN || (jeu.getFaceCouronne() == Jeton.PETITE_CRN && jeu.getPositionPion(Plateau.PION_ROI) == Plateau.FONTAINE))) {
                if (jeu.getFaceCouronne() == Jeton.GRANDE_CRN)
                    couronne = true;
                indiceMelange = i;
                jeu.setFaceCouronne(Jeton.PETITE_CRN);
                copier(jeu.getDefausse(), defaussePasse);
                if (defausseFutur.estVide()) {
                    jeu.getDefausse().melanger();
                    copier(jeu.getDefausse(), defausseFutur);
                } else {
                    copier(defausseFutur, jeu.getDefausse());
                }
                while (!jeu.getDefausse().estVide())
                    jeu.getPioche().ajouter(jeu.getDefausse().piocher());
            }
            cartes[i] = jeu.getDefausse().piocher();
            jeu.getMain(joueur).ajouter(cartes[i]);
        }
        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(Type.TYPE_IND);
        jeu.alternerJoueurCourant();
    }

    void desexecuterFinTour() {
        if (joueur == Jeu.JOUEUR_VERT)
            jeu.setPositionCouronne(calculerDeplacementCouronne(), Plateau.VERS_ROUGE);
        else
            jeu.setPositionCouronne(calculerDeplacementCouronne(), Plateau.VERS_VERT);

        for (int i = nbCartesAPiocher-1; i >= 0; i--) {
            jeu.getPioche().ajouter(jeu.getMain(joueur).defausser(cartes[i]));
            if (i == indiceMelange) {
                if (couronne)
                   jeu.setFaceCouronne(Jeton.GRANDE_CRN);
                copier(defaussePasse, jeu.getDefausse());
                jeu.getPioche().vider();
            }
        }
        jeu.setTypeCourant(typePasse);
        jeu.alternerJoueurCourant();
    }

    int calculerDeplacementCouronne() {
        int deplacement = 0, inc = 0;
        for (int p = Plateau.PION_ROI; p <= Plateau.PION_FOU; p++) {
            if (jeu.pionEstDansChateau(joueur, p))
                deplacement++;
            if (p <= Plateau.PION_GRD_ROUGE && jeu.pionEstDansDuche(joueur, p))
                inc++;
        }
        if (inc == 3)
            deplacement++;
        return deplacement;
    }

    public Paquet copier(Paquet source, Paquet dest) {
        dest.vider();
        for (int i = 0; i < source.getTaille(); i++)
            dest.ajouter(source.getCarte(i));
        return dest;
    }

    @Override
    public Coup clone() {
        return null;
    }
}
