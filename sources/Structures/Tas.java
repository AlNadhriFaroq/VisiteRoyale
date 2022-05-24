package Structures;

import java.util.*;

public class Tas<E> {
    private List<E> valeurs;
    private List<Integer> poids;
    private int taille;

    public Tas() {
        valeurs = new ArrayList<>();
        poids = new ArrayList<>();
    }

    public int getTaille() {
        return taille;
    }

    private void echanger(int ind1, int ind2) {
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
        while (poids.get(ind1) < poids.get(ind2)) {
            echanger(ind1, ind2);
            ind1 = ind2;
            ind2 = (ind1 - 1) / 2;
        }

        taille++;
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
        while ((ind1 < taille && (poids.get(ind1) < poids.get(ind))) || (ind2 < taille && (poids.get(ind2) < poids.get(ind)))) {
            if ((ind2 >= taille) || (poids.get(ind1) < poids.get(ind2))) {
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
