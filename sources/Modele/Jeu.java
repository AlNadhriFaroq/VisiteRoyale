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

    int setPositionPion(int pion, int deplacement, int direction) {
        return setPositionPion(pion, plateau.getPion(pion).getPosition() + direction * deplacement);
    }

    boolean setFaceCouronne(boolean face) {
        return plateau.getCouronne().setFace(face);
    }

    int setPositionCouronne(int position) {
        return plateau.getCouronne().setPosition(position);
    }

    int setPositionCouronne(int deplacement, int direction) {
        return setPositionCouronne(plateau.getCouronne().getPosition() + direction * deplacement);
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
        if ((typeCoup == Coup.DEPLACEMENT && peutDeplacer(joueurCourant, cartes[0], pions, deplacements, directions)) ||
            (typeCoup == Coup.PRIVILEGE_ROI && peutUtiliserPrivilegeRoi(joueurCourant, cartes, directions[0])) ||
            (typeCoup == Coup.POUVOIR_SOR && peutUtiliserPouvoirSorcier(pions[0])) ||
            (typeCoup == Coup.POUVOIR_FOU && peutUtiliserPouvoirFou(joueurCourant, cartes[0], pions[0], deplacements[0], directions[0])) ||
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

    public boolean pionEstDeplacable(int pion, int deplacement, int direction) {
        return pionEstDeplacable(pion, plateau.getPion(pion).getPosition() + direction * deplacement);
    }

    public boolean couronneEstDeplacable(int position) {
        return plateau.couronneEstDeplacable(position);
    }

    public boolean couronneEstDeplacable(int deplacement, int direction) {
        return couronneEstDeplacable(plateau.getCouronne().getPosition() + direction * deplacement);
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

    public boolean peutDeplacer(int joueur, Carte carte, int[] pions, int[] deplacements, int[] directions) {
        boolean utilisable = true;
        if (carte.getDeplacement() == Carte.DEPLACEMENT_GAR_CENTRE || carte.getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE)
            utilisable = typeCarteEgalTypePion(carte, pions[0]);
        else
            for (int i = 0; i < pions.length; i++)
                if (!typeCarteEgalTypePion(carte, pions[i]) || !pionEstDeplacable(pions[i], deplacements[i], directions[i]))
                    utilisable = false;

        return utilisable &&
               !typeCourant.estValeur(Type.FIN) &&
               carteDansMain(joueur, carte) &&
               (typeCourant.estValeur(carte.getType()) || typeCourant.estValeur(Type.IND));
    }

    public boolean peutUtiliserPrivilegeRoi(int joueur, Carte[] cartes, int direction) {
        if (cartes.length != 2)
            return false;

        boolean utilisable = true;
        for (int i = 0; i < 2; i++)
            if (!carteDansMain(joueur, cartes[i]) ||
                cartes[i].getType() != Type.ROI ||
                (!typeCourant.estValeur(cartes[i].getType()) && !typeCourant.estValeur(Type.IND)))
                utilisable = false;

        return utilisable &&
               !typeCourant.estValeur(Type.FIN) &&
               plateau.peutUtiliserPrivilegeRoi(direction);
    }

    public boolean peutUtiliserPouvoirSorcier(int pion) {
        return !typeCourant.estValeur(Type.FIN) || typeCourant.estValeur(Type.IND) &&
               plateau.peutUtiliserPouvoirSor(pion);
    }

    public boolean peutUtiliserPouvoirFou(int joueur, Carte carte, int pion, int destination) {
        boolean utilisable;
        if (joueur == JOUEUR_VRT)
            utilisable = plateau.vrtPeutUtiliserPouvoirFou(pion, destination);
        else if (joueur == JOUEUR_RGE)
            utilisable = plateau.rgePeutUtiliserPouvoirFou(pion, destination);
        else
            throw new RuntimeException("Modele.Jeu.peutUtiliserPouvoirFou : Joueur entré invalide.");

        return utilisable &&
               !typeCourant.estValeur(Type.FIN) &&
               carteDansMain(joueur, carte) &&
               carte.getType() == Type.FOU &&
               (typeCourant.estValeur(getTypePion(pion)) || typeCourant.estValeur(Type.IND)) &&
               pionEstDeplacable(pion, destination);
    }

    public boolean peutUtiliserPouvoirFou(int joueur, Carte carte, int pion, int deplacement, int direction) {
        return peutUtiliserPouvoirFou(joueur, carte, pion, getPositionPion(pion) + direction * deplacement);
    }

    public boolean peutFinirTour() {
        return (!typeCourant.estValeur(Type.IND));
    }

    public boolean carteDansMain(int joueur, Carte carte) {
        if (joueur == JOUEUR_VRT)
            return mainJoueurVrt.contientCarte(carte);
        else if (joueur == JOUEUR_RGE)
            return mainJoueurRge.contientCarte(carte);
        else
            throw new RuntimeException("Modele.Jeu.CarteEstDansMain() : Joueur entré invalide.");
    }

    public boolean typeCarteEgalTypePion(Carte carte, int pion) {
        return carte.getType() == getTypePion(pion);
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

            txt += mainJoueurVrt.toString() + "\n";
            txt += plateau.toString() + "\n";
            txt += mainJoueurRge.toString() + "\n";
        }

        return txt;
    }
}
