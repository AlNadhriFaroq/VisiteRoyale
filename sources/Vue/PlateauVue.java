package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Pion;
import Modele.Plateau;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PlateauVue extends JPanel implements MouseInputListener {
    private static int taille = 17;
    Jeu jeu;
    private int posRoi;
    private int posGR;
    private int posGV;
    private int posFou;
    private int posSor;

    private int posJeton;

    PionVue pionRoi = new PionVue(Pion.ROI);
    PionVue pionGardeR = new PionVue(Pion.GAR_RGE);
    PionVue pionGardeV = new PionVue(Pion.GAR_VRT);
    PionVue pionSor = new PionVue(Pion.SOR);
    PionVue pionFou = new PionVue(Pion.FOU);

    CarteVue  carteVue;

    JeuVue jeuVue;

    ControleurMediateur ctrl;

    public PlateauVue(ControleurMediateur ctrl, Jeu jeu) {
        this.jeu = jeu;

        posRoi = jeu.getPlateau().getPositionPion(Pion.ROI);
        posGR = jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
        posGV = jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
        posFou = jeu.getPlateau().getPositionPion(Pion.FOU);
        posSor = jeu.getPlateau().getPositionPion(Pion.SOR);
        posJeton = jeu.getPlateau().getPositionCouronne();


        this.ctrl = ctrl;

        this.setPions();


        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {

        int a = (this.getHeight() / 10) - (this.getHeight() % 10);
        int t = (this.getHeight()-a)/3;
        super.paintComponent(g);
        for (int i = 0; i < taille; i++)
            g.drawLine((this.getWidth() / 17) * i, 0, (this.getWidth() / 17) * i, this.getHeight());

        g.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight());


        for (int i = 0; i <3; i++) {
            g.drawLine(0, t*i+a, this.getWidth(), t*i+a);
        }
        g.drawLine(0,0,this.getWidth(),0);
        g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
        g.drawLine(0,a,this.getWidth(),a);

        for(int i=0; i<3; i++){
            g.setColor(new Color(71,132,78));
            g.fillRect(1,(t*i)+a+1,this.getWidth()/17-2,t-2);
            g.fillRect(2+this.getWidth()/17,(t*i)+a+1,this.getWidth()/17-3,t-2);
        }

        for(int i=0; i<3; i++){
            g.setColor(new Color(225,15,50));
            g.fillRect(1+this.getWidth()/17*15,(t*i)+a+1,this.getWidth()/17-2,t-2);
            g.fillRect(2+this.getWidth()/17*16,(t*i)+a+1,this.getWidth()/17-3,t-2);
        }

        for(int i=0; i<3; i++){
            g.setColor(new Color(225,150,115));
            g.fillRect(1+this.getWidth()/17*8,(t*i)+a+1,this.getWidth()/17-2,t-2);
        }
        /*g.setColor(Color.ORANGE);
        g.drawOval(posSor*this.getWidth()/17,a,this.getWidth()/17,t);
        g.fillOval(posSor*this.getWidth()/17,a,this.getWidth()/17,t);

        g.setColor(Color.cyan);
        g.drawOval(posFou*this.getWidth()/17, (t*2)+a,this.getWidth()/17,t);
        g.fillOval(posFou*this.getWidth()/17, (t*2)+a,this.getWidth()/17,t);

        g.setColor(new Color(200,15,200));
        g.drawOval(posRoi*this.getWidth()/17,t+a,this.getWidth()/17,t);
        g.fillOval(posRoi*this.getWidth()/17,t+a,this.getWidth()/17,t);

        g.setColor(Color.GRAY);
        g.drawOval(posGR*this.getWidth()/17,t+a,this.getWidth()/17,t);
        g.fillOval(posGR*this.getWidth()/17,t+a,this.getWidth()/17,t);

        g.drawOval(posGV*this.getWidth()/17,t+a,this.getWidth()/17,t);
        g.fillOval(posGV*this.getWidth()/17,t+a,this.getWidth()/17,t);*/

        g.setColor(Color.YELLOW);
        g.drawOval(posJeton*this.getWidth()/17,0,this.getWidth()/17,a);
        g.fillOval(posJeton*this.getWidth()/17,0,this.getWidth()/17,a);

        this.pionSor.setSize(this.getWidth()/17, t);
        this.pionSor.setLocation(posSor*this.getWidth()/17,a);

        this.pionRoi.setSize(this.getWidth()/17,t+5);
        this.pionRoi.setLocation(posRoi*this.getWidth()/17,t+a);

        this.pionGardeV.setSize(this.getWidth()/17,t);
        this.pionGardeV.setLocation(posGV*this.getWidth()/17,t+a);

        this.pionGardeR.setSize(this.getWidth()/17,t);
        this.pionGardeR.setLocation(posGR*this.getWidth()/17,t+a);

        this.pionFou.setSize(this.getWidth()/17,t);
        this.pionFou.setLocation(posFou*this.getWidth()/17,2*t+a);








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

    public void majPositions(){
        posRoi = jeu.getPlateau().getPositionPion(Pion.ROI);
        posGR = jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
        posGV = jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
        posFou = jeu.getPlateau().getPositionPion(Pion.FOU);
        posSor = jeu.getPlateau().getPositionPion(Pion.SOR);
        posJeton = jeu.getPlateau().getPositionCouronne();
    }

    public void setPions(){
        this.pionRoi.setSize(30,55);
        this.pionRoi.setLocation(0,0);
        this.pionRoi.setVisible(true);
        this.add(pionRoi);

        this.pionFou.setSize(30,55);
        this.pionFou.setLocation(0,0);
        this.pionFou.setVisible(true);
        this.add(pionFou);

        this.pionSor.setSize(30,55);
        this.pionSor.setLocation(0,0);
        this.pionSor.setVisible(true);
        this.add(pionSor);

        this.pionGardeR.setSize(30,55);
        this.pionGardeR.setLocation(0,0);
        this.pionGardeR.setVisible(true);
        this.add(pionGardeR);

        this.pionGardeV.setSize(30,55);
        this.pionGardeV.setLocation(0,0);
        this.pionGardeV.setVisible(true);
        this.add(pionGardeV);
    }
}
