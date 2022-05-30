package Vue;

import Modele.Pion;
import jdk.jshell.execution.Util;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class PionVue extends JPanel{
    public static final HashMap<String, BufferedImage> images = new HashMap<>();

    private static String path = "/Images/Pions/";
    private Pion pion;
    private boolean dragged;

    private Image image;

    public PionVue(Pion pion) {
        this.pion = pion;
        this.dragged = false;

        this.setImage();

        this.setOpaque(false);

        this.setSize(30,55);
        this.setVisible(true);
    }

    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    public boolean isDragged() {
        return dragged;
    }


    public void setImage() {
        this.image = getImage(this.pion.toString());
        this.image = this.image.getScaledInstance(70,120,Image.SCALE_SMOOTH);
    }

    private static Image getImage(String name){
        switch (name){
            case "R":
                images.put(name,Utils.loadImg(path+"ROI.png"));
                return images.get(name);
            case "GV":
                images.put(name, Utils.loadImg(path+"GARDEA.png"));
                return images.get(name);
            case "GR":
                images.put(name, Utils.loadImg(path+"GARDEB.png"));
                return images.get(name);
            case "F":
                images.put(name, Utils.loadImg(path+"FOU.png"));
                return images.get(name);
            case "S":
                images.put(name, Utils.loadImg(path+"SORCIER.png"));
                return images.get(name);
            default:
                throw new RuntimeException("Vue pion invalide");
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(this.image,0,0,this.getWidth(),this.getHeight(), this);
    }
    public Pion getPion() {
        return pion;
    }

    public void setPion(Pion pion) {
        this.pion = pion;
        this.setImage();
    }
}
