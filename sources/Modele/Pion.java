package Modele;

public class Pion {
    Type type;
    int position;

    public Pion(Type type, int position) {
        this.type = type;
        this.position = position;
    }

    public int getType() {
        return type.getType();
    }

    public int getPosition() {
        return position;
    }

    public int setPosition(int position) {
        return this.position = position;
    }

    public int setPosition(int deplacement, int direction) {
        return this.position += direction*deplacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Pion pion = (Pion) o;

        return (type.equals(pion.type) && pion.position == position);
    }

    @Override
    public Pion clone() {
        /*try {
            Pion resultat = (Pion) super.clone();
            resultat.position = position;
            resultat.type = type.clone();
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Bug interne, pion non clonable");
        }*/
        return new Pion(new Type(getType()),getPosition());
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
