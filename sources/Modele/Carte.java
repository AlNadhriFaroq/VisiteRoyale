package Modele;

public class Carte implements Cloneable, Comparable<Carte> {
    public static final int DEPLACEMENT_GRD_CENTRE = 3;
    public static final int DEPLACEMENT_GRD_DEUX = 2;
    public static final int DEPLACEMENT_FOU_CENTRE = 6;

    Type type;
    int deplacement;

    public Carte(Type type, int deplacement) {
        this.type = type;
        this.deplacement = deplacement;
    }

    public int getType() {
        return type.getType();
    }

    public int getDeplacement() {
        return deplacement;
    }

    public boolean estType(int type) {
        return this.type.estType(type);
    }

    public static int texteEnValeur(String txt) {
        if (txt.length() != 2)
            return -1;
        return Type.texteEnValeur(txt.charAt(0)) * 10 + Character.getNumericValue(txt.charAt(1));
    }

    public static String valeurEnTexte(int valeur) {
        return Type.valeurEnTexte(valeur % 10) + String.valueOf(valeur / 10);
    }

    /* a modifier */
    @Override
    public int compareTo(Carte carte) {
        return type.compareTo(carte.type)*10 + (deplacement - carte.deplacement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Carte carte = (Carte) o;

        return (type.equals(carte.type) && carte.deplacement == deplacement);
    }

    @Override
    public Carte clone() {
        try {
            Carte resultat = (Carte) super.clone();
            resultat.type = type.clone();
            resultat.deplacement = deplacement;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Bug interne, carte non clonable");
        }
    }

    @Override
    public String toString() {
        return type.toString() + deplacement;
    }
}
