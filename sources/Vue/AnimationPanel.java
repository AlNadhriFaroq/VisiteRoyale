package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationPanel {
    final CarteVue carteVue;
    final Point debut;
    final Point dest;
    final Dimension dimension;
    final int stepX;
    final int stepY;
    final int delai;
    int step;
    public Timer timer;

    public AnimationPanel(CarteVue carteVue, Point debut, Point dest, Dimension d, int delai) {
        this.carteVue = carteVue;
        this.debut = debut;
        this.dest = dest;
        this.dimension = d;
        this.step = 20;
        this.stepX = (this.dest.x - this.debut.x) / step;
        this.stepY = (this.dest.y - this.debut.y) / step;
        carteVue.setVisible(true);
        carteVue.setLocation(this.debut);
        //this.delai = 25;
        this.delai = delai;
        timer = new Timer(delai, new MvtListener());
    }

    public AnimationPanel(CarteVue c, Point dest, Dimension d) {
        this(c, new Point(c.getX(), c.getY()), dest, d, 25);
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
    public void AnimStart(){
        this.timer.start();
    }

    private class MvtListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            EtapeSuivante();
            if (isDone()){
                ( (Timer) e.getSource() ).stop();
                carteVue.setSize(dimension);
            }




        }
    }
}
