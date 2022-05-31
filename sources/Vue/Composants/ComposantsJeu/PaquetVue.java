package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.*;
import Vue.GBC;

import javax.swing.*;
import java.awt.*;

public class PaquetVue extends JPanel {
    public static final int MAIN_VRT = 0;
    public static final int MAIN_RGE = 1;
    public static final int SELECTION_VRT = 2;
    public static final int SELECTION_RGE = 3;

    Programme prog;
    int paquet;

    CarteVue[] cartesVue;

    public PaquetVue(Programme prog, int paquet) {
        this.prog = prog;
        this.paquet = paquet;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        cartesVue = new CarteVue[Jeu.TAILLE_MAIN];
        for (int i = 0; i < Jeu.TAILLE_MAIN; i++)
            cartesVue[i] = new CarteVue();
    }

    public CarteVue getCarteVue(int indice) {
        return cartesVue[indice];
    }

    public boolean contientCarteVue(CarteVue carteVue) {
        for (CarteVue cv : cartesVue)
            if (carteVue.equals(cv))
                return true;
        return false;
    }

    public void redimensionner(int hauteur) {
        Dimension dim = new Dimension(cartesVue.length * hauteur * Images.CARTE_VIDE.getWidth(null) / Images.CARTE_VIDE.getHeight(null), hauteur);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);
        setSize(dim);
        for (CarteVue carteVue : cartesVue)
            carteVue.redimensionner(hauteur * 8 / 9);
    }

    public void mettreAJour(boolean faceCachee, boolean parcourable, boolean selectionnable) {
        setVisible(false);

        Paquet paquet = null;
        int position = GBC.CENTER;
        switch (this.paquet) {
            case MAIN_VRT:
                paquet = prog.getJeu().getMain(Jeu.JOUEUR_VRT);
                position = GBC.PAGE_START;
                break;
            case MAIN_RGE:
                paquet = prog.getJeu().getMain(Jeu.JOUEUR_RGE);
                position = GBC.PAGE_END;
                break;
            case SELECTION_VRT:
                paquet = prog.getJeu().getSelectionCartes(Jeu.JOUEUR_VRT);
                break;
            case SELECTION_RGE:
                paquet = prog.getJeu().getSelectionCartes(Jeu.JOUEUR_RGE);
                break;
            default:
                throw new RuntimeException("Vue.Composants.ComposantsJeu.PaquetVue.mettreAJour() : Paquet invalide.");
        }

        removeAll();
        add(Box.createGlue(), new GBC(0, 0).setWeight(1, 1));
        for (int i = 0; i < paquet.getTaille(); i++) {
            cartesVue[i].mettreAJour(paquet.getCarte(i), faceCachee, parcourable, selectionnable && prog.getJeu().peutSelectionnerCarte(paquet.getCarte(i)));
            add(cartesVue[i], new GBC(i + 1, 0).setWeighty(1).setAnchor(position));
        }
        add(Box.createGlue(), new GBC(paquet.getTaille() + 1, 0).setWeight(1, 1));
        repaint();
        setVisible(true);
    }
}
