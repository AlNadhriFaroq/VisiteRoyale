package Vue;

import java.awt.*;

public class AnimationPanel {
    final CarteVue carteVue;
    final Point debut;
    final Point dest;
    final int stepX;
    final int stepY;
    int step;

    public AnimationPanel(CarteVue carteVue, Point debut, Point dest) {
        this.carteVue = carteVue;
        this.debut = debut;
        this.dest = dest;
        this.step = 20;
        this.stepX = (this.dest.x - this.debut.x) / step;
        this.stepY = (this.dest.y - this.debut.y) / step;
        carteVue.setVisible(true);
        carteVue.setLocation(this.debut);
    }

    public AnimationPanel(CarteVue c, Point dest) {
        this(c, new Point(c.getX(), c.getY()), dest);
    }

    boolean isDone() {
        return this.step == 0;
    }

    void EtapeSuivante() {
        this.step--;
        if (isDone()) {
            this.carteVue.setLocation(this.dest);
        } else {
            this.debut.x += this.stepX;
            this.debut.y += this.stepY;
            this.carteVue.setLocation(this.debut);
        }
        this.carteVue.repaint();
    }

    void FinAnimation() {
    }
}
