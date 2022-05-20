package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class MyBouton extends JButton {
    private Color colorBackGround;
    private Color colorForeGround;
    private Color colorOver;
    private Color borderColor;
    private Color colorClicked;
    private int raduis;
    private Font font;

    public MyBouton(String nom) {
        super(nom);
        colorBackGround = new Color(10, 45, 190);
        colorForeGround = new Color(255, 255, 255);
        borderColor = new Color(10, 45, 190);
        colorOver = new Color(236, 111, 8);
        colorClicked = new Color(45, 153, 211);
        raduis = 100;
        font = new Font("Verdana", Font.BOLD, 14);
        this.setBackground(colorBackGround);
        this.setForeground(colorForeGround);
        this.setFont(font);
        setSize(120,70);
        //this.setBorder(BorderFactory.createBevelBorder(1));
        setContentAreaFilled(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               setBackground(colorOver);
            }

            @Override
            public void mouseExited(MouseEvent e) {
               setBackground(colorBackGround);
            }

        });
    }
    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getBackground());
        g.drawOval(0, 0, getSize().width-1,     getSize().height-1);
    }

    Shape shape;
    @Override
    public boolean contains(int x, int y) {
        if (shape == null ||
                !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(colorClicked);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getSize().width-1,getSize().height-1);

        super.paintComponent(g);


    }
}
