package Vue;

import Modele.Jeu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PlateauFrame extends JComponent {
    Jeu jeu;
    public final JFrame frame;
    static String path;

    List<CarteVue> deck;
    List<CarteVue> mainA;
    List<CarteVue> mainB;
    List<CarteVue> joueesA;
    List<CarteVue> joueesB;
    List<CarteVue> defausse;

    int heigth, width, carteH, carteW, TerrainH, TerrainW;


    public PlateauFrame(Jeu jeu) {

        this.deck = new List<CarteVue>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<CarteVue> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(CarteVue carteVue) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public CarteVue get(int index) {
                return null;
            }

            @Override
            public CarteVue set(int index, CarteVue element) {
                return null;
            }

            @Override
            public void add(int index, CarteVue element) {

            }

            @Override
            public CarteVue remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<CarteVue> listIterator() {
                return null;
            }

            @Override
            public ListIterator<CarteVue> listIterator(int index) {
                return null;
            }

            @Override
            public List<CarteVue> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        this.mainA = new List<CarteVue>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<CarteVue> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(CarteVue carteVue) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public CarteVue get(int index) {
                return null;
            }

            @Override
            public CarteVue set(int index, CarteVue element) {
                return null;
            }

            @Override
            public void add(int index, CarteVue element) {

            }

            @Override
            public CarteVue remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<CarteVue> listIterator() {
                return null;
            }

            @Override
            public ListIterator<CarteVue> listIterator(int index) {
                return null;
            }

            @Override
            public List<CarteVue> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        this.mainB = new List<CarteVue>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<CarteVue> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(CarteVue carteVue) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public CarteVue get(int index) {
                return null;
            }

            @Override
            public CarteVue set(int index, CarteVue element) {
                return null;
            }

            @Override
            public void add(int index, CarteVue element) {

            }

            @Override
            public CarteVue remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<CarteVue> listIterator() {
                return null;
            }

            @Override
            public ListIterator<CarteVue> listIterator(int index) {
                return null;
            }

            @Override
            public List<CarteVue> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        this.joueesB = new List<CarteVue>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<CarteVue> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(CarteVue carteVue) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public CarteVue get(int index) {
                return null;
            }

            @Override
            public CarteVue set(int index, CarteVue element) {
                return null;
            }

            @Override
            public void add(int index, CarteVue element) {

            }

            @Override
            public CarteVue remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<CarteVue> listIterator() {
                return null;
            }

            @Override
            public ListIterator<CarteVue> listIterator(int index) {
                return null;
            }

            @Override
            public List<CarteVue> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        this.joueesA = new List<CarteVue>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<CarteVue> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(CarteVue carteVue) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public CarteVue get(int index) {
                return null;
            }

            @Override
            public CarteVue set(int index, CarteVue element) {
                return null;
            }

            @Override
            public void add(int index, CarteVue element) {

            }

            @Override
            public CarteVue remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<CarteVue> listIterator() {
                return null;
            }

            @Override
            public ListIterator<CarteVue> listIterator(int index) {
                return null;
            }

            @Override
            public List<CarteVue> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        this.defausse = new List<CarteVue>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<CarteVue> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(CarteVue carteVue) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends CarteVue> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public CarteVue get(int index) {
                return null;
            }

            @Override
            public CarteVue set(int index, CarteVue element) {
                return null;
            }

            @Override
            public void add(int index, CarteVue element) {

            }

            @Override
            public CarteVue remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<CarteVue> listIterator() {
                return null;
            }

            @Override
            public ListIterator<CarteVue> listIterator(int index) {
                return null;
            }

            @Override
            public List<CarteVue> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        this.frame = new JFrame();
        this.jeu =jeu;

        this.heigth = this.frame.getHeight();
        this.width = this.frame.getWidth();


        //setLayout(null);
        this.setVisible(true);

        this.genererDeck();
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
        for (int i=0; i<taille; i++){
            CarteVue carteVue = new CarteVue();
            carteVue.setLocation(0,0);
            carteVue.setCarte(this.jeu.getPioche().getCarte(i));
            carteVue.setSize(90,160);
            carteVue.setVisible(true);
            this.frame.add(carteVue);
            this.deck.add(carteVue);
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
    }
    public void defausserJeu(boolean joueur){
        int taille;
        if (joueur){
            taille = this.joueesA.size();
            for (int i=0; i< taille;i++ ){
                donnerCarte(i, this.defausse, this.joueesA);
            }

        }
        else{
            taille = this.joueesB.size();
            for (int i=0; i< taille;i++ ){
                donnerCarte(i, this.defausse, this.joueesB);
            }
        }
    }

    /* AFFICHAGE */


    void afficheMain(){
        int x, size, taille;
        int yA = this.heigth-this.carteH-5;
        int yB = 5;
        int yARaised = yA - this.carteH/ 10;
        int yBRaised = yB + this.carteH / 10 ;

        boolean joueur = this.jeu.getJoueurCourant()==1;
        x = (this.width-((this.width/this.carteW)*8) )/2;

        if (joueur){
            taille = this.mainA.size();
            for (int i=0; i<taille; i++){
                this.mainA.get(i).setLocation(x,yB);
                this.mainA.get(i).setDos(false);
                x+=this.carteW;
            }
        }else{
            taille = this.mainB.size();
            for (int i=0; i<taille; i++){
                this.mainB.get(i).setLocation(x,yB);
                this.mainB.get(i).setDos(false);
                x+=this.carteW;
            }
        }




    }




}
