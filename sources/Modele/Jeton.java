package Modele;

public class Jeton {
    public static final boolean GRANDE_CRN = true;
    public static final boolean PETITE_CRN = false;

    boolean face;
    int position;

    public Jeton(boolean face, int position) {
        this.face = face;
        this.position = position;
    }

    public boolean getFace() {
        return face;
    }

    public int getPosition() {
        return position;
    }

    public boolean setFace(boolean face) {
        return this.face = face;
    }

    public int setPosition(int position) {
        return this.position = position;
    }

    public int setPosition(int deplacement, int direction) {
        return this.position += direction*deplacement;
    }

    public boolean alternerFace() {
        return face = !face;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Jeton jeton = (Jeton) o;

        return (jeton.face == face && jeton.position == position);
    }

    @Override
    public Jeton clone() {
       /* try {
            Jeton resultat = (Jeton) super.clone();
            resultat.face = face;
            resultat.position = position;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Bug interne, jeton non clonable");
        }*/
        return new Jeton(getFace(),getPosition());
    }

    @Override
    public String toString() {
        if (face == GRANDE_CRN)
            return "C";
        else
            return "c";
    }

}
