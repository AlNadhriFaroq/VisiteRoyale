package Global;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class Images {
    private static String dossier = "Images";

    public static final Image CARTE_VIDE = lireImage("Cartes" + File.separator + "Vide");
    public static final Image CARTE_DOS = lireImage("Cartes" + File.separator + "Dos");
    public static final Image CARTE_R1 = lireImage("Cartes" + File.separator + "R1");
    public static final Image CARTE_G1 = lireImage("Cartes" + File.separator + "G1");
    public static final Image CARTE_G2 = lireImage("Cartes" + File.separator + "G2");
    public static final Image CARTE_GC = lireImage("Cartes" + File.separator + "GC");
    public static final Image CARTE_S1 = lireImage("Cartes" + File.separator + "S1");
    public static final Image CARTE_S2 = lireImage("Cartes" + File.separator + "S2");
    public static final Image CARTE_S3 = lireImage("Cartes" + File.separator + "S3");
    public static final Image CARTE_F1 = lireImage("Cartes" + File.separator + "F1");
    public static final Image CARTE_F2 = lireImage("Cartes" + File.separator + "F2");
    public static final Image CARTE_F3 = lireImage("Cartes" + File.separator + "F3");
    public static final Image CARTE_F4 = lireImage("Cartes" + File.separator + "F4");
    public static final Image CARTE_F5 = lireImage("Cartes" + File.separator + "F5");
    public static final Image CARTE_FM = lireImage("Cartes" + File.separator + "FM");

    public static final Image PION_R = lireImage("Pions" + File.separator + "R");
    public static final Image PION_GV = lireImage("Pions" + File.separator + "GV");
    public static final Image PION_GR = lireImage("Pions" + File.separator + "GR");
    public static final Image PION_S = lireImage("Pions" + File.separator + "S");
    public static final Image PION_F = lireImage("Pions" + File.separator + "F");

    public static final Image COURONNE_GRD = lireImage("Pions" + File.separator + "CouronneGrd");
    public static final Image COURONNE_PTT = lireImage("Pions" + File.separator + "CouronnePtt");

    public static final Image TEXTE_TITRE = lireImage("Textes" + File.separator + "Titre");
    public static final Image TEXTE_DEFAITE = lireImage("Textes" + File.separator + "Defaite");
    public static final Image TEXTE_VICTOIRE = lireImage("Textes" + File.separator + "Victoire");
    public static final Image TEXTE_VICTOIRE_VRT = lireImage("Textes" + File.separator + "VictoireJoueurVrt");
    public static final Image TEXTE_VICTOIRE_RGE = lireImage("Textes" + File.separator + "VictoireJoueurRge");
    public static final Image TEXTE_ANNULER_REFAIRE = lireImage("Textes" + File.separator + "AnnulerRefaire");
    public static final Image TEXTE_PRECEDENT_SUIVANT = lireImage("Textes" + File.separator + "PrecedentSuivant");
    public static final Image TEXTE_OUVRIR_MENU = lireImage("Textes" + File.separator + "OuvrirMenu");
    public static final Image TEXTE_INDICE = lireImage("Textes" + File.separator + "Ampoule");
    public static final Image TEXTE_SUPPRIMER = lireImage("Textes" + File.separator + "Supprimer");

    public static final Image CHATEAU_VRT = lireImage("Decors" + File.separator + "ChateauVrt");
    public static final Image CHATEAU_RGE = lireImage("Decors" + File.separator + "ChateauRge");
    public static final Image POUVOIR_SOR = lireImage("Decors" + File.separator + "PouvoirSor");
    public static final Image POUVOIR_FOU = lireImage("Decors" + File.separator + "PouvoirFou");

    public static final Image FOND_JEU = lireImage("Fonds" + File.separator + "FondBoisFonce");
    public static final Image FOND_MENU = lireImage("Fonds" + File.separator + "FondDosCarte");

    private static Image lireImage(String nom) {
        try {
            return ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(dossier + File.separator + nom + ".png")));
        } catch (Exception e) {
            throw new RuntimeException("Global.Images.lireImage() : Impossible d'ouvrir l'image.\n" + e);
        }
    }

    public static Image getImageCarte(String nom) {
        return lireImage("Cartes" + File.separator + nom);
    }

    public static Image getImagePion(String nom) {
        return lireImage("Pions" + File.separator + nom);
    }

    public static void setTexture(String texture) {
        Configuration.instance().ecrire("Texture", texture);
        switch (texture) {
            case "Daltonien":
                dossier = "Images";
            default:
                dossier = "Images";
        }
    }

    public static Image tournerImage(Image source, int angleRotation) {
        BufferedImage src = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D dessinSrc = src.createGraphics();
        dessinSrc.drawImage(source, 0, 0, src.getWidth() - 1, src.getHeight() - 1, null);
        dessinSrc.dispose();

        double theta = (Math.PI * 2) / 360 * angleRotation;
        int largeur = src.getWidth();
        int hauteur = src.getHeight();

        BufferedImage dest;
        if (angleRotation == 90 || angleRotation == 270)
            dest = new BufferedImage(hauteur, largeur, src.getType());
        else
            dest = new BufferedImage(largeur, hauteur, src.getType());
        Graphics2D dessinDest = dest.createGraphics();

        if (angleRotation == 90) {
            dessinDest.translate((hauteur - largeur) / 2, (hauteur - largeur) / 2);
            dessinDest.rotate(theta, hauteur / 2, largeur / 2);
        } else if (angleRotation == 270) {
            dessinDest.translate((largeur - hauteur) / 2, (largeur - hauteur) / 2);
            dessinDest.rotate(theta, hauteur / 2, largeur / 2);
        } else {
            dessinDest.translate(0, 0);
            dessinDest.rotate(theta, largeur / 2, hauteur / 2);
        }
        dessinDest.drawRenderedImage(src, null);

        return dest.getScaledInstance(largeur - 1, hauteur - 1, Image.SCALE_DEFAULT);
    }
}
