package Modele;

public class Type implements Cloneable, Comparable<Type> {
    public static final Type IND = new Type(0);
    public static final Type ROI = new Type(1);
    public static final Type GAR = new Type(2);
    public static final Type SOR = new Type(3);
    public static final Type FOU = new Type(4);
    public static final Type FIN = new Type(5);

    private int valeur;

    private Type(int valeur) {
        this.valeur = valeur;
    }

    static Type texteEnType(String texte) {
        switch (texte) {
            case "R": return ROI;
            case "G": return GAR;
            case "S": return SOR;
            case "F": return FOU;
            default: throw new RuntimeException("Modele.Type.texteEnType() : Texte entré invalide.");
        }
    }

    @Override
    public int compareTo(Type type) {
        return valeur - type.valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Type type = (Type) o;

        return (valeur == type.valeur);
    }

    @Override
    public Type clone() {
        try {
            Type resultat = (Type) super.clone();
            resultat.valeur = valeur;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Type.clone(): Type non clonable.");
        }
    }

    @Override
    public String toString() {
        switch (valeur) {
            case 1: return "R";
            case 2: return "G";
            case 3: return "S";
            case 4: return "F";
            default: throw new RuntimeException("Modele.Type.toString() : Type non affichable.");
        }
    }
}
