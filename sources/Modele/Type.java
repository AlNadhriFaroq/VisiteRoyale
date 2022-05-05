package Modele;

public class Type implements Comparable<Type> {
    public static final int TYPE_IND = 0;
    public static final int TYPE_ROI = 1;
    public static final int TYPE_GRD = 2;
    public static final int TYPE_SRC = 3;
    public static final int TYPE_FOU = 4;
    public static final int TYPE_FIN = 5;

    int type;

    public Type(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int setType(int type) {
        return this.type = type;
    }

    public boolean estType(int type) {
        return this.type == type;
    }

    public static int texteEnValeur(char txt) {
        if (txt == 'R')
            return 1;
        else if (txt == 'G')
            return 2;
        else if (txt == 'S')
            return 3;
        else if (txt == 'F')
            return 4;
        return -1;
    }

    public static char valeurEnTexte(int valeur) {
        if (valeur == 1)
            return 'R';
        else if (valeur == 2)
            return 'G';
        else if (valeur == 3)
            return 'S';
        else if (valeur == 4)
            return 'F';
        return '?';
    }

    @Override
    public int compareTo(Type type) {
        return this.type - type.getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Type type = (Type) o;

        return (this.type == type.type);
    }

    @Override
    public Type clone() {
        /*try {
            Type resultat = (Type) super.clone();
            resultat.type = type;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Bug interne, carte non clonable");
        }*/
        return new Type(getType());
    }

    @Override
    public String toString() {
        if (type == TYPE_ROI)
            return "R";
        else if (type == TYPE_GRD)
            return "G";
        else if (type == TYPE_SRC)
            return "S";
        else if (type == TYPE_FOU)
            return "F";
        else
            return "?";
    }
}
