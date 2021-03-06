package Modele;

import java.io.Serializable;
import java.util.*;

public class Jeu extends Historique implements Cloneable, Serializable {
    public static final int ETAT_CHOIX_JOUEUR = 0;
    public static final int ETAT_CHOIX_CARTE = 1;
    public static final int ETAT_CHOIX_PION = 2;
    public static final int ETAT_CHOIX_DIRECTION = 3;
    public static final int ETAT_FIN_DE_PARTIE = 4;

    public static final int JOUEUR_VRT = 0;
    public static final int JOUEUR_RGE = 1;

    public static final int TAILLE_MAIN = 8;

    private int joueurCourant;
    private Type typeCourant;
    private Plateau plateau;
    private Paquet pioche;
    private Paquet defausse;
    private Paquet mainJoueurVrt;
    private Paquet mainJoueurRge;

    private int etatJeu;
    private int activationPrivilegeRoi;
    private boolean activationPouvoirSor;
    private boolean activationPouvoirFou;
    private Paquet selectionCartesVrt;
    private Paquet selectionCartesRge;
    private Pion[] selectionPions;
    private int[] selectionDirections;

    public Jeu() {
        typeCourant = Type.IND;
        plateau = new Plateau();
        pioche = new Paquet(54, false);
        defausse = new Paquet(54, false);
        mainJoueurVrt = new Paquet(TAILLE_MAIN, true);
        mainJoueurRge = new Paquet(TAILLE_MAIN, true);

        etatJeu = ETAT_FIN_DE_PARTIE;
        selectionCartesVrt = new Paquet(TAILLE_MAIN, false);
        selectionCartesRge = new Paquet(TAILLE_MAIN, false);
        selectionPions = new Pion[2];
        selectionDirections = new int[2];
    }

    public void nouvellePartie() {
        initialiser();

        joueurCourant = JOUEUR_RGE;
        typeCourant = Type.IND;
        plateau.nouveauPlateau(getDirectionJoueur(joueurCourant));
        pioche.vider();
        defausse.vider();
        mainJoueurVrt.vider();
        mainJoueurRge.vider();

        pioche.remplir();

        etatJeu = ETAT_CHOIX_JOUEUR;
        activationPrivilegeRoi = 0;
        activationPouvoirSor = false;
        activationPouvoirFou = false;
        selectionCartesVrt.vider();
        selectionCartesRge.vider();
        selectionPions[0] = null;
        selectionPions[1] = null;
        selectionDirections[0] = Plateau.DIRECTION_IND;
        selectionDirections[1] = Plateau.DIRECTION_IND;
    }

    public void nouvellePartiePersonalise(int joueur, int posRoi, int posGV, int posGR, int posSor, int posFou, int posCouronne, boolean faceCouronne, int nbCartesDefausse, Carte[] cartesMainVrt, Carte[] cartesMainRge) {
        nouvellePartie();

        joueurCourant = joueur;
        etatJeu = ETAT_CHOIX_CARTE;

        plateau.setPositionPion(Pion.ROI, posRoi);
        plateau.setPositionPion(Pion.GAR_VRT, posGV);
        plateau.setPositionPion(Pion.GAR_RGE, posGR);
        plateau.setPositionPion(Pion.SOR, posSor);
        plateau.setPositionPion(Pion.FOU, posFou);
        plateau.setPositionCouronne(posCouronne);
        plateau.setFaceCouronne(faceCouronne);

        if (cartesMainVrt == null)
            cartesMainVrt = new Carte[0];
        if (cartesMainRge == null)
            cartesMainRge = new Carte[0];

        for (Carte carte : cartesMainVrt)
            mainJoueurVrt.inserer(pioche.extraire(carte));
        for (Carte carte : cartesMainRge)
            mainJoueurRge.inserer(pioche.extraire(carte));
        for (int i = cartesMainVrt.length; i < TAILLE_MAIN; i++)
            mainJoueurVrt.inserer(pioche.extraire());
        for (int i = cartesMainRge.length; i < TAILLE_MAIN; i++)
            mainJoueurRge.inserer(pioche.extraire());

        for (int i = 0; i < nbCartesDefausse; i++)
            defausse.inserer(pioche.extraire());
    }

