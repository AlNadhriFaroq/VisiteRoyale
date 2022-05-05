package Modele;

import java.util.ArrayList;
import java.util.List;

public class Plateau {
    public static final int BORDURE_VERT = 0;
    public static final int CHATEAU_VERT = 1;
    public static final int FONTAINE = 8;
    public static final int CHATEAU_ROUGE = 15;
    public static final int BORDURE_ROUGE = 16;

    public static final int VERS_VERT = -1;
    public static final int VERS_ROUGE = 1;

    public static final int PION_ROI = 0;
    public static final int PION_GRD_VERT = 1;
    public static final int PION_GRD_ROUGE = 2;
    public static final int PION_SRC = 3;
    public static final int PION_FOU = 4;

    List<Pion> pions;
    Jeton couronne;

    public Plateau(int directionQuiCommence) {
        pions = new ArrayList<>();
        pions.add(new Pion(new Type(Type.TYPE_ROI), FONTAINE));
        pions.add(new Pion(new Type(Type.TYPE_GRD), FONTAINE + 2*VERS_VERT));
        pions.add(new Pion(new Type(Type.TYPE_GRD), FONTAINE + 2*VERS_ROUGE));
        pions.add(new Pion(new Type(Type.TYPE_SRC), FONTAINE + directionQuiCommence));
        pions.add(new Pion(new Type(Type.TYPE_FOU), FONTAINE - directionQuiCommence));
        couronne = new Jeton(Jeton.GRANDE_CRN, FONTAINE);
    }

    public int getPositionPion(int pion) {
        return pions.get(pion).getPosition();
    }

    public int getTypePion(int pion) {
        return pions.get(pion).getType();
    }

    public int getPositionCouronne() {
        return couronne.getPosition();
    }

    public boolean getFaceCouronne() {
        return couronne.getFace();
    }

    public int setPositionPion(int pion, int position) {
        if (pionEstDeplacable(pion, position))
            return pions.get(pion).setPosition(position);
        return -1;
    }

    public int setPositionPion(int pion, int deplacement, int direction) {
        if (pionEstDeplacable(pion, deplacement, direction))
            return pions.get(pion).setPosition(deplacement, direction);
        return -1;
    }

    public int setPositionCouronne(int position) {
        if (couronneEstDeplacable(position))
            return couronne.setPosition(position);
        return -1;
    }

    public int setPositionCouronne(int deplacement, int direction) {
        if (couronneEstDeplacable(deplacement, direction))
            return couronne.setPosition(deplacement, direction);
        return -1;
    }

    public boolean setFaceCouronne(boolean face) {
        return couronne.setFace(face);
    }

    public boolean alternerFaceCouronne() {
        return couronne.alternerFace();
    }

    public boolean pionEstDeplacable(int pion, int position) {
        if (position >= BORDURE_VERT && position <= BORDURE_ROUGE) {
            if (pion == PION_ROI) {
                if (position >= getPositionPion(PION_GRD_VERT) && position <= getPositionPion(PION_GRD_ROUGE))
                    return true;
            } else if (pion == PION_GRD_VERT) {
                if (position < getPositionPion(PION_ROI) && position < getPositionPion(PION_GRD_ROUGE))
                    return true;
            } else if (pion == PION_GRD_ROUGE) {
                if (position > getPositionPion(PION_ROI) && position > getPositionPion(PION_GRD_VERT))
                    return true;
            } else if (pion == PION_SRC || pion == PION_FOU) {
                return true;
            } else {
                throw new RuntimeException("Pion invalide");
            }
        }
        return false;
    }

    public boolean pionEstDeplacable(int pion, int deplacement, int direction) {
        int position = getPositionPion(pion) + direction*deplacement;
        return pionEstDeplacable(pion, position);
    }

    public boolean couronneEstDeplacable(int position) {
        return (position >= BORDURE_VERT && position <= BORDURE_ROUGE);
    }

    public boolean couronneEstDeplacable(int deplacement, int direction) {
        int position = getPositionCouronne() + direction*deplacement;
        return couronneEstDeplacable(position);
    }

    public boolean pionEstDansDucheVert(int pion) {
        return getPositionPion(pion) < FONTAINE;
    }

    public boolean pionEstDansDucheRouge(int pion) {
        return getPositionPion(pion) > FONTAINE;
    }

    public boolean pionEstDansChateauVert(int pion) {
        return getPositionPion(pion) <= CHATEAU_VERT;
    }

    public boolean pionEstDansChateauRouge(int pion) {
        return getPositionPion(pion) >= CHATEAU_ROUGE;
    }

    public boolean couronneEstDansChateauVert() {
        return getPositionCouronne() <= CHATEAU_VERT;
    }

    public boolean couronneEstDansChateauRouge() {
        return getPositionCouronne() >= CHATEAU_ROUGE;
    }

    public boolean pionEstDansFontaine(int pion) {
        return getPositionPion(pion) == Plateau.FONTAINE;
    }

    public boolean estTerminee() {
        return (pionEstDansChateauVert(PION_ROI) || pionEstDansChateauRouge(PION_ROI) ||
                couronneEstDansChateauVert() || couronneEstDansChateauRouge());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Plateau plateau = (Plateau) o;

        if (!couronne.equals(plateau.couronne))
            return false;

        for (int i = 0; i < pions.size(); i++)
            if (!pions.get(i).equals(plateau.pions.get(i)))
                return false;

        return true;
    }

    @Override
    public Plateau clone() {
        try {
            Plateau resultat = (Plateau) super.clone();
            for (int i = 0; i < pions.size(); i++)
                resultat.pions.add(pions.get(i).clone());
            resultat.couronne = couronne.clone();
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Bug interne, pion non clonable");
        }
    }

    @Override
    public String toString() {
        String txt = "";

        for (int l = 0; l < 4; l++) {
            for (int c = BORDURE_VERT; c <= BORDURE_ROUGE; c++) {
                if (l == 0 && c == getPositionCouronne())
                    txt += couronne.toString();
                else if (l == 1 && c == getPositionPion(PION_SRC))
                    txt += pions.get(PION_SRC).toString();
                else if (l == 2 && c == getPositionPion(PION_GRD_VERT))
                    txt += pions.get(PION_GRD_VERT).toString();
                else if (l == 2 && c == getPositionPion(PION_ROI))
                    txt += pions.get(PION_ROI).toString();
                else if (l == 2 && c == getPositionPion(PION_GRD_ROUGE))
                    txt += pions.get(PION_GRD_ROUGE).toString();
                else if (l == 3 && c == getPositionPion(PION_FOU))
                    txt += pions.get(PION_FOU).toString();
                else
                    txt += "_";
                if (c != BORDURE_ROUGE)
                    txt += " ";
            }
            txt += "\n";
        }

        return txt;
    }
}
