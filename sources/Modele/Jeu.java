package Modele;

import Patterns.Observable;

import java.util.*;

public class Jeu extends Observable implements Cloneable {
    public static final int ETAT_CHOIX_JOUEUR = 0;
    public static final int ETAT_EN_JEU = 1;
    public static final int ETAT_GAME_OVER = 2;

    public static final int JOUEUR_VRT = 0;
    public static final int JOUEUR_RGE = 1;
    public static final int TAILLE_MAIN = 8;

    private List<Coup> passe;
    private List<Coup> futur;

    private int etatJeu;
    private int joueurCourant;
    private Plateau plateau;
    private Paquet pioche;
    private Paquet defausse;
    private Main mainJoueurVrt;
    private Main mainJoueurRge;
    private Main selectionJoueurVrt;
    private Main selectionJoueurRge;
    private Type typeCourant;

    public Jeu() {
        nouvellePartie();
    }

    public void nouvellePartie() {
        passe = new ArrayList<>();
        futur = new ArrayList<>();

        etatJeu = ETAT_CHOIX_JOUEUR;

        pioche = new Paquet();
        defausse = new Paquet();
        mainJoueurVrt = new Main(TAILLE_MAIN);
        mainJoueurRge = new Main(TAILLE_MAIN);
        selectionJoueurVrt = new Main(TAILLE_MAIN);
        selectionJoueurRge = new Main(TAILLE_MAIN);

        pioche.remplir();
        for (int c = 0; c < TAILLE_MAIN; c++) {
            mainJoueurVrt.piocher(pioche.piocher());
            mainJoueurRge.piocher(pioche.piocher());
        }
        mainJoueurVrt.trier();
        mainJoueurRge.trier();

        typeCourant = Type.IND;
        mettreAJour();
    }

