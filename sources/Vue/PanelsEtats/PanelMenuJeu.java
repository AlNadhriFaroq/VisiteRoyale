package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Patterns.Observateur;
import Vue.Bouton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PanelMenuJeu extends JPanel implements Observateur {
    Programme prog;
    Image img;
    Bouton boutonReprendre;
    Bouton boutonNouvellePartie;
    Bouton boutonSauvegardes;
    Bouton boutonOptions;
    Bouton boutonTutoriel;
    Bouton boutonRetour;

    public PanelMenuJeu(Programme prog) {
        this.prog = prog;

        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("Images" + File.separator + "bg.jpg"));
        } catch (Exception e) {
            throw new RuntimeException("Vue.PanelImage() : Impossible d'ouvrir l'image.\n" + e);
        }

        Box box = Box.createVerticalBox();
        box.add(Box.createGlue());
        box.add(creerBoutons());
        box.add(Box.createGlue());

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(Box.createGlue());
        add(box);
        for (int i = 0; i < 10; i++)
            add(Box.createGlue());
    }

    public Bouton getBoutonReprendre() {
        return boutonReprendre;
    }

    public Bouton getBoutonNouvellePartie() {
        return boutonNouvellePartie;
    }

    public Bouton getBoutonSauvegardes() {
        return boutonSauvegardes;
    }

    public Bouton getBoutonOptions() {
        return boutonOptions;
    }

    public Bouton getBoutonTutoriel() {
        return boutonTutoriel;
    }

    public Bouton getBoutonRetour() {
        return boutonRetour;
    }

    private JPanel creerBoutons() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setLayout(new GridLayout(7, 1, 0, 10));

        boutonReprendre = new Bouton("Reprendre");
        boutonNouvellePartie = new Bouton("Nouvelle partie");
        boutonSauvegardes = new Bouton("Sauvegardes");
        boutonOptions = new Bouton("Options");
        boutonTutoriel = new Bouton("Tutoriel");
        boutonRetour = new Bouton("Retour au menu principal");

        panel.add(boutonReprendre);
        panel.add(boutonNouvellePartie);
        panel.add(boutonSauvegardes);
        panel.add(boutonOptions);
        panel.add(boutonTutoriel);
        panel.add(boutonRetour);

        return panel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        dessin.drawImage(img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_MENU_JEU)
            repaint();
    }
}
