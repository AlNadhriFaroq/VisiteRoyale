package Modele;

import java.util.*;

public class Main implements Cloneable {
    private List<Carte> cartes;
    private int tailleMax;

    Main(int tailleMax) {
        cartes = new ArrayList<>();
        this.tailleMax = tailleMax;
    }

    public Carte getCarte(int indice) {
        return cartes.get(indice);
    }

    public int getIndiceCarte(Carte carte) {
        return cartes.indexOf(carte);
    }

    public int getTaille() {
        return cartes.size();
    }

    public int getTailleMax() {
        return tailleMax;
    }

    public int getNombreTypeCarte(Type type) {
        int nb = 0;
        for (int i = 0; i < getTaille(); i++)
            if (getCarte(i).getType().equals(type))
                nb++;
        return nb;
    }

    public int getNombreCarte(Carte carte) {
        int nb = 0;
        for (int i = 0; i < getTaille(); i++)
            if (getCarte(i).equals(carte))
                nb++;
        return nb;
    }

    void piocher(Carte carte) {
        if (getTaille() == tailleMax)
            throw new RuntimeException("Modele.Main.piocher() : Main déjà pleine.");
        cartes.add(carte);
        trier();
    }

    Carte defausser(Carte carte) {
        if (!cartes.contains(carte))
            throw new RuntimeException("Modele.Main.defausser() : Carte non présente dans la main.");
        cartes.remove(carte);
        return carte;
    }

    void transferer(Main main) {
        while (!main.estVide())
            piocher(main.defausser(main.getCarte(0)));
    }

    void copier(Main main) {
        for (int i = 0; i < main.getTaille(); i++)
            piocher(main.getCarte(i));
    }

    void trier() {
        Collections.sort(cartes);
    }

    void vider() {
        cartes.clear();
    }

    public boolean contientCarte(Carte carte) {
        return cartes.contains(carte);
    }

    public boolean estVide() {
        return cartes.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;
        Main main = (Main) o;

        if (getTaille() != main.getTaille())
            return false;

        for (int i = 0; i < getTaille(); i++)
            if (getNombreCarte(getCarte(i)) != main.getNombreCarte(getCarte(i)))
                return false;
        return true;
    }

    @Override
    public Main clone() {
        try {
            Main resultat = (Main) super.clone();
            resultat.cartes = new ArrayList<>();
            resultat.copier(this);
            resultat.tailleMax = tailleMax;
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Paquet.cloner() : Paquet non clonable.");
        }
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder();

        for (int i = 0; i < getTaille(); i++) {
            txt.append(getCarte(i).toString());
            if (i != getTaille()-1)
                txt.append(" ");
        }

        return txt.toString();
    }
}
