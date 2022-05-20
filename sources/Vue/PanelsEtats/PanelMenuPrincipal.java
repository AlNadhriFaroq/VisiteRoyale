package Vue.PanelsEtats;

import Modele.Programme;
import Patterns.Observateur;
import Vue.Bouton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PanelMenuPrincipal extends JPanel implements Observateur {
    Programme prog;
    Image img;
    Bouton boutonJouer1vs1;
    Bouton boutonJouerVsIA;
    Bouton boutonSauvegardes;
    Bouton boutonOptions;
    Bouton boutonTutoriel;
    Bouton boutonCredits;
    Bouton boutonQuitter;

    public PanelMenuPrincipal(Programme prog) {
        this.prog = prog;

        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("Images" + File.separator + "bg.jpg"));
        } catch (Exception e) {
            throw new RuntimeException("Vue.PanelMenuPrincipal() : Impossible d'ouvrir l'image.\n" + e);
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

    public Bouton getBoutonJouer1vs1() {
        return boutonJouer1vs1;
    }

    public Bouton getBoutonJouerVsIA() {
        return boutonJouerVsIA;
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

    public Bouton getBoutonCredits() {
        return boutonCredits;
    }

    public Bouton getBoutonQuitter() {
        return boutonQuitter;
    }

    private JPanel creerBoutons() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setLayout(new GridLayout(7, 1, 0, 10));

        boutonJouer1vs1 = new Bouton("Jouer 1vs1");
        boutonJouerVsIA = new Bouton("Joueur contre IA");
        boutonSauvegardes = new Bouton("Sauvegardes");
        boutonOptions = new Bouton("Options");
        boutonTutoriel = new Bouton("Tutoriel");
        boutonCredits = new Bouton("Crédits");
        boutonQuitter = new Bouton("Quitter");

        panel.add(boutonJouer1vs1);
        panel.add(boutonJouerVsIA);
        panel.add(boutonSauvegardes);
        panel.add(boutonOptions);
        panel.add(boutonTutoriel);
        panel.add(boutonCredits);
        panel.add(boutonQuitter);

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
        if (prog.getEtat() == Programme.ETAT_MENU_PRINCIPAL)
            repaint();
    }
}
