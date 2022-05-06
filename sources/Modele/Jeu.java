package Modele;

import Patterns.Observable;

import java.util.*;

public class Jeu extends Observable implements Cloneable {
    public static final int JOUEUR_IND = -1;
    public static final int JOUEUR_VRT = 0;
    public static final int JOUEUR_RGE = 1;
    public static final int TAILLE_MAIN = 8;

    List<Coup> passe;
    List<Coup> futur;

    int joueurCourant;
    Plateau plateau;
    Paquet pioche;
    Paquet defausse;
    Paquet mainJoueurVrt;
    Paquet mainJoueurRge;
    Paquet selectionJoueurVrt;
    Paquet selectionJoueurRge;
    Type typeCourant;

    public Jeu() {
        nouvellePartie();
    }

    public void nouvellePartie() {
        passe = new ArrayList<>();
        futur = new ArrayList<>();

        joueurCourant = JOUEUR_IND;

        Paquet.creerJeuCartes();
        pioche = new Paquet(Paquet.ORDONNE);
        defausse = new Paquet(Paquet.ORDONNE);
        mainJoueurVrt = new Paquet(Paquet.NON_ORDONNE);
        mainJoueurRge = new Paquet(Paquet.NON_ORDONNE);
        selectionJoueurVrt = new Paquet(Paquet.NON_ORDONNE);
        selectionJoueurRge = new Paquet(Paquet.NON_ORDONNE);

        pioche.remplir();
        pioche.melanger();
        for (int c = 0; c < TAILLE_MAIN; c++) {
            mainJoueurVrt.ajouter(pioche.piocher());
            mainJoueurRge.ajouter(pioche.piocher());
        }

        typeCourant = new Type(Type.IND);
        mettreAJour();
    }

    public int getJoueurCourant() {
        return joueurCourant;
    }

    public int getJoueurGagnant() {
        if (estTerminee())
            if (pionDansChateau(JOUEUR_VRT, Pion.ROI) || couronneDansChateau(JOUEUR_VRT))
                return JOUEUR_VRT;
            else if (pionDansChateau(JOUEUR_RGE, Pion.ROI) || couronneDansChateau(JOUEUR_RGE))
                return JOUEUR_VRT;
            else if (pioche.estVide() && plateau.getCouronne().estFace(Jeton.FACE_PTT_CRN))
                if (pionDansDuche(JOUEUR_VRT, Pion.ROI))
                    return JOUEUR_VRT;
                else
                    return JOUEUR_RGE;
        return JOUEUR_IND;
    }

    public int getTypePion(int pion) {
        return plateau.getPion(pion).getType();
    }

    public int getPositionPion(int pion) {
        return plateau.getPion(pion).getPosition();
    }

    public boolean getFaceCouronne() {
        return plateau.getCouronne().getFace();
    }

