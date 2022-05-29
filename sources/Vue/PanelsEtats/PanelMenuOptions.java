package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.*;
import IA.IA;
import Modele.Programme;
import Vue.Adaptateurs.*;
import Vue.Composants.ComposantsMenus.BoutonMenu;
import Vue.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelMenuOptions extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    JSlider volumeMusique;
    JSlider volumeSons;
    JComboBox<String> musique;
    JComboBox<String> niveau;
    JComboBox<String> texture;
    JCheckBox pleinEcran;
    JCheckBox mainCachee;
    BoutonMenu boutonRetour;

    public PanelMenuOptions(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        /* Construction des composants */
        JLabel texteTitre = new JLabel("Options");
        texteTitre.setFont(new Font(null).deriveFont(30f));

        JLabel texteVolumeMusique = new JLabel("Volume de la musique");
        JLabel texteVolumeSons = new JLabel("Volume des effets sonores");
        JLabel texteMusique = new JLabel("Musique");
        JLabel texteNiveau = new JLabel("Niveau de difficulté de l'IA");
        JLabel textePleinEcran = new JLabel("Plein écran");
        JLabel texteMainCachee = new JLabel("Main adverse cachée");
        JLabel texteTexture = new JLabel("Texture");

        volumeMusique = new JSlider();
        volumeMusique.setBackground(new Color(0, 0, 0, 0));
        volumeMusique.setOpaque(false);
        volumeMusique.setMinimum(0);
        volumeMusique.setMaximum(10);
        volumeMusique.setMinorTickSpacing(1);
        volumeMusique.setMajorTickSpacing(1);
        volumeMusique.setPaintTicks(true);
        volumeMusique.setPaintLabels(true);
        volumeMusique.setSnapToTicks(true);
        volumeMusique.setValue(Integer.parseInt(Configuration.instance().lire("VolumeMusique")));

        volumeSons = new JSlider();
        volumeSons.setBackground(new Color(0, 0, 0, 0));
        volumeSons.setOpaque(false);
        volumeSons.setMinimum(0);
        volumeSons.setMaximum(10);
        volumeSons.setMinorTickSpacing(1);
        volumeSons.setMajorTickSpacing(1);
        volumeSons.setPaintTicks(true);
        volumeSons.setPaintLabels(true);
        volumeSons.setSnapToTicks(true);
        volumeSons.setValue(Integer.parseInt(Configuration.instance().lire("VolumeSons")));

        musique = new JComboBox<>();
        musique.setModel(new DefaultComboBoxModel<>(Audios.MUSIQUES));
        musique.setSelectedItem(Configuration.instance().lire("Musique"));

        niveau = new JComboBox<>();
        niveau.setModel(new DefaultComboBoxModel<>(new String[] {"Débutant", "Amateur", "Intermédiaire", "Professionnel", "Expert"}));
        niveau.setSelectedItem(IA.IAenTexte(Integer.parseInt(Configuration.instance().lire("NiveauDifficulteIA"))));

        texture = new JComboBox<>();
        texture.setModel(new DefaultComboBoxModel<>(new String[] {"Normal", "Daltonien"}));
        texture.setSelectedItem(Configuration.instance().lire("Texture"));

        pleinEcran = new JCheckBox();
        pleinEcran.setBackground(new Color(0, 0, 0, 0));
        pleinEcran.setOpaque(false);
        pleinEcran.setSelected(Boolean.parseBoolean(Configuration.instance().lire("PleinEcran")));

        mainCachee = new JCheckBox();
        mainCachee.setBackground(new Color(0, 0, 0, 0));
        mainCachee.setOpaque(false);
        mainCachee.setSelected(Boolean.parseBoolean(Configuration.instance().lire("MainCachee")));

        boutonRetour = new BoutonMenu("Retour");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 142, 225, 255));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setLayout(new GridBagLayout());

        /* Disposition dans le panel */
        panel.add(Box.createGlue(), new GBC(0, 0, 1, 11).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(3, 0, 1, 11).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(1, 0, 2, 0).setWeighty(10));
        panel.add(Box.createGlue(), new GBC(1, 10, 2, 1).setWeighty(10));

        panel.add(texteTitre, new GBC(1, 1).setWeighty(9).setAnchor(GBC.LINE_START));
        panel.add(texteVolumeMusique, new GBC(1, 2).setWeight(50, 9).setFill(GBC.BOTH));
        panel.add(volumeMusique, new GBC(2, 2).setWeight(30, 9).setFill(GBC.BOTH));
        panel.add(texteVolumeSons, new GBC(1, 3).setWeight(50, 9).setFill(GBC.BOTH));
        panel.add(volumeSons, new GBC(2, 3).setWeight(30, 9).setFill(GBC.BOTH));
        panel.add(texteMusique, new GBC(1, 4).setWeight(50, 9).setFill(GBC.BOTH));
        panel.add(musique, new GBC(2, 4).setWeight(30, 9).setFill(GBC.HORIZONTAL));
        panel.add(texteNiveau, new GBC(1, 5).setWeight(50, 9).setFill(GBC.BOTH));
        panel.add(niveau, new GBC(2, 5).setWeight(30, 9).setFill(GBC.HORIZONTAL));
        panel.add(textePleinEcran, new GBC(1, 6).setWeight(50, 9).setFill(GBC.BOTH));
        panel.add(pleinEcran, new GBC(2, 6).setWeight(30, 9).setAnchor(GBC.LINE_START));
        panel.add(texteMainCachee, new GBC(1, 7).setWeight(50, 9).setFill(GBC.BOTH));
        panel.add(mainCachee, new GBC(2, 7).setWeight(30, 9).setAnchor(GBC.LINE_START));
        panel.add(texteTexture, new GBC(1, 8).setWeight(50, 9).setFill(GBC.BOTH));
        panel.add(texture, new GBC(2, 8).setWeight(30, 9).setFill(GBC.HORIZONTAL));
        panel.add(boutonRetour, new GBC(2, 9).setWeighty(9).setAnchor(GBC.LINE_END));

        add(Box.createGlue(), new GBC(0, 0, 1, 3).setWeightx(40));
        add(Box.createGlue(), new GBC(2, 0, 1, 3).setWeightx(40));
        add(Box.createGlue(), new GBC(1, 0).setWeight(20, 25));
        add(Box.createGlue(), new GBC(1, 2).setWeight(20, 25));
        add(panel, new GBC(1, 1).setWeight(20, 50).setFill(GBC.BOTH));

        /* Retransmission des événements au contrôleur */
        volumeMusique.addChangeListener(new AdaptateurChangement(ctrl, fenetre, prog));
        volumeSons.addChangeListener(new AdaptateurChangement(ctrl, fenetre, prog));
        musique.addItemListener(new AdaptateurItem(ctrl, fenetre, prog));
        niveau.addItemListener(new AdaptateurItem(ctrl, fenetre, prog));
        texture.addItemListener(new AdaptateurItem(ctrl, fenetre, prog));
        pleinEcran.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        mainCachee.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonRetour.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonRetour.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
    }

    public JSlider getVolumeMusique() {
        return volumeMusique;
    }

    public JSlider getVolumeSons() {
        return volumeSons;
    }

    public JComboBox<String> getMusique() {
        return musique;
    }

    public JComboBox<String> getNiveau() {
        return niveau;
    }

    public JComboBox<String> getTexture() {
        return texture;
    }

    public JCheckBox getPleinEcran() {
        return pleinEcran;
    }

    public JCheckBox getMainCachee() {
        return mainCachee;
    }

    public BoutonMenu getBoutonRetour() {
        return boutonRetour;
    }

    public void mettreAJour() {
        // des/activer boutons
    }
}
