package Modele;

import java.io.Serializable;
import java.util.Arrays;

public class Coup implements Cloneable, Serializable {
    public static final int CHOISIR_CARTE = 0;
    public static final int CHOISIR_PION = 1;
    public static final int CHOISIR_DIRECTION = 2;
    public static final int ACTIVER_POUVOIR_SOR = 3;
    public static final int ACTIVER_POUVOIR_FOU = 4;
    public static final int FINIR_TOUR = 5;

    Jeu jeu;
    int joueur;

    int typeCoup;
    Carte carte;
    Pion pion;
    int direction;

    Type typePasse;
    int[] positionsPasse;

    boolean faceCouronnePasse;
    int indiceMelange;
    Paquet defaussePasse;
    Paquet defausseFutur;
    int activationPrivilegeRoiPasse;
    int nbCartesAPiocher;
    Carte[] cartesPiochees;
    Pion[] selectionPionsPasse;
    int[] selectionDirectionsPasse;

    public Coup(int typeCoup, Carte carte, Pion pion, int direction) {
        this.typeCoup = typeCoup;
        this.carte = carte;
        this.pion = pion;
        this.direction = direction;

        typePasse = Type.IND;
        positionsPasse = new int[2];

        nbCartesAPiocher = -1;
        indiceMelange = -1;
        defaussePasse = new Paquet(54, false);
        defausseFutur = new Paquet(54, false);
        activationPrivilegeRoiPasse = 0;
        cartesPiochees = new Carte[Jeu.TAILLE_MAIN];
        selectionPionsPasse = new Pion[2];
        selectionDirectionsPasse = new int[2];
    }

    public int getJoueur() {
        return joueur;
    }

    public int getTypeCoup() {
        return typeCoup;
    }

    public Carte getCarte() {
        return carte;
    }

    public Pion getPion() {
        return pion;
    }

    public int getDirection() {
        return direction;
    }

    public void fixerJeu(Jeu jeu) {
        this.jeu = jeu;
        this.joueur = jeu.getJoueurCourant();
    }

    public void executer() {
        switch (typeCoup) {
            case CHOISIR_CARTE:
                executerChoisirCarte();
                break;
            case CHOISIR_PION:
                executerChoisirPion();
                break;
            case CHOISIR_DIRECTION:
                executerChoisirDirection();
                break;
            case ACTIVER_POUVOIR_SOR:
                executerActiverPouvoirSorcier();
                break;
            case ACTIVER_POUVOIR_FOU:
                executerActiverPouvoirFou();
                break;
            case FINIR_TOUR:
                executerFinirTour();
                break;
            default:
                throw new RuntimeException("Modele.Coup.executer() : Coup invalide.");
        }
    }

    public void desexecuter() {
        switch (typeCoup) {
            case CHOISIR_CARTE:
                desexecuterChoisirCarte();
                break;
            case CHOISIR_PION:
                desexecuterChoisirPion();
                break;
            case CHOISIR_DIRECTION:
                desexecuterChoisirDirection();
                break;
            case ACTIVER_POUVOIR_SOR:
                desexecuterActiverPouvoirSorcier();
                break;
            case ACTIVER_POUVOIR_FOU:
                desexecuterActiverPouvoirFou();
                break;
            case FINIR_TOUR:
                desexecuterFinirTour();
                break;
            default:
                throw new RuntimeException("Modele.Coup.desexecuter() : Coup invalide.");
        }
    }

