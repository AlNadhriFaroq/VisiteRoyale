package IA;

import Modele.*;

public abstract class IA {
    public static final int DEBUTANT = 0;
    public static final int AMATEUR = 1;
    public static final int INTERMEDIAIRE = 2;
    public static final int PROFESSIONNEL = 3;
    public static final int EXPERT = 4;

    private Jeu jeuReel;
    protected Jeu jeu;

    IA(Jeu jeu) {
        jeuReel = jeu;
    }

    public final Coup elaborerCoup() {
        jeu = jeuReel.clone();
        return calculerCoup();
    }

    Coup calculerCoup() {
        return null;
    }

    public static int texteEnIA(String texte) {
        switch (texte.toLowerCase().replace('é', 'e')) {
            case "debutant":
                return DEBUTANT;
            case "amateur":
                return AMATEUR;
            case "intermediaire":
                return INTERMEDIAIRE;
            case "professionnel":
                return PROFESSIONNEL;
            case "expert":
                return EXPERT;
            default:
                throw new RuntimeException("IA.IA.texteEnIA() : Texte entré invalide.");
        }
    }

    public static String IAenTexte(int ia) {
        switch (ia) {
            case DEBUTANT:
                return "Débutant";
            case AMATEUR:
                return "Amateur";
            case INTERMEDIAIRE:
                return "Intermédiaire";
            case PROFESSIONNEL:
                return "Professionnel";
            case EXPERT:
                return "Expert";
            default:
                throw new RuntimeException("IA.IA.IAenTexte() : Entier entré invalide.");
        }
    }
}
