package Modele;

public class Pion implements Cloneable {
    public static final int ROI = 0;
    public static final int GAR_VRT = 1;
    public static final int GAR_RGE = 2;
    public static final int SOR = 3;
    public static final int FOU = 4;

    private Type type;
    private int position;

    Pion(int type, int position) {
        this.type = new Type(type);
        this.position = position;
    }

    public int getType() {
        return type.getValeur();
    }

    public int getPosition() {
        return position;
    }

    int setPosition(int destination) {
        return this.position = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Pion pion = (Pion) o;

        return (type.equals(pion.type) && position == pion.position);
    }

    @Override
    public Pion clone() {
        try {
            Pion resultat = (Pion) super.clone();
            resultat.type = type.clone();
            resultat.position = position;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Pion.clone() : Pion non clonable.");
        }
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