    private void executerChoisirCarte() {
        jeu.getSelectionCartes(joueur).inserer(jeu.getMain(joueur).extraire(carte));

        if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_DIRECTION) {
            /* cas du privilège du Roi */
            jeu.setActivationPrivilegeRoi(2);
        } else if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_CARTE) {
            if (jeu.getActivationPouvoirFou()) {
                /* cas du pouvoir du Fou */
                if (jeu.getTypeCourant().equals(Type.IND) || jeu.getTypeCourant().equals(Type.GAR)) {
                    /* cas où il faut choisir un pion (premier coup du tour ou déplacement des Gardes) */
                    jeu.setEtatJeu(Jeu.ETAT_CHOIX_PION);
                } else {
                    /* pion déjà choisi précédemment */
                    jeu.putSelectionPions(0, Pion.typeEnPion(jeu.getTypeCourant()));
                    if (carte.estDeplacementFouCentre())
                        executerDeplacement();
                    else
                        jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
                }
            } else {
                /* cas normal de sélection d'une carte */
                if (carte.estDeplacementGarCentre()) {
                    jeu.putSelectionPions(0, Pion.GAR_VRT);
                    jeu.putSelectionPions(1, Pion.GAR_RGE);
                    executerDeplacement();
                } else if (carte.getType().equals(Type.GAR)) {
                    jeu.setEtatJeu(Jeu.ETAT_CHOIX_PION);
                } else {
                    jeu.putSelectionPions(0, Pion.typeEnPion(carte.getType()));
                    if (carte.estDeplacementFouCentre()) {
                        executerDeplacement();
                    } else {
                        if (carte.getType().equals(Type.ROI))
                            jeu.setActivationPrivilegeRoi(1);
                        jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
                    }
                }
            }
        }
    }

    private void desexecuterChoisirCarte() {
        if (jeu.getActivationPrivilegeRoi() == 2) {
            /* cas du privilège du Roi */
            jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
            jeu.setActivationPrivilegeRoi(1);
        } else {
            if (jeu.getActivationPouvoirFou()) {
                /* cas du pouvoir du Fou */
                if (jeu.getEtatJeu() != Jeu.ETAT_CHOIX_PION) {
                    if (carte.estDeplacementFouCentre())
                        desexecuterDeplacement();
                    jeu.putSelectionPions(0, null);
                }
            } else {
                /* cas normal de sélection d'une carte */
                if (carte.estDeplacementGarCentre()) {
                    desexecuterDeplacement();
                    jeu.putSelectionPions(1, null);
                }
                if (carte.estDeplacementFouCentre())
                    desexecuterDeplacement();
                if (carte.getType().equals(Type.ROI))
                    jeu.setActivationPrivilegeRoi(0);
                jeu.putSelectionPions(0, null);
            }
            jeu.setEtatJeu(Jeu.ETAT_CHOIX_CARTE);
        }

        jeu.getMain(joueur).inserer(jeu.getSelectionCartes(joueur).extraire());
    }

    private void executerChoisirPion() {
        if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_DIRECTION) {
            /* cas de la sélection d'un deuxième pion Garde */
            jeu.putSelectionPions(1, pion);
        } else if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_PION) {
            jeu.putSelectionPions(0, pion);

            if (jeu.getActivationPouvoirSor()) {
                /* cas du pouvoir du Sorcier */
                positionsPasse[0] = jeu.getPlateau().getPositionPion(pion);
                jeu.getPlateau().setPositionPion(pion, jeu.getPlateau().getPositionPion(Pion.SOR));
                jeu.setTypeCourant(Type.FIN);
                jeu.setActivationPouvoirSor(false);
                jeu.setEtatJeu(jeu.getPlateau().estTerminee() ? Jeu.ETAT_FIN_DE_PARTIE : Jeu.ETAT_CHOIX_CARTE);
            } else {
                /* cas normal de sélection d'un pion */
                Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);
                if (jeu.getActivationPouvoirFou() && carte.estDeplacementFouCentre())
                    executerDeplacement();
                else
                    jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
            }
        }
    }

    private void desexecuterChoisirPion() {
        if (jeu.getSelectionPions(1) != null) {
            /* cas de la sélection d'un deuxième pion Garde */
            jeu.putSelectionPions(1, null);
        } else {
            if (jeu.getSelectionCartes(joueur).estVide()) {
                /* cas du pouvoir du Sorcier */
                jeu.setActivationPouvoirSor(true);
                jeu.setTypeCourant(Type.IND);
                jeu.getPlateau().setPositionPion(pion, positionsPasse[0]);
            } else {
                /* cas normal de sélection d'un pion */
                Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);
                if (jeu.getActivationPouvoirFou() && carte.estDeplacementFouCentre())
                    desexecuterDeplacement();
            }

            jeu.putSelectionPions(0, null);
            jeu.setEtatJeu(Jeu.ETAT_CHOIX_PION);
        }
    }

    private void executerChoisirDirection() {
        Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        if (jeu.getActivationPrivilegeRoi() == 2) {
            /* cas du privilège du Roi */
            jeu.getPlateau().setPositionPion(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) + direction);
            jeu.getPlateau().setPositionPion(Pion.ROI, jeu.getPlateau().getPositionPion(Pion.ROI) + direction);
            jeu.getPlateau().setPositionPion(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + direction);

            executerChangerTypeCourant();
            jeu.putSelectionPions(0, null);
            jeu.setEtatJeu(jeu.getPlateau().estTerminee() ? Jeu.ETAT_FIN_DE_PARTIE : Jeu.ETAT_CHOIX_CARTE);
        } else if (carte.estDeplacementGar1Plus1() && jeu.getSelectionPions(1) != null) {
            /* cas de sélection d'une direction dans le cas d'une carte G2 précédemment sélectionnée avec les deux pions Gardes sélectionnés */
            if (jeu.getSelectionDirections(0) == Plateau.DIRECTION_IND) {
                jeu.putSelectionDirections(0, direction);
            } else if (jeu.getSelectionDirections(1) == Plateau.DIRECTION_IND) {
                jeu.putSelectionDirections(1, direction);
                executerDeplacement();
            }
        } else {
            /* cas normal de sélection d'une direction */
            jeu.putSelectionDirections(0, direction);
            executerDeplacement();
        }

        activationPrivilegeRoiPasse = jeu.getActivationPrivilegeRoi();
        jeu.setActivationPrivilegeRoi(0);
    }

    private void desexecuterChoisirDirection() {
        Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        if (activationPrivilegeRoiPasse == 2) {
            /* cas du privilège du Roi */
            jeu.putSelectionPions(0, Pion.ROI);
            desexecuterChangerTypeCourant();
            jeu.getPlateau().setPositionPion(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - direction);
            jeu.getPlateau().setPositionPion(Pion.ROI, jeu.getPlateau().getPositionPion(Pion.ROI) - direction);
            jeu.getPlateau().setPositionPion(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - direction);
        } else if (carte.estDeplacementGar1Plus1() && (selectionPionsPasse[1] != null || jeu.getSelectionPions(1) != null)) {
            /* cas de sélection d'une direction dans le cas d'une carte G2 précédemment sélectionnée avec les deux pions Gardes sélectionnés */
            if (selectionDirectionsPasse[1] != Plateau.DIRECTION_IND) {
                desexecuterDeplacement();
                jeu.putSelectionDirections(1, Plateau.DIRECTION_IND);
            } else if (jeu.getSelectionDirections(0) != Plateau.DIRECTION_IND) {
                jeu.putSelectionDirections(0, Plateau.DIRECTION_IND);
            }
        } else {
            /* cas normal de sélection d'une direction */
            desexecuterDeplacement();
            jeu.putSelectionDirections(0, Plateau.DIRECTION_IND);
        }

        jeu.setActivationPrivilegeRoi(activationPrivilegeRoiPasse);
        jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
    }

    private void executerActiverPouvoirSorcier() {
        jeu.setActivationPouvoirSor(true);
        jeu.setEtatJeu(Jeu.ETAT_CHOIX_PION);
    }

    private void desexecuterActiverPouvoirSorcier() {
        jeu.setEtatJeu(Jeu.ETAT_CHOIX_CARTE);
        jeu.setActivationPouvoirSor(false);
    }

    private void executerActiverPouvoirFou() {
        jeu.setActivationPouvoirFou(true);
    }

    private void desexecuterActiverPouvoirFou() {
        jeu.setActivationPouvoirFou(false);
    }

    private void executerFinirTour() {
        boolean finPartie = false;

        /* sauvegarder et vider les sélections */
        executerViderSelections();

        activationPrivilegeRoiPasse = jeu.getActivationPrivilegeRoi();
        jeu.setActivationPrivilegeRoi(0);
        jeu.setActivationPouvoirFou(false);

        nbCartesAPiocher = jeu.getSelectionCartes(joueur).getTaille();
        for (int i = 0; i < nbCartesAPiocher; i++)
            jeu.getDefausse().inserer(jeu.getSelectionCartes(joueur).extraire());

        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(Type.IND);

        /* déplacer la couronne et vérifier si la partie est finie */
        jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + (joueur == Jeu.JOUEUR_VRT ? Plateau.DIRECTION_VRT : Plateau.DIRECTION_RGE) * jeu.evaluerDeplacementCouronne(joueur));
        if (jeu.getPlateau().estTerminee())
            jeu.setEtatJeu(Jeu.ETAT_FIN_DE_PARTIE);

        /* piocher */
        for (int i = 0; i < nbCartesAPiocher; i++) {
            cartesPiochees[i] = jeu.getPioche().extraire();
            jeu.getMain(joueur).inserer(cartesPiochees[i]);
            if (jeu.getPioche().estVide()) {
                /* cas où la pioche est vide */
                indiceMelange = i;

                /* cas où la partie est finie */
                if (jeu.getPlateau().getFaceCouronne() == Plateau.FACE_PTT_CRN && !jeu.pionDansFontaine(Pion.ROI))
                    jeu.setEtatJeu(Jeu.ETAT_FIN_DE_PARTIE);

                /* re-remplir la pioche avec la défausse */
                faceCouronnePasse = jeu.getPlateau().getFaceCouronne();
                jeu.getPlateau().setFaceCouronne(Plateau.FACE_PTT_CRN);

                defaussePasse.copier(jeu.getDefausse());
                if (defausseFutur.estVide()) {
                    jeu.getDefausse().melanger();
                    defausseFutur.copier(jeu.getDefausse());
                } else {
                    jeu.getDefausse().vider();
                    jeu.getDefausse().copier(defausseFutur);
                }
                jeu.getPioche().transferer(jeu.getDefausse());
            }
        }

        /* changer le joueur */
        jeu.alternerJoueurCourant();
    }

    private void desexecuterFinirTour() {
        /* changer le joueur */
        jeu.alternerJoueurCourant();

        /* dépiocher */
        for (int i = nbCartesAPiocher - 1; i >= 0; i--) {
            if (i == indiceMelange && !jeu.estTerminee()) {
                jeu.getDefausse().copier(defaussePasse);
                jeu.getPioche().vider();
                jeu.getPlateau().setFaceCouronne(faceCouronnePasse);
            }
            jeu.getPioche().inserer(jeu.getMain(joueur).extraire(cartesPiochees[i]));
        }

        /* déplacer la couronne */
        jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + (joueur == Jeu.JOUEUR_VRT ? Plateau.DIRECTION_RGE : Plateau.DIRECTION_VRT) * jeu.evaluerDeplacementCouronne(joueur));

        /* charger et reremplir les sélections */
        jeu.setTypeCourant(typePasse);

        for (int i = nbCartesAPiocher - 1; i >= 0; i--)
            jeu.getSelectionCartes(joueur).inserer(jeu.getDefausse().extraire());

        if (!jeu.getSelectionCartes(joueur).estVide() &&
                jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1).getType().equals(Type.FOU) &&
                !typePasse.equals(Type.FOU))
            jeu.setActivationPouvoirFou(true);
        jeu.setActivationPrivilegeRoi(activationPrivilegeRoiPasse);

        desexecuterViderSelections();

        jeu.setEtatJeu(Jeu.ETAT_CHOIX_CARTE);
    }

    private void executerDeplacement() {
        Carte carte = this.carte != null ? this.carte : jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        positionsPasse[0] = jeu.getPlateau().getPositionPion(jeu.getSelectionPions(0));
        if (carte.estDeplacementFouCentre()) {
            jeu.getPlateau().setPositionPion(jeu.getSelectionPions(0), Plateau.FONTAINE);
        } else if (carte.estDeplacementGarCentre()) {
            jeu.getPlateau().setPositionPion(jeu.getSelectionPions(0), jeu.getPlateau().getPositionPion(Pion.ROI) + Plateau.DIRECTION_VRT);
            positionsPasse[1] = jeu.getPlateau().getPositionPion(jeu.getSelectionPions(1));
            jeu.getPlateau().setPositionPion(jeu.getSelectionPions(1), jeu.getPlateau().getPositionPion(Pion.ROI) + Plateau.DIRECTION_RGE);
        } else if (carte.estDeplacementGar1Plus1() && jeu.getSelectionPions(1) != null) {
            positionsPasse[1] = jeu.getPlateau().getPositionPion(jeu.getSelectionPions(1));
            int positionFutur0 = jeu.getPlateau().getPositionPion(jeu.getSelectionPions(0)) + jeu.getSelectionDirections(0);
            int positionFutur1 = jeu.getPlateau().getPositionPion(jeu.getSelectionPions(1)) + jeu.getSelectionDirections(1);
            jeu.getPlateau().setPositionPion(jeu.getSelectionPions(0), positionFutur0);
            jeu.getPlateau().setPositionPion(jeu.getSelectionPions(1), positionFutur1);
        } else {
            int positionPion = jeu.getPlateau().getPositionPion(jeu.getSelectionPions(0)) + jeu.getSelectionDirections(0) * carte.getDeplacement();
            jeu.getPlateau().setPositionPion(jeu.getSelectionPions(0), positionPion);
        }

        executerChangerTypeCourant();
        executerViderSelections();

        jeu.setEtatJeu(jeu.getPlateau().estTerminee() ? Jeu.ETAT_FIN_DE_PARTIE : Jeu.ETAT_CHOIX_CARTE);
    }

    private void desexecuterDeplacement() {
        Carte carte = this.carte != null ? this.carte : jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        desexecuterViderSelections();
        desexecuterChangerTypeCourant();

        if (carte.estDeplacementGarCentre() || (carte.estDeplacementGar1Plus1() && jeu.getSelectionPions(1) != null))
            jeu.getPlateau().setPositionPion(jeu.getSelectionPions(1), positionsPasse[1]);
        jeu.getPlateau().setPositionPion(jeu.getSelectionPions(0), positionsPasse[0]);

        jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
    }

    private void executerChangerTypeCourant() {
        Carte carte = this.carte != null ? this.carte : jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        typePasse = jeu.getTypeCourant();
        if (jeu.getActivationPouvoirFou() && jeu.getTypeCourant().equals(Type.IND))
            jeu.setTypeCourant(jeu.getSelectionPions(0).getType());
        else if (!jeu.getActivationPouvoirFou() && jeu.getTypeCourant().equals(Type.IND))
            jeu.setTypeCourant(carte.getType());
    }

    private void desexecuterChangerTypeCourant() {
        jeu.setTypeCourant(typePasse);
    }

    private void executerViderSelections() {
        selectionPionsPasse[0] = jeu.getSelectionPions(0);
        selectionPionsPasse[1] = jeu.getSelectionPions(1);
        selectionDirectionsPasse[0] = jeu.getSelectionDirections(0);
        selectionDirectionsPasse[1] = jeu.getSelectionDirections(1);
        jeu.putSelectionDirections(1, Plateau.DIRECTION_IND);
        jeu.putSelectionDirections(0, Plateau.DIRECTION_IND);
        jeu.putSelectionPions(1, null);
        jeu.putSelectionPions(0, null);
    }

    private void desexecuterViderSelections() {
        jeu.putSelectionPions(0, selectionPionsPasse[0]);
        jeu.putSelectionPions(1, selectionPionsPasse[1]);
        jeu.putSelectionDirections(0, selectionDirectionsPasse[0]);
        jeu.putSelectionDirections(1, selectionDirectionsPasse[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Coup coup = (Coup) o;

        return jeu.equals(coup.jeu) &&
                joueur == coup.joueur &&
                typeCoup == coup.typeCoup &&
                carte.equals(coup.carte) &&
                pion.equals(coup.pion) &&
                direction == coup.direction;
    }

    @Override
    public Coup clone() {
        try {
            Coup resultat = (Coup) super.clone();
            resultat.jeu = jeu.clone();
            resultat.joueur = joueur;
            resultat.typeCoup = typeCoup;
            resultat.carte = carte.clone();
            resultat.pion = pion.clone();
            resultat.direction = direction;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Coup.clone() : Coup non clonable.");
        }
    }

    @Override
    public String toString() {
        switch (typeCoup) {
            case CHOISIR_CARTE:
                return carte.toString();
            case CHOISIR_PION:
                return pion.toString();
            case CHOISIR_DIRECTION:
                return direction == Plateau.DIRECTION_VRT ? "V" : "R";
            case ACTIVER_POUVOIR_SOR:
                return "Sor";
            case ACTIVER_POUVOIR_FOU:
                return "Fou";
            case FINIR_TOUR:
                return "Fin";
            default:
                throw new RuntimeException("Modele.Coup.toString() : type de coup invalide.");
        }
    }
}
