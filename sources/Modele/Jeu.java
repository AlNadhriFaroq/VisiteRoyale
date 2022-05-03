package Modele;

import Patterns.Observable;

import java.util.*;

public class Jeu extends Observable implements Cloneable {
    public static final int BORDUREVERT = 0;
    public static final int CHATEAUVERT = 1;
    public static final int FONTAINE = 8;
    public static final int CHATEAUROUGE = 15;
    public static final int BORDUREROUGE = 16;

    public static final int JOUEURVERT = 0;
    public static final int JOUEURROUGE = 1;
    public static final int MAIN = 8;

    public static final int COURONNE = 0;
    public static final int ROI = 1;
    public static final int GARDEVERT = 2;
    public static final int GARDEROUGE = 3;
    public static final int SORCIER = 4;
    public static final int FOU = 5;

    public static final boolean GRANDECOURONNE = true;
    public static final boolean PETITECOURONNE = false;

    List<Coup> passe;
    List<Coup> futur;

    List<Carte> pioche;
    List<Carte> defausse;
    List<Carte> mainJoueurVert;
    List<Carte> mainJoueurRouge;
    List<Integer> pions;
    boolean couronne;
    int joueurCourant;

    public Jeu() {
        nouvellePartie();
    }

    public void nouvellePartie() {
        Random r = new Random();

        passe = new ArrayList<>();
        futur = new ArrayList<>();

        pions = new ArrayList<>();
        pions.add(FONTAINE);
        pions.add(FONTAINE);
        pions.add(FONTAINE - 2);
        pions.add(FONTAINE + 2);
        if ((joueurCourant = r.nextInt(2)) == JOUEURVERT) {
            pions.add(FONTAINE - 1);
            pions.add(FONTAINE + 1);
        } else {
            pions.add(FONTAINE + 1);
            pions.add(FONTAINE - 1);
        }

        pioche = Carte.creerPioche();
        defausse = new ArrayList<>();
        mainJoueurVert = new ArrayList<>();
        mainJoueurRouge = new ArrayList<>();
        for (int c = 0; c < MAIN; c++) {
            mainJoueurVert.add(pioche.remove(pioche.size()-1));
            mainJoueurRouge.add(pioche.remove(pioche.size()-1));
        }

        couronne = GRANDECOURONNE;
        mettreAJour();
    }

    public int joueurCourant(){
        return joueurCourant;
    }

    /* autres getters */

    public int changerJoueurCourant() {
        joueurCourant = 1 - joueurCourant;
        mettreAJour();
        return joueurCourant;
    }

    /* autres setters simples */

    public Coup creerCoup(/* arguments */) {
        Coup resultat = new Coup();
        /* creer un coup selon les arguments ou renvoie null si le coup n'est pas possible */
        return resultat;
    }

    public void jouerCoup(Coup coup) {
        coup.fixerJeu(this);
        coup.executer();
        passe.add(coup);
        futur.clear();
        mettreAJour();
    }

    private Coup transfert(List<Coup> source, List<Coup> dest) {
        Coup resultat = source.remove(source.size()-1);
        dest.add(resultat);
        return resultat;
    }

    public Coup annulerCoup() {
        Coup coup = transfert(passe, futur);
        coup.desexecuter();
        mettreAJour();
        return coup;
    }

    public Coup refaireCoup() {
        Coup coup = transfert(futur, passe);
        coup.executer();
        mettreAJour();
        return coup;
    }

    /* autres methodes / manipulation plus complexe des attributs */

    public boolean pionEstDansDuche(int joueur, int pion) {
        if (joueur == JOUEURVERT)
            return pions.get(pion) < FONTAINE;
        else
            return pions.get(pion) > FONTAINE;
    }

    public boolean pionEstDansChateau(int joueur, int pion) {
        if (joueur == JOUEURVERT)
            return pions.get(pion) <= CHATEAUVERT;
        else
            return pions.get(pion) >= CHATEAUROUGE;
    }

    public boolean estJouable(Coup coup) {
        return false;
    }

    public boolean estTerminee() {
        if (pionEstDansChateau(JOUEURVERT, ROI) ||
            pionEstDansChateau(JOUEURROUGE, ROI) ||
            pionEstDansChateau(JOUEURVERT, COURONNE) ||
            pionEstDansChateau(JOUEURROUGE, COURONNE) ||
            (pioche.isEmpty() && pions.get(ROI) != FONTAINE))
            return true;
        return false;
    }

    public boolean peutAnnuler() {
        return !passe.isEmpty();
    }

    public boolean peutRefaire() {
        return !futur.isEmpty();
    }

    /* autres tests */

    @Override
    public Jeu clone() {
        Jeu resultat = new Jeu();
        for (int i = 0; i < passe.size(); i++)
            resultat.passe.set(i, passe.get(i).clone());
        for (int i = 0; i < futur.size(); i++)
            resultat.futur.set(i, futur.get(i).clone());
        for (int i = 0; i < pioche.size(); i++)
            resultat.pioche.set(i, pioche.get(i).clone());
        for (int i = 0; i < defausse.size(); i++)
            resultat.defausse.set(i, defausse.get(i).clone());
        for (int i = 0; i < mainJoueurVert.size(); i++)
            resultat.mainJoueurVert.set(i, mainJoueurVert.get(i).clone());
        for (int i = 0; i < mainJoueurRouge.size(); i++)
            resultat.mainJoueurRouge.set(i, mainJoueurRouge.get(i).clone());
        for (int i = 0; i < pions.size(); i++)
            resultat.pions.set(i, pions.get(i).intValue());
        resultat.couronne = couronne;
        resultat.joueurCourant = joueurCourant;
        return resultat;
    }

    @Override
    public String toString() {
        return "";
    }
}
