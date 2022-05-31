package Vue.Composants.ComposantsMenus;

import javax.swing.*;
import java.awt.*;

public class BoutonSauvegarde extends JButton {
    public static final Color couleurNormal = new Color(253, 62, 69, 255);
    public static final Color couleurClaire = new Color(247, 225, 201, 255);
    public static final Color couleurFoncee = new Color(84, 12, 45, 255);

    public BoutonSauvegarde(String txt) {
        super(txt);

        setBorder(BorderFactory.createRaisedBevelBorder());
        setForeground(couleurClaire);
        setBackground(couleurNormal);
        setFocusable(false);
        setContentAreaFilled(false);
        setOpaque(true);
    }
}
