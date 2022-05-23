package Vue;

import javax.swing.*;

public class Cadre extends Box {

    public Cadre(JComponent comp, int gauche, int droite, int haut, int bas) {
        super(BoxLayout.PAGE_AXIS);

        Box box = Box.createHorizontalBox();
        for (int i = 0; i < gauche; i++)
            box.add(Box.createHorizontalGlue());
        box.add(comp);
        for (int i = 0; i < droite; i++)
            box.add(Box.createHorizontalGlue());

        for (int i = 0; i < haut; i++)
            add(Box.createVerticalGlue());
        add(box);
        for (int i = 0; i < bas; i++)
            add(Box.createVerticalGlue());
    }
}
