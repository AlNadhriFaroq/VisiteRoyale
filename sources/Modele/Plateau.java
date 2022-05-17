package Modele;

import java.io.Serializable;
import java.util.Hashtable;

public class Plateau implements Cloneable, Serializable {
    public static final int BORDURE_VRT = 0;
    public static final int CHATEAU_VRT = 1;
    public static final int FONTAINE = 8;
    public static final int CHATEAU_RGE = 15;
    public static final int BORDURE_RGE = 16;

    public static final int DIRECTION_VRT = -1;
    public static final int DIRECTION_IND = 0;
    public static final int DIRECTION_RGE = 1;

    public static final boolean FACE_GRD_CRN = false;
    public static final boolean FACE_PTT_CRN = true;

    private Hashtable<Pion, Integer> positionsPions;
    private boolean faceCouronne;
    private int positionCouronne;

    Plateau(int directionQuiCommence) {
        positionsPions = new Hashtable<>();
        positionsPions.put(Pion.ROI, FONTAINE);
        positionsPions.put(Pion.GAR_VRT, FONTAINE + 2 * DIRECTION_VRT);
        positionsPions.put(Pion.GAR_RGE, FONTAINE + 2 * DIRECTION_RGE);
        positionsPions.put(Pion.SOR, FONTAINE + directionQuiCommence);
        positionsPions.put(Pion.FOU, FONTAINE - directionQuiCommence);
        faceCouronne = FACE_GRD_CRN;
        positionCouronne = FONTAINE;
    }

    public int getPositionPion(Pion pion) {
        return positionsPions.get(pion);
    }

    public boolean getFaceCouronne() {
        return faceCouronne;
    }

    public int getPositionCouronne() {
        return positionCouronne;
    }

    void setPositionPion(Pion pion, int destination) {
        positionsPions.put(pion, destination);
    }

    void setPositionCouronne(int destination) {
        positionCouronne = destination;
    }

    void setFaceCouronne(boolean face) {
        faceCouronne = face;
    }

    int evaluerDeplacementCouronneVrt() {
        int deplacement = 0;

        for (Pion pion : positionsPions.keySet())
            if (pionDansChateauVrt(pion))
                deplacement++;

        if (pionDansDucheVrt(Pion.GAR_VRT) && pionDansDucheVrt(Pion.ROI) && pionDansDucheVrt(Pion.GAR_RGE))
            deplacement++;

        return deplacement;
    }

    int evaluerDeplacementCouronneRge() {
        int deplacement = 0;

        for (Pion pion : positionsPions.keySet())
            if (pionDansChateauRge(pion))
                deplacement++;

        if (pionDansDucheRge(Pion.GAR_VRT) && pionDansDucheRge(Pion.ROI) && pionDansDucheRge(Pion.GAR_RGE))
            deplacement++;

        return deplacement;
    }

    public boolean pionDansDucheVrt(Pion pion) {
        return getPositionPion(pion) < FONTAINE;
    }

    public boolean pionDansDucheRge(Pion pion) {
        return getPositionPion(pion) > FONTAINE;
    }

    public boolean pionDansChateauVrt(Pion pion) {
        return getPositionPion(pion) <= CHATEAU_VRT;
    }

    public boolean pionDansChateauRge(Pion pion) {
        return getPositionPion(pion) >= CHATEAU_RGE;
    }

    public boolean pionDansFontaine(Pion pion) {
        return getPositionPion(pion) == FONTAINE;
    }

    public boolean couronneDansChateauVrt() {
        return getPositionCouronne() <= CHATEAU_VRT;
    }

    public boolean couronneDansChateauRge() {
        return getPositionCouronne() >= CHATEAU_RGE;
    }

    public boolean pionEstDeplacable(Pion pion, int destination) {
        return destination >= BORDURE_VRT && destination <= BORDURE_RGE &&
                ((pion.equals(Pion.ROI) && destination > getPositionPion(Pion.GAR_VRT) && destination < getPositionPion(Pion.GAR_RGE)) ||
                        (pion.equals(Pion.GAR_VRT) && destination < getPositionPion(Pion.ROI) && destination < getPositionPion(Pion.GAR_RGE)) ||
                        (pion.equals(Pion.GAR_RGE) && destination > getPositionPion(Pion.ROI) && destination > getPositionPion(Pion.GAR_VRT)) ||
                        pion.equals(Pion.SOR) || pion.equals(Pion.FOU));
    }

