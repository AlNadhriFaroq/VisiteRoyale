package Structures;

import java.util.*;

public class Tas<E> {
    List<E> valeurs;
    List<Integer> poids;
    int taille;

    boolean mom;

    public Tas(boolean minOuMax) {
        valeurs = new ArrayList<>();
        poids = new ArrayList<>();
        mom = minOuMax;
    }

    public int getTaille() {
        return taille;
    }

    void echanger(int ind1, int ind2) {
        E valeurTmp = valeurs.get(ind1);
        Integer poidsTmp = poids.get(ind1);

        valeurs.set(ind1, valeurs.get(ind2));
        poids.set(ind1, poids.get(ind2));

        valeurs.set(ind2, valeurTmp);
        poids.set(ind2, poidsTmp);
    }

    public void inserer(E e, Integer p) {
        int ind1, ind2;

        valeurs.add(e);
        poids.add(p);

        ind1 = taille;
        ind2 = (ind1 - 1) / 2;
        while (mom ? poids.get(ind1) > poids.get(ind2) : poids.get(ind1) < poids.get(ind2)) {
            echanger(ind1, ind2);
            ind1 = ind2;
            ind2 = (ind1 - 1) / 2;
        }

        taille++;
        if(taille == 6){
            valeurs.remove(5);
            poids.remove(5);
            taille --;
        }
    }

    public E extraire() {
        if (estVide())
            return null;

        int ind, ind1, ind2;

        taille--;
        echanger(0, taille);
        E res = valeurs.remove(taille);
        poids.remove(taille);

        ind = 0;
        ind1 = 1;
        ind2 = 2;
        while (mom ? (ind1 < taille && (poids.get(ind1) > poids.get(ind))) || (ind2 < taille && (poids.get(ind2) > poids.get(ind))) : (ind1 < taille && (poids.get(ind1) < poids.get(ind))) || (ind2 < taille && (poids.get(ind2) < poids.get(ind)))) {
            if (mom ? (ind2 >= taille) || (poids.get(ind1) > poids.get(ind2)) : (ind2 >= taille) || (poids.get(ind1) < poids.get(ind2))) {
                echanger(ind, ind1);
                ind = ind1;
            } else {
                echanger(ind, ind2);
                ind = ind2;
            }
            ind1 = ind * 2 + 1;
            ind2 = ind * 2 + 2;
        }

        return res;
    }

    public boolean estVide() {
        return valeurs.isEmpty();
    }
}