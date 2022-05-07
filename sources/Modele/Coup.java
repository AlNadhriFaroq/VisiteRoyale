package Modele;

import java.util.Arrays;

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
    Pion[] pions;
    int[] destinations;
    int[] positionPasse;
    int nbCartesAPiocher;
    Carte[] cartes;
    Paquet defaussePasse;
    Paquet defausseFutur;
    int indiceMelange;
    boolean faceCouronnePasse;

    public Coup(int joueur, int typeCoup, Carte[] cartes, Pion[] pions, int[] destinations) {
        this.joueur = joueur;
        this.typeCoup = typeCoup;
        typePasse = Type.IND;
        this.pions = pions;
        this.destinations = destinations;
        positionPasse = new int[2];
        nbCartesAPiocher = -1;
        if (typeCoup == FIN_TOUR)
            this.cartes = new Carte[Jeu.TAILLE_MAIN];
        else
            this.cartes = cartes;
        defaussePasse = new Paquet();
        defausseFutur = new Paquet();
        indiceMelange = -1;
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
        jeu.getSelection(joueur).piocher(jeu.getMain(joueur).defausser(cartes[0]));

        if (cartes[0].estDeplacementFouCentre()) {
            positionPasse[0] = jeu.getPlateau().getPositionPion(pions[0]);
            jeu.getPlateau().setPositionPion(pions[0], Plateau.FONTAINE);
        } else if (cartes[0].estDeplacementGarCentre()) {
            for (int i = 0; i < pions.length; i++) {
                positionPasse[i] = jeu.getPlateau().getPositionPion(pions[i]);
                jeu.getPlateau().setPositionPion(pions[i], Plateau.FONTAINE);
            }
        } else {
            for (int i = 0; i < pions.length; i++)
                jeu.getPlateau().setPositionPion(pions[i], destinations[i]);
        }

        typePasse = jeu.getTypeCourant();
        if (jeu.getMain(joueur).getNombreTypeCarte(cartes[0].getType()) == 0)
            jeu.setTypeCourant(Type.FIN);
        else
            jeu.setTypeCourant(cartes[0].getType());
    }

    void desexecuterDeplacement() {
        jeu.setTypeCourant(typePasse);

        if (cartes[0].estDeplacementFouCentre()) {
            jeu.getPlateau().setPositionPion(pions[0], positionPasse[0]);
        } else if (cartes[0].estDeplacementGarCentre()) {
            jeu.getPlateau().setPositionPion(pions[0], positionPasse[0]);
            jeu.getPlateau().setPositionPion(pions[1], positionPasse[1]);
        } else {
            for (int i = pions.length-1; i >= 0; i--)
                jeu.getPlateau().setPositionPion(pions[i], destinations[i]);
        }

        jeu.getMain(joueur).piocher(jeu.getSelection(joueur).defausser(cartes[0]));
    }

    void executerPrivilegeRoi() {
        jeu.getSelection(joueur).piocher(jeu.getMain(joueur).defausser(cartes[0]));
        jeu.getSelection(joueur).piocher(jeu.getMain(joueur).defausser(cartes[1]));

        jeu.getPlateau().setPositionPion(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) + destinations[0]);
        jeu.getPlateau().setPositionPion(Pion.ROI, jeu.getPlateau().getPositionPion(Pion.ROI) + destinations[0]);
        jeu.getPlateau().setPositionPion(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + destinations[0]);

        typePasse = jeu.getTypeCourant();
        if (jeu.getMain(joueur).getNombreTypeCarte(cartes[0].getType()) == 0)
            jeu.setTypeCourant(Type.FIN);
        else
            jeu.setTypeCourant(cartes[0].getType());
    }

    void desexecuterPrivilegeRoi() {
        jeu.setTypeCourant(typePasse);

        jeu.getPlateau().setPositionPion(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - destinations[0]);
        jeu.getPlateau().setPositionPion(Pion.ROI, jeu.getPlateau().getPositionPion(Pion.ROI) - destinations[0]);
        jeu.getPlateau().setPositionPion(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - destinations[0]);

        jeu.getMain(joueur).piocher(jeu.getSelection(joueur).defausser(cartes[1]));
        jeu.getMain(joueur).piocher(jeu.getSelection(joueur).defausser(cartes[0]));
    }

    void executerPouvoirSorcier() {
        positionPasse[0] = jeu.getPlateau().getPositionPion(pions[0]);
        jeu.getPlateau().setPositionPion(pions[0], jeu.getPlateau().getPositionPion(Pion.SOR));

        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(Type.FIN);
    }

    void desexecuterPouvoirSorcier() {
        jeu.setTypeCourant(typePasse);
        jeu.getPlateau().setPositionPion(pions[0], positionPasse[0]);
    }

    void executerPouvoirFou() {
        jeu.getSelection(joueur).piocher(jeu.getMain(joueur).defausser(cartes[0]));

        if (cartes[0].estDeplacementFouCentre()) {
            positionPasse[0] = jeu.getPlateau().getPositionPion(pions[0]);
            jeu.getPlateau().setPositionPion(pions[0], Plateau.FONTAINE);
        } else {
            jeu.getPlateau().setPositionPion(pions[0], destinations[0]);
        }

        typePasse = jeu.getTypeCourant();
        if (jeu.getMain(joueur).getNombreTypeCarte(Type.FOU) == 0)
            jeu.setTypeCourant(Type.FIN);
        else
            jeu.setTypeCourant(pions[0].getType());
    }

    void desexecuterPouvoirFou() {
        jeu.setTypeCourant(typePasse);

        if (cartes[0].estDeplacementFouCentre())
            jeu.getPlateau().setPositionPion(pions[0], positionPasse[0]);
        else
            jeu.getPlateau().setPositionPion(pions[0], destinations[0]);

        jeu.getMain(joueur).piocher(jeu.getSelection(joueur).defausser(cartes[0]));
    }

    void executerFinTour() {
        if (joueur == Jeu.JOUEUR_VRT)
            jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + Plateau.DIRECTION_VRT * jeu.evaluerDeplacementCouronne(joueur));
        else if (joueur == Jeu.JOUEUR_RGE)
            jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + Plateau.DIRECTION_RGE * jeu.evaluerDeplacementCouronne(joueur));
        else
            throw new RuntimeException("Modele.Coup.executerFinTour() : Joueur entré invalide.");

        if (nbCartesAPiocher == -1)
            nbCartesAPiocher = jeu.getSelection(joueur).getTaille();

        for (int i = 0; i < nbCartesAPiocher; i++)
            jeu.getDefausse().defausser(jeu.getSelection(joueur).defausser(jeu.getSelection(joueur).getCarte(i)));

        for (int i = 0; i < nbCartesAPiocher; i++) {
            if (jeu.getPioche().estVide() && (jeu.getPlateau().getFaceCouronne() == Plateau.FACE_GRD_CRN || (jeu.getPlateau().getFaceCouronne() == Plateau.FACE_PTT_CRN && jeu.getPlateau().getPositionPion(Pion.ROI) == Plateau.FONTAINE))) {
                faceCouronnePasse = jeu.getPlateau().getFaceCouronne();
                if (jeu.getPlateau().getFaceCouronne() == Plateau.FACE_GRD_CRN)
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
            cartes[i] = jeu.getPioche().piocher();
            jeu.getMain(joueur).piocher(cartes[i]);
        }

        typePasse = jeu.getTypeCourant();
        jeu.setTypeCourant(Type.IND);
        jeu.alternerJoueurCourant();
    }

    void desexecuterFinTour() {
        jeu.alternerJoueurCourant();
        jeu.setTypeCourant(typePasse);

        for (int i = nbCartesAPiocher-1; i >= 0; i--) {
            jeu.getPioche().defausser(jeu.getMain(joueur).defausser(cartes[i]));
            if (i == indiceMelange) {
                jeu.getDefausse().copier(defaussePasse);
                jeu.getPioche().vider();
                jeu.getPlateau().setFaceCouronne(faceCouronnePasse);
            }
        }

        for (int i = nbCartesAPiocher-1; i >= 0; i--)
            jeu.getSelection(joueur).piocher(jeu.getDefausse().piocher());

        if (joueur == Jeu.JOUEUR_VRT)
            jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + Plateau.DIRECTION_RGE * jeu.evaluerDeplacementCouronne(joueur));
        else if (joueur == Jeu.JOUEUR_RGE)
            jeu.getPlateau().setPositionCouronne(jeu.getPlateau().getPositionCouronne() + Plateau.DIRECTION_VRT * jeu.evaluerDeplacementCouronne(joueur));
        else
            throw new RuntimeException("Modele.Coup.desexecuterFinTour() : Joueur courant invalide.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Coup coup = (Coup) o;

        return jeu.equals(coup.jeu) && joueur == coup.joueur && typeCoup == coup.typeCoup &&
               typePasse.equals(coup.typePasse) && Arrays.equals(pions, coup.pions) &&
               Arrays.equals(destinations, coup.destinations) &&
               Arrays.equals(positionPasse, coup.positionPasse) && nbCartesAPiocher == coup.nbCartesAPiocher &&
               Arrays.equals(cartes, coup.cartes) &&
               defaussePasse.equals(coup.defaussePasse) && defausseFutur.equals(coup.defausseFutur) &&
               indiceMelange == coup.indiceMelange && faceCouronnePasse == coup.faceCouronnePasse;
    }

    @Override
    public Coup clone() {
        try {
            Coup resultat = (Coup) super.clone();
            resultat.jeu = jeu.clone();
            resultat.joueur = joueur;
            resultat.typeCoup = typeCoup;
            resultat.typePasse = typePasse.clone();
            resultat.pions = pions.clone();
            resultat.destinations = destinations.clone();
            resultat.positionPasse = positionPasse.clone();
            resultat.nbCartesAPiocher = nbCartesAPiocher;
            resultat.cartes = cartes.clone();
            resultat.defaussePasse = defaussePasse.clone();
            resultat.defausseFutur = defausseFutur.clone();
            resultat.indiceMelange = indiceMelange;
            resultat.faceCouronnePasse = faceCouronnePasse;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Coup.clone() : Coup non clonable.");
        }
    }
}
