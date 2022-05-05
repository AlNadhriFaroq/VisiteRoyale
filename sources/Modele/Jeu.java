package Modele;

import Patterns.Observable;

import java.util.*;

public class Jeu extends Observable implements Cloneable {
    public static final int JOUEUR_VERT = 0;
    public static final int JOUEUR_ROUGE = 1;
    public static final int TAILLE_MAIN = 8;

    List<Coup> passe;
    List<Coup> futur;

    int joueurCourant;
    Plateau plateau;
    Paquet pioche;
    Paquet defausse;
    Paquet mainJoueurVert;
    Paquet mainJoueurRouge;
    int typeCourant;

    public Jeu() {
        nouvellePartie(0);
    }

    public void nouvellePartie(int joueurQuiCommence) {
        passe = new ArrayList<>();
        futur = new ArrayList<>();

        if (joueurQuiCommence == JOUEUR_VERT) {
            joueurCourant = joueurQuiCommence;
            plateau = new Plateau(Plateau.VERS_VERT);
        } else if (joueurQuiCommence == JOUEUR_ROUGE) {
            joueurCourant = joueurQuiCommence;
            plateau = new Plateau(Plateau.VERS_ROUGE);
        } else {
            System.err.println("Joueur inconnu");
            System.exit(1);
        }

        Paquet.creerJeuCartes();
        pioche = new Paquet(Paquet.ORDONNE);
        defausse = new Paquet(Paquet.ORDONNE);
        mainJoueurVert = new Paquet(Paquet.NON_ORDONNE);
        mainJoueurRouge = new Paquet(Paquet.NON_ORDONNE);

        pioche.remplir();
        for (int c = 0; c < TAILLE_MAIN; c++) {
            mainJoueurVert.ajouter(pioche.piocher());
            mainJoueurRouge.ajouter(pioche.piocher());
        }

        typeCourant = Type.TYPE_IND;
        mettreAJour();
    }

    public int getJoueurCourant() {
        return joueurCourant;
    }

    public int getPositionPion(int pion) {
        return plateau.getPositionPion(pion);
    }

    public int getTypePion(int pion) {
        return plateau.getTypePion(pion);
    }

    public int getPositionCouronne() {
        return plateau.getPositionCouronne();
    }

    public boolean getFaceCouronne() {
        return plateau.getFaceCouronne();
    }

    public Paquet getPioche() {
        return pioche;
    }

    public Paquet getDefausse() {
        return defausse;
    }

    public Paquet getMain(int joueur) {
        if (joueur == JOUEUR_VERT)
            return mainJoueurVert;
        else
            return mainJoueurRouge;
    }

    public int getTypeCourant() {
        return typeCourant;
    }

    public int setPositionPion(int pion, int position) {
        int resultat = plateau.setPositionPion(pion, position);
        if (resultat != -1)
            mettreAJour();
        return resultat;
    }

    public int setPositionPion(int pion, int deplacement, int direction) {
        int resultat = plateau.setPositionPion(pion, deplacement, direction);
        if (resultat != -1)
            mettreAJour();
        return resultat;
    }

    public int setPositionCouronne(int position) {
        int resultat = plateau.setPositionCouronne(position);
        if (resultat != -1)
            mettreAJour();
        return resultat;
    }

    public int setPositionCouronne(int deplacement, int direction) {
        int resultat = plateau.setPositionCouronne(deplacement, direction);
        if (resultat != -1)
            mettreAJour();
        return resultat;
    }

    public boolean setFaceCouronne(boolean face) {
        return plateau.setFaceCouronne(face);
    }

    public boolean alternerFaceCouronne() {
        return plateau.alternerFaceCouronne();
    }

    public int setTypeCourant(int type) {
        return typeCourant = type;
    }

    public int alternerJoueurCourant() {
        joueurCourant = 1 - joueurCourant;
        mettreAJour();
        return joueurCourant;
    }

