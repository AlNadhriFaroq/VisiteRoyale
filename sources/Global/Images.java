package Global;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;

public class Images {
    private static String texture = "Images";

    public static final Image CARTE_VIDE = lireImage("Cartes/Vide");
    public static final Image CARTE_DOS = lireImage("Cartes/Dos");
    public static final Image CARTE_R1 = lireImage("Cartes/R1");
    public static final Image CARTE_G1 = lireImage("Cartes/G1");
    public static final Image CARTE_G2 = lireImage("Cartes/G2");
    public static final Image CARTE_GC = lireImage("Cartes/GC");
    public static final Image CARTE_S1 = lireImage("Cartes/S1");
    public static final Image CARTE_S2 = lireImage("Cartes/S2");
    public static final Image CARTE_S3 = lireImage("Cartes/S3");
    public static final Image CARTE_F1 = lireImage("Cartes/F1");
    public static final Image CARTE_F2 = lireImage("Cartes/F2");
    public static final Image CARTE_F3 = lireImage("Cartes/F3");
    public static final Image CARTE_F4 = lireImage("Cartes/F4");
    public static final Image CARTE_F5 = lireImage("Cartes/F5");
    public static final Image CARTE_FM = lireImage("Cartes/FM");

    public static final Image PION_R = lireImage("Pions/R");
    public static final Image PION_GV = lireImage("Pions/GV");
    public static final Image PION_GR = lireImage("Pions/GR");
    public static final Image PION_S = lireImage("Pions/S");
    public static final Image PION_F = lireImage("Pions/F");

    public static final Image COURONNE_GRD = lireImage("Pions/CouronneGrd");
    public static final Image COURONNE_PTT = lireImage("Pions/CouronnePtt");

    public static final Image TEXTE_TITRE = lireImage("Textes/Titre");
    public static final Image TEXTE_DEFAITE = lireImage("Textes/Defaite");
    public static final Image TEXTE_VICTOIRE = lireImage("Textes/Victoire");
    public static final Image TEXTE_VICTOIRE_VRT = lireImage("Textes/VictoireJoueurVrt");
    public static final Image TEXTE_VICTOIRE_RGE = lireImage("Textes/VictoireJoueurRge");
    public static final Image TEXTE_ANNULER_REFAIRE = lireImage("Textes/AnnulerRefaire");
    public static final Image TEXTE_PRECEDENT_SUIVANT = lireImage("Textes/PrecedentSuivant");
    public static final Image TEXTE_OUVRIR_MENU = lireImage("Textes/OuvrirMenu");
    public static final Image TEXTE_INDICE = lireImage("Textes/Ampoule");
    public static final Image TEXTE_SUPPRIMER = lireImage("Textes/Supprimer");
    public static final Image TEXTE_FIN_TOUR = lireImage("Textes/FinTour");

    public static final Image CHATEAU_VRT = lireImage("Decors/ChateauVrt");
    public static final Image CHATEAU_RGE = lireImage("Decors/ChateauRge");
    public static final Image POUVOIR_SOR = lireImage("Decors/PouvoirSor");
    public static final Image POUVOIR_FOU = lireImage("Decors/PouvoirFou");
    public static final Image ICONE = lireImage("Decors/Icone");

    public static final Image FOND_JEU = lireImage("Fonds/FondBoisFonce");
    public static final Image FOND_MENU = lireImage("Fonds/FondDosCarte");

    private static Image lireImage(String nom) {
        String chemin = "/" + texture + "/" + nom + ".png";
        try {
            return ImageIO.read(new BufferedInputStream(Images.class.getResourceAsStream(chemin)));
        } catch (Exception e) {
            throw new RuntimeException("Global.Images.lireImage() : Impossible d'ouvrir l'image '" + chemin + "'.\n" + e);
        }
    }

    public static Image getImageCarte(String nom) {
        return lireImage("Cartes/" + nom);
    }

    public static Image getImagePion(String nom) {
        return lireImage("Pions/" + nom);
    }

    public static void setTexture(String texture) {
        Configuration.instance().ecrire("Texture", texture);
        switch (texture) {
            case "Daltonien":
                texture = "Images";
            default:
                texture = "Images";
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