    public void nouvellePartieAleatoire() {
        Random r = new Random();
        int joueur = r.nextInt(2);
        int posRoi = r.nextInt(Plateau.CHATEAU_RGE - 2) + 2;
        int posGV = r.nextInt(posRoi - Plateau.BORDURE_VRT) + Plateau.BORDURE_VRT;
        int posGR = r.nextInt(Plateau.BORDURE_RGE + 1 - posRoi) + posRoi;
        int posSor = r.nextInt(Plateau.BORDURE_RGE + 1);
        int posFou = r.nextInt(Plateau.BORDURE_RGE + 1);
        int posCouronne = r.nextInt(Plateau.BORDURE_RGE - 3) + 2;
        boolean faceCouronne = r.nextBoolean();
        int cartesDefausse = r.nextInt(27);
        nouvellePartiePersonalise(joueur, posRoi, posGV, posGR, posSor, posFou, posCouronne, faceCouronne, cartesDefausse, null, null);
    }

    public void definirJoueurQuiCommence(int joueur) {
        setJoueurCourant(joueur);
        plateau.nouveauPlateau(getDirectionJoueur(joueur));

        for (int c = 0; c < TAILLE_MAIN; c++) {
            mainJoueurVrt.inserer(pioche.extraire());
            mainJoueurRge.inserer(pioche.extraire());
        }

        setEtatJeu(ETAT_CHOIX_CARTE);
    }

    public int getJoueurCourant() {
        return joueurCourant;
    }

    public int getJoueurGagnant() {
        if (estTerminee())
            if (pionDansChateau(JOUEUR_VRT, Pion.ROI) || couronneDansChateau(JOUEUR_VRT))
                return JOUEUR_VRT;
            else if (pionDansChateau(JOUEUR_RGE, Pion.ROI) || couronneDansChateau(JOUEUR_RGE))
                return JOUEUR_RGE;
            else
                return pionDansDuche(JOUEUR_VRT, Pion.ROI) ? JOUEUR_VRT : JOUEUR_RGE;
        return -1;
    }

