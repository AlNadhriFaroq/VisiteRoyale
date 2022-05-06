package Modele;

public class Jeton implements Cloneable {
    public static final boolean FACE_GRD_CRN = false;
    public static final boolean FACE_PTT_CRN = true;

    private boolean face;
    private int position;

    Jeton(int position) {
        this.face = FACE_GRD_CRN;
        this.position = position;
    }

    public boolean getFace() {
        return face;
    }

    public int getPosition() {
        return position;
    }

    boolean setFace(boolean face) {
        return this.face = face;
    }

    int setPosition(int destination) {
        return this.position = destination;
    }

    public boolean estFace(boolean face) {
        return this.face == face;
    }

    boolean tournerFace() {
        return face = !face;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Jeton jeton = (Jeton) o;

        return (face == jeton.face && position == jeton.position);
    }

    @Override
    public Jeton clone() {
       try {
            Jeton resultat = (Jeton) super.clone();
            resultat.face = face;
            resultat.position = position;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Jeton.clone(): Jeton non clonable.");
        }
    }

    @Override
    public String toString() {
        if (face == FACE_GRD_CRN)
            return "C";
        else
            return "c";
    }

}
