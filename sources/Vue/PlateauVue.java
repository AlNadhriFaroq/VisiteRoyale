package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Pion;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PlateauVue extends JPanel {
    private static int taille = 17;
    Jeu jeu;
    private int posRoi;
    private int posGR;
    private int posGV;
    private int posFou;
    private int posSor;

    private int posJeton;

    private int tailleZone;

    PionVue pionRoi = new PionVue(Pion.ROI);
    PionVue pionGardeR = new PionVue(Pion.GAR_RGE);
    PionVue pionGardeV = new PionVue(Pion.GAR_VRT);
    PionVue pionSor = new PionVue(Pion.SOR);
    PionVue pionFou = new PionVue(Pion.FOU);

    //JetonVue jetonCouronne = new JetonVue();

    JeuVue jeuVue;

    ControleurMediateur ctrl;

    public PlateauVue(ControleurMediateur ctrl, Jeu jeu) {
        this.jeu = jeu;
        this.ctrl = ctrl;

        posRoi = jeu.getPlateau().getPositionPion(Pion.ROI);
        posGR = jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
        posGV = jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
        posFou = jeu.getPlateau().getPositionPion(Pion.FOU);
        posSor = jeu.getPlateau().getPositionPion(Pion.SOR);
        posJeton = jeu.getPlateau().getPositionCouronne();


        this.setPions();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        super.paintComponent(g2);

        g2.setStroke(new BasicStroke(2));

        tailleZone = this.getWidth()/17;
        int caseJeton = (this.getHeight() / 10) - (this.getHeight() % 10);
        int casePion = (this.getHeight()-caseJeton)/3;

        for (int i = 0; i < taille; i++)
            g.drawLine(tailleZone * i, 0, tailleZone * i, this.getHeight());

        g.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight());


        for (int i = 0; i <3; i++) {
            g.drawLine(0, casePion*i+caseJeton, this.getWidth(), casePion*i+caseJeton);
        }
        g.drawLine(0,1,this.getWidth(),1);
        g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
        g.drawLine(0,caseJeton,this.getWidth(),caseJeton);

        for(int i=0; i<3; i++){
            g.setColor(new Color(71,132,78));
            g.fillRect(1,(casePion*i)+caseJeton+1,tailleZone-2,casePion-2);
            g.fillRect(2+tailleZone,(casePion*i)+caseJeton+1,tailleZone-3,casePion-2);
        }

        for(int i=0; i<3; i++){
            g.setColor(new Color(225,15,50));
            g.fillRect(1+tailleZone*15,(casePion*i)+caseJeton+1,tailleZone-2,casePion-2);
            g.fillRect(2+tailleZone*16,(casePion*i)+caseJeton+1,tailleZone-3,casePion-2);
        }

        for(int i=0; i<3; i++){
            g.setColor(new Color(225,150,115));
            g.fillRect(1+tailleZone*8,(casePion*i)+caseJeton+1,tailleZone-2,casePion-2);
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
        g.drawOval(posJeton*tailleZone,0,tailleZone,caseJeton);
        g.fillOval(posJeton*tailleZone,0,tailleZone,caseJeton);

        this.pionSor.setSize(tailleZone, casePion);
        this.pionSor.setLocation(posSor*tailleZone,caseJeton);

        this.pionRoi.setSize(tailleZone,casePion+5);
        this.pionRoi.setLocation(posRoi*tailleZone,casePion+caseJeton);

        this.pionGardeV.setSize(tailleZone,casePion);
        this.pionGardeV.setLocation(posGV*tailleZone,casePion+caseJeton);

        this.pionGardeR.setSize(tailleZone,casePion);
        this.pionGardeR.setLocation(posGR*tailleZone,casePion+caseJeton);

        this.pionFou.setSize(tailleZone,casePion);
        this.pionFou.setLocation(posFou*tailleZone,2*casePion+caseJeton);

        /*this.jetonCouronne.setSize(this.getWidth()/17, caseJeton);
        this.jetonCouronne.setLocation(posJeton*this.getWidth()/17, 0);
        System.out.println(this.jetonCouronne.getHeight());*/








    }

    private boolean estValide(int x, int y){
        int xinit = (jeuVue.frame.getSize().width/2) - ( jeuVue.carteW*10/2);
        System.out.println("Plateau :" + xinit);
        if((x>xinit) && (x<xinit+(jeuVue.carteW*10))){
            System.out.println("Reussi :" + x);
            return true;
        }
        return false;
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

    public void gestionEvent(MouseEvent e){

    }
}