    public Type getTypeCourant() {
        return typeCourant;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Paquet getPioche() {
        return pioche;
    }

    public Paquet getDefausse() {
        return defausse;
    }

    public Paquet getMain(int joueur) {
        return joueur == JOUEUR_VRT ? mainJoueurVrt : mainJoueurRge;
    }

    public int getEtatJeu() {
        return etatJeu;
    }

    public int getActivationPrivilegeRoi() {
        return activationPrivilegeRoi;
    }

    public boolean getActivationPouvoirSor() {
        return activationPouvoirSor;
    }

    public boolean getActivationPouvoirFou() {
        return activationPouvoirFou;
    }

    public Paquet getSelectionCartes(int joueur) {
        return joueur == JOUEUR_VRT ? selectionCartesVrt : selectionCartesRge;
    }

    public Pion getSelectionPions(int indice) {
        return selectionPions[indice];
    }

    public int getSelectionDirections(int indice) {
        return selectionDirections[indice];
    }

    void setJoueurCourant(int joueur) {
        this.joueurCourant = joueur;
    }

    void setTypeCourant(Type type) {
        this.typeCourant = type;
    }

    void setEtatJeu(int etatJeu) {
        this.etatJeu = etatJeu;
    }

    void setActivationPrivilegeRoi(int activation) {
        this.activationPrivilegeRoi = activation;
    }

    void setActivationPouvoirSor(boolean activation) {
        this.activationPouvoirSor = activation;
    }

    void setActivationPouvoirFou(boolean activation) {
        this.activationPouvoirFou = activation;
    }

    void putSelectionPions(int indice, Pion pion) {
        this.selectionPions[indice] = pion;
    }

    public void putSelectionDirections(int indice, int direction) {
        this.selectionDirections[indice] = direction;
    }

    void alternerJoueurCourant() {
        setJoueurCourant(1 - joueurCourant);
    }

    int evaluerDeplacementCouronne(int joueur) {
        return joueur == JOUEUR_VRT ? plateau.evaluerDeplacementCouronneVrt() : plateau.evaluerDeplacementCouronneRge();
    }

    public List<Coup> calculerListeCoup() {
        List<Coup> coups;
        switch (etatJeu) {
            case ETAT_CHOIX_CARTE:
                if (activationPouvoirFou) {
                    coups = calculerCoupsCartes();
                    if (peutFinirTour())
                        coups.add(new Coup(Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND));
                } else {
                    coups = calculerCoupsPouvoirs();
                }
                break;
            case ETAT_CHOIX_PION:
                coups = calculerCoupsPions();
                break;
            case ETAT_CHOIX_DIRECTION:
                coups = calculerCoupsCartes();
                coups.addAll(calculerCoupsPions());
                coups.addAll(calculerCoupsDirections());
                break;
            default:
                throw new RuntimeException("Modele.jeu.calculerCoup() : Erreur d'etat dans le jeu. Etat " + etatJeu);
        }
        return coups;
    }

    private List<Coup> calculerCoupsPouvoirs() {
        List<Coup> pouvoirsJouables = calculerCoupsCartes();
        if (peutUtiliserPouvoirSorcier())
            pouvoirsJouables.add(new Coup(Coup.ACTIVER_POUVOIR_SOR, null, null, Plateau.DIRECTION_IND));
        if (peutUtiliserPouvoirFou())
            pouvoirsJouables.add(new Coup(Coup.ACTIVER_POUVOIR_FOU, null, null, Plateau.DIRECTION_IND));
        if (peutFinirTour())
            pouvoirsJouables.add(new Coup(Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND));
        return pouvoirsJouables;
    }

    private List<Coup> calculerCoupsCartes() {
        List<Coup> cartesJouables = new ArrayList<>();
        for (int i = 0; i < getMain(joueurCourant).getTaille(); i++) {
            Carte carte = getMain(joueurCourant).getCarte(i);
            if (peutSelectionnerCarte(carte))
                cartesJouables.add(new Coup(Coup.CHOISIR_CARTE, carte, null, Plateau.DIRECTION_IND));
        }
        return cartesJouables;
    }

    private List<Coup> calculerCoupsPions() {
        List<Coup> pionsJouables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Pion pion = Pion.valeurEnPion(i);
            if (peutSelectionnerPion(pion))
                pionsJouables.add(new Coup(Coup.CHOISIR_PION, null, pion, Plateau.DIRECTION_IND));
        }
        return pionsJouables;
    }

    private List<Coup> calculerCoupsDirections() {
        List<Coup> directionsJouables = new ArrayList<>();
        if (peutSelectionnerDirection(Plateau.DIRECTION_VRT))
            directionsJouables.add(new Coup(Coup.CHOISIR_DIRECTION, null, null, Plateau.DIRECTION_VRT));
        if (peutSelectionnerDirection(Plateau.DIRECTION_RGE))
            directionsJouables.add(new Coup(Coup.CHOISIR_DIRECTION, null, null, Plateau.DIRECTION_RGE));
        return directionsJouables;
    }

    public void jouerCoup(Coup coup) {
        coup.fixerJeu(this);
        super.jouerCoup(coup);
    }

    private boolean pionDeplacable(Pion pion, int deplacement) {
        return plateau.pionEstDeplacable(pion, plateau.getPositionPion(pion) + Plateau.DIRECTION_VRT * deplacement) ||
                plateau.pionEstDeplacable(pion, plateau.getPositionPion(pion) + Plateau.DIRECTION_RGE * deplacement);
    }

    public boolean pionDansDuche(int joueur, Pion pion) {
        return joueur == JOUEUR_VRT ? plateau.pionDansDucheVrt(pion) : plateau.pionDansDucheRge(pion);
    }

    public boolean pionDansChateau(int joueur, Pion pion) {
        return joueur == JOUEUR_VRT ? plateau.pionDansChateauVrt(pion) : plateau.pionDansChateauRge(pion);
    }

