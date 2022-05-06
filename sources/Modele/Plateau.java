package Modele;

public class Plateau implements Cloneable {
    public static final int BORDURE_VRT = 0;
    public static final int CHATEAU_VRT = 1;
    public static final int FONTAINE = 8;
    public static final int CHATEAU_RGE = 15;
    public static final int BORDURE_RGE = 16;

    public static final int DIRECTION_VRT = -1;
    public static final int DIRECTION_RGE = 1;

    private Pion[] pions;
    private Jeton couronne;

    Plateau(int directionQuiCommence) {
        pions = new Pion[5];
        pions[Pion.ROI] = new Pion(Type.ROI, FONTAINE);
        pions[Pion.GAR_VRT] = new Pion(Type.GAR, FONTAINE + 2*DIRECTION_VRT);
        pions[Pion.GAR_RGE] = new Pion(Type.GAR, FONTAINE + 2*DIRECTION_RGE);
        pions[Pion.SOR] = new Pion(Type.SOR, FONTAINE + directionQuiCommence);
        pions[Pion.FOU] = new Pion(Type.FOU, FONTAINE - directionQuiCommence);
        couronne = new Jeton(FONTAINE);
    }

    public Pion getPion(int pion) {
        return pions[pion];
    }

    public Jeton getCouronne() {
        return couronne;
    }

    int getDeplacementCouronneVrt() {
        int deplacement = 0;

        for (int pion = Pion.ROI; pion <= Pion.FOU; pion++)
            if (pionDansChateauVrt(pion))
                deplacement++;

        if (pionDansDucheVrt(Pion.GAR_VRT) && pionDansDucheVrt(Pion.ROI) && pionDansDucheVrt(Pion.GAR_RGE))
            deplacement++;

        return deplacement;
    }

    int getDeplacementCouronneRge() {
        int deplacement = 0;

        for (int pion = Pion.ROI; pion <= Pion.FOU; pion++)
            if (pionDansChateauRge(pion))
                deplacement++;

        if (pionDansDucheRge(Pion.GAR_VRT) && pionDansDucheRge(Pion.ROI) && pionDansDucheRge(Pion.GAR_RGE))
            deplacement++;

        return deplacement;
    }

    public boolean pionEstDeplacable(int pion, int destination) {
        return destination >= BORDURE_VRT && destination <= BORDURE_RGE &&
               ((pion == Pion.ROI && destination > getPion(Pion.GAR_VRT).getPosition() && destination < getPion(Pion.GAR_RGE).getPosition()) ||
                (pion == Pion.GAR_VRT && destination < getPion(Pion.ROI).getPosition() && destination < getPion(Pion.GAR_RGE).getPosition()) ||
                (pion == Pion.GAR_RGE && destination > getPion(Pion.ROI).getPosition() && destination > getPion(Pion.GAR_VRT).getPosition()) ||
                pion == Pion.SOR || pion == Pion.FOU);
    }

    public boolean couronneEstDeplacable(int destination) {
        return (destination >= BORDURE_VRT && destination <= BORDURE_RGE);
    }

    public boolean pionDansDucheVrt(int pion) {
        return getPion(pion).getPosition() < FONTAINE;
    }

    public boolean pionDansDucheRge(int pion) {
        return getPion(pion).getPosition() > FONTAINE;
    }

    public boolean pionDansChateauVrt(int pion) {
        return getPion(pion).getPosition() <= CHATEAU_VRT;
    }

    public boolean pionDansChateauRge(int pion) {
        return getPion(pion).getPosition() >= CHATEAU_RGE;
    }

    public boolean couronneDansChateauVrt() {
        return getCouronne().getPosition() <= CHATEAU_VRT;
    }

    public boolean couronneDansChateauRge() {
        return getCouronne().getPosition() >= CHATEAU_RGE;
    }

    public boolean pionDansFontaine(int pion) {
        return getPion(pion).getPosition() == FONTAINE;
    }

    public boolean peutUtiliserPrivilegeRoi(int direction) {
        return (direction == DIRECTION_VRT && getPion(Pion.GAR_VRT).getPosition() != BORDURE_VRT) ||
               (direction == DIRECTION_RGE && getPion(Pion.GAR_RGE).getPosition() != BORDURE_RGE);
    }

    public boolean peutUtiliserPouvoirSor(int pion) {
        return pion != Pion.FOU && pion != Pion.SOR &&
               getPion(pion).getPosition() != getPion(Pion.SOR).getPosition() &&
               pionEstDeplacable(pion, getPion(Pion.SOR).getPosition());
    }

    public boolean vrtPeutUtiliserPouvoirFou() {
        return getPion(Pion.FOU).getPosition() < getPion(Pion.ROI).getPosition() &&
               getPion(Pion.FOU).getPosition() > CHATEAU_VRT;
    }

    public boolean vrtPeutUtiliserPouvoirFou(int pion, int destination) {
        return vrtPeutUtiliserPouvoirFou() &&
               getPion(pion).getType() != Type.FOU &&
               pionEstDeplacable(pion, destination);
    }

    public boolean rgePeutUtiliserPouvoirFou() {
        return getPion(Pion.FOU).getPosition() > getPion(Pion.ROI).getPosition() &&
               getPion(Pion.FOU).getPosition() < CHATEAU_RGE;
    }

    public boolean rgePeutUtiliserPouvoirFou(int pion, int destination) {
        return rgePeutUtiliserPouvoirFou() &&
               getPion(pion).getType() != Type.FOU &&
               pionEstDeplacable(pion, destination);
    }

    public boolean estTerminee() {
        return (pionDansChateauVrt(Pion.ROI) || pionDansChateauRge(Pion.ROI) ||
                couronneDansChateauVrt() || couronneDansChateauRge());
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

        for (int i = 0; i < pions.length; i++)
            if (!pions[i].equals(plateau.pions[i]))
                return false;

        return true;
    }

    @Override
    public Plateau clone() {
        try {
            Plateau resultat = (Plateau) super.clone();
            resultat.pions = new Pion[pions.length];
            for (int i = 0; i < pions.length; i++)
                resultat.pions[i] = pions[i].clone();
            resultat.couronne = couronne.clone();
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Plateau.clone() : Plateau non clonable.");
        }
    }

    @Override
    public String toString() {
        String txt = "";

        for (int l = 0; l < 4; l++) {
            for (int c = BORDURE_VRT; c <= BORDURE_RGE; c++) {
                if (l == 0 && c == getCouronne().getPosition())
                    txt += couronne.toString();
                else if (l == 1 && c == getPion(Pion.SOR).getPosition())
                    txt += pions[Pion.SOR].toString();
                else if (l == 2 && c == getPion(Pion.GAR_VRT).getPosition())
                    txt += pions[Pion.GAR_VRT].toString();
                else if (l == 2 && c == getPion(Pion.ROI).getPosition())
                    txt += pions[Pion.ROI].toString();
                else if (l == 2 && c == getPion(Pion.GAR_RGE).getPosition())
                    txt += pions[Pion.GAR_RGE].toString();
                else if (l == 3 && c == getPion(Pion.FOU).getPosition())
                    txt += pions[Pion.FOU].toString();
                else
                    txt += "_";
                if (c != BORDURE_RGE)
                    txt += " ";
            }
            if (l != 3)
                txt += "\n";
        }

        return txt;
    }
}
