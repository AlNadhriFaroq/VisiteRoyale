package Vue;

import Controleur.ControleurMediateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MenuPanel extends JPanel implements MouseInputListener {

    private JeuVue jeuVue;
    private ControleurMediateur ctrl;
    private static String path = "/Images/Boutons/";
    private  static String base = "PAUSE";
    private static String entered = "PAUSE2";
    private int largeur;


    private Image image;
    private Image imageBase;
    private Image imageEntered;

    public MenuPanel(JeuVue j, ControleurMediateur ctrl){
        this.jeuVue = j;
        this.ctrl = ctrl;
        this.largeur = this.jeuVue.frame.getHeight()/10;
        this.setSize(this.largeur, this.largeur);



        try{
            this.imageBase = ImageIO.read(getClass().getResource(path + base+".png"));
            this.imageBase = this.imageBase.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            this.imageEntered = ImageIO.read(getClass().getResource(path + entered+".png"));
            this.imageEntered = this.imageEntered.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        }catch (IOException e){}

        this.image = imageBase;


        this.setLocation(this.jeuVue.width - this.largeur - (this.largeur/2) , 5   );
        this.setOpaque(false);
        this.setVisible(true);

        this.addMouseMotionListener(this);
        this.addMouseListener(this);

    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), this);
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        //TODO APPEL A LA FRAME MENU PAUSE
        System.out.println("MON MENU");
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        this.image = this.imageEntered;
        this.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.image = this.imageBase;
        this.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
