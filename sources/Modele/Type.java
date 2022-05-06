package Modele;

public class Type implements Cloneable, Comparable<Type> {
    public static final int IND = 0;
    public static final int ROI = 1;
    public static final int GAR = 2;
    public static final int SOR = 3;
    public static final int FOU = 4;
    public static final int FIN = 5;

    private int valeur;

    Type(int valeur) {
        this.valeur = valeur;
    }

    int getValeur() {
        return valeur;
    }

    int setValeur(int valeur) {
        return this.valeur = valeur;
    }

    boolean estValeur(int valeur) {
        return this.valeur == valeur;
    }

    static int caractereEnValeur(char car) {
        if (car == 'R')
            return ROI;
        else if (car == 'G')
            return GAR;
        else if (car == 'S')
            return SOR;
        else if (car == 'F')
            return FOU;
        throw new RuntimeException("Modele.Type.texteEnValeur() : Caractère entré invalide.");
    }

    static char valeurEnCaractere(int val) {
        if (val == ROI)
            return 'R';
        else if (val == GAR)
            return 'G';
        else if (val == SOR)
            return 'S';
        else if (val == FOU)
            return 'F';
        throw new RuntimeException("Modele.Type.valeurEnTexte() : Valeur entrée invalide.");
    }

    @Override
    public int compareTo(Type type) {
        return valeur - type.getValeur();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Type type = (Type) o;

        return (valeur == type.getValeur());
    }

    @Override
    public Type clone() {
        try {
            Type resultat = (Type) super.clone();
            resultat.setValeur(valeur);
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Type.clone() : Type non clonable.");
        }
    }

    @Override
    public String toString() {
        return Character.toString(valeurEnCaractere(valeur));
    }
}
