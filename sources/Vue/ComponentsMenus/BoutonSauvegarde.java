package Vue.ComponentsMenus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoutonSauvegarde extends JButton {
    private final Color couleurNormal = new Color(253, 62, 69, 255);
    private final Color couleurClaire = new Color(247, 225, 201, 255);
    private final Color couleurFoncee = new Color(84, 12, 45, 255);

    public BoutonSauvegarde(String txt) {
        super(txt);

        setBorder(BorderFactory.createRaisedBevelBorder());
        setForeground(couleurClaire);
        setBackground(couleurNormal);
        setFocusable(false);
        setContentAreaFilled(false);
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(couleurFoncee);
                setBackground(couleurClaire);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(couleurClaire);
                setBackground(couleurNormal);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBorder(BorderFactory.createLoweredBevelBorder());
                setForeground(couleurNormal);
                setBackground(couleurFoncee);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBorder(BorderFactory.createRaisedBevelBorder());
                setForeground(couleurClaire);
                setBackground(couleurNormal);
            }
        });
    }
}
