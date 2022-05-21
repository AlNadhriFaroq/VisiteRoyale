package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Paquet;
import Modele.Pion;
import Vue.Boutons.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class JeuVue extends JComponent {
    public final JFrame frame;
    static final int LARGEURFENETRE = 1600;
    static final int HAUTEURFENETRE = 900;
    private static int OFFSET ;

    Jeu jeu;
    ControleurMediateur ctrl;

    List<CarteVue> deck;
    List<CarteVue> mainA;
    List<CarteVue> mainB;
    List<CarteVue> joueesA;
    List<CarteVue> joueesB;
    List<CarteVue> defausse;
    public PlateauVue terrain;
    List<Bouton> boutons;

    int heigth, width, carteH, carteW, joueeH, joueeW, xDep, yA, yB, delai;
    boolean FinAnim;
    Dimension screenSize;

    public JeuVue(ControleurMediateur ctrl, Jeu jeu) {
        this.delai = 25;

        this.deck = new ArrayList<>();
        this.mainA = new ArrayList<>();
        this.mainB = new ArrayList<>();
        this.joueesB = new ArrayList<>();
        this.joueesA = new ArrayList<>();
        this.defausse = new ArrayList<>();
        this.boutons = new ArrayList<>();

        this.frame = new JFrame();

        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //this.frame.setMinimumSize(new Dimension(LARGEURFENETRE, HAUTEURFENETRE));
        //this.frame.setSize(this.screenSize);
        this.frame.setSize(1200,800);
        this.frame.setVisible(true);

        this.jeu = jeu;
        this.ctrl = ctrl;

        this.terrain = new PlateauVue(ctrl, this.jeu.getPlateau());

        this.heigth = this.frame.getHeight();
        this.width = this.frame.getWidth();
        this.carteH = this.frame.getWidth() / 10;
        this.carteW = this.frame.getHeight() / 10;
        this.joueeH = (this.carteH/4 )+ (this.carteH / 2);
        this.joueeW = (this.carteW/4 )+ (this.carteW / 2);
        this.OFFSET = this.width /80;
        this.xDep = (this.width / 2) - (this.carteW * 4);
        this.yA = this.heigth - this.carteH - 40;
        this.yB = 5;
        this.setVisible(true);

        this.genererDeck();
        this.GenererMains();
        //this.afficheMain();
        this.afficheTerrain();
        this.genererBoutons();

        this.FinAnim = false;
    }

    public JFrame getFrame() {
        return frame;
    }

    /* PaintComponent */
    @Override
    public void paintComponent(Graphics g) {
        this.afficheMain();
        this.afficheTerrain();
        this.afficherBoutons();
        super.paintComponent(g);
        g.setColor(Color.darkGray);
        int w = this.carteW + (this.OFFSET/2);
        int h = this.carteH + (this.OFFSET*2);
        g.fillRect(this.OFFSET - (this.OFFSET/4),(this.heigth/2) - (h/2) - 40,w,h);
        g.fillRect(( (2 * this.OFFSET ) - (this.OFFSET/4) ) + this.carteW,((this.heigth / 2) - (h / 2)) - 40,w,h);
    }

    /* GENERATIONS & GESTION DES LISTES */
    public void genererDeck() {
        int taille = this.jeu.getPioche().getTaille();
        int y = ((this.heigth / 2) - (this.carteH / 2)) - 40;

        for (int i = 0; i < taille; i++) {
            CarteVue carteVue = new CarteVue(jeu, ctrl, this);
            carteVue.setCarte(this.jeu.getPioche().getCarte(i));
            carteVue.setSize(this.carteW, this.carteH);
            carteVue.setVisible(true);
            carteVue.setLocation(OFFSET, y);

            this.deck.add(carteVue);
            this.frame.add(carteVue);
        }

        this.frame.repaint();
    }

    public void donnerCarte(int i, List<CarteVue> Target, List<CarteVue> Source) {
        Target.add(Source.get(i));
        Source.remove(i);
    }

    public void piocher(int joueur) {
        int max = jeu.getSelectionCartes(joueur).getTaille();
        Point dest;
        if (joueur == this.jeu.JOUEUR_RGE) {
            dest = new Point(this.xDep, this.yA);
            for (int i=0; i< max; i++){
                envoiPioche(this.deck.get(this.deck.size()-1), dest);
                donnerCarte(this.deck.size()-1, this.mainA, this.deck);


            }
        }else {
            dest = new Point(this.xDep, this.yB);
            for (int i=0; i< max; i++){
                envoiPioche(this.deck.get(this.deck.size()-1), dest);
                donnerCarte(this.deck.size()-1, this.mainB, this.deck);
            }
        }
    }

    public void jouer(int i, boolean joueur) {
        if (joueur) {
            donnerCarte(i, this.joueesA, this.mainA);
        }else {
            donnerCarte(i, this.joueesB, this.mainB);
        }
    }

    public void jouerCarte (CarteVue c){
        if (jeu.getJoueurCourant() == this.jeu.JOUEUR_RGE){
            jouer(this.mainA.indexOf(c), true);
        }else{
            jouer(this.mainB.indexOf(c), false);
        }
    }

    public void defausserMain(int i, boolean joueur) {
        if (joueur)
            donnerCarte(i, this.defausse, this.mainA);
        else
            donnerCarte(i, this.defausse, this.mainB);
        PlacerDefausse(this.defausse.get(this.defausse.size() - 1));
    }

    public void defausserJeu(int joueur) {
        int taille;
        if (joueur == Jeu.JOUEUR_RGE) {
            taille = this.joueesA.size();
            for (int i = 0; i < taille; i++) {
                donnerCarte(i, this.defausse, this.joueesA);
                PlacerDefausse(this.defausse.get(this.defausse.size() - 1));
            }
        } else {
            taille = this.joueesB.size();
            for (int i = 0; i < taille; i++) {
                donnerCarte(i, this.defausse, this.joueesB);
                PlacerDefausse(this.defausse.get(this.defausse.size() - 1));
            }
        }
    }

    public void GenererMains() {
        Paquet main = this.jeu.getMain(Jeu.JOUEUR_RGE);
        int taille = main.getTaille();
        int x = this.OFFSET;
        int y = ((this.heigth / 2) - (this.carteH / 2)) - 40;


        for (int i = 0; i < taille; i++) {
            CarteVue carteVue = new CarteVue(jeu, ctrl, this);
            carteVue.setCarte(main.getCarte(i));
            carteVue.setSize(this.carteW, this.carteH);
            carteVue.setVisible(true);
            carteVue.setLocation(x, y);

            this.mainA.add(carteVue);
            this.frame.add(carteVue);

            envoiPioche(carteVue, new Point(this.xDep, this.yA));
        }

        main = this.jeu.getMain(Jeu.JOUEUR_VRT);
        taille = main.getTaille();

        for (int i = 0; i < taille; i++) {
            CarteVue carteVue = new CarteVue(jeu, ctrl, this);
            carteVue.setCarte(main.getCarte(i));
            carteVue.setSize(this.carteW, this.carteH);
            carteVue.setVisible(true);
            carteVue.setLocation(x, y);

            this.mainB.add(carteVue);
            this.frame.add(carteVue);

            envoiPioche(carteVue, new Point(this.xDep, this.yB));
        }


    }

    /* AFFICHAGE */

    void afficheMain() {
        int x, taille;


        //boolean joueur = this.jeu.getJoueurCourant()==1;
        x = this.xDep;
        taille = this.mainA.size();
        for (int i = 0; i < taille; i++) {
            this.mainA.get(i).setLocation(x, this.yA);
            this.mainA.get(i).setDos(false);
            x += this.carteW;
        }

        x = (this.width / 2) - (this.carteW * 4);
        taille = this.mainB.size();
        for (int i = 0; i < taille; i++) {
            this.mainB.get(i).setLocation(x, yB);
            this.mainB.get(i).setDos(false);
            x += this.carteW;
        }

        this.frame.repaint();
    }
    public void updateMains(){
        Collections.sort(this.mainA);
        Collections.sort(this.mainB);
        this.afficheMain();
        this.frame.repaint();

    }

    public void PlacerDefausse(CarteVue c) {


        int x = (2 * OFFSET) + this.carteW;
        int y = ((this.heigth / 2) - (this.carteH / 2)) - 40;

        Point posC = new Point(c.getX(), c.getY());
        Point posD = new Point(x,y);
        Dimension resize = new Dimension(this.carteW, this.carteH);

        envoiCarte(c,posC, posD, resize, this.delai);

        this.frame.repaint();

    }

    private int CalculJeuX(List<CarteVue> liste) {
        int centre = (this.width / 2);
        int largeur = (liste.get(0).getWidth() / 2);
        int taille = liste.size();
        return centre - (taille * largeur);
    }

    public void decaler(List<CarteVue> list) {
        int x = CalculJeuX(list);
        int y = list.get(0).getY();

        for (CarteVue carteVue : list) {
            carteVue.setLocation(x, y);
            x += carteVue.getWidth();
        }
        this.frame.repaint();
    }

    public void PlacerJeuA(CarteVue c) {
        int x = (this.width / 2) - (c.getWidth() / 2);
        int y = this.terrain.getY() + this.terrain.getHeight() + (this.carteH/4);

        decaler(this.joueesA);
        Point posC = new Point(c.getX(), c.getY());
        Point posD = new Point(x,y);
        Dimension resize = new Dimension(this.joueeW, this.joueeH);
        envoiCarte(c, posC, posD, resize, this.delai);
        this.frame.repaint();
    }
    public void PlacerJeuB(CarteVue c) {
        int x = (this.width / 2) - (c.getWidth() / 2);
        int y = this.terrain.getY() - (this.carteH/4) - (this.joueeH);

        decaler(this.joueesB);
        Point posC = new Point(c.getX(), c.getY());
        Point posD = new Point(x,y);
        Dimension resize = new Dimension(this.joueeW, this.joueeH);
        envoiCarte(c, posC, posD, resize, this.delai);
        this.frame.repaint();
    }

    public void afficheTerrain() {
        Dimension dimension = this.frame.getSize();

        int w = carteW * 10;
        int h = carteH + carteH / 2;

        if (!(w % 17 == 0))
            w -= w % 17;

        if(!(h % 3 == 0))
            h -= h%3;

        int y = ((dimension.height / 2) - (h / 3)*2);
        int x = ((dimension.width / 2) - (w / 2));

        terrain.setBounds(x, y, w, h);
        terrain.setVisible(true);
        this.frame.add(terrain);
        terrain.majPositions();

        terrain.repaint();
    }

    public void genererBoutons() {
        int BoutonLargeur = this.carteW / 2;
        int BoutonHauteur = BoutonLargeur;
        int x = this.terrain.getX() + this.terrain.getWidth() + BoutonLargeur;
        int y = this.terrain.getY() + BoutonHauteur;

        BoutonPouvoirFou pouvoirFou = new BoutonPouvoirFou(ctrl, this.jeu);
        BoutonPouvoirSorcier pouvoirSorcier = new BoutonPouvoirSorcier(ctrl, this.jeu);
        BoutonFinirTour finTour = new BoutonFinirTour(ctrl, this.jeu, this);
        BoutonAnnuler annuler = new BoutonAnnuler(ctrl, this.jeu);
        BoutonRefaire refaire = new BoutonRefaire(ctrl, this.jeu);
        Gauche gauche = new Gauche(ctrl, this,this.jeu);
        Droite droite = new Droite(ctrl,this, this.jeu);

        pouvoirFou.setSize(BoutonLargeur, BoutonHauteur);
        pouvoirFou.setLocation(x, y);
        pouvoirFou.setVisible(true);

        pouvoirSorcier.setSize(BoutonLargeur, BoutonHauteur);
        y = this.terrain.getY() + this.terrain.getHeight() - (BoutonHauteur * 2);
        pouvoirSorcier.setLocation(x, y);
        pouvoirSorcier.setVisible(true);

        finTour.setSize((BoutonLargeur * 2) + (BoutonLargeur / 2), BoutonHauteur + (BoutonHauteur / 2));
        x = this.width - (finTour.getWidth() * 2);
        y = this.heigth - this.carteH - finTour.getHeight();
        finTour.setLocation(x, y);
        finTour.setVisible(true);

        annuler.setSize(BoutonLargeur, BoutonHauteur);
        refaire.setSize(BoutonLargeur, BoutonHauteur);
        y = this.terrain.getY() + this.terrain.getHeight() + annuler.getHeight();
        x = (this.width / 2) - (this.carteW * 4) - (annuler.getWidth() + (annuler.getWidth() / 2));
        annuler.setLocation(x, y);
        annuler.setVisible(true);
        x = (this.width / 2) + (this.carteW * 4) + (annuler.getWidth() + (annuler.getWidth() / 2));
        refaire.setLocation(x, y);
        refaire.setVisible(true);

        gauche.setSize(BoutonLargeur, BoutonHauteur);
        gauche.setLocation(0, 0);
        gauche.setVisible(true);

        droite.setSize(BoutonLargeur, BoutonHauteur);
        droite.setLocation(BoutonLargeur*2, 0);
        droite.setVisible(true);

        this.boutons.add(pouvoirFou);
        this.boutons.add(pouvoirSorcier);
        this.boutons.add(finTour);
        this.boutons.add(annuler);
        this.boutons.add(refaire);
        this.boutons.add(gauche);
        this.boutons.add(droite);

        ajoutBoutons();
    }

    public void ajoutBoutons() {
        for (Bouton bouton : this.boutons) this.frame.add(bouton);
    }

    public void afficherBoutons() {
        for (Bouton bouton : this.boutons) {
            bouton.setVisible(true);
            bouton.setLocation(bouton.getLocation());
        }
    }


    /* ANIMATIONS */

    public void envoiCarte(CarteVue c, Point dep, Point fin, Dimension d,int delai){
        AnimationPanel animationPanel = new AnimationPanel(c, dep, fin, d, delai);
        animationPanel.AnimStart();
    }

    public void envoiPioche(CarteVue carteVue, Point dest){
        Point depart = new Point(carteVue.getX(), carteVue.getY());
        envoiCarte(carteVue, depart, dest, new Dimension(carteVue.getSize()), 200);
    }







}
