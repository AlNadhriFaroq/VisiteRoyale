package Modele;

import java.util.*;

public class Paquet implements Cloneable {
    private List<Carte> cartes;

    Paquet() {
        cartes = new ArrayList<>();
    }

    public Carte getCarte(int indice) {
        return cartes.get(indice);
    }

    public Carte getCarte(Type type, int deplacement) {
        for (int i = 0; i < getTaille(); i++)
            if (getCarte(i).getType().equals(type) && getCarte(i).getDeplacement() == deplacement)
                return getCarte(i);
        throw new RuntimeException("Modele.Paquet.getCarte() : Type ou deplacement invalide.");
    }

    public int getIndiceCarte(Carte carte) {
        return cartes.indexOf(carte);
    }

    public int getTaille() {
        return cartes.size();
    }

    public int getNombreTypeCarte(Type type) {
        int nb = 0;
        for (int i = 0; i < getTaille(); i++)
            if (getCarte(i).getType().equals(type))
                nb++;
        return nb;
    }

    public int getNombreCarte(Type type, int deplacement) {
        int nb = 0;
        for (int i = 0; i < getTaille(); i++)
            if (getCarte(i).getType().equals(type) && getCarte(i).getDeplacement() == deplacement)
                nb++;
        return nb;
    }

    void defausser(Carte carte) {
        cartes.add(carte);
    }

    Carte piocher() {
        if (!estVide())
            return cartes.remove(getTaille()-1);
        return null;
    }

    void remplir() {
        remplir(12, Carte.R1);
        remplir(4, Carte.G1);
        remplir(10, Carte.G2);
        remplir(2, Carte.GC);
        remplir(2, Carte.S1);
        remplir(8, Carte.S2);
        remplir(2, Carte.S3);
        remplir(1, Carte.F1);
        remplir(3, Carte.F2);
        remplir(4, Carte.F3);
        remplir(3, Carte.F4);
        remplir(1, Carte.F5);
        remplir(2, Carte.FM);
        melanger();
    }

    private void remplir(int nb, Carte carte) {
        for (int i = 0; i < nb; i++)
            defausser(carte);
    }

    void transferer(Paquet paquet) {
        while (!paquet.estVide())
            defausser(paquet.piocher());
    }
    public void ajouter(Carte carte){
        cartes.add(carte);
    }

    void copier(Paquet paquet) {
        for (int i = 0; i < paquet.getTaille(); i++)
            defausser(paquet.getCarte(i));
    }

    void melanger() {
        Collections.shuffle(cartes);
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
        Paquet paquet = (Paquet) o;

        if (getTaille() != paquet.getTaille())
            return false;

        for (int i = 0; i < getTaille(); i++)
            if (!(getCarte(i).equals(paquet.getCarte(i))))
                return false;
        return true;
    }

    @Override
    public Paquet clone() {
        try {
            Paquet resultat = (Paquet) super.clone();
            resultat.cartes = new ArrayList<>();
            resultat.copier(this);
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