    public int getPositionCouronne() {
        return plateau.getCouronne().getPosition();
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

    public Paquet getSelection(int joueur) {
        if (joueur == JOUEUR_VRT)
            return selectionJoueurVrt;
        else if (joueur == JOUEUR_RGE)
            return selectionJoueurRge;
        else
            throw new RuntimeException("Modele.Jeu.getSelection() : Joueur entré invalide.");
    }

    public int getNombreTypeCarte(Paquet paquet, int type) {
        return paquet.getNombreTypeCarte(type);
    }

    public int getNombreCarte(Paquet paquet, int type, int deplacement) {
        return paquet.getNombreCarte(type, deplacement);
    }

    public int getTypeCourant() {
        return typeCourant.getValeur();
    }

    int getDeplacementCouronne(int joueur) {
        if (joueur == JOUEUR_VRT)
            return plateau.getDeplacementCouronneVrt();
        else if (joueur == JOUEUR_RGE)
            return plateau.getDeplacementCouronneRge();
        else
            throw new RuntimeException("Modele.Jeu.getDeplacementCouronne() : Joueur entré invalide.");
    }

    int setPositionPion(int pion, int destination) {
        return plateau.getPion(pion).setPosition(destination);
    }

    boolean setFaceCouronne(boolean face) {
        return plateau.getCouronne().setFace(face);
    }

    int setPositionCouronne(int position) {
        return plateau.getCouronne().setPosition(position);
    }

    int setTypeCourant(int type) {
        return typeCourant.setValeur(type);
    }

    public void definirJoueurQuiCommence(int joueur) {
        if (joueur == JOUEUR_VRT) {
            joueurCourant = joueur;
            plateau = new Plateau(Plateau.DIRECTION_VRT);
        } else if (joueur == JOUEUR_RGE) {
            joueurCourant = joueur;
            plateau = new Plateau(Plateau.DIRECTION_RGE);
        } else {
            throw new RuntimeException("Modele.Jeu.definirJoueurQuiCommence() : Joueur entré invalide.");
        }
        mettreAJour();
    }

    int alternerJoueurCourant() {
        return joueurCourant = 1 - joueurCourant;
    }

    boolean tournerFaceCouronne() {
        return plateau.getCouronne().tournerFace();
    }

    public Coup creerCoup(int typeCoup, Carte[] cartes, int[] pions, int[] deplacements, int[] directions) {
        if ((typeCoup == Coup.DEPLACEMENT && peutDeplacer(cartes[0], pions, deplacements, directions)) ||
            (typeCoup == Coup.PRIVILEGE_ROI && peutUtiliserPrivilegeRoi(cartes, directions[0])) ||
            (typeCoup == Coup.POUVOIR_SOR && peutUtiliserPouvoirSorcier(pions[0])) ||
            (typeCoup == Coup.POUVOIR_FOU && peutUtiliserPouvoirFou(cartes[0], pions[0], getPositionPion(pions[0]) + directions[0] * deplacements[0])) ||
            (typeCoup == Coup.FIN_TOUR && peutFinirTour()))
            return new Coup(joueurCourant, typeCoup, cartes, pions, deplacements, directions);
        return null;
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

    public Coup annulerCoup() {
        Coup coup = transfererCoup(passe, futur);
        coup.desexecuter();
        mettreAJour();
        return coup;
    }

    public Coup refaireCoup() {
        Coup coup = transfererCoup(futur, passe);
        coup.executer();
        mettreAJour();
        return coup;
    }

    public boolean pionEstDeplacable(int pion, int destination) {
        return plateau.pionEstDeplacable(pion, destination);
    }

    public boolean couronneEstDeplacable(int destination) {
        return plateau.couronneEstDeplacable(destination);
    }

    public boolean pionDansDuche(int joueur, int pion) {
        if (joueur == JOUEUR_VRT)
            return plateau.pionDansDucheVrt(pion);
        else if (joueur == JOUEUR_RGE)
            return plateau.pionDansDucheRge(pion);
        else
            throw new RuntimeException("Modele.Jeu.pionDansDuche() : Joueur entré invalide.");
    }

    public boolean pionDansChateau(int joueur, int pion) {
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

    public boolean pionDansFontaine(int pion) {
        return plateau.pionDansFontaine(pion);
    }

    public boolean peutUtiliserCarte(Carte carte) {
        if (carte.estDeplacementGar1Plus1()) {
            return pionEstDeplacable(Pion.GAR_VRT, getPositionPion(Pion.GAR_VRT) + Plateau.DIRECTION_VRT * 2) ||
                   pionEstDeplacable(Pion.GAR_VRT, getPositionPion(Pion.GAR_VRT) + Plateau.DIRECTION_RGE * 2) ||
                   pionEstDeplacable(Pion.GAR_RGE, getPositionPion(Pion.GAR_RGE) + Plateau.DIRECTION_VRT * 2) ||
                   pionEstDeplacable(Pion.GAR_RGE, getPositionPion(Pion.GAR_RGE) + Plateau.DIRECTION_RGE * 2) ||
                   ((pionEstDeplacable(Pion.GAR_VRT, getPositionPion(Pion.GAR_VRT) + Plateau.DIRECTION_VRT) ||
                     pionEstDeplacable(Pion.GAR_VRT, getPositionPion(Pion.GAR_VRT) + Plateau.DIRECTION_RGE)) &&
                    (pionEstDeplacable(Pion.GAR_RGE, getPositionPion(Pion.GAR_RGE) + Plateau.DIRECTION_VRT) ||
                     pionEstDeplacable(Pion.GAR_RGE, getPositionPion(Pion.GAR_RGE) + Plateau.DIRECTION_RGE)));
        } else if (carte.estDeplacementGarCentre()) {
            return pionEstDeplacable(Pion.GAR_VRT, getPositionPion(Pion.ROI) + Plateau.DIRECTION_VRT) &&
                   pionEstDeplacable(Pion.GAR_RGE, getPositionPion(Pion.ROI) + Plateau.DIRECTION_RGE);
        } else if (carte.estDeplacementFouCentre()) {
            return pionEstDeplacable(Pion.FOU, Plateau.FONTAINE);
        } else if (carte.estType(Type.ROI)) {
            return pionEstDeplacable(Pion.ROI, getPositionPion(Pion.ROI) + Plateau.DIRECTION_VRT * carte.getDeplacement()) ||
                   pionEstDeplacable(Pion.ROI, getPositionPion(Pion.ROI) + Plateau.DIRECTION_RGE * carte.getDeplacement());
        } else if (carte.estType(Type.GAR)) {
            return pionEstDeplacable(Pion.GAR_VRT, getPositionPion(Pion.GAR_VRT) + Plateau.DIRECTION_VRT * carte.getDeplacement()) ||
                   pionEstDeplacable(Pion.GAR_VRT, getPositionPion(Pion.GAR_VRT) + Plateau.DIRECTION_RGE * carte.getDeplacement()) ||
                   pionEstDeplacable(Pion.GAR_RGE, getPositionPion(Pion.GAR_RGE) + Plateau.DIRECTION_VRT * carte.getDeplacement()) ||
                   pionEstDeplacable(Pion.GAR_RGE, getPositionPion(Pion.GAR_RGE) + Plateau.DIRECTION_RGE * carte.getDeplacement());
        } else if (carte.estType(Type.SOR)) {
            return pionEstDeplacable(Pion.SOR, getPositionPion(Pion.SOR) + Plateau.DIRECTION_VRT * carte.getDeplacement()) ||
                   pionEstDeplacable(Pion.SOR, getPositionPion(Pion.SOR) + Plateau.DIRECTION_RGE * carte.getDeplacement());
        } else if (carte.estType(Type.FOU)) {
            return pionEstDeplacable(Pion.FOU, getPositionPion(Pion.FOU) + Plateau.DIRECTION_VRT * carte.getDeplacement()) ||
                   pionEstDeplacable(Pion.FOU, getPositionPion(Pion.FOU) + Plateau.DIRECTION_RGE * carte.getDeplacement());
        }
        return false;
    }

    public boolean peutDeplacer() {
        for (int i = 0; i < getMain(joueurCourant).getTaille(); i++)
            if (peutUtiliserCarte(getMain(joueurCourant).getCarte(i)))
                return true;
        return false;
    }

    public boolean peutDeplacer(Carte carte, int[] pions, int[] deplacements, int[] directions) {
        boolean utilisable = true;
        if (carte.estDeplacementGarCentre() || carte.estDeplacementFouCentre())
            utilisable = typeCarteEgalTypePion(carte, plateau.getPion(pions[0]));
        else if (carte.estDeplacementGar1Plus1())
            utilisable = typeCarteEgalTypePion(carte, plateau.getPion(pions[0])) &&
                         pionEstDeplacable(pions[0], getPositionPion(pions[0]) +  directions[0] * deplacements[0]) &&
                         typeCarteEgalTypePion(carte, plateau.getPion(pions[0])) &&
                         pionEstDeplacable(pions[0], getPositionPion(pions[0]) +  directions[0] * deplacements[0]);
        else
            utilisable = typeCarteEgalTypePion(carte, plateau.getPion(pions[0])) &&
                         pionEstDeplacable(pions[0], getPositionPion(pions[0]) +  directions[0] * deplacements[0]);

        return utilisable &&
               !typeCourant.estValeur(Type.FIN) &&
               carteDansMain(joueurCourant, carte) &&
               (typeCourant.estValeur(carte.getType()) || typeCourant.estValeur(Type.IND));
    }

    public boolean peutUtiliserPrivilegeRoi() {
        return (typeCourant.estValeur(Type.ROI) || typeCourant.estValeur(Type.IND)) &&
               !typeCourant.estValeur(Type.FIN) &&
               getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 2 &&
               (plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_VRT) || plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_RGE));
    }

    public boolean peutUtiliserPrivilegeRoi(Carte[] cartes, int direction) {
        return peutUtiliserPrivilegeRoi() &&
               cartes[0].estType(Type.ROI) && cartes[1].estType(Type.ROI) &&
               plateau.peutUtiliserPrivilegeRoi(direction);
    }

    public boolean peutUtiliserPouvoirSorcier() {
        return !typeCourant.estValeur(Type.FIN) || typeCourant.estValeur(Type.IND);
    }

    public boolean peutUtiliserPouvoirSorcier(int pion) {
        return peutUtiliserPouvoirSorcier() && plateau.peutUtiliserPouvoirSor(pion);
    }

    public boolean peutUtiliserPouvoirFou() {
        boolean possible = false;
        if (joueurCourant == JOUEUR_VRT)
            possible = plateau.vrtPeutUtiliserPouvoirFou();
        else if (joueurCourant == JOUEUR_RGE)
            possible = plateau.rgePeutUtiliserPouvoirFou();
        else
            throw new RuntimeException("Modele.Jeu.peutUtiliserPouvoirFou() : Joueur corant invalide.");

        return possible &&
               getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0 &&
               !typeCourant.estValeur(Type.FIN);
    }

    public boolean peutUtiliserPouvoirFou(Carte carte, int pion, int destination) {
        return peutUtiliserPouvoirFou() &&
               carte.estType(Type.FOU) &&
               !typeCarteEgalTypePion(carte, plateau.getPion(pion)) &&
               (typeCourant.estValeur(getTypePion(pion)) || typeCourant.estValeur(Type.IND)) &&
               pionEstDeplacable(pion, destination);
    }

    public boolean peutFinirTour() {
        return !typeCourant.estValeur(Type.IND);
    }

    public boolean carteDansMain(int joueur, Carte carte) {
        if (joueur == JOUEUR_VRT)
            return mainJoueurVrt.contientCarte(carte);
        else if (joueur == JOUEUR_RGE)
            return mainJoueurRge.contientCarte(carte);
        else
            throw new RuntimeException("Modele.Jeu.CarteEstDansMain() : Joueur entré invalide.");
    }

    public boolean typeCarteEgalTypePion(Carte carte, Pion pion) {
        return carte.estType(pion.getType());
    }

    public boolean estTerminee() {
        return (plateau.estTerminee() ||
                (pioche.estVide() && getFaceCouronne() == Jeton.FACE_PTT_CRN && !pionDansFontaine(Pion.ROI)));
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Jeu jeu = (Jeu) o;

        return (joueurCourant == jeu.joueurCourant && plateau.equals(jeu.plateau) &&
                pioche.equals(jeu.pioche) && defausse.equals(jeu.defausse) &&
                mainJoueurVrt.equals(jeu.mainJoueurVrt) && mainJoueurRge.equals(jeu.mainJoueurRge) &&
                selectionJoueurVrt.equals(jeu.selectionJoueurVrt) && selectionJoueurRge.equals(jeu.selectionJoueurRge) &&
                typeCourant == jeu.typeCourant);
    }

    @Override
    public Jeu clone() {
        try {
            Jeu resultat = (Jeu) super.clone();
            resultat.joueurCourant = joueurCourant;
            resultat.plateau = plateau.clone();
            resultat.pioche = pioche.clone();
            resultat.defausse = defausse.clone();
            resultat.mainJoueurVrt = mainJoueurVrt.clone();
            resultat.mainJoueurRge = mainJoueurRge.clone();
            resultat.selectionJoueurVrt = selectionJoueurVrt.clone();
            resultat.selectionJoueurRge = selectionJoueurRge.clone();
            resultat.typeCourant = typeCourant.clone();
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Jeu.clone() : Jeu non clonable.");
        }
    }

    @Override
    public String toString() {
        String txt = "";

        if (joueurCourant == JOUEUR_IND) {
            txt += "Tirage du joueur qui commence.\nMain gauche ou main droite ?\n";
        } else {
            txt = "Au tour de : ";

            if (joueurCourant == JOUEUR_VRT)
                txt += "Joueur vert\n";
            else if (joueurCourant == JOUEUR_RGE)
                txt += "Joueur rouge\n";
            else
                throw new RuntimeException("Modele.Jeu.toString() : Joueur courant invalide.");

            txt += "Vert  : " + mainJoueurVrt.toString() + "\n";
            txt += "        " + selectionJoueurVrt.toString() + "\n";
            txt += plateau.toString() + "\n";
            txt += "        " + selectionJoueurRge.toString() + "\n";
            txt += "Rouge : " + mainJoueurRge.toString() + "\n";
        }

        return txt;
    }
}
