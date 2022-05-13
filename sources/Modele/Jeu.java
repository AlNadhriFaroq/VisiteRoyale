package Modele;

import Patterns.Observable;

import java.util.*;

public class Jeu extends Observable implements Cloneable {
    public static final int ETAT_CHOIX_JOUEUR = 0;
    public static final int ETAT_CHOIX_CARTE = 1;
    public static final int ETAT_CHOIX_PION = 2;
    public static final int ETAT_CHOIX_DIRECTION = 3;
    public static final int ETAT_FIN_DE_PARTIE = 4;

    public static final int JOUEUR_IND = -1;
    public static final int JOUEUR_VRT = 0;
    public static final int JOUEUR_RGE = 1;

    public static final int TAILLE_MAIN = 8;

    private List<Coup> passe;
    private List<Coup> futur;

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
        nouvellePartie();
       // partieAleatoire();
        //nouvellePartiePersonalise(JOUEUR_RGE,12,0, 16,1,8,8,true,37);
    }

    public void nouvellePartie() {
        passe = new ArrayList<>();
        futur = new ArrayList<>();

        joueurCourant = JOUEUR_IND;
        typeCourant = Type.IND;
        plateau = new Plateau(getDirectionJoueur(joueurCourant));
        pioche = new Paquet(54);
        defausse = new Paquet(54);
        mainJoueurVrt = new Paquet(TAILLE_MAIN);
        mainJoueurRge = new Paquet(TAILLE_MAIN);

        pioche.remplir();
        for (int c = 0; c < TAILLE_MAIN; c++) {
            mainJoueurVrt.inserer(pioche.extraire(), true);
            mainJoueurRge.inserer(pioche.extraire(), true);
        }

        etatJeu = ETAT_CHOIX_JOUEUR;
        activationPrivilegeRoi = 0;
        activationPouvoirSor = false;
        activationPouvoirFou = false;
        selectionCartesVrt = new Paquet(TAILLE_MAIN);
        selectionCartesRge = new Paquet(TAILLE_MAIN);
        selectionPions = new Pion[2];
        selectionDirections = new int[2];

        mettreAJour();
    }

    public  void partieAleatoire(){
        Random rand = new Random();
        int joueur = rand.nextInt(2);
        int posRoi = rand.nextInt(Plateau.CHATEAU_RGE - 2) + 2;
        int posGV = rand.nextInt(posRoi - Plateau.BORDURE_VRT) + Plateau.BORDURE_VRT;
        int posGR = rand.nextInt(Plateau.BORDURE_RGE + 1 - posRoi) + posRoi;
        System.out.println("Garde Rouge " + posGR);
        int posSor = rand.nextInt(Plateau.BORDURE_RGE + 1);
        int posFou = rand.nextInt(Plateau.BORDURE_RGE + 1);
        int posCouronne = rand.nextInt(Plateau.BORDURE_RGE - 3) + 2;
        boolean faceCouronne =  rand.nextBoolean();
        int cartesDefausse = rand.nextInt(27);
        nouvellePartiePersonalise(joueur, posRoi, posGV, posGR, posSor, posFou, posCouronne, faceCouronne, cartesDefausse);
    }

    public void nouvellePartiePersonalise(int joueur, int posRoi,int posGV, int posGR, int posSor, int posFou, int posCouronne,boolean faceCouronne, int cartesDefausse) {
        passe = new ArrayList<>();
        futur = new ArrayList<>();

        joueurCourant = joueur;
        typeCourant = Type.IND;
        plateau = new Plateau(getDirectionJoueur(joueurCourant));
        /* Calcul position pion aléatoirement*/
        plateau.setPositionPion(Pion.ROI,posRoi);
        plateau.setPositionPion(Pion.GAR_VRT,posGV);
        plateau.setPositionPion(Pion.GAR_RGE,posGR);
        plateau.setPositionPion(Pion.SOR,posSor);
        plateau.setPositionPion(Pion.FOU,posFou);

        plateau.setPositionCouronne(posCouronne);
        plateau.setFaceCouronne(faceCouronne);

        pioche = new Paquet(54);
        defausse = new Paquet(54);
        mainJoueurVrt = new Paquet(TAILLE_MAIN);
        mainJoueurRge = new Paquet(TAILLE_MAIN);

        pioche.remplir();
        for (int c = 0; c < TAILLE_MAIN; c++) {
            mainJoueurVrt.inserer(pioche.extraire(), true);
            mainJoueurRge.inserer(pioche.extraire(), true);
        }

        /* mettre les cartes dans la défausse*/
        for(int i = 0 ; i < cartesDefausse; i++ ){
            defausse.inserer(pioche.extraire());
        }

        etatJeu = ETAT_CHOIX_CARTE;
        activationPrivilegeRoi = 0;
        activationPouvoirSor = false;
        activationPouvoirFou = false;
        selectionCartesVrt = new Paquet(TAILLE_MAIN);
        selectionCartesRge = new Paquet(TAILLE_MAIN);
        selectionPions = new Pion[2];
        selectionDirections = new int[2];

        mettreAJour();
    }

    public int getJoueurCourant() {
        return joueurCourant;
    }

    public Type getTypeCourant() {
        return typeCourant;
    }

    public int getJoueurGagnant() {
        if (estTerminee())
            if (pionDansChateau(JOUEUR_VRT, Pion.ROI) || couronneDansChateau(JOUEUR_VRT))
                return JOUEUR_VRT;
            else if (pionDansChateau(JOUEUR_RGE, Pion.ROI) || couronneDansChateau(JOUEUR_RGE))
                return JOUEUR_RGE;
            else if (pioche.estVide() && plateau.getFaceCouronne() == Plateau.FACE_PTT_CRN)
                if (pionDansDuche(JOUEUR_VRT, Pion.ROI))
                    return JOUEUR_VRT;
                else
                    return JOUEUR_RGE;
        return -1;
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
        if (joueur == JOUEUR_VRT)
            return mainJoueurVrt;
        else if (joueur == JOUEUR_RGE)
            return mainJoueurRge;
        else
            throw new RuntimeException("Modele.Jeu.getMain() : Joueur entré invalide.");
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
        if (joueur == JOUEUR_VRT)
            return selectionCartesVrt;
        else if (joueur == JOUEUR_RGE)
            return selectionCartesRge;
        else
            throw new RuntimeException("Modele.Jeu.getSelection() : Joueur entré invalide.");
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

    void putSelectionDirections(int indice, int direction) {
        this.selectionDirections[indice] = direction;
    }

    public void definirJoueurQuiCommence(int joueur) {
        setJoueurCourant(joueur);
        plateau = new Plateau(getDirectionJoueur(joueur));
        setEtatJeu(ETAT_CHOIX_CARTE);
        mettreAJour();
    }

    void alternerJoueurCourant() {
        setJoueurCourant(1 - joueurCourant);
    }

    int evaluerDeplacementCouronne(int joueur) {
        if (joueur == JOUEUR_VRT)
            return plateau.evaluerDeplacementCouronneVrt();
        else if (joueur == JOUEUR_RGE)
            return plateau.evaluerDeplacementCouronneRge();
        else
            throw new RuntimeException("Modele.Jeu.getDeplacementCouronne() : Joueur entré invalide.");
    }

    public List<Coup> calculerListeCoup() {
        List<Coup> coups;
        switch (etatJeu) {
            case ETAT_CHOIX_CARTE:
                if (activationPouvoirFou) {
                    coups = calculerCoupsCartes();
                    if (coups.isEmpty())
                        coups.add(new Coup(joueurCourant, Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND));
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
                throw new RuntimeException("Controleur.IAALeatoire.calculerCoup() : Erreur d'etat dans le jeu.");
        }
        return coups;
    }

    private List<Coup> calculerCoupsPouvoirs() {
        List<Coup> pouvoirsJouables = calculerCoupsCartes();
        if (peutUtiliserPouvoirSorcier())
            pouvoirsJouables.add(new Coup(joueurCourant, Coup.ACTIVER_POUVOIR_SOR, null, null, Plateau.DIRECTION_IND));
        if (peutUtiliserPouvoirFou())
            pouvoirsJouables.add(new Coup(joueurCourant, Coup.ACTIVER_POUVOIR_FOU, null, null, Plateau.DIRECTION_IND));
        if (peutFinirTour())
            pouvoirsJouables.add(new Coup(joueurCourant, Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND));
        return pouvoirsJouables;
    }

    private List<Coup> calculerCoupsCartes() {
        List<Coup> cartesJouables = new ArrayList<>();
        for (int i = 0; i < getMain(joueurCourant).getTaille(); i++) {
            Carte carte = getMain(joueurCourant).getCarte(i);
            if (peutSelectionnerCarte(carte))
                cartesJouables.add(new Coup(joueurCourant, Coup.CHOISIR_CARTE, carte, null, Plateau.DIRECTION_IND));
        }
        return cartesJouables;
    }

    private List<Coup> calculerCoupsPions() {
        List<Coup> pionsJouables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Pion pion = Pion.valeurEnPion(i);
            if (peutSelectionnerPion(pion))
                pionsJouables.add(new Coup(joueurCourant, Coup.CHOISIR_PION, null, pion, Plateau.DIRECTION_IND));
        }
        return pionsJouables;
    }

    private List<Coup> calculerCoupsDirections() {
        List<Coup> directionsJouables = new ArrayList<>();
        if (peutSelectionnerDirection(Plateau.DIRECTION_VRT))
            directionsJouables.add(new Coup(joueurCourant, Coup.CHOISIR_DIRECTION, null, null, Plateau.DIRECTION_VRT));
        if (peutSelectionnerDirection(Plateau.DIRECTION_RGE))
            directionsJouables.add(new Coup(joueurCourant, Coup.CHOISIR_DIRECTION, null, null, Plateau.DIRECTION_RGE));
        return directionsJouables;
    }

    public void jouerCoup(Coup coup) {
        coup.fixerJeu(this);
        coup.executer();
        passe.add(coup);
        futur.clear();
        mettreAJour();
    }

    private Coup transfererCoup(List<Coup> source, List<Coup> dest) {
        Coup resultat = source.remove(source.size()-1);
        dest.add(resultat);
        return resultat;
    }

    public void annulerCoup() {
        Coup coup = transfererCoup(passe, futur);
        coup.desexecuter();
        mettreAJour();
    }

    public void refaireCoup() {
        Coup coup = transfererCoup(futur, passe);
        coup.executer();
        mettreAJour();
    }

    private boolean pionDeplacable(Pion pion, int deplacement) {
        return plateau.pionEstDeplacable(pion, plateau.getPositionPion(pion) + Plateau.DIRECTION_VRT * deplacement) ||
               plateau.pionEstDeplacable(pion, plateau.getPositionPion(pion) + Plateau.DIRECTION_RGE * deplacement);
    }

    public boolean pionDansDuche(int joueur, Pion pion) {
        if (joueur == JOUEUR_VRT)
            return plateau.pionDansDucheVrt(pion);
        else if (joueur == JOUEUR_RGE)
            return plateau.pionDansDucheRge(pion);
        else
            throw new RuntimeException("Modele.Jeu.pionDansDuche() : Joueur entré invalide.");
    }

    public boolean pionDansChateau(int joueur, Pion pion) {
        if (joueur == JOUEUR_VRT)
            return plateau.pionDansChateauVrt(pion);
        else if (joueur == JOUEUR_RGE)
            return plateau.pionDansChateauRge(pion);
        else
            throw new RuntimeException("Modele.Jeu.pionDansChateau() : Joueur entré invalide.");
    }

    public boolean couronneDansChateau(int joueur) {
        if (joueur == JOUEUR_VRT)
            return plateau.couronneDansChateauVrt();
        else if (joueur == JOUEUR_RGE)
            return plateau.couronneDansChateauRge();
        else
            throw new RuntimeException("Modele.Jeu.couronneDansChateau : Joueur entré invalide.");
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
            return (typeCourant.equals(Type.ROI) || typeCourant.equals(Type.IND)) &&
                   carte.getType().equals(Type.ROI) &&
                   activationPrivilegeRoi == 1 &&
                   (plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_VRT) || plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_RGE));
        } else if (etatJeu == ETAT_CHOIX_CARTE){
            if (activationPouvoirFou &&
                ((joueurCourant == JOUEUR_VRT && plateau.vrtPeutUtiliserPouvoirFou()) ||
                 (joueurCourant == JOUEUR_RGE && plateau.rgePeutUtiliserPouvoirFou()))) {
                if (carte.estDeplacementFouCentre())
                    return typeCourant.equals(Type.IND) ||
                           (typeCourant.equals(Type.GAR) && (plateau.pionEstDeplacable(Pion.GAR_VRT, Plateau.FONTAINE) || plateau.pionEstDeplacable(Pion.GAR_RGE, Plateau.FONTAINE))) ||
                           (!typeCourant.equals(Type.GAR) && plateau.pionEstDeplacable(Pion.typeEnPion(typeCourant), carte.getDeplacement()));
                else if (carte.getType().equals(Type.FOU))
                    return typeCourant.equals(Type.IND) ||
                           (typeCourant.equals(Type.GAR) && (pionDeplacable(Pion.GAR_VRT, carte.getDeplacement()) || pionDeplacable(Pion.GAR_RGE, carte.getDeplacement()))) ||
                           (!typeCourant.equals(Type.GAR) && pionDeplacable(Pion.typeEnPion(typeCourant), carte.getDeplacement()));
            } else if (!activationPouvoirFou && typeCourant.equals(carte.getType()) || typeCourant.equals(Type.IND)) {
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
        if (etatJeu == ETAT_CHOIX_PION && activationPouvoirSor)
            return plateau.peutUtiliserPouvoirSor(pion) && !pion.getType().equals(Type.FOU) && !pion.getType().equals(Type.SOR);

        Carte carte = getSelectionCartes(joueurCourant).getCarte(getSelectionCartes(joueurCourant).getTaille()-1);
        if (etatJeu == ETAT_CHOIX_DIRECTION) {
            return carte.estDeplacementGar1Plus1() && pion.getType().equals(Type.GAR) &&
                   getSelectionPions(0) != null && getSelectionPions(1) == null &&
                   !pion.equals(getSelectionPions(0)) && pionDeplacable(pion, 1);
        } else if (etatJeu == ETAT_CHOIX_PION) {
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
        Carte carte = getSelectionCartes(joueurCourant).getCarte(getSelectionCartes(joueurCourant).getTaille()-1);

        if (activationPouvoirFou) {
            if ((typeCourant.equals(Type.IND) || typeCourant.equals(Type.GAR)) && getSelectionPions(0) != null)
                return plateau.pionEstDeplacable(getSelectionPions(0), plateau.getPositionPion(getSelectionPions(0)) + direction * carte.getDeplacement());
            else if (!typeCourant.equals(Type.IND) && !typeCourant.equals(Type.GAR))
                return plateau.pionEstDeplacable(Pion.typeEnPion(carte.getType()), plateau.getPositionPion(Pion.typeEnPion(carte.getType())) + direction * carte.getDeplacement());
        } else {
            if (activationPrivilegeRoi == 2)
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

    public boolean peutAnnuler() {
        return !passe.isEmpty();
    }

    public boolean peutRefaire() {
        return !futur.isEmpty();
    }

    public static String joueurEnTexte(int joueur) {
        if (joueur == JOUEUR_VRT)
            return "Joueur vert";
        else if (joueur == JOUEUR_RGE)
            return "Joueur rouge";
        else
            throw new RuntimeException("Modele.Jeu.joueurEnTexte() : Joueur entré invalide.");
    }

    public static int getDirectionJoueur(int joueur) {
        if (joueur == JOUEUR_VRT)
            return Plateau.DIRECTION_VRT;
        else if (joueur == JOUEUR_RGE)
            return  Plateau.DIRECTION_RGE;
        else if (joueur == JOUEUR_IND)
            return Plateau.DIRECTION_IND;
        else
            throw new RuntimeException("Modele.Jeu.getDirectionJoueur() : Joueur entré invalide.");
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
               pioche.equals(jeu.pioche) && defausse.equals(jeu.defausse) &&
               mainJoueurVrt.equals(jeu.mainJoueurVrt) && mainJoueurRge.equals(jeu.mainJoueurRge) &&
               etatJeu == jeu.etatJeu &&
               activationPrivilegeRoi == jeu.activationPrivilegeRoi &&
               activationPouvoirSor == jeu.activationPouvoirSor &&
               activationPouvoirFou == jeu.activationPouvoirFou &&
               selectionCartesVrt.equals(jeu.selectionCartesVrt) && selectionCartesRge.equals(jeu.selectionCartesRge) &&
               Arrays.equals(selectionPions, jeu.selectionPions) &&
               Arrays.equals(selectionDirections, jeu.selectionDirections);
    }

    @Override
    public Jeu clone() {
        try {
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
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Jeu.clone() : Jeu non clonable.");
        }
    }

    @Override
    public String toString() {
        String txt = "";

        switch (etatJeu) {
            case ETAT_CHOIX_JOUEUR:
                txt += "Tirage du joueur qui commence.\nMain gauche ou main droite ?";
                break;
            case ETAT_CHOIX_CARTE:
            case ETAT_CHOIX_PION:
            case ETAT_CHOIX_DIRECTION:
                txt = "AU TOUR DE : " + joueurEnTexte(joueurCourant).toUpperCase();
                txt += "              Pioche : " + getPioche().getTaille() + "\n";
                txt += "     Main vert  : " + mainJoueurVrt.toString() + "\n";
                txt += "                  " + selectionCartesVrt.toString() + "\n";
                txt += plateau.toString() + "\n";
                txt += "                  " + selectionCartesRge.toString() + "\n";
                txt += "     Main rouge : " + mainJoueurRge.toString();
                break;
            case ETAT_FIN_DE_PARTIE:
                txt = "AU TOUR DE : " + joueurEnTexte(joueurCourant).toUpperCase();
                txt += "              Pioche : " + getPioche().getTaille() + "\n";
                txt += "     Main vert  : " + mainJoueurVrt.toString() + "\n";
                txt += "                  " + selectionCartesVrt.toString() + "\n";
                txt += plateau.toString() + "\n";
                txt += "                  " + selectionCartesRge.toString() + "\n";
                txt += "     Main rouge : " + mainJoueurRge.toString();
                txt += "\n\n     VICTOIRE DU " + joueurEnTexte(getJoueurGagnant()).toUpperCase() + " !!!";
                break;
            default:
               throw new RuntimeException("Modele.jeu.toString() : Etat de jeu non affichable.");
        }

        return txt;
    }

}
