package Vue;

import Modele.Jeu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class PlateauFrame extends JComponent {
    Jeu jeu;
    public final JFrame frame;
    static String path;
    static final int LARGEURFENETRE = 1600;
    static final int HAUTEURFENETRE = 900;


    List<CarteVue> deck;
    List<CarteVue> mainA;
    List<CarteVue> mainB;
    List<CarteVue> joueesA;
    List<CarteVue> joueesB;
    List<CarteVue> defausse;

    int heigth, width, carteH, carteW, TerrainH, TerrainW;
    private static int OFFSET = 20;


    public PlateauFrame(Jeu jeu) {

        this.deck = new ArrayList<CarteVue>() ;
        this.mainA = new ArrayList<CarteVue>();
        this.mainB = new ArrayList<CarteVue>();
        this.joueesB = new ArrayList<CarteVue>();
        this.joueesA = new ArrayList<CarteVue>();
        this.defausse = new ArrayList<CarteVue>();

        this.frame = new JFrame();
        this.frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        //this.frame.setSize(LARGEURFENETRE, HAUTEURFENETRE);
        this.jeu =jeu;

        this.heigth = this.frame.getHeight();
        this.width = this.frame.getWidth();

        this.setVisible(true);

        this.genererDeck();
        this.CreerMain(false);
        this.CreerMain(true);
        this.afficheMain();
    }

    public JFrame getFrame() {
        return frame;
    }

    /* PaintComponent */
    @Override
    public void paintComponent(Graphics g){
        this.afficheMain();
        super.paintComponent(g);

    }

    /* GENERATIONS & GESTION DES LISTES */
    public void genererDeck(){
        int taille = this.jeu.getPioche().getTaille();
        int x = OFFSET;
        int y =0 ;

        for (int i=0; i<taille; i++){
            CarteVue carteVue = new CarteVue();

            carteVue.setCarte(this.jeu.getPioche().getCarte(i));
            carteVue.setSize(90,160);
            carteVue.setVisible(true);
            if (i==0){y = ( (this.heigth/2) - (carteVue.getHeight()/2) ) -40;}
            carteVue.setLocation(x,y);
            this.deck.add(carteVue);
            this.frame.add(carteVue);
        }

        this.frame.repaint();
    }


    public void donnerCarte(int i, List<CarteVue> Target, List<CarteVue> Source){
        Target.add(Source.get(i));
        Source.remove(i);

    }

    public void piocher(int i,boolean joueur){
        if (joueur){
            donnerCarte(i, this.mainA, this.deck);
        }else{
            donnerCarte(i, this.mainB, this.deck);
        }
    }

    public void jouer(int i, boolean joueur){
        if (joueur){
            donnerCarte(i, this.joueesA, this.mainA);
        }else{
            donnerCarte(i, this.joueesB, this.mainB);
        }
    }

    public void defausserMain(int i, boolean joueur){
        if (joueur){
            donnerCarte(i, this.defausse, this.mainA);

        }else{
            donnerCarte(i, this.defausse, this.mainB);
        }
        PlacerDefausse(this.defausse.get(this.defausse.size()-1) );
    }
    public void defausserJeu(boolean joueur){
        int taille;
        if (joueur){
            taille = this.joueesA.size();
            for (int i=0; i< taille;i++ ){
                donnerCarte(i, this.defausse, this.joueesA);
                PlacerDefausse(this.defausse.get(this.defausse.size()-1) );
            }

        }
        else{
            taille = this.joueesB.size();
            for (int i=0; i< taille;i++ ){
                donnerCarte(i, this.defausse, this.joueesB);
                PlacerDefausse(this.defausse.get(this.defausse.size()-1) );
            }
        }
    }

    public void CreerMain(boolean j){
        for (int i=0; i<8; i++){
            this.piocher(1,j);
        }
    }

    /* AFFICHAGE */


    void afficheMain(){
        int x, size, taille;
        this.carteH = this.deck.get(0).getHeight();
        this.carteW = this.deck.get(0).getWidth();
        int yA = this.heigth-this.carteH-40;
        int yB = 5;
        int yARaised = yA - this.carteH/ 10;
        int yBRaised = yB + this.carteH / 10 ;

        //boolean joueur = this.jeu.getJoueurCourant()==1;
        x =(this.width/2)-(this.carteW*4);

        //if (joueur){
            taille = this.mainA.size();
            for (int i=0; i<taille; i++){

                this.mainA.get(i).setLocation(x,yA);
                this.mainA.get(i).setDos(false);
                x+=this.carteW;
            }
        //}else{
            taille = this.mainB.size();
            x =(this.width/2)-(this.carteW*4);

        for (int i=0; i<taille; i++){
                this.mainB.get(i).setLocation(x,yB);
                this.mainB.get(i).setDos(false);
                x+=this.carteW;
            }
        //}
        this.frame.repaint();
    }

    public void PlacerDefausse(CarteVue c){

        int x = (2*OFFSET) + c.getWidth();
        int y = ((this.heigth/2) - (c.getHeight()/2)) - 40;

        c.setLocation(x, y);
    }

    private int CalculJeuX(List<CarteVue> liste){
        int centre = (this.width/2);
        int largeur = (liste.get(0).getWidth()/2);
        int taille = liste.size();
        return centre - (taille*largeur);
    }

    private void decaler(List<CarteVue> list){
        int taille = list.size();
        int x = CalculJeuX(list);
        int y = list.get(0).getY();


        for (int i=0; i<taille; i++){
            list.get(i).setLocation(x, y);
            x += list.get(i).getWidth();
        }
    }

    public void PlacerJeuA(CarteVue c){
        int x = (this.width/2) - (c.getWidth()/2);
        int y = ((this.heigth) - (c.getHeight()*2))- (c.getHeight()/5) - 40;

        decaler(this.joueesA);
        c.setLocation(x, y);
    }



}
