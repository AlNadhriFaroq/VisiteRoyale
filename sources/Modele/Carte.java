package Modele;

import Vue.Couleur;

import java.io.Serializable;

public class Carte implements Cloneable, Serializable, Comparable<Carte> {
    public static final Carte R1 = new Carte(Type.ROI, 1);
    public static final Carte G1 = new Carte(Type.GAR, 1);
    public static final Carte G2 = new Carte(Type.GAR, 2);
    public static final Carte GC = new Carte(Type.GAR, 3);
    public static final Carte S1 = new Carte(Type.SOR, 1);
    public static final Carte S2 = new Carte(Type.SOR, 2);
    public static final Carte S3 = new Carte(Type.SOR, 3);
    public static final Carte F1 = new Carte(Type.FOU, 1);
    public static final Carte F2 = new Carte(Type.FOU, 2);
    public static final Carte F3 = new Carte(Type.FOU, 3);
    public static final Carte F4 = new Carte(Type.FOU, 4);
    public static final Carte F5 = new Carte(Type.FOU, 5);
    public static final Carte FM = new Carte(Type.FOU, 6);

    private Type type;
    private int deplacement;

    private Carte(Type type, int deplacement) {
        this.type = type;
        this.deplacement = deplacement;
    }

    public Type getType() {
        return type;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public boolean estDeplacementGar1Plus1() {
        return type.equals(Type.GAR) && deplacement == 2;
    }

    public boolean estDeplacementGarCentre() {
        return type.equals(Type.GAR) && deplacement == 3;
    }

    public boolean estDeplacementFouCentre() {
        return type.equals(Type.FOU) && deplacement == 6;
    }

    public static Carte texteEnCarte(String texte) {
        switch (texte) {
            case "R1":
                return R1;
            case "G1":
                return G1;
            case "G2":
                return G2;
            case "GC":
                return GC;
            case "S1":
                return S1;
            case "S2":
                return S2;
            case "S3":
                return S3;
            case "F1":
                return F1;
            case "F2":
                return F2;
            case "F3":
                return F3;
            case "F4":
                return F4;
            case "F5":
                return F5;
            case "FM":
                return FM;
            default:
                throw new RuntimeException("Modele.Carte.texteEnCarte() : Texte entré invalide.");
        }
    }

    @Override
    public int compareTo(Carte carte) {
        return type.compareTo(carte.type) * 10 + (deplacement - carte.deplacement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Carte carte = (Carte) o;

        return (type.equals(carte.type) && deplacement == carte.deplacement);
    }

    @Override
    public Carte clone() {
        try {
            Carte resultat = (Carte) super.clone();
            resultat.type = type.clone();
            resultat.deplacement = deplacement;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Carte.clone(): Carte non clonable.");
        }
    }

    @Override
    public String toString() {
        return type + (type.equals(Type.GAR) && deplacement == 3 ? "C" : (deplacement == 6 ? "M" : Integer.toString(deplacement)));
    }
}
