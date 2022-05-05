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
        nouvellePartie(1);
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
        pioche.melanger();
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

    public int getNombreTypeCarte(Paquet paquet, int type) {
        return paquet.getNombreTypeCarte(type);
    }

    public int getNombreCarte(Paquet paquet, int type, int deplacement) {
        return paquet.getNombreCarte(type, deplacement);
    }

    public int getTypeCourant() {
        return typeCourant;
    }

    int getDeplacementCouronne(int joueur) {
        if (joueur == JOUEUR_VERT)
            return plateau.getDeplacementCouronneVert();
        else if (joueur == JOUEUR_ROUGE)
            return plateau.getDeplacementCouronneRouge();
        else
            throw new RuntimeException("Joueur Inexistant");
    }

    public int setPositionPion(int pion, int position) {
        return plateau.setPositionPion(pion, position);
    }

    public int setPositionPion(int pion, int deplacement, int direction) {
        return plateau.setPositionPion(pion, deplacement, direction);
    }

    public int setPositionCouronne(int position) {
        return plateau.setPositionCouronne(position);
    }

    public int setPositionCouronne(int deplacement, int direction) {
        return plateau.setPositionCouronne(deplacement, direction);
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
        return joueurCourant = 1 - joueurCourant;
    }

    public Coup creerCoup(int typeCoup, Carte[] cartes, int[] pions, int[] deplacements, int[] directions) {
        if ((typeCoup == Coup.COUP_DEPLACEMENT && peutDeplacer(joueurCourant, cartes[0], pions, deplacements, directions)) ||
            (typeCoup == Coup.COUP_PRIVILEGE_ROI && peutUtiliserPrivilegeRoi(joueurCourant, cartes, directions[0])) ||
            (typeCoup == Coup.COUP_POUVOIR_SRC && peutUtiliserPouvoirSorcier(pions[0])) ||
            (typeCoup == Coup.COUP_POUVOIR_FOU && peutUtiliserPouvoirFou(joueurCourant, cartes[0], pions[0], deplacements[0], directions[0])) ||
            (typeCoup == Coup.COUP_FIN_TOUR && peutFinirTour()))
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

    public boolean peutDeplacer(int joueur, Carte carte, int[] pions, int[] deplacements, int[] directions) {
        boolean utilisable = true;
        if (carte.getDeplacement() == Carte.DEPLACEMENT_GRD_CENTRE || carte.getDeplacement() == Carte.DEPLACEMENT_FOU_CENTRE)
            utilisable = typeCarteEgalTypePion(carte, pions[0]);
        else
            for (int i = 0; i < pions.length; i++)
                if (!typeCarteEgalTypePion(carte, pions[i]) || !pionEstDeplacable(pions[i], deplacements[i], directions[i]))
                    utilisable = false;

        return utilisable &&
               typeCourant != Type.TYPE_FIN &&
               carteEstDansMain(joueur, carte) &&
               (carte.getType() == typeCourant || typeCourant == Type.TYPE_IND);
    }

    public boolean peutUtiliserPrivilegeRoi(int joueur, Carte[] cartes, int direction) {
        if (cartes.length != 2)
            return false;

        boolean utilisable = true;
        for (int i = 0; i < 2; i++)
            if (!carteEstDansMain(joueurCourant, cartes[i]) ||
                cartes[i].getType() != Type.TYPE_ROI ||
                (cartes[i].getType() != typeCourant && typeCourant != Type.TYPE_IND))
                utilisable = false;

        return utilisable &&
               typeCourant != Type.TYPE_FIN &&
               plateau.privilegeRoiUtilisable(direction);
    }

    public boolean peutUtiliserPouvoirSorcier(int pion) {
        return typeCourant != Type.TYPE_FIN && typeCourant == Type.TYPE_IND &&
               plateau.pouvoirSrcUtilisable(pion);
    }

    public boolean peutUtiliserPouvoirFou(int joueur, Carte carte, int pion, int destination) {
        boolean utilisable;
        if (joueur == JOUEUR_VERT)
            utilisable = plateau.pouvoirFouUtilisableParVert(pion, destination);
        else if (joueur == JOUEUR_ROUGE)
            utilisable = plateau.pouvoirFouUtilisableParRouge(pion, destination);
        else
            throw new RuntimeException("Joueur inexistant");

        return utilisable &&
               typeCourant != Type.TYPE_FIN &&
               carteEstDansMain(joueur, carte) &&
               carte.getType() == Type.TYPE_FOU &&
               (getTypePion(pion) == typeCourant || typeCourant == Type.TYPE_IND) &&
               pionEstDeplacable(pion, destination);
    }

    public boolean peutUtiliserPouvoirFou(int joueur, Carte carte, int pion, int deplacement, int direction) {
        return peutUtiliserPouvoirFou(joueur, carte, pion, getPositionPion(pion) + direction*deplacement);
    }

    public boolean peutFinirTour() {
        return typeCourant == Type.TYPE_IND;
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

    public boolean estTerminee() {
        return (plateau.estTerminee() ||
                (pioche.estVide() && getFaceCouronne() == Jeton.PETITE_CRN && !pionEstDansFontaine(Plateau.PION_ROI)));
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
        String txt = "Au tour de : ";

        if (joueurCourant == JOUEUR_VERT)
            txt += "Joueur vert\n";
        else if (joueurCourant == JOUEUR_ROUGE)
            txt += "Joueur rouge\n";
        else
            throw new RuntimeException("Joueur inexistant");

        txt += mainJoueurVert.toString() + "\n";
        txt += plateau.toString();
        txt += mainJoueurRouge.toString() + "\n";

        return txt;
    }
}
