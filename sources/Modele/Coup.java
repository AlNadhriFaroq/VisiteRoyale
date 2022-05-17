package Modele;

public class Coup implements Cloneable {
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
    Pion[] pionsPasse;
    int[] positionsPasse;

    int activationPrivilegeRoiPasse;
    int nbCartesAPiocher;
    Paquet defaussePasse;
    Paquet defausseFutur;
    Carte[] cartesPiochees;
    int indiceMelange;
    boolean faceCouronnePasse;
    Pion[] selectionPionsPasse;
    int[] selectionDirectionsPasse;

    public Coup(int joueur, int typeCoup, Carte carte, Pion pion, int direction) {
        this.joueur = joueur;

        this.typeCoup = typeCoup;
        this.carte = carte;
        this.pion = pion;
        this.direction = direction;

        activationPrivilegeRoiPasse = 0;
        typePasse = Type.IND;
        pionsPasse = new Pion[2];
        positionsPasse = new int[2];
        nbCartesAPiocher = -1;
        defaussePasse = new Paquet(54);
        defausseFutur = new Paquet(54);
        cartesPiochees = new Carte[Jeu.TAILLE_MAIN];
        indiceMelange = -1;
        selectionPionsPasse = new Pion[2];
        selectionDirectionsPasse = new int[2];
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
            jeu.setActivationPrivilegeRoi(2);
        } else if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_CARTE) {
            if (jeu.getActivationPouvoirFou()) {
                if (jeu.getTypeCourant().equals(Type.IND) || jeu.getTypeCourant().equals(Type.GAR))
                    jeu.setEtatJeu(Jeu.ETAT_CHOIX_PION);
                else if (carte.estDeplacementFouCentre())
                    executerDeplacement();
                else
                    jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
            } else {
                if (carte.estDeplacementFouCentre() || carte.estDeplacementGarCentre())
                    executerDeplacement();
                else if (carte.getType().equals(Type.GAR)) {
                    jeu.setEtatJeu(Jeu.ETAT_CHOIX_PION);
                } else {
                    jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
                    if (carte.getType().equals(Type.ROI))
                        jeu.setActivationPrivilegeRoi(1);
                }
            }
        }
    }

    private void desexecuterChoisirCarte() {
        if (jeu.getActivationPrivilegeRoi() == 2) {
            jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
            jeu.setActivationPrivilegeRoi(1);
        } else {
            if (jeu.getActivationPouvoirFou()) {
                jeu.setEtatJeu(Jeu.ETAT_CHOIX_CARTE);
                if (carte.estDeplacementFouCentre() && !typePasse.equals(Type.IND) && !typePasse.equals(Type.GAR))
                    desexecuterDeplacement();
            } else {
                jeu.setEtatJeu(Jeu.ETAT_CHOIX_CARTE);
                if (carte.estDeplacementFouCentre() || carte.estDeplacementGarCentre())
                    desexecuterDeplacement();
                else if (carte.getType().equals(Type.ROI))
                    jeu.setActivationPrivilegeRoi(0);
            }
        }

        jeu.getMain(joueur).inserer(jeu.getSelectionCartes(joueur).extraire(carte), true);
        jeu.getMain(joueur).trier();
    }

    private void executerChoisirPion() {
        if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_DIRECTION) {
            jeu.putSelectionPions(1, pion);
        } else if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_PION) {
            jeu.putSelectionPions(0, pion);

            if (jeu.getActivationPouvoirSor()) {
                positionsPasse[0] = jeu.getPlateau().getPositionPion(pion);
                jeu.getPlateau().setPositionPion(pion, jeu.getPlateau().getPositionPion(Pion.SOR));
                typePasse = jeu.getTypeCourant();
                jeu.setTypeCourant(Type.FIN);

                jeu.setActivationPouvoirSor(false);

                jeu.setEtatJeu(jeu.getPlateau().estTerminee() ? Jeu.ETAT_FIN_DE_PARTIE : Jeu.ETAT_CHOIX_CARTE);
            } else {
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
            jeu.putSelectionPions(1, null);
            jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
        } else {
            jeu.setEtatJeu(Jeu.ETAT_CHOIX_PION);

            if (jeu.getSelectionCartes(joueur).estVide()) {
                jeu.setActivationPouvoirSor(true);
                jeu.setTypeCourant(typePasse);
                jeu.getPlateau().setPositionPion(pion, positionsPasse[0]);
            } else {
                Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);
                if (jeu.getActivationPouvoirFou() && carte.estDeplacementFouCentre())
                    desexecuterDeplacement();
            }

            jeu.putSelectionPions(0, null);
        }
    }

    private void executerChoisirDirection() {
        Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        if (jeu.getActivationPrivilegeRoi() == 2) {
            activationPrivilegeRoiPasse = jeu.getActivationPrivilegeRoi();

            jeu.getPlateau().setPositionPion(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) + direction);
            jeu.getPlateau().setPositionPion(Pion.ROI, jeu.getPlateau().getPositionPion(Pion.ROI) + direction);
            jeu.getPlateau().setPositionPion(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + direction);

            executerChangerTypeCourant();
            jeu.setActivationPrivilegeRoi(0);

            jeu.setEtatJeu(jeu.getPlateau().estTerminee() ? Jeu.ETAT_FIN_DE_PARTIE : Jeu.ETAT_CHOIX_CARTE);
        } else if (carte.estDeplacementGar1Plus1() && jeu.getSelectionPions(1) != null && jeu.getSelectionDirections(0) == Plateau.DIRECTION_IND) {
            jeu.putSelectionDirections(0, direction);
        } else if (carte.estDeplacementGar1Plus1() && jeu.getSelectionPions(1) != null && jeu.getSelectionDirections(1) == Plateau.DIRECTION_IND) {
            jeu.putSelectionDirections(1, direction);
            executerDeplacement();
        } else {
            jeu.putSelectionDirections(0, direction);
            executerDeplacement();
        }
    }

    private void desexecuterChoisirDirection() {
        jeu.setEtatJeu(Jeu.ETAT_CHOIX_DIRECTION);
        Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        if (activationPrivilegeRoiPasse == 2) {
            jeu.setActivationPrivilegeRoi(activationPrivilegeRoiPasse);
            desexecuterChangerTypeCourant();
            jeu.getPlateau().setPositionPion(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - direction);
            jeu.getPlateau().setPositionPion(Pion.ROI, jeu.getPlateau().getPositionPion(Pion.ROI) - direction);
            jeu.getPlateau().setPositionPion(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - direction);
        } else if (carte.estDeplacementGar1Plus1() && selectionPionsPasse[1] != null && selectionDirectionsPasse[1] != Plateau.DIRECTION_IND) {
            desexecuterDeplacement();
            jeu.putSelectionDirections(1, Plateau.DIRECTION_IND);
        } else if (carte.estDeplacementGar1Plus1() && jeu.getSelectionPions(1) != null && jeu.getSelectionDirections(0) != Plateau.DIRECTION_IND) {
            jeu.putSelectionDirections(0, Plateau.DIRECTION_IND);
        } else {
            desexecuterDeplacement();
            jeu.putSelectionDirections(0, Plateau.DIRECTION_IND);
        }
    }

    private void executerDeplacement() {
        Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        Pion pion = null;
        if ((jeu.getTypeCourant().equals(Type.IND) && carte.getType().equals(Type.GAR)) ||
                jeu.getTypeCourant().equals(Type.GAR) || carte.estDeplacementGar1Plus1() ||
                (jeu.getActivationPouvoirFou() && jeu.getTypeCourant().equals(Type.IND)))
            pion = jeu.getSelectionPions(0);
        else if (jeu.getActivationPouvoirFou())
            pion = Pion.typeEnPion(jeu.getTypeCourant());
        else if (!carte.estDeplacementGarCentre() && !carte.estDeplacementGar1Plus1())
            pion = Pion.typeEnPion(carte.getType());
        pionsPasse[0] = pion;

        if (carte.estDeplacementFouCentre()) {
            positionsPasse[0] = jeu.getPlateau().getPositionPion(pion);
            jeu.getPlateau().setPositionPion(pion, Plateau.FONTAINE);
        } else if (carte.estDeplacementGarCentre()) {
            positionsPasse[0] = jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
            jeu.getPlateau().setPositionPion(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.ROI) + Plateau.DIRECTION_VRT);
            positionsPasse[1] = jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
            jeu.getPlateau().setPositionPion(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.ROI) + Plateau.DIRECTION_RGE);
        } else if (carte.estDeplacementGar1Plus1() && jeu.getSelectionPions(1) != null) {
            pionsPasse[0] = jeu.getSelectionPions(0);
            positionsPasse[0] = jeu.getPlateau().getPositionPion(pionsPasse[0]);
            int positionPion0 = jeu.getPlateau().getPositionPion(pionsPasse[0]) + jeu.getSelectionDirections(0);
            jeu.getPlateau().setPositionPion(jeu.getSelectionPions(0), positionPion0);
            pionsPasse[1] = jeu.getSelectionPions(1);
            positionsPasse[1] = jeu.getPlateau().getPositionPion(pionsPasse[1]);
            int positionPion1 = jeu.getPlateau().getPositionPion(pionsPasse[1]) + jeu.getSelectionDirections(1);
            jeu.getPlateau().setPositionPion(jeu.getSelectionPions(1), positionPion1);
        } else {
            positionsPasse[0] = jeu.getPlateau().getPositionPion(pion);
            int positionPion = jeu.getPlateau().getPositionPion(pion) + jeu.getSelectionDirections(0) * carte.getDeplacement();
            jeu.getPlateau().setPositionPion(pion, positionPion);
        }

        executerChangerTypeCourant();

        selectionPionsPasse[0] = jeu.getSelectionPions(0);
        selectionPionsPasse[1] = jeu.getSelectionPions(1);
        selectionDirectionsPasse[0] = jeu.getSelectionDirections(0);
        selectionDirectionsPasse[1] = jeu.getSelectionDirections(1);
        jeu.putSelectionPions(0, null);
        jeu.putSelectionPions(1, null);
        jeu.putSelectionDirections(0, Plateau.DIRECTION_IND);
        jeu.putSelectionDirections(1, Plateau.DIRECTION_IND);

        jeu.setEtatJeu(jeu.getPlateau().estTerminee() ? Jeu.ETAT_FIN_DE_PARTIE : Jeu.ETAT_CHOIX_CARTE);
    }

    private void desexecuterDeplacement() {
        Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        desexecuterChangerTypeCourant();

        jeu.putSelectionDirections(1, selectionDirectionsPasse[1]);
        jeu.putSelectionDirections(0, selectionDirectionsPasse[0]);
        jeu.putSelectionPions(1, selectionPionsPasse[1]);
        jeu.putSelectionPions(0, selectionPionsPasse[0]);

        if (carte.estDeplacementFouCentre()) {
            jeu.getPlateau().setPositionPion(pionsPasse[0], positionsPasse[0]);
        } else if (carte.estDeplacementGarCentre()) {
            jeu.getPlateau().setPositionPion(Pion.GAR_RGE, positionsPasse[1]);
            jeu.getPlateau().setPositionPion(Pion.GAR_VRT, positionsPasse[0]);
        } else if (carte.estDeplacementGar1Plus1() && jeu.getSelectionPions(1) != null) {
            jeu.getPlateau().setPositionPion(pionsPasse[1], positionsPasse[1]);
            jeu.getPlateau().setPositionPion(pionsPasse[0], positionsPasse[0]);
        } else {
            jeu.getPlateau().setPositionPion(pionsPasse[0], positionsPasse[0]);
        }
    }

    private void executerChangerTypeCourant() {
        typePasse = jeu.getTypeCourant();

        Carte carte = jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1);

        if (jeu.getActivationPouvoirFou() && jeu.getTypeCourant().equals(Type.IND))
            jeu.setTypeCourant(jeu.getSelectionPions(0).getType());
        else if (!jeu.getActivationPouvoirFou() && jeu.getTypeCourant().equals(Type.IND))
            jeu.setTypeCourant(carte.getType());
    }

    private void desexecuterChangerTypeCourant() {
        jeu.setTypeCourant(typePasse);
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

        selectionPionsPasse[0] = jeu.getSelectionPions(0);
        selectionPionsPasse[1] = jeu.getSelectionPions(1);
        selectionDirectionsPasse[0] = jeu.getSelectionDirections(0);
        selectionDirectionsPasse[1] = jeu.getSelectionDirections(1);
        jeu.putSelectionPions(0, null);
        jeu.putSelectionPions(1, null);
        jeu.putSelectionDirections(0, Plateau.DIRECTION_IND);
        jeu.putSelectionDirections(1, Plateau.DIRECTION_IND);

        jeu.setActivationPouvoirFou(false);
        activationPrivilegeRoiPasse = jeu.getActivationPrivilegeRoi();
        jeu.setActivationPrivilegeRoi(0);

        if (joueur == Jeu.JOUEUR_VRT)
            jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + Plateau.DIRECTION_VRT * jeu.evaluerDeplacementCouronne(joueur));
        else if (joueur == Jeu.JOUEUR_RGE)
            jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + Plateau.DIRECTION_RGE * jeu.evaluerDeplacementCouronne(joueur));
        else
            throw new RuntimeException("Modele.Coup.executerFinTour() : Joueur entré invalide.");

        nbCartesAPiocher = jeu.getSelectionCartes(joueur).getTaille();

        for (int i = 0; i < nbCartesAPiocher; i++)
            jeu.getDefausse().inserer(jeu.getSelectionCartes(joueur).extraire(jeu.getSelectionCartes(joueur).getCarte(0)));

        for (int i = 0; i < nbCartesAPiocher; i++) {
            cartesPiochees[i] = jeu.getPioche().extraire();
            jeu.getMain(joueur).inserer(cartesPiochees[i], true);
            if ((jeu.getPioche().estVide() && jeu.getPlateau().getFaceCouronne() == Plateau.FACE_PTT_CRN && !jeu.pionDansFontaine(Pion.ROI))) {
                finPartie = true;
                break;
            } else if (jeu.getPioche().estVide()) {
                faceCouronnePasse = jeu.getPlateau().getFaceCouronne();
                jeu.getPlateau().setFaceCouronne(Plateau.FACE_PTT_CRN);

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

        }
        jeu.getMain(joueur).trier();

        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(Type.IND);

        if (finPartie || jeu.getPlateau().estTerminee())
            jeu.setEtatJeu(Jeu.ETAT_FIN_DE_PARTIE);
        else
            jeu.alternerJoueurCourant();
    }

    private void desexecuterFinirTour() {
        if (jeu.getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE)
            jeu.alternerJoueurCourant();

        jeu.setTypeCourant(typePasse);

        for (int i = nbCartesAPiocher - 1; i >= 0; i--) {
            jeu.getPioche().inserer(jeu.getMain(joueur).extraire(cartesPiochees[i]));
            if (i == indiceMelange) {
                jeu.getDefausse().copier(defaussePasse);
                jeu.getPioche().vider();
                jeu.getPlateau().setFaceCouronne(faceCouronnePasse);
            }
        }

        for (int i = nbCartesAPiocher - 1; i >= 0; i--)
            jeu.getSelectionCartes(joueur).inserer(jeu.getDefausse().extraire());

        if (joueur == Jeu.JOUEUR_VRT)
            jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + Plateau.DIRECTION_RGE * jeu.evaluerDeplacementCouronne(joueur));
        else if (joueur == Jeu.JOUEUR_RGE)
            jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + Plateau.DIRECTION_VRT * jeu.evaluerDeplacementCouronne(joueur));
        else
            throw new RuntimeException("Modele.Coup.desexecuterFinTour() : Joueur courant invalide.");

        jeu.setActivationPrivilegeRoi(activationPrivilegeRoiPasse);

        if (!jeu.getSelectionCartes(joueur).estVide() &&
                jeu.getSelectionCartes(joueur).getCarte(jeu.getSelectionCartes(joueur).getTaille() - 1).getType().equals(Type.FOU) &&
                !typePasse.equals(Type.FOU))
            jeu.setActivationPouvoirFou(true);


        jeu.putSelectionDirections(1, selectionDirectionsPasse[1]);
        jeu.putSelectionDirections(0, selectionDirectionsPasse[0]);
        jeu.putSelectionPions(1, selectionPionsPasse[1]);
        jeu.putSelectionPions(0, selectionPionsPasse[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Coup coup = (Coup) o;

        return jeu.equals(coup.jeu) && joueur == coup.joueur &&
                typeCoup == coup.typeCoup && carte.equals(coup.carte) &&
                pion.equals(coup.pion) && direction == coup.direction;
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
                return "Fin tour";
            default:
                throw new RuntimeException("Modele.Coup.toString() : type de coup invalide.");
        }
    }
}
