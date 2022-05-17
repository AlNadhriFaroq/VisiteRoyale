package Vue;

import Modele.Pion;
import Modele.Plateau;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Terrain extends JPanel implements MouseInputListener {
    private static int taille = 17;
    Plateau plateau;
    private int posRoi;
    private int posGR;
    private int posGV;
    private int posFou;
    private int posSor;

    private Image icon;

    public Terrain(Plateau p){
        this.plateau = p;

        posRoi = p.getPositionPion(Pion.ROI);
        posGR = p.getPositionPion(Pion.GAR_RGE);
        posGV = p.getPositionPion(Pion.GAR_VRT);
        posFou = p.getPositionPion(Pion.FOU);
        posSor = p.getPositionPion(Pion.SOR);



        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g){



        super.paintComponent(g);
        for(int i=0; i<taille; i++){
            g.drawLine((this.getWidth()/17)*i,0,(this.getWidth()/17)*i, this.getHeight());


        }
        g.drawLine(this.getWidth()-1,0,this.getWidth()-1, this.getHeight());
        for(int i=0; i<4; i++){
            if(i==1){
               g.drawLine(0, 30, this.getWidth(), 30);
            }
            g.drawLine(0,(this.getHeight()+30)/3*i,this.getWidth(), (this.getHeight()+30)/3*i);
        }
        g.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);

    }

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

    public int getPosRoi() {
        return posRoi;
    }

    public void setPosRoi(int posRoi) {
        this.posRoi = posRoi;
    }

    public int getPosGR() {
        return posGR;
    }

    public void setPosGR(int posGR) {
        this.posGR = posGR;
    }

    public int getPosGV() {
        return posGV;
    }

    public void setPosGV(int posGV) {
        this.posGV = posGV;
    }

    public int getPosFou() {
        return posFou;
    }

    public void setPosFou(int posFou) {
        this.posFou = posFou;
    }

    public int getPosSor() {
        return posSor;
    }

    public void setPosSor(int posSor) {
        this.posSor = posSor;
    }
}
