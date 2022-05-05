package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Coup implements Cloneable {
    public static final int COUP_DEPLACEMENT = 0;
    public static final int COUP_PRIVILEGE_ROI = 1;
    public static final int COUP_POUVOIR_SRC = 2;
    public static final int COUP_POUVOIR_FOU = 3;
    public static final int COUP_FIN_TOUR = 4;

    Jeu jeu;
    int joueur;
    int typeCoup;

    int typePasse;
    int[] pions;
    int[] deplacements;
    int[] directions;
    int[] positionPasse;
    int nbCartesAPiocher;
    Carte[] cartes;
    Paquet defaussePasse;
    Paquet defausseFutur;
    int indiceMelange;
    boolean faceCouronnePasse;

    public Coup(int joueur, int typeCoup, Carte[] cartes, int[] pions, int[] deplacements, int[] directions) {
        this.joueur = joueur;
        this.typeCoup = typeCoup;
        this.pions = pions;
        this.deplacements = deplacements;
        this.directions = directions;
        positionPasse = new int[2];
        nbCartesAPiocher = -1;
        if (typeCoup == COUP_FIN_TOUR)
            this.cartes = new Carte[8];
        else
            this.cartes = cartes;
        defaussePasse = new Paquet(Paquet.ORDONNE);
        defausseFutur = new Paquet(Paquet.ORDONNE);
        indiceMelange = -1;
        faceCouronnePasse = false;
    }

    public void fixerJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public void executer() {
        switch (typeCoup) {
            case COUP_DEPLACEMENT:
                executerDeplacement();
                break;
            case COUP_PRIVILEGE_ROI:
                executerPrivilegeRoi();
                break;
            case COUP_POUVOIR_SRC:
                executerPouvoirSorcier();
                break;
            case COUP_POUVOIR_FOU:
                executerPouvoirFou();
                break;
            case COUP_FIN_TOUR:
                executerFinTour();
                break;
            default:
                System.err.println("Coup impossible !");
                break;
        }
    }

    public void desexecuter() {
        switch (typeCoup) {
            case COUP_DEPLACEMENT:
                desexecuterDeplacement();
                break;
            case COUP_PRIVILEGE_ROI:
                desexecuterPrivilegeRoi();
                break;
            case COUP_POUVOIR_SRC:
                desexecuterPouvoirSorcier();
                break;
            case COUP_POUVOIR_FOU:
                desexecuterPouvoirFou();
                break;
            case COUP_FIN_TOUR:
                desexecuterFinTour();
                break;
            default:
                System.err.println("Coup impossible !");
                break;
        }
    }

    void executerDeplacement() {
        jeu.getDefausse().ajouter(jeu.getMain(joueur).defausser(cartes[0]));

        if (cartes[0].getType() == Type.TYPE_FOU && cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE) {
            positionPasse[0] = jeu.getPositionPion(pions[0]);
            jeu.setPositionPion(pions[0], Plateau.FONTAINE);
        } else if (cartes[0].getType() == Type.TYPE_GRD && cartes[0].getDeplacement() == Carte.DEPLACEMENT_GRD_CENTRE) {
            for (int i = 0; i < pions.length; i++) {
                positionPasse[i] = jeu.getPositionPion(pions[i]);
                jeu.setPositionPion(pions[i], Plateau.FONTAINE);
            }
        } else {
            for (int i = 0; i < pions.length; i++)
                jeu.setPositionPion(pions[i], deplacements[i], directions[i]);
        }

        typePasse = jeu.getTypeCourant();
        if (jeu.getNombreTypeCarte(jeu.getMain(joueur), cartes[0].getType()) == 0)
            jeu.setTypeCourant(Type.TYPE_FIN);
        else
            jeu.setTypeCourant(cartes[0].getType());
    }

    void desexecuterDeplacement() {
        jeu.setTypeCourant(typePasse);

        if (cartes[0].getType() == Type.TYPE_FOU && cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE) {
            jeu.setPositionPion(pions[0], positionPasse[0]);
        } else if (cartes[0].getType() == Type.TYPE_GRD && cartes[0].getDeplacement() == Carte.DEPLACEMENT_GRD_CENTRE) {
            for (int i = pions.length-1; i >= 0; i++)
                jeu.setPositionPion(pions[i], positionPasse[i]);
        } else {
            for (int i = pions.length-1; i >= 0; i--)
                jeu.setPositionPion(pions[i], deplacements[i], -directions[i]);
        }

        jeu.getMain(joueur).ajouter(jeu.getDefausse().piocher());
    }

    void executerPrivilegeRoi() {
        jeu.getDefausse().ajouter(jeu.getMain(joueur).defausser(cartes[0]));
        jeu.getDefausse().ajouter(jeu.getMain(joueur).defausser(cartes[1]));

        jeu.setPositionPion(Plateau.PION_GRD_VERT, 1, directions[0]);
        jeu.setPositionPion(Plateau.PION_ROI, 1, directions[0]);
        jeu.setPositionPion(Plateau.PION_GRD_ROUGE, 1, directions[0]);

        typePasse = jeu.getTypeCourant();
        if (jeu.getNombreTypeCarte(jeu.getMain(joueur), cartes[0].getType()) == 0)
            jeu.setTypeCourant(Type.TYPE_FIN);
        else
            jeu.setTypeCourant(cartes[0].getType());
    }

    void desexecuterPrivilegeRoi() {
        jeu.setTypeCourant(typePasse);

        jeu.setPositionPion(Plateau.PION_GRD_ROUGE, 1, -directions[0]);
        jeu.setPositionPion(Plateau.PION_ROI, 1, -directions[0]);
        jeu.setPositionPion(Plateau.PION_GRD_VERT, 1, -directions[0]);

        jeu.getMain(joueur).ajouter(jeu.getDefausse().piocher());
        jeu.getMain(joueur).ajouter(jeu.getDefausse().piocher());
    }

    void executerPouvoirSorcier() {
        positionPasse[0] = jeu.getPositionPion(pions[0]);
        jeu.setPositionPion(pions[0], jeu.getPositionPion(Plateau.PION_SRC));

        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(Type.TYPE_FIN);
    }

    void desexecuterPouvoirSorcier() {
        jeu.setTypeCourant(typePasse);
        jeu.setPositionPion(pions[0], positionPasse[0]);
    }

    void executerPouvoirFou() {
        jeu.getDefausse().ajouter(jeu.getMain(joueur).defausser(cartes[0]));

        if (cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE) {
            positionPasse[0] = jeu.getPositionPion(pions[0]);
            jeu.setPositionPion(pions[0], Plateau.FONTAINE);
        } else {
            jeu.setPositionPion(pions[0], deplacements[0], directions[0]);
        }

        typePasse = jeu.getTypeCourant();
        if (jeu.getNombreTypeCarte(jeu.getMain(joueur), Type.TYPE_FOU) == 0)
            jeu.setTypeCourant(Type.TYPE_FIN);
        else
            jeu.setTypeCourant(jeu.getTypePion(pions[0]));
    }

    void desexecuterPouvoirFou() {
        jeu.setTypeCourant(typePasse);

        if (cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE) {
            jeu.setPositionPion(pions[0], positionPasse[0]);
        }
        else {
            jeu.setPositionPion(pions[0], deplacements[0], -directions[0]);
        }

        jeu.getMain(joueur).ajouter(jeu.getDefausse().piocher());
    }

    void executerFinTour() {
        if (nbCartesAPiocher == -1)
            nbCartesAPiocher = Jeu.TAILLE_MAIN - jeu.getMain(joueur).getTaille();

        if (joueur == Jeu.JOUEUR_VERT)
            jeu.setPositionCouronne(jeu.getDeplacementCouronne(joueur), Plateau.VERS_VERT);
        else if (joueur == Jeu.JOUEUR_ROUGE)
            jeu.setPositionCouronne(jeu.getDeplacementCouronne(joueur), Plateau.VERS_ROUGE);
        else
            throw new RuntimeException("Joueur inexistant");

        for (int i = 0; i < nbCartesAPiocher; i++) {
            if (jeu.getPioche().estVide() && (jeu.getFaceCouronne() == Jeton.GRANDE_CRN || (jeu.getFaceCouronne() == Jeton.PETITE_CRN && jeu.getPositionPion(Plateau.PION_ROI) == Plateau.FONTAINE))) {
                if (jeu.getFaceCouronne() == Jeton.GRANDE_CRN)
                    faceCouronnePasse = true;
                jeu.setFaceCouronne(Jeton.PETITE_CRN);
                indiceMelange = i;
                defaussePasse.copier(jeu.getDefausse());
                if (defausseFutur.estVide()) {
                    jeu.getDefausse().melanger();
                    defausseFutur.copier(jeu.getDefausse());
                } else {
                    jeu.getDefausse().copier(defausseFutur);
                }
                jeu.getPioche().transferer(jeu.getDefausse());
            }
            cartes[i] = jeu.getPioche().piocher();
            jeu.getMain(joueur).ajouter(cartes[i]);
        }

        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(Type.TYPE_IND);
        jeu.alternerJoueurCourant();
    }

    void desexecuterFinTour() {
        jeu.alternerJoueurCourant();
        jeu.setTypeCourant(typePasse);

        for (int i = nbCartesAPiocher-1; i >= 0; i--) {
            jeu.getPioche().ajouter(jeu.getMain(joueur).defausser(cartes[i]));
            if (i == indiceMelange) {
                if (faceCouronnePasse) {
                    jeu.setFaceCouronne(Jeton.GRANDE_CRN);
                }
                jeu.getDefausse().copier(defaussePasse);
                jeu.getPioche().vider();
            }
        }

        if (joueur == Jeu.JOUEUR_VERT)
            jeu.setPositionCouronne(jeu.getDeplacementCouronne(joueur), Plateau.VERS_ROUGE);
        else if (joueur == Jeu.JOUEUR_ROUGE)
            jeu.setPositionCouronne(jeu.getDeplacementCouronne(joueur), Plateau.VERS_VERT);
        else
            throw new RuntimeException("Joueur inexistant");

    }

    @Override
    public Coup clone() {
        return null;
    }
}
