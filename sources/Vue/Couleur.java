package Vue;

public class Couleur {
    public static final String NOIR = "0";
    public static final String ROUGE = "1";
    public static final String VERT = "2";
    public static final String JAUNE = "3";
    public static final String BLEU = "4";
    public static final String VIOLET = "5";
    public static final String CYAN = "6";
    public static final String GRIS = "7";

    public static String formaterTexte(String texte, int tR, int tV, int tB, int fR, int fV, int fB, String format) {
        String txt = "\033[0;";
        txt += formater(format);
        txt += "3" + colorer(tR, tV, tB);
        txt += ";4" + colorer(fR, fV, fB);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formaterTexte(String texte, String couleurTexte, int fR, int fV, int fB, String format) {
        String txt = "\033[0;";
        txt += formater(format);
        txt += "3" + couleurTexte;
        txt += ";4" + colorer(fR, fV, fB);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formaterTexte(String texte, int tR, int tV, int tB, String couleurFond, String format) {
        String txt = "\033[0;";
        txt += formater(format);
        txt += "3" + colorer(tR, tV, tB);
        txt += ";4" + couleurFond;
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formaterTexte(String texte, String couleurTexte, String couleurFond, String format) {
        String txt = "\033[0;";
        txt += formater(format);
        txt += "3" + couleurTexte;
        txt += ";4" + couleurFond;
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formaterTexte(String texte, int tR, int tV, int tB, int fR, int fV, int fB) {
        String txt = "\033[0;";
        txt += "3" + colorer(tR, tV, tB);
        txt += ";4" + colorer(fR, fV, fB);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formaterTexte(String texte, String couleurTexte, int fR, int fV, int fB) {
        String txt = "\033[0;";
        txt += "3" + couleurTexte;
        txt += ";4" + colorer(fR, fV, fB);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formaterTexte(String texte, int tR, int tV, int tB, String format) {
        String txt = "\033[0;";
        txt += formater(format);
        txt += "3" + colorer(tR, tV, tB);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formaterTexte(String texte, String couleurTexte, String format) {
        String txt = "\033[0;";
        txt += formater(format);
        txt += "3" + couleurTexte;
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formaterTexte(String texte, int tR, int tV, int tB) {
        String txt = "\033[0;";
        txt += "3" + colorer(tR, tV, tB);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formaterTexte(String texte, String couleurTexte) {
        String txt = "\033[0;";
        txt += "3" + couleurTexte;
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    private static String colorer(int R, int V, int B) {
        return "8;2;" + R + ";" + V + ";" + B;
    }

    private static String formater(String format) {
        String txt = "";
        if (format.length() >= 1 && format.charAt(0) == '1')
            txt += "1;";
        if (format.length() >= 2 && format.charAt(1) == '1')
            txt += "3;";
        if (format.length() >= 3 && format.charAt(2) == '1')
            txt += "4;";
        if (format.length() >= 4 && format.charAt(3) == '1')
            txt += "7;";
        if (format.length() >= 5 && format.charAt(4) == '1')
            txt += "52;";
        return txt;
    }

    public static int[] obtenirCouleur(int i) {
        int[] res = new int[3];
        switch (i) {
            case 0:
                res[0] = 50;
                res[1] = 60;
                res[2] = 40;
                break;
            case 1:
                res[0] = 60;
                res[1] = 80;
                res[2] = 45;
                break;
            case 2:
                res[0] = 80;
                res[1] = 100;
                res[2] = 70;
                break;
            case 3:
                res[0] = 80;
                res[1] = 120;
                res[2] = 100;
                break;
            case 4:
                res[0] = 90;
                res[1] = 140;
                res[2] = 120;
                break;
            case 5:
                res[0] = 150;
                res[1] = 190;
                res[2] = 150;
                break;
            case 6:
                res[0] = 180;
                res[1] = 210;
                res[2] = 180;
                break;
            case 7:
                res[0] = 220;
                res[1] = 180;
                res[2] = 160;
                break;
            case 8:
                res[0] = 220;
                res[1] = 170;
                res[2] = 140;
                break;
            case 9:
                res[0] = 200;
                res[1] = 140;
                res[2] = 100;
                break;
            case 10:
                res[0] = 200;
                res[1] = 120;
                res[2] = 100;
                break;
            case 11:
                res[0] = 200;
                res[1] = 100;
                res[2] = 110;
                break;
            case 12:
                res[0] = 200;
                res[1] = 80;
                res[2] = 100;
                break;
            case 13:
                res[0] = 160;
                res[1] = 70;
                res[2] = 100;
                break;
            case 14:
                res[0] = 120;
                res[1] = 40;
                res[2] = 80;
                break;
            case 15:
                res[0] = 100;
                res[1] = 40;
                res[2] = 50;
                break;
            case 16:
                res[0] = 80;
                res[1] = 20;
                res[2] = 40;
                break;
        }
        return res;
    }
}
