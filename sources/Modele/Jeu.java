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
    private Main mainJoueurVrt;
    private Main mainJoueurRge;

    private int etatJeu;
    private int activationPrivilegeRoi;
    private boolean activationPouvoirSor;
    private boolean activationPouvoirFou;
    private Main selectionCartesVrt;
    private Main selectionCartesRge;
    private Pion[] selectionPions;
    private int[] selectionDirections;

    public Jeu() {
        nouvellePartie();
    }

    public void nouvellePartie() {
        passe = new ArrayList<>();
        futur = new ArrayList<>();

        joueurCourant = JOUEUR_IND;
        typeCourant = Type.IND;
        plateau = new Plateau(getDirectionJoueur(joueurCourant));
        pioche = new Paquet();
        defausse = new Paquet();
        mainJoueurVrt = new Main(TAILLE_MAIN);
        mainJoueurRge = new Main(TAILLE_MAIN);

        pioche.remplir();
        for (int c = 0; c < TAILLE_MAIN; c++) {
            mainJoueurVrt.piocher(pioche.piocher());
            mainJoueurRge.piocher(pioche.piocher());
        }
        mainJoueurVrt.trier();
        mainJoueurRge.trier();

        etatJeu = ETAT_CHOIX_JOUEUR;
        activationPrivilegeRoi = 0;
        activationPouvoirSor = false;
        activationPouvoirFou = false;
        selectionCartesVrt = new Main(TAILLE_MAIN);
        selectionCartesRge = new Main(TAILLE_MAIN);
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
                return JOUEUR_VRT;
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

    public Main getMain(int joueur) {
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

    public Main getSelectionCartes(int joueur) {
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

    public void jouerCoup(Coup coup) {
        coup.fixerJeu(this);
        coup.executer();
        passe.add(coup);
        futur.clear();
        mettreAJour();
        if (estTerminee()) {
            etatJeu = ETAT_FIN_DE_PARTIE;
            mettreAJour();
        }
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
                   activationPrivilegeRoi == 1 &&
                   (plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_VRT) || plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_RGE));
        } else if (etatJeu == ETAT_CHOIX_CARTE) {
            if (activationPouvoirFou) {
                if (carte.estDeplacementFouCentre())
                    return typeCourant.equals(Type.IND) ||
                           (typeCourant.equals(Type.GAR) && (plateau.pionEstDeplacable(Pion.GAR_VRT, Plateau.FONTAINE) || plateau.pionEstDeplacable(Pion.GAR_RGE, Plateau.FONTAINE))) ||
                           (!typeCourant.equals(Type.GAR) && plateau.pionEstDeplacable(Pion.typeEnPion(typeCourant), carte.getDeplacement()));
                else if (carte.getType().equals(Type.FOU))
                    return typeCourant.equals(Type.IND) ||
                           (typeCourant.equals(Type.GAR) && (pionDeplacable(Pion.GAR_VRT, carte.getDeplacement()) || pionDeplacable(Pion.GAR_RGE, carte.getDeplacement()))) ||
                           (!typeCourant.equals(Type.GAR) && pionDeplacable(Pion.typeEnPion(typeCourant), carte.getDeplacement()));
            } else if (typeCourant.equals(carte.getType()) || typeCourant.equals(Type.IND)) {
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
                return typeCourant.equals(Type.IND) && !pion.getType().equals(Type.FOU) && plateau.pionEstDeplacable(pion, Plateau.FONTAINE);
            else if (activationPouvoirFou)
                return typeCourant.equals(Type.IND) && !pion.getType().equals(Type.FOU) && pionDeplacable(pion, carte.getDeplacement());
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
                txt += "VICTOIRE DU " + joueurEnTexte(getJoueurGagnant()).toUpperCase() + " !!!";
                break;
            default:
               throw new RuntimeException("Modele.jeu.tosTring() : Etat de jeu non affichable.");
        }

        return txt;
    }
}
