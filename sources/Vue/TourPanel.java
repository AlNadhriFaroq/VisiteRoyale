package Vue;
import Controleur.ControleurMediateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class TourPanel extends JPanel implements MouseInputListener {

private JeuVue jeuVue;
private ControleurMediateur ctrl;
private static String path = "/Images/Autres/";
private  static String bleuCol = "TourBleue";
private  static String bleuGris = "TourBleueToutGris";
private  static String rougeCol = "TourRouge";
private  static String rougeGris = "TourRougeToutGris";

private int largeur;
private int hauteur;


private Image image;
private Image bleuBase;
private Image bleuSec;
private Image rougeBase;
private Image rougeSec;

public TourPanel(JeuVue j, ControleurMediateur ctrl){
        this.jeuVue = j;
        this.ctrl = ctrl;
        this.hauteur = this.jeuVue.frame.getHeight()/10;
        this.largeur = this.hauteur + (this.hauteur / 10);
        this.setSize(this.largeur, this.hauteur);



        try{
        this.bleuBase = ImageIO.read(getClass().getResource(path + bleuCol +".png"));
        this.bleuBase = this.bleuBase.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        this.bleuSec = ImageIO.read(getClass().getResource(path + bleuGris+".png"));
        this.bleuSec = this.bleuSec.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        this.rougeBase = ImageIO.read(getClass().getResource(path + rougeCol +".png"));
        this.rougeBase = this.rougeBase.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        this.rougeSec = ImageIO.read(getClass().getResource(path + rougeGris+".png"));
        this.rougeSec = this.rougeSec.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);

        }catch (IOException e){}

        this.image = bleuBase;



        this.setVisible(true);

        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        }


    @Override
    public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public Image getBleuBase() {
        return bleuBase;
    }

    public Image getBleuSec() {
        return bleuSec;
    }

    public Image getRougeBase() {
        return rougeBase;
    }

    public Image getRougeSec() {
        return rougeSec;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
            this.setSize(this.largeur + (this.largeur/2), this.hauteur + (this.hauteur/2) );
            }

    @Override
    public void mouseExited(MouseEvent e) {
            this.setSize(this.largeur, this.hauteur);
            }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}


}

