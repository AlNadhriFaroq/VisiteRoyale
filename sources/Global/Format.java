package Global;

import java.awt.*;

public class Format {
    public static String formater(String texte, Color couleurTexte, Color couleurFond, String format) {
        String txt = "\033[0;";
        txt += formaterTexte(format);
        txt += "3" + colorerTexte(couleurTexte);
        txt += ";4" + colorerTexte(couleurFond);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formater(String texte, Color couleurTexte, Color couleurFond) {
        String txt = "\033[0;";
        txt += "3" + colorerTexte(couleurTexte);
        txt += ";4" + colorerTexte(couleurFond);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formater(String texte, Color couleurTexte, String format) {
        String txt = "\033[0;";
        txt += formaterTexte(format);
        txt += "3" + colorerTexte(couleurTexte);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    public static String formater(String texte, Color couleurTexte) {
        String txt = "\033[0;";
        txt += "3" + colorerTexte(couleurTexte);
        txt += "m" + texte + "\033[0m";
        return txt;
    }

    private static String colorerTexte(Color couleur) {
        return "8;2;" + couleur.getRed() + ";" + couleur.getGreen() + ";" + couleur.getBlue();
    }

    private static String formaterTexte(String format) {
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

    public static Color getCouleurCase(int i) {
        switch (i) {
            case 0:
                return new Color(40, 55, 35);
            case 1:
                return new Color(50, 75, 40);
            case 2:
                return new Color(65, 90, 60);
            case 3:
                return new Color(45, 110, 95);
            case 4:
                return new Color(50, 140, 130);
            case 5:
                return new Color(130, 180, 145);
            case 6:
                return new Color(170, 210, 180);
            case 7:
                return new Color(215, 180, 160);
            case 8:
                return new Color(220, 170, 135);
            case 9:
                return new Color(230, 150, 100);
            case 10:
                return new Color(240, 125, 110);
            case 11:
                return new Color(240, 100, 115);
            case 12:
                return new Color(225, 80, 100);
            case 13:
                return new Color(180, 60, 95);
            case 14:
                return new Color(120, 40, 80);
            case 15:
                return new Color(90, 30, 50);
            case 16:
                return new Color(75, 20, 35);
            default:
                return Color.BLACK;
        }
    }

    public static Color getCouleurType(String type) {
        switch (type) {
            case "R":
                return new Color(190, 68, 125);
            case "G":
            case "GV":
            case "GR":
                return new Color(149, 169, 177);
            case "S":
                return new Color(255, 133, 55);
            case "F":
                return new Color(100, 199, 194);
            default:
                return Color.BLACK;
        }
    }
}
