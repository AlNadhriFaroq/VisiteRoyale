package Modele;

public class Carte implements Cloneable, Comparable<Carte> {
    public static final int DEPLACEMENT_GAR_1PLUS1 = 2;
    public static final int DEPLACEMENT_GAR_CENTRE = 3;
    public static final int DEPLACEMENT_FOU_CENTRE = 6;

    private Type type;
    private int deplacement;

    Carte(int type, int deplacement) {
        this.type = new Type(type);
        this.deplacement = deplacement;
    }

    public int getType() {
        return type.getValeur();
    }

    public int getDeplacement() {
        return deplacement;
    }

    public boolean estType(int type) {
        return this.type.estValeur(type);
    }

    public boolean estDeplacementGar1Plus1() {
        return type.estValeur(Type.GAR) && deplacement == DEPLACEMENT_GAR_1PLUS1;
    }

    public boolean estDeplacementGarCentre() {
        return type.estValeur(Type.GAR) && deplacement == DEPLACEMENT_GAR_CENTRE;
    }

    public boolean estDeplacementFouCentre() {
        return type.estValeur(Type.FOU) && deplacement == DEPLACEMENT_FOU_CENTRE;
    }

    public static int texteEnValeur(String txt) {
        if (txt.length() != 2)
            throw new RuntimeException("Modele.Carte.texteEnValeur() : Texte entré invalide.");
        return Type.caractereEnValeur(txt.charAt(0)) * 10 + Character.getNumericValue(txt.charAt(1));
    }

    public static String valeurEnTexte(int val) {
        if (val < 10 || 99 < val)
            throw new RuntimeException("Modele.Carte.valeurEnTexte() : Valeur entrée invalide.");
        return Type.valeurEnCaractere(val / 10) + String.valueOf(val % 10);
    }

    /* a modifier */
    @Override
    public int compareTo(Carte carte) {
        return type.compareTo(carte.type) * 10 + (deplacement - carte.getDeplacement());
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
            throw new RuntimeException("Modele.Carte.clone() : Carte non clonable.");
        }
    }

    @Override
    public String toString() {
        return type.toString() + deplacement;
    }
}
