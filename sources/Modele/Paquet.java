package Modele;

import java.util.*;

public class Paquet implements Cloneable {
    public static Paquet JEU_CARTES;

    public static final boolean ORDONNE = true;
    public static final boolean NON_ORDONNE = false;

    List<Carte> paquet;
    boolean ordonne;

    public Paquet(boolean ordonne) {
        paquet = new ArrayList<>();
        this.ordonne = ordonne;
    }

    public Carte getCarte(int indiceCarte) {
        return paquet.get(indiceCarte);
    }

    public Carte getCarte(int type, int deplacement) {
        for (int i = 0; i < getTaille(); i++)
            if (getCarte(i).getType() == type && getCarte(i).getDeplacement() == deplacement)
                return getCarte(i);
        throw new RuntimeException("Carte non presente dans le paquet");
    }

    public int getIndiceCarte(Carte carte) {
        return paquet.indexOf(carte);
    }

    public int getTaille() {
        return paquet.size();
    }

    public int getNombreTypeCarte(int type) {
        int nb = 0;
        for (int i = 0; i < getTaille(); i++)
            if (paquet.get(i).getType() == type)
                nb++;
        return nb;
    }

    public int getNombreCarte(int type, int deplacement) {
        int nb = 0;
        for (int i = 0; i < getTaille(); i++)
            if (paquet.get(i).getType() == type && paquet.get(i).getDeplacement() == deplacement)
                nb++;
        return nb;
    }

    public void ajouter(Carte carte) {
        paquet.add(carte);
    }

    public Carte piocher() {
        if (!ordonne)
            throw new RuntimeException("Impossible de piocher une carte dans un paquet non ordonne !");
        if (!estVide())
            return paquet.remove(paquet.size()-1);
        return null;
    }

    public Carte defausser(Carte carte) {
        if (ordonne)
            throw new RuntimeException("Impossible de defausser une carte dans un paquet ordonne !");
        paquet.remove(carte);
        return carte;
    }

    public Carte defausser(int indiceCarte) {
        if (ordonne)
            throw new RuntimeException("Impossible de defaussser une carte dans un paquet ordonne !");
        return paquet.remove(indiceCarte);
    }

    public void permuterCartes(Carte carte1, Carte carte2) {
        if (ordonne)
            throw new RuntimeException("Impossible de permuter des cartes dans un paquet ordonne !");
        int indiceCarte1 = paquet.indexOf(carte1);
        int indiceCarte2 = paquet.indexOf(carte2);
        paquet.set(indiceCarte1, carte2);
        paquet.set(indiceCarte2, carte1);
    }

    public void permuterCartes(int indiceCarte1, int indiceCarte2) {
        if (ordonne)
            throw new RuntimeException("Impossible de permuter des cartes dans un paquet ordonne !");
        Carte carte1 = paquet.get(indiceCarte1);
        Carte carte2 = paquet.get(indiceCarte2);
        paquet.set(indiceCarte1, carte2);
        paquet.set(indiceCarte2, carte1);
    }

    public void remplir() {
        for (int i = 0; i < JEU_CARTES.getTaille(); i++)
            ajouter(JEU_CARTES.getCarte(i));
    }

    public void transferer(Paquet paquet) {
        if (!ordonne)
            throw new RuntimeException("Impossible de transferer des cartes depuis un paquet non ordonne !");
        while (!paquet.estVide())
            ajouter(paquet.piocher());
    }

    public void copier(Paquet paquet) {
        if (!ordonne)
            throw new RuntimeException("Impossible de transferer des cartes depuis un paquet non ordonne !");
        for (int i = 0; i < paquet.getTaille(); i++)
            ajouter(paquet.getCarte(i));
    }

    public void melanger() {
        Collections.shuffle(paquet);
    }

    public void trier() {
        Collections.sort(paquet);
    }

    public void vider() {
        paquet.clear();
    }

    public boolean contientCarte(Carte carte) {
        return paquet.contains(carte);
    }

    public boolean estVide() {
        return paquet.isEmpty();
    }

    public static void creerJeuCartes() {
        JEU_CARTES = new Paquet(ORDONNE);

        creerCartes(12, Type.TYPE_ROI, 1);
        creerCartes(4, Type.TYPE_GRD, 1);
        creerCartes(10, Type.TYPE_GRD, Carte.DEPLACEMENT_GRD_DEUX);
        creerCartes(2, Type.TYPE_GRD, Carte.DEPLACEMENT_GRD_CENTRE);
        creerCartes(2, Type.TYPE_SRC, 1);
        creerCartes(8, Type.TYPE_SRC, 2);
        creerCartes(2, Type.TYPE_SRC, 3);
        creerCartes(1, Type.TYPE_FOU, 1);
        creerCartes(3, Type.TYPE_FOU, 2);
        creerCartes(5, Type.TYPE_FOU, 3);
        creerCartes(3, Type.TYPE_FOU, 4);
        creerCartes(1, Type.TYPE_FOU, 5);
        creerCartes(2, Type.TYPE_FOU, Carte.DEPLACEMENT_FOU_CENTRE);

        JEU_CARTES.trier();
    }

    public static void creerCartes(int nb, int type, int deplacement) {
        Carte carte;
        for (int i = 0; i < nb; i++) {
            carte = new Carte(new Type(type), deplacement);
            JEU_CARTES.ajouter(carte);
        }
    }

    public static Carte texteEnCarte(String nom) {
        return entierEnCarte(Carte.texteEnValeur(nom));
    }

    public static Carte entierEnCarte(int valeur) {
        int type = valeur / 10;
        int deplacement = valeur % 10;
        for (int i = 0; i < JEU_CARTES.getTaille(); i++)
            if (JEU_CARTES.getCarte(i).getType() == type && JEU_CARTES.getCarte(i).getDeplacement() == deplacement)
                return JEU_CARTES.getCarte(i);
        return null;
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

        if (ordonne == ORDONNE && paquet.ordonne == ORDONNE) {
            for (int i = 0; i < getTaille(); i++)
                if (!(getCarte(i) == paquet.getCarte(i)))
                    return false;
        } else if (ordonne == NON_ORDONNE && paquet.ordonne == NON_ORDONNE) {
            for (int i = 0; i < getTaille(); i++)
                if (!(paquet.contientCarte(getCarte(i))))
                    return false;
        }

        return true;
    }

    @Override
    public Paquet clone() {
        try {
            Paquet resultat = (Paquet) super.clone();
            resultat.ordonne = ordonne;
            for (int i = 0; i < getTaille(); i++)
                resultat.ajouter(getCarte(i).clone());
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Bug interne, paquet de cartes non clonable");
        }
    }

    @Override
    public String toString() {
        String txt = "";

        for (int i = 0; i < getTaille(); i++) {
            txt += getCarte(i).toString();
            if (i != getTaille()-1)
                txt += " ";
        }

        return txt;
    }
}
