package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.*;
import IA.IA;
import Modele.Programme;
import Vue.Adaptateurs.*;
import Vue.ComponentsMenus.BoutonMenu;
import Vue.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelMenuOptions extends PanelEtat {
    BoutonMenu boutonMenuRetour;
    JSlider volumeMusique;
    JSlider volumeSons;
    JComboBox<String> musique;
    JComboBox<String> niveau;
    JComboBox<String> texture;
    JCheckBox pleinEcran;
    JCheckBox mainCachee;

    public PanelMenuOptions(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        setLayout(new GridBagLayout());

        /* Creation des components */
        JLabel texteTitre = new JLabel("Options");
        texteTitre.setFont(new Font(Font.DIALOG, 0, 30));
        JLabel texteVolumeMusique = new JLabel("Volume de la musique");
        JLabel texteVolumeSons = new JLabel("Volume des effets sonores");
        JLabel texteMusique = new JLabel("Musique");
        JLabel texteNiveau = new JLabel("Niveau de difficulté de l'IA");
        JLabel textePleinEcran = new JLabel("Plein écran");
        JLabel texteMainCachee = new JLabel("Main adverse cachée");
        JLabel texteTexture = new JLabel("Texture");

        volumeMusique = new JSlider();
        volumeMusique.setBackground(new Color(0, 0, 0, 0));
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
        pleinEcran.setSelected(Boolean.parseBoolean(Configuration.instance().lire("PleinEcran")));

        mainCachee = new JCheckBox();
        mainCachee.setBackground(new Color(0, 0, 0, 0));
        mainCachee.setSelected(Boolean.parseBoolean(Configuration.instance().lire("MainCachee")));

        boutonMenuRetour = new BoutonMenu("Retour");

        /* Lien avec les actions */
        volumeMusique.addChangeListener(new AdaptateurChange(ctrl, vue, prog));
        volumeSons.addChangeListener(new AdaptateurChange(ctrl, vue, prog));
        musique.addItemListener(new AdaptateurItem(ctrl, vue, prog));
        niveau.addItemListener(new AdaptateurItem(ctrl, vue, prog));
        texture.addItemListener(new AdaptateurItem(ctrl, vue, prog));
        pleinEcran.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        mainCachee.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        /* Disposition dans le panel */
        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 142, 225, 255));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setLayout(new GridBagLayout());
        panel.add(new JLabel(), new GridBagConstraints(0, 0, 1, 11, 0.10, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(new JLabel(), new GridBagConstraints(1, 0, 1, 1, 0.05, 0.15, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(texteTitre, new GridBagConstraints(1, 1, 1, 1, 0.30, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(texteVolumeMusique, new GridBagConstraints(1, 2, 1, 1, 0.50, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(volumeMusique, new GridBagConstraints(2, 2, 1, 1, 0.30, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(texteVolumeSons, new GridBagConstraints(1, 3, 1, 1, 0.50, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(volumeSons, new GridBagConstraints(2, 3, 1, 1, 0.30, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(texteMusique, new GridBagConstraints(1, 4, 1, 1, 0.50, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(musique, new GridBagConstraints(2, 4, 1, 1, 0.30, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(texteNiveau, new GridBagConstraints(1, 5, 1, 1, 0.50, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(niveau, new GridBagConstraints(2, 5, 1, 1, 0.30, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(textePleinEcran, new GridBagConstraints(1, 6, 1, 1, 0.50, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(pleinEcran, new GridBagConstraints(2, 6, 1, 1, 0.30, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(texteMainCachee, new GridBagConstraints(1, 7, 1, 1, 0.50, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(mainCachee, new GridBagConstraints(2, 7, 1, 1, 0.30, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(texteTexture, new GridBagConstraints(1, 8, 1, 1, 0.50, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(texture, new GridBagConstraints(2, 8, 1, 1, 0.30, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(boutonMenuRetour, new GridBagConstraints(2, 9, 1, 1, 0.50, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(new JLabel(), new GridBagConstraints(1, 10, 1, 1, 0.05, 0.15, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(new JLabel(), new GridBagConstraints(3, 0, 1, 11, 0.10, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        add(new JLabel(), new GridBagConstraints(0, 0, 1, 3, 0.35, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(new JLabel(), new GridBagConstraints(1, 0, 1, 1, 0.30, 0.15, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(panel, new GridBagConstraints(1, 1, 1, 1, 0.30, 0.70, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(new JLabel(), new GridBagConstraints(1, 2, 1, 1, 0.30, 0.15, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(new JLabel(), new GridBagConstraints(2, 0, 1, 3, 0.35, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
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
        return boutonMenuRetour;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_MENU_OPTIONS)
            repaint();
    }
}
