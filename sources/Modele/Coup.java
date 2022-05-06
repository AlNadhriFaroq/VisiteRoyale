package Modele;

public class Coup implements Cloneable {
    public static final int DEPLACEMENT = 0;
    public static final int PRIVILEGE_ROI = 1;
    public static final int POUVOIR_SOR = 2;
    public static final int POUVOIR_FOU = 3;
    public static final int FIN_TOUR = 4;

    Jeu jeu;
    int joueur;
    int typeCoup;

    Type typePasse;
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
        typePasse = new Type(Type.IND);
        this.pions = pions;
        this.deplacements = deplacements;
        this.directions = directions;
        positionPasse = new int[2];
        nbCartesAPiocher = -1;
        if (typeCoup == FIN_TOUR)
            this.cartes = new Carte[Jeu.TAILLE_MAIN];
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
            case DEPLACEMENT:
                executerDeplacement();
                break;
            case PRIVILEGE_ROI:
                executerPrivilegeRoi();
                break;
            case POUVOIR_SOR:
                executerPouvoirSorcier();
                break;
            case POUVOIR_FOU:
                executerPouvoirFou();
                break;
            case FIN_TOUR:
                executerFinTour();
                break;
            default:
                throw new RuntimeException("Modele.Coup.executer() : Coup invalide.");
        }
    }

    public void desexecuter() {
        switch (typeCoup) {
            case DEPLACEMENT:
                desexecuterDeplacement();
                break;
            case PRIVILEGE_ROI:
                desexecuterPrivilegeRoi();
                break;
            case POUVOIR_SOR:
                desexecuterPouvoirSorcier();
                break;
            case POUVOIR_FOU:
                desexecuterPouvoirFou();
                break;
            case FIN_TOUR:
                desexecuterFinTour();
                break;
            default:
                throw new RuntimeException("Modele.Coup.desexecuter() : Coup invalide.");
        }
    }

    void executerDeplacement() {
        jeu.getSelection(joueur).ajouter(jeu.getMain(joueur).defausser(cartes[0]));

        if (cartes[0].estDeplacementFouCentre()) {
            positionPasse[0] = jeu.getPositionPion(pions[0]);
            jeu.setPositionPion(pions[0], Plateau.FONTAINE);
        } else if (cartes[0].estDeplacementGarCentre()) {
            for (int i = 0; i < pions.length; i++) {
                positionPasse[i] = jeu.getPositionPion(pions[i]);
                jeu.setPositionPion(pions[i], Plateau.FONTAINE);
            }
        } else {
            for (int i = 0; i < pions.length; i++)
                jeu.setPositionPion(pions[i], jeu.getPositionPion(pions[i]) + directions[i] * deplacements[i]);
        }

        typePasse.setValeur(jeu.getTypeCourant());
        if (jeu.getNombreTypeCarte(jeu.getMain(joueur), cartes[0].getType()) == 0)
            jeu.setTypeCourant(Type.FIN);
        else
            jeu.setTypeCourant(cartes[0].getType());
    }

    void desexecuterDeplacement() {
        jeu.setTypeCourant(typePasse.getValeur());

        if (cartes[0].estDeplacementFouCentre()) {
            jeu.setPositionPion(pions[0], positionPasse[0]);
        } else if (cartes[0].estDeplacementGarCentre()) {
            jeu.setPositionPion(pions[0], positionPasse[0]);
            jeu.setPositionPion(pions[1], positionPasse[1]);
        } else {
            for (int i = pions.length-1; i >= 0; i--)
                jeu.setPositionPion(pions[i], jeu.getPositionPion(pions[i]) - directions[i] * deplacements[i]);
        }

        jeu.getMain(joueur).ajouter(jeu.getSelection(joueur).defausser(cartes[0]));
    }

    void executerPrivilegeRoi() {
        jeu.getSelection(joueur).ajouter(jeu.getMain(joueur).defausser(cartes[0]));
        jeu.getSelection(joueur).ajouter(jeu.getMain(joueur).defausser(cartes[1]));

        jeu.setPositionPion(Pion.GAR_VRT, jeu.getPositionPion(Pion.GAR_VRT) + directions[0]);
        jeu.setPositionPion(Pion.ROI, jeu.getPositionPion(Pion.ROI) + directions[0]);
        jeu.setPositionPion(Pion.GAR_RGE, jeu.getPositionPion(Pion.GAR_RGE) + directions[0]);

        typePasse.setValeur(jeu.getTypeCourant());
        if (jeu.getNombreTypeCarte(jeu.getMain(joueur), cartes[0].getType()) == 0)
            jeu.setTypeCourant(Type.FIN);
        else
            jeu.setTypeCourant(cartes[0].getType());
    }

    void desexecuterPrivilegeRoi() {
        jeu.setTypeCourant(typePasse.getValeur());

        jeu.setPositionPion(Pion.GAR_RGE, jeu.getPositionPion(Pion.GAR_RGE) - directions[0]);
        jeu.setPositionPion(Pion.ROI, jeu.getPositionPion(Pion.ROI) - directions[0]);
        jeu.setPositionPion(Pion.GAR_VRT, jeu.getPositionPion(Pion.GAR_VRT) - directions[0]);

        jeu.getMain(joueur).ajouter(jeu.getSelection(joueur).defausser(cartes[1]));
        jeu.getMain(joueur).ajouter(jeu.getSelection(joueur).defausser(cartes[0]));
    }

    void executerPouvoirSorcier() {
        positionPasse[0] = jeu.getPositionPion(pions[0]);
        jeu.setPositionPion(pions[0], jeu.getPositionPion(Pion.SOR));

        typePasse.setValeur(jeu.getTypeCourant());
        jeu.setTypeCourant(Type.FIN);
    }

    void desexecuterPouvoirSorcier() {
        jeu.setTypeCourant(typePasse.getValeur());
        jeu.setPositionPion(pions[0], positionPasse[0]);
    }

    void executerPouvoirFou() {
        jeu.getSelection(joueur).ajouter(jeu.getMain(joueur).defausser(cartes[0]));

        if (cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE) {
            positionPasse[0] = jeu.getPositionPion(pions[0]);
            jeu.setPositionPion(pions[0], Plateau.FONTAINE);
        } else {
            jeu.setPositionPion(pions[0], jeu.getPositionPion(pions[0]) + directions[0] * deplacements[0]);
        }

        typePasse.setValeur(jeu.getTypeCourant());
        if (jeu.getNombreTypeCarte(jeu.getMain(joueur), Type.FOU) == 0)
            jeu.setTypeCourant(Type.FIN);
        else
            jeu.setTypeCourant(jeu.getTypePion(pions[0]));
    }

    void desexecuterPouvoirFou() {
        jeu.setTypeCourant(typePasse.getValeur());

        if (cartes[0].getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE) {
            jeu.setPositionPion(pions[0], positionPasse[0]);
        }
        else {
            jeu.setPositionPion(pions[0], jeu.getPositionPion(pions[0]) - directions[0] * deplacements[0]);
        }

        jeu.getMain(joueur).ajouter(jeu.getSelection(joueur).defausser(cartes[0]));
    }

    void executerFinTour() {
        if (joueur == Jeu.JOUEUR_VRT) {
            jeu.setPositionCouronne(jeu.getPositionCouronne() + Plateau.DIRECTION_VRT * jeu.getDeplacementCouronne(joueur));
        } else if (joueur == Jeu.JOUEUR_RGE) {
            jeu.setPositionCouronne(jeu.getPositionCouronne() + Plateau.DIRECTION_RGE * jeu.getDeplacementCouronne(joueur));
        } else {
            throw new RuntimeException("Modele.Coup.executerFinTour() : Joueur entré invalide.");
        }

        if (nbCartesAPiocher == -1)
            nbCartesAPiocher = jeu.getSelection(joueur).getTaille();

        jeu.getDefausse().transferer(jeu.getSelection(joueur));

        for (int i = 0; i < nbCartesAPiocher; i++) {
            if (jeu.getPioche().estVide() && (jeu.getFaceCouronne() == Jeton.FACE_GRD_CRN || (jeu.getFaceCouronne() == Jeton.FACE_PTT_CRN && jeu.getPositionPion(Pion.ROI) == Plateau.FONTAINE))) {
                if (jeu.getFaceCouronne() == Jeton.FACE_GRD_CRN)
                    faceCouronnePasse = true;
                //jeu.setFaceCouronne(Jeton.FACE_PTT_CRN);
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

        typePasse.setValeur(jeu.getTypeCourant());
        jeu.setTypeCourant(Type.IND);
        jeu.alternerJoueurCourant();
    }

    void desexecuterFinTour() {
        jeu.alternerJoueurCourant();
        jeu.setTypeCourant(typePasse.getValeur());

        for (int i = nbCartesAPiocher-1; i >= 0; i--) {
            jeu.getPioche().ajouter(jeu.getMain(joueur).defausser(cartes[i]));
            if (i == indiceMelange) {
                if (faceCouronnePasse)
                    jeu.setFaceCouronne(Jeton.FACE_GRD_CRN);
                jeu.getDefausse().copier(defaussePasse);
                jeu.getPioche().vider();
            }
        }

        for (int i = nbCartesAPiocher-1; i >= 0; i--)
            jeu.getSelection(joueur).ajouter(jeu.getDefausse().piocher());

        if (joueur == Jeu.JOUEUR_VRT)
            jeu.setPositionCouronne(jeu.getPositionCouronne() + Plateau.DIRECTION_RGE * jeu.getDeplacementCouronne(joueur));
        else if (joueur == Jeu.JOUEUR_RGE)
            jeu.setPositionCouronne(jeu.getPositionCouronne() + Plateau.DIRECTION_VRT * jeu.getDeplacementCouronne(joueur));
        else
            throw new RuntimeException("Modele.Coup.desexecuterFinTour() : Joueur courant invalide.");

    }

    @Override
    public Coup clone() {
        return null;
    }
}