    public boolean couronneEstDeplacable(int destination) {
        return (destination >= BORDURE_VRT && destination <= BORDURE_RGE);
    }

    public boolean peutUtiliserPrivilegeRoi(int direction) {
        return (direction == DIRECTION_VRT && getPositionPion(Pion.GAR_VRT) != BORDURE_VRT) ||
                (direction == DIRECTION_RGE && getPositionPion(Pion.GAR_RGE) != BORDURE_RGE);
    }

    public boolean peutUtiliserPouvoirSor() {
        return getPositionPion(Pion.GAR_VRT) != getPositionPion(Pion.SOR) &&
                getPositionPion(Pion.ROI) != getPositionPion(Pion.SOR) &&
                getPositionPion(Pion.GAR_RGE) != getPositionPion(Pion.SOR);
    }

    public boolean peutUtiliserPouvoirSor(Pion pion) {
        return !pion.equals(Pion.FOU) && !pion.equals(Pion.SOR) &&
                pionEstDeplacable(pion, getPositionPion(Pion.SOR));
    }

    public boolean vrtPeutUtiliserPouvoirFou() {
        return getPositionPion(Pion.FOU) < getPositionPion(Pion.ROI);
    }

    public boolean rgePeutUtiliserPouvoirFou() {
        return getPositionPion(Pion.FOU) > getPositionPion(Pion.ROI);
    }

    public boolean estTerminee() {
        return (pionDansChateauVrt(Pion.ROI) || pionDansChateauRge(Pion.ROI) ||
                couronneDansChateauVrt() || couronneDansChateauRge());
    }

    public static int texteEnDirection(String texte) {
        switch (texte.toUpperCase()) {
            case "G":
            case "GAUCHE":
            case "V":
            case "VERT":
            case "-1":
                return DIRECTION_VRT;
            case "D":
            case "DROITE":
            case "R":
            case "ROUGE":
            case "1":
                return DIRECTION_RGE;
            default:
                throw new RuntimeException("Modele.Plateau.texteEnDirection() : Texte entré invalide.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Plateau plateau = (Plateau) o;

        return positionsPions.equals(plateau.positionsPions) &&
                faceCouronne == plateau.faceCouronne &&
                positionCouronne == plateau.positionCouronne;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Plateau clone() {
        try {
            Plateau resultat = (Plateau) super.clone();
            resultat.positionsPions = (Hashtable<Pion, Integer>) positionsPions.clone();
            resultat.faceCouronne = faceCouronne;
            resultat.positionCouronne = positionCouronne;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Plateau.clone() : Plateau non clonable.");
        }
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder();

        for (int l = 0; l < 4; l++) {
            for (int c = BORDURE_VRT; c <= BORDURE_RGE; c++) {
                if (l == 0 && c == getPositionCouronne())
                    if (faceCouronne == FACE_GRD_CRN)
                        txt.append("C ");
                    else
                        txt.append("c ");
                else if (l == 1 && c == getPositionPion(Pion.SOR))
                    txt.append(Pion.SOR).append(" ");
                else if (l == 2 && c == getPositionPion(Pion.GAR_VRT))
                    txt.append(Pion.GAR_VRT);
                else if (l == 2 && c == getPositionPion(Pion.ROI))
                    txt.append(Pion.ROI).append(" ");
                else if (l == 2 && c == getPositionPion(Pion.GAR_RGE))
                    txt.append(Pion.GAR_RGE);
                else if (l == 3 && c == getPositionPion(Pion.FOU))
                    txt.append(Pion.FOU).append(" ");
                else
                    txt.append("__");
                if (c != BORDURE_RGE)
                    txt.append(" ");
            }
            if (l != 3)
                txt.append("\n");
        }

        return txt.toString();
    }
}