    public int getEtatJeu() {
        return etatJeu;
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

    public Main getSelection(int joueur) {
        if (joueur == JOUEUR_VRT)
            return selectionJoueurVrt;
        else if (joueur == JOUEUR_RGE)
            return selectionJoueurRge;
        else
            throw new RuntimeException("Modele.Jeu.getSelection() : Joueur entré invalide.");
    }

    public Type getTypeCourant() {
        return typeCourant;
    }

    void alternerJoueurCourant() {
        joueurCourant = 1 - joueurCourant;
    }

    void setTypeCourant(Type type) {
        typeCourant = type;
    }

    public void definirJoueurQuiCommence(int joueur) {
        joueurCourant = joueur;
        plateau = new Plateau(getDirectionJoueur(joueur));
        etatJeu = ETAT_EN_JEU;
        mettreAJour();
    }

    int evaluerDeplacementCouronne(int joueur) {
        if (joueur == JOUEUR_VRT)
            return plateau.evaluerDeplacementCouronneVrt();
        else if (joueur == JOUEUR_RGE)
            return plateau.evaluerDeplacementCouronneRge();
        else
            throw new RuntimeException("Modele.Jeu.getDeplacementCouronne() : Joueur entré invalide.");
    }

    public Coup creerCoup(int typeCoup, Carte[] cartes, Pion[] pions, int[] destinations) {
        if ((typeCoup == Coup.DEPLACEMENT && peutDeplacer(cartes[0], pions, destinations)) ||
            (typeCoup == Coup.PRIVILEGE_ROI && peutUtiliserPrivilegeRoi(cartes, destinations[0])) ||
            (typeCoup == Coup.POUVOIR_SOR && peutUtiliserPouvoirSorcier(pions[0])) ||
            (typeCoup == Coup.POUVOIR_FOU && peutUtiliserPouvoirFou(cartes[0], pions[0], destinations[0])) ||
            (typeCoup == Coup.FIN_TOUR && peutFinirTour()))
            return new Coup(joueurCourant, typeCoup, cartes, pions, destinations);
        return null;
    }

    public void jouerCoup(Coup coup) {
        coup.fixerJeu(this);
        coup.executer();
        passe.add(coup);
        futur.clear();
        mettreAJour();
        if (estTerminee()) {
            etatJeu = ETAT_GAME_OVER;
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

    public boolean peutUtiliserCarte(Carte carte) {
        boolean possible = false;
        if (carte.estDeplacementGar1Plus1())
            possible = peutUtiliserCarte(Pion.GAR_VRT, 2) || peutUtiliserCarte(Pion.GAR_RGE, 2) ||
                       (peutUtiliserCarte(Pion.GAR_VRT, 1) && peutUtiliserCarte(Pion.GAR_RGE, 1));
        else if (carte.estDeplacementGarCentre() || carte.estDeplacementFouCentre())
            possible = true;
        else if (carte.getType().equals(Type.ROI))
            possible = peutUtiliserCarte(Pion.ROI, carte.getDeplacement());
        else if (carte.getType().equals(Type.GAR))
            possible = peutUtiliserCarte(Pion.GAR_VRT, carte.getDeplacement()) || peutUtiliserCarte(Pion.GAR_RGE, carte.getDeplacement());
        else if (carte.getType().equals(Type.SOR))
            possible = peutUtiliserCarte(Pion.SOR, carte.getDeplacement());
        else if (carte.getType().equals(Type.FOU))
            possible = peutUtiliserCarte(Pion.FOU, carte.getDeplacement());
        return possible && (getTypeCourant() == carte.getType() || getTypeCourant() == Type.IND);
    }

    private boolean peutUtiliserCarte(Pion pion, int deplacement) {
        return plateau.pionEstDeplacable(pion, plateau.getPositionPion(pion) + Plateau.DIRECTION_VRT * deplacement) ||
               plateau.pionEstDeplacable(pion, plateau.getPositionPion(pion) + Plateau.DIRECTION_RGE * deplacement);
    }

    public boolean peutDeplacer() {
        for (int i = 0; i < getMain(joueurCourant).getTaille(); i++)
            if (peutUtiliserCarte(getMain(joueurCourant).getCarte(i)))
                return true;
        return false;
    }

    public boolean peutDeplacer(Carte carte, Pion[] pions, int[] destinations) {
        boolean utilisable;
        if (carte.estDeplacementGarCentre() || carte.estDeplacementFouCentre())
            utilisable = true;
        else if (carte.estDeplacementGar1Plus1() && pions[1] != null)
            utilisable = carte.getType().equals(pions[0].getType()) && carte.getType().equals(pions[1].getType()) &&
                         plateau.pionEstDeplacable(pions[0], destinations[0]) &&
                         plateau.pionEstDeplacable(pions[1], destinations[1]);
        else
            utilisable = carte.getType().equals(pions[0].getType()) &&
                         plateau.pionEstDeplacable(pions[0], destinations[0]);

        return utilisable &&
               carteDansMain(joueurCourant, carte) &&
               !typeCourant.equals(Type.FIN) &&
               (typeCourant.equals(carte.getType()) || typeCourant.equals(Type.IND));
    }

    public boolean peutUtiliserPrivilegeRoi() {
        return (typeCourant.equals(Type.ROI) || typeCourant.equals(Type.IND)) &&
               !typeCourant.equals(Type.FIN) &&
               getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 2 &&
               (plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_VRT) || plateau.peutUtiliserPrivilegeRoi(Plateau.DIRECTION_RGE));
    }

    public boolean peutUtiliserPrivilegeRoi(Carte[] cartes, int direction) {
        return peutUtiliserPrivilegeRoi() &&
               cartes[0].getType().equals(Type.ROI) && cartes[1].getType().equals(Type.ROI) &&
               plateau.peutUtiliserPrivilegeRoi(direction);
    }

    public boolean peutUtiliserPouvoirSorcier() {
        return !typeCourant.equals(Type.FIN) || typeCourant.equals(Type.IND);
    }

    public boolean peutUtiliserPouvoirSorcier(Pion pion) {
        return peutUtiliserPouvoirSorcier() && plateau.peutUtiliserPouvoirSor(pion);
    }

    public boolean peutUtiliserPouvoirFou() {
        boolean possible;
        if (joueurCourant == JOUEUR_VRT)
            possible = plateau.vrtPeutUtiliserPouvoirFou();
        else if (joueurCourant == JOUEUR_RGE)
            possible = plateau.rgePeutUtiliserPouvoirFou();
        else
            throw new RuntimeException("Modele.Jeu.peutUtiliserPouvoirFou() : Joueur corant invalide.");

        return possible &&
               getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0 &&
               !typeCourant.equals(Type.FIN);
    }

    public boolean peutUtiliserPouvoirFou(Carte carte, Pion pion, int destination) {
        return peutUtiliserPouvoirFou() &&
               carte.getType().equals(Type.FOU) &&
               !carte.getType().equals(pion.getType()) &&
               (typeCourant.equals(pion.getType()) || typeCourant.equals(Type.IND)) &&
               plateau.pionEstDeplacable(pion, destination);
    }

    public boolean peutFinirTour() {
        return !typeCourant.equals(Type.IND);
    }

    public boolean carteDansMain(int joueur, Carte carte) {
        if (joueur == JOUEUR_VRT)
            return mainJoueurVrt.contientCarte(carte);
        else if (joueur == JOUEUR_RGE)
            return mainJoueurRge.contientCarte(carte);
        else
            throw new RuntimeException("Modele.Jeu.CarteEstDansMain() : Joueur entré invalide.");
    }

    public boolean estTerminee() {
        return (plateau.estTerminee() ||
                (pioche.estVide() && plateau.getFaceCouronne() == Plateau.FACE_PTT_CRN && !pionDansFontaine(Pion.ROI)));
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

        return (joueurCourant == jeu.joueurCourant && plateau.equals(jeu.plateau) &&
                pioche.equals(jeu.pioche) && defausse.equals(jeu.defausse) &&
                mainJoueurVrt.equals(jeu.mainJoueurVrt) && mainJoueurRge.equals(jeu.mainJoueurRge) &&
                selectionJoueurVrt.equals(jeu.selectionJoueurVrt) && selectionJoueurRge.equals(jeu.selectionJoueurRge) &&
                typeCourant.equals(jeu.typeCourant));
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

        if (etatJeu == ETAT_CHOIX_JOUEUR) {
            txt += "Tirage du joueur qui commence.\nMain gauche ou main droite ?";
        } else if (etatJeu == ETAT_EN_JEU) {
            txt = "AU TOUR DE : " + joueurEnTexte(joueurCourant).toUpperCase();
            txt += "              Pioche : " + getPioche().getTaille() + "\n";
            txt += "     Main vert  : " + mainJoueurVrt.toString() + "\n";
            txt += "                  " + selectionJoueurVrt.toString() + "\n";
            txt += plateau.toString() + "\n";
            txt += "                  " + selectionJoueurRge.toString() + "\n";
            txt += "     Main rouge : " + mainJoueurRge.toString();
        } else if (etatJeu == ETAT_GAME_OVER) {
            txt += "VICTOIRE DU " + joueurEnTexte(getJoueurGagnant()).toUpperCase() + " !!!";
        } else {
            throw new RuntimeException("Modele.jeu.tosTring() : Etat de jeu non affichable.");
        }

        return txt;
    }
}
