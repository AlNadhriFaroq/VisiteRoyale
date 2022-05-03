package Modele;

import java.util.*;

public class Carte implements Cloneable {
    public static final int NBCARTES = 54;

    public static final int TYPEGARDES = 1;
    public static final int TYPEFOU = 2;
    public static final int TYPESORCIER = 3;
    public static final int TYPEROI = 4;

    public static final int EFFETGARDESCENTRE = 0;
    public static final int EFFETGARDESDEUX = 2;
    public static final int EFFETFOUCENTRE = 0;

    int carte;

    public Carte(int type, int effet) {
        carte = type*10 + effet;
    }

    public int type() {
        return carte / 10;
    }

    public int effet() {
        return carte % 10;
    }

    public static List<Carte> creerPioche() {
        List<Carte> pioche = new ArrayList<>();
        Carte carte;

        for (int r = 0; r < 2; r++) {
            carte = new Carte(TYPEGARDES, 1);
            pioche.add(carte);
            carte = new Carte(TYPEGARDES, EFFETGARDESCENTRE);
            pioche.add(carte);
            carte = new Carte(TYPEGARDES, EFFETGARDESDEUX);
            pioche.add(carte);

            for (int i = 1; i < 6; i++) {
                carte = new Carte(TYPEFOU, i);
                pioche.add(carte);
            }
            carte = new Carte(TYPEFOU, EFFETFOUCENTRE);
            pioche.add(carte);

            for (int i = 1; i < 4; i++) {
                carte = new Carte(TYPESORCIER, i);
                pioche.add(carte);
            }

            carte = new Carte(TYPEROI, 1);
            pioche.add(carte);
        }

        Collections.shuffle(pioche);
        return pioche;
    }

    @Override
    public Carte clone() {
        return new Carte(type(), effet());
    }
}