    public boolean couronneDansChateau(int joueur) {
        return joueur == JOUEUR_VRT ? plateau.couronneDansChateauVrt() : plateau.couronneDansChateauRge();
    }

    public boolean pionDansFontaine(Pion pion) {
        return plateau.pionDansFontaine(pion);
    }

    public boolean peutUtiliserPrivilegeRoi() {
        return (plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_VRT) || plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_RGE)) &&
                ((etatJeu == ETAT_CHOIX_CARTE && activationPrivilegeRoi == 0 && getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 2) ||
                        (etatJeu == ETAT_CHOIX_DIRECTION && activationPrivilegeRoi == 1 && getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1)) &&
                (getTypeCourant() == Type.ROI || getTypeCourant() == Type.IND);
    }

    public boolean peutUtiliserPouvoirSorcier() {
        return !activationPouvoirFou && etatJeu == ETAT_CHOIX_CARTE && typeCourant.equals(Type.IND) && plateau.peutUtiliserPouvoirSor();
    }

    public boolean peutUtiliserPouvoirFou() {
        return !activationPouvoirFou &&
                ((joueurCourant == JOUEUR_VRT && plateau.vrtPeutUtiliserPouvoirFou()) ||
                        (joueurCourant == JOUEUR_RGE && plateau.rgePeutUtiliserPouvoirFou())) &&
                etatJeu == ETAT_CHOIX_CARTE && typeCourant.equals(Type.IND) &&
                getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0;
    }

    public boolean peutFinirTour() {
        return etatJeu == ETAT_CHOIX_CARTE && !typeCourant.equals(Type.IND);
    }

    public boolean peutSelectionnerCarte(Carte carte) {
        if (!getMain(joueurCourant).contientCarte(carte))
            return false;

        if (etatJeu == ETAT_CHOIX_DIRECTION) {
            /* cas du privilege du roi */
            return (typeCourant.equals(Type.ROI) || typeCourant.equals(Type.IND)) &&
                    carte.getType().equals(Type.ROI) &&
                    activationPrivilegeRoi == 1 &&
                    (plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_VRT) || plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_RGE));
        } else if (etatJeu == ETAT_CHOIX_CARTE) {
            if (activationPouvoirFou &&
                    ((joueurCourant == JOUEUR_VRT && plateau.vrtPeutUtiliserPouvoirFou()) ||
                            (joueurCourant == JOUEUR_RGE && plateau.rgePeutUtiliserPouvoirFou()))) {
                /* cas du pouvoir du fou */
                if (carte.estDeplacementFouCentre())
                    return typeCourant.equals(Type.IND) ||
                            (typeCourant.equals(Type.GAR) && (plateau.pionEstDeplacable(Pion.GAR_VRT, Plateau.FONTAINE) || plateau.pionEstDeplacable(Pion.GAR_RGE, Plateau.FONTAINE))) ||
                            (!typeCourant.equals(Type.GAR) && plateau.pionEstDeplacable(Pion.typeEnPion(typeCourant), carte.getDeplacement()));
                else if (carte.getType().equals(Type.FOU))
                    return typeCourant.equals(Type.IND) ||
                            (typeCourant.equals(Type.GAR) && (pionDeplacable(Pion.GAR_VRT, carte.getDeplacement()) || pionDeplacable(Pion.GAR_RGE, carte.getDeplacement()))) ||
                            (!typeCourant.equals(Type.GAR) && pionDeplacable(Pion.typeEnPion(typeCourant), carte.getDeplacement()));
            } else if (!activationPouvoirFou && typeCourant.equals(carte.getType()) || typeCourant.equals(Type.IND)) {
                /* cas normal de selection d'une carte */
                if (carte.estDeplacementGarCentre() || carte.estDeplacementFouCentre())
                    return true;
                else if (carte.estDeplacementGar1Plus1())
                    return pionDeplacable(Pion.GAR_VRT, 2) || pionDeplacable(Pion.GAR_RGE, 2) ||
                            (pionDeplacable(Pion.GAR_VRT, 1) && pionDeplacable(Pion.GAR_RGE, 1));
                else if (carte.getType().equals(Type.GAR))
                    return pionDeplacable(Pion.GAR_VRT, carte.getDeplacement()) || pionDeplacable(Pion.GAR_RGE, carte.getDeplacement());
                else if (carte.getType().equals(Type.ROI))
                    return pionDeplacable(Pion.ROI, carte.getDeplacement()) || getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 2;
                else if (carte.getType().equals(Type.SOR))
                    return pionDeplacable(Pion.SOR, carte.getDeplacement());
                else if (carte.getType().equals(Type.FOU))
                    return pionDeplacable(Pion.FOU, carte.getDeplacement());
            }
        }
        return false;
    }

    public boolean peutSelectionnerPion(Pion pion) {
        /* cas du pouvoir du sorcier */
        if (etatJeu == ETAT_CHOIX_PION && activationPouvoirSor)
            return plateau.peutUtiliserPouvoirSor(pion) && !pion.getType().equals(Type.FOU) && !pion.getType().equals(Type.SOR);

        /* impossible de selectionner un pion si aucune carte n'a etait precedemment selectionnee */
        if (getSelectionCartes(joueurCourant).estVide())
            return false;
        Carte carte = getSelectionCartes(joueurCourant).getCarte(getSelectionCartes(joueurCourant).getTaille() - 1);

        if (etatJeu == ETAT_CHOIX_DIRECTION) {
            /* cas de la selection du deuxieme pion garde pour la carte G2 */
            return carte.estDeplacementGar1Plus1() && pion.getType().equals(Type.GAR) &&
                    getSelectionPions(0) != null && getSelectionPions(1) == null &&
                    !pion.equals(getSelectionPions(0)) && pionDeplacable(pion, 1);
        } else if (etatJeu == ETAT_CHOIX_PION) {
            /* cas normal de selection d'un pion */
            if (activationPouvoirFou && carte.estDeplacementFouCentre())
                return (typeCourant.equals(Type.IND) || pion.getType().equals(Type.GAR)) && !pion.getType().equals(Type.FOU) && plateau.pionEstDeplacable(pion, Plateau.FONTAINE);
            else if (activationPouvoirFou)
                return (typeCourant.equals(Type.IND) || pion.getType().equals(Type.GAR)) && !pion.getType().equals(Type.FOU) && pionDeplacable(pion, carte.getDeplacement());
            else if (carte.getType().equals(Type.GAR) && !carte.estDeplacementGarCentre())
                return pion.getType().equals(Type.GAR) && getSelectionPions(0) == null && pionDeplacable(pion, 1);
        }
        return false;
    }

    public boolean peutSelectionnerDirection(int direction) {
        /* impossible de selectionner une direction si aucune carte n'a etait precedemment selectionnee */
        if (getSelectionCartes(joueurCourant).estVide())
            return false;
        Carte carte = getSelectionCartes(joueurCourant).getCarte(getSelectionCartes(joueurCourant).getTaille() - 1);

        if (activationPouvoirFou) {
            /* cas du pouvoir du fou */
            if ((typeCourant.equals(Type.IND) || typeCourant.equals(Type.GAR)) && getSelectionPions(0) != null)
                return plateau.pionEstDeplacable(getSelectionPions(0), plateau.getPositionPion(getSelectionPions(0)) + direction * carte.getDeplacement());
            else if (!typeCourant.equals(Type.IND) && !typeCourant.equals(Type.GAR))
                return plateau.pionEstDeplacable(Pion.typeEnPion(typeCourant), plateau.getPositionPion(Pion.typeEnPion(typeCourant)) + direction * carte.getDeplacement());
        } else {
            if (activationPrivilegeRoi == 2)
                /* cas du privilege du roi */
                return plateau.peutUtiliserPrivilegeRoi(direction);
            else if (carte.getType().equals(Type.GAR) && getSelectionPions(0) != null && getSelectionPions(1) == null)
                return plateau.pionEstDeplacable(getSelectionPions(0), plateau.getPositionPion(getSelectionPions(0)) + direction * carte.getDeplacement());
            else if (carte.estDeplacementGar1Plus1() && getSelectionPions(1) != null && getSelectionDirections(0) == Plateau.DIRECTION_IND)
                return plateau.pionEstDeplacable(getSelectionPions(0), plateau.getPositionPion(getSelectionPions(0)) + direction);
            else if (carte.estDeplacementGar1Plus1() && getSelectionPions(1) != null && getSelectionDirections(0) != Plateau.DIRECTION_IND)
                return plateau.pionEstDeplacable(getSelectionPions(1), plateau.getPositionPion(getSelectionPions(1)) + direction);
            else if (!carte.getType().equals(Type.GAR))
                return plateau.pionEstDeplacable(Pion.typeEnPion(carte.getType()), plateau.getPositionPion(Pion.typeEnPion(carte.getType())) + direction * carte.getDeplacement());
        }
        return false;
    }

    public boolean estTerminee() {
        return etatJeu == ETAT_FIN_DE_PARTIE;
    }

    public static String joueurEnTexte(int joueur) {
        return joueur == JOUEUR_VRT ? "Joueur vert" : "Joueur rouge";
    }

    public static int getDirectionJoueur(int joueur) {
        return joueur == JOUEUR_VRT ? Plateau.DIRECTION_VRT : Plateau.DIRECTION_RGE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Jeu jeu = (Jeu) o;

        return joueurCourant == jeu.joueurCourant &&
                typeCourant.equals(jeu.typeCourant) &&
                plateau.equals(jeu.plateau) &&
                pioche.equals(jeu.pioche) &&
                defausse.equals(jeu.defausse) &&
                mainJoueurVrt.equals(jeu.mainJoueurVrt) &&
                mainJoueurRge.equals(jeu.mainJoueurRge) &&
                etatJeu == jeu.etatJeu &&
                activationPrivilegeRoi == jeu.activationPrivilegeRoi &&
                activationPouvoirSor == jeu.activationPouvoirSor &&
                activationPouvoirFou == jeu.activationPouvoirFou &&
                selectionCartesVrt.equals(jeu.selectionCartesVrt) &&
                selectionCartesRge.equals(jeu.selectionCartesRge) &&
                Arrays.equals(selectionPions, jeu.selectionPions) &&
                Arrays.equals(selectionDirections, jeu.selectionDirections);
    }

    @Override
    public Jeu clone() {
        Jeu resultat = (Jeu) super.clone();
        resultat.joueurCourant = joueurCourant;
        resultat.typeCourant = typeCourant.clone();
        resultat.plateau = plateau.clone();
        resultat.pioche = pioche.clone();
        resultat.defausse = defausse.clone();
        resultat.mainJoueurVrt = mainJoueurVrt.clone();
        resultat.mainJoueurRge = mainJoueurRge.clone();
        resultat.etatJeu = etatJeu;
        resultat.activationPrivilegeRoi = activationPrivilegeRoi;
        resultat.activationPouvoirSor = activationPouvoirSor;
        resultat.activationPouvoirFou = activationPouvoirFou;
        resultat.selectionCartesVrt = selectionCartesVrt.clone();
        resultat.selectionCartesRge = selectionCartesRge.clone();
        resultat.selectionPions = selectionPions.clone();
        resultat.selectionDirections = selectionDirections.clone();
        return resultat;
    }

    @Override
    public String toString() {
        String txt = "";
        txt += "AU TOUR DE : " + joueurEnTexte(joueurCourant).toUpperCase() + "                    Tour : " + getNbTour() + "\n";
        txt += "                                        Pioche : " + pioche.getTaille() + "\n";
        txt += "    Joueur    " + mainJoueurVrt.toString() + "\n";
        txt += "      vert    " + selectionCartesVrt + "\n";
        txt += plateau.toString() + "\n";
        txt += "    Joueur    " + selectionCartesRge.toString() + "\n";
        txt += "     rouge    " + mainJoueurRge;
        return txt;
    }
}
