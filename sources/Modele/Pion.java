package Modele;

public class Pion implements Cloneable {
    public static final Pion ROI = new Pion(0, Type.ROI);
    public static final Pion GAR_VRT = new Pion(1, Type.GAR);
    public static final Pion GAR_RGE = new Pion(2, Type.GAR);
    public static final Pion SOR = new Pion(3, Type.SOR);
    public static final Pion FOU = new Pion(4, Type.FOU);

    private int valeur;
    private Type type;

    private Pion(int valeur, Type type) {
        this.valeur = valeur;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public static Pion texteEnPion(String texte) {
        switch (texte) {
            case "R": return ROI;
            case "GV": return GAR_VRT;
            case "GR": return GAR_RGE;
            case "S": return SOR;
            case "F": return FOU;
            default: throw new RuntimeException("Modele.Pion.texteEnPion() : Texte entré invalide.");
        }
    }

    public static Pion valeurEnPion(int valeur) {
        switch (valeur) {
            case 0: return ROI;
            case 1: return GAR_VRT;
            case 2: return GAR_RGE;
            case 3: return SOR;
            case 4: return FOU;
            default: throw new RuntimeException("Modele.Pion.texteEnPion() : Texte entré invalide.");
        }
    }

    public static Pion typeEnPion(Type type) {
        if (type.equals(Type.ROI))
            return ROI;
        else if (type.equals(Type.SOR))
            return SOR;
        else if (type.equals(Type.FOU))
            return FOU;
        else
            throw new RuntimeException("Modele.Pion.typeEnPion() : Type entré invalide.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Pion pion = (Pion) o;

        return valeur == pion.valeur && type.equals(pion.type);
    }

    @Override
    public Pion clone() {
        try {
            Pion resultat = (Pion) super.clone();
            resultat.valeur = valeur;
            resultat.type = type.clone();
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Pion.clone(): Pion non clonable.");
        }
    }

    @Override
    public String toString() {
        switch (valeur) {
            case 0: return "R";
            case 1: return "GV";
            case 2: return "GR";
            case 3: return "S";
            case 4: return "F";
            default: throw new RuntimeException("Modele.Pion.toString() : Pion non affichable.");
        }
    }
}
