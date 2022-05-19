package Vue;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CaseVue extends JPanel implements MouseInputListener {
    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private int position;
    private Image image;
    private static String path = "/Images/Cases/";
    private String nom;

    /* SETTERS/GETTERS */

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void setImage() {
        this.image = getImage(this.nom);
        this.image = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
    }

    /* GESTION IMAGE */
    private static BufferedImage getImage(String name) {
        name = path + name + ".png";
        if (!images.containsKey(name))
            images.put(name, Utils.loadImg(name));

        return images.get(name);
    }

    /* MOUSE LISTENER */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
