package Vue;

import Modele.Jeu;
import Modele.Paquet;
import Vue.Boutons.*;

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

    Terrain terrain;

    List<BoutonPouvoir> boutons;


    int heigth, width, carteH, carteW, joueeH, joueeW;
    private static int OFFSET = 20;
    Dimension screenSize, frameSize;


    public PlateauFrame(Jeu jeu) {

        this.deck = new ArrayList<CarteVue>() ;
        this.mainA = new ArrayList<CarteVue>();
        this.mainB = new ArrayList<CarteVue>();
        this.joueesB = new ArrayList<CarteVue>();
        this.joueesA = new ArrayList<CarteVue>();
        this.defausse = new ArrayList<CarteVue>();
        this.boutons = new ArrayList<BoutonPouvoir>();


        this.frame = new JFrame();

        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setMinimumSize(new Dimension(LARGEURFENETRE, HAUTEURFENETRE));
        this.frame.setSize(this.screenSize);
        this.frame.setVisible(true);

        this.jeu =jeu;

        this.terrain = new Terrain(this.jeu.getPlateau());

        this.heigth = this.frame.getHeight();
        this.width = this.frame.getWidth();
        this.carteH = this.frame.getHeight()/10;
        this.carteW = this.frame.getWidth()/10;
        this.joueeH = this.carteH - (this.carteH/2);
        this.joueeW = this.carteW - (this.carteW/2);

        this.setVisible(true);

        this.genererDeck();
        this.GenererMains();
        this.afficheMain();
        this.afficheTerrain();
        this.genererBoutons();

    }

    public JFrame getFrame() {
        return frame;
    }

    /* PaintComponent */
    @Override
    public void paintComponent(Graphics g){

        this.afficheMain();
        this.afficheTerrain();
        this.afficherBoutons();
        super.paintComponent(g);


    }

    /* GENERATIONS & GESTION DES LISTES */
    public void genererDeck(){
        int taille = this.jeu.getPioche().getTaille();
        int x = OFFSET;
        int y =0 ;

        for (int i=0; i<taille; i++){
            CarteVue carteVue = new CarteVue(this);

            carteVue.setCarte(this.jeu.getPioche().getCarte(i));
            carteVue.setSize(this.carteW,this.carteH);
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

    public void GenererMains(){
        Paquet main = this.jeu.getMain(this.jeu.JOUEUR_RGE);
        int taille = main.getTaille();
        int x =0; int y = 0;


        for (int i=0; i<taille; i++){
            CarteVue carteVue = new CarteVue(this);
            carteVue.setCarte(main.getCarte(i));
            carteVue.setSize(this.carteW,this.carteH);
            carteVue.setVisible(true);
            carteVue.setLocation(x,y);
            this.mainA.add(carteVue);
            this.frame.add(carteVue);
        }

        main = this.jeu.getMain(this.jeu.JOUEUR_VRT);
        taille = main.getTaille();

        for (int i=0; i<taille; i++){
            CarteVue carteVue = new CarteVue(this);
            carteVue.setCarte(main.getCarte(i));
            carteVue.setSize(this.carteW,this.carteH);
            carteVue.setVisible(true);
            carteVue.setLocation(x,y);
            this.mainB.add(carteVue);
            this.frame.add(carteVue);
        }
    }

    /* AFFICHAGE */


    void afficheMain(){
        int x, size, taille;
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

        c.setSize(this.carteW, this.carteH);
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
        c.setSize(this.joueeW, this.joueeH);
        c.setLocation(x, y);

    }

    public void afficheTerrain(){

        Dimension dimension = this.frame.getSize();

        int w = carteW*10;
        int h = carteH+ carteH/2;

        if(!(w%17==0)){
            w -= w%17;
        }

        int y = ((dimension.height/2)-(h/2));
        int x = ((dimension.width/2)-(w/2));

        terrain.setBounds(x, y, w, h);
        terrain.setVisible(true);
        this.frame.add(terrain);
        terrain.repaint();
    }

    public void genererBoutons(){
        int BoutonLargeur = this.carteW/2;
        int BoutonHauteur = BoutonLargeur;
        int x= this.terrain.getX() + this.terrain.getWidth() + BoutonLargeur;
        int y= this.terrain.getY() + BoutonHauteur ;

        BoutonPouvoirFou pouvoirFou = new BoutonPouvoirFou(this.jeu);
        BoutonPouvoirSorcier pouvoirSorcier = new BoutonPouvoirSorcier(this.jeu);
        BoutonFInTour fInTour = new BoutonFInTour(this.jeu);
        BoutonAnnuler annuler = new BoutonAnnuler(this.jeu);
        BoutonRefaire refaire = new BoutonRefaire(this.jeu);

        pouvoirFou.setSize(BoutonLargeur, BoutonHauteur);
        pouvoirFou.setLocation(x,y);
        pouvoirFou.setVisible(true);


        pouvoirSorcier.setSize(BoutonLargeur, BoutonHauteur);
        y = this.terrain.getY() + this.terrain.getHeight() - (BoutonHauteur*2);
        pouvoirSorcier.setLocation(x,y);
        pouvoirSorcier.setVisible(true);

        fInTour.setSize( (BoutonLargeur*2) + (BoutonLargeur/2), BoutonHauteur + (BoutonHauteur/2));
        x = this.width - (fInTour.getWidth()*2);
        y = this.heigth - this.carteH - fInTour.getHeight();
        fInTour.setLocation(x,y);
        fInTour.setVisible(true);

        annuler.setSize(BoutonLargeur, BoutonHauteur);
        refaire.setSize(BoutonLargeur, BoutonHauteur);
        y = this.terrain.getY() + this.terrain.getHeight() + annuler.getHeight();
        x =(this.width/2)-(this.carteW*4) - (annuler.getWidth() + (annuler.getWidth()/2));
        annuler.setLocation(x,y);
        annuler.setVisible(true);
        x =(this.width/2)+(this.carteW*4) + (annuler.getWidth() + (annuler.getWidth()/2));
        refaire.setLocation(x, y);
        refaire.setVisible(true);

        this.boutons.add(pouvoirFou);
        this.boutons.add(pouvoirSorcier);
        this.boutons.add(fInTour);
        this.boutons.add(annuler);
        this.boutons.add(refaire);

        ajoutBoutons();

    }

    public void ajoutBoutons(){
        int taille = this.boutons.size();
        for (int i=0; i<taille; i++){
            this.frame.add(this.boutons.get(i));
        }
    }

    public void afficherBoutons(){
        int taille = this.boutons.size();
        for (int i=0; i< taille; i++){
            this.boutons.get(i).setVisible(true);
            this.boutons.get(i).setLocation(this.boutons.get(i).getLocation());
        }
    }

}