    public Coup creerCoup(int typeCoup, Carte[] cartes, int[] pions, int[] deplacements, int[] directions) {
        Coup resultat = null;
        boolean correct = true;

        if (typeCourant == Type.TYPE_FIN)
            return null;

        switch (typeCoup) {
        case Coup.DEPLACEMENT:
            if (!carteEstDansMain(joueurCourant, cartes[0]) ||
                (cartes[0].getType() != typeCourant && typeCourant != Type.TYPE_IND))
                correct = false;
            else
                for (int i = 0; i < pions.length; i++)
                    if (!typeCarteEgalTypePion(cartes[0], pions[i]) ||
                        !pionEstDeplacable(pions[0], deplacements[i], directions[i]))
                        correct = false;
            break;
        case Coup.PRIVILEGEROI:
            if (cartes.length != 2 ||
                (this.getPositionPion(Plateau.PION_GRD_VERT) == Plateau.BORDURE_VERT && directions[0] == Plateau.VERS_VERT) ||
                (this.getPositionPion(Plateau.PION_GRD_ROUGE) == Plateau.BORDURE_ROUGE && directions[0] == Plateau.VERS_ROUGE))
                correct = false;
            else
                for (int i = 0; i < cartes.length; i++)
                    if (!carteEstDansMain(joueurCourant, cartes[i]) ||
                        cartes[i].getType() != Type.TYPE_ROI ||
                        (cartes[i].getType() != typeCourant && typeCourant != Type.TYPE_IND))
                        correct = false;
            break;
        case Coup.POUVOIRSORCIER:
            if (typeCourant != Type.TYPE_IND ||
                pions[0] == Plateau.PION_FOU ||
                pions[0] == Plateau.PION_SRC ||
                !pionEstDeplacable(pions[0], this.getPositionPion(Plateau.PION_SRC)))
                correct = false;
            break;
        case Coup.POUVOIRFOU:
            if (!conditionPouvoirFou() ||
                !carteEstDansMain(joueurCourant, cartes[0]) ||
                (cartes[0].getType() != Type.TYPE_FOU) ||
                (getTypePion(pions[0]) != typeCourant && typeCourant != Type.TYPE_IND) ||
                typeCarteEgalTypePion(cartes[0], pions[0]) ||
                !pionEstDeplacable(pions[0], deplacements[0], directions[0]))
                correct = false;
            break;
        case Coup.FINTOUR:
            if (typeCourant == Type.TYPE_IND)
                correct = false;
            break;
        default:
            System.err.println("Coup impossible !");
            break;
        }

        if (correct)
            resultat = new Coup(joueurCourant, typeCoup, cartes, pions, deplacements, directions);
        return resultat;
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

    public Carte transfererCarte(List<Carte> source, List<Carte> dest) {
        Carte resultat = source.remove(source.size()-1);
        dest.add(resultat);
        return resultat;
    }

    public boolean pionEstDeplacable(int pion, int position) {
        return plateau.pionEstDeplacable(pion, position);
    }

    public boolean pionEstDeplacable(int pion, int deplacement, int direction) {
        return plateau.pionEstDeplacable(pion, deplacement, direction);
    }

    public boolean couronneEstDeplacable(int position) {
        return plateau.couronneEstDeplacable(position);
    }

    public boolean couronneEstDeplacable(int deplacement, int direction) {
        return plateau.couronneEstDeplacable(deplacement, direction);
    }

    public boolean pionEstDansDuche(int joueur, int pion) {
        if (joueur == JOUEUR_VERT)
            return plateau.pionEstDansDucheVert(pion);
        else if (joueur == JOUEUR_ROUGE)
            return plateau.pionEstDansDucheRouge(pion);
        else
            throw new RuntimeException("Joueur inexistant");
    }

    public boolean pionEstDansChateau(int joueur, int pion) {
        if (joueur == JOUEUR_VERT)
            return plateau.pionEstDansChateauVert(pion);
        else if (joueur == JOUEUR_ROUGE)
            return plateau.pionEstDansChateauRouge(pion);
        else
            throw new RuntimeException("Joueur inexistant");
    }

    public boolean couronneEstDansChateau(int joueur, int pion) {
        if (joueur == JOUEUR_VERT)
            return plateau.couronneEstDansChateauVert();
        else if (joueur == JOUEUR_ROUGE)
            return plateau.couronneEstDansChateauRouge();
        else
            throw new RuntimeException("Joueur inexistant");
    }

    public boolean pionEstDansFontaine(int pion) {
        return plateau.pionEstDansFontaine(pion);
    }

    public boolean carteEstDansMain(int joueur, Carte carte) {
        if (joueur == JOUEUR_VERT)
            return mainJoueurVert.contientCarte(carte);
        else if (joueur == JOUEUR_ROUGE)
            return mainJoueurRouge.contientCarte(carte);
        else
            throw new RuntimeException("Joueur inexistant");
    }

    public boolean typeCarteEgalTypePion(Carte carte, int pion) {
        return carte.getType() == getTypePion(pion);
    }

    public boolean conditionPouvoirFou() {
        if (joueurCourant == JOUEUR_VERT)
            return getPositionPion(Plateau.PION_FOU) > Plateau.CHATEAU_VERT && getPositionPion(Plateau.PION_FOU) < getPositionPion(Plateau.PION_ROI);
        else
            return getPositionPion(Plateau.PION_FOU) > getPositionPion(Plateau.PION_ROI) && getPositionPion(Plateau.PION_FOU) < Plateau.CHATEAU_ROUGE;
    }

    public boolean estTerminee() {
        return (plateau.estTerminee() ||
                (pioche.estVide() && getFaceCouronne() == Jeton.PETITE_CRN && !pionEstDansFontaine(plateau.PION_ROI)));
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
                mainJoueurVert.equals(jeu.mainJoueurVert) && mainJoueurRouge.equals(jeu.mainJoueurRouge) &&
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
            resultat.mainJoueurVert = mainJoueurVert.clone();
            resultat.mainJoueurRouge = mainJoueurRouge.clone();
            resultat.typeCourant = typeCourant;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Bug interne, pion non clonable");
        }
    }

    @Override
    public String toString() {
        String txt = "";

        txt += mainJoueurVert.toString();
        txt += plateau.toString();
        txt += mainJoueurRouge.toString();

        return txt;
    }
}
