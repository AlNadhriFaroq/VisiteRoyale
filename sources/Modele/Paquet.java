package Modele;

import java.util.*;

public class Paquet implements Cloneable {
    public static Paquet JEU_CARTES;

    public static final boolean ORDONNE = true;
    public static final boolean NON_ORDONNE = false;

    private List<Carte> cartes;
    private boolean ordonne;

    Paquet(boolean ordonne) {
        cartes = new ArrayList<>();
        this.ordonne = ordonne;
    }

    public Carte getCarte(int indice) {
        return cartes.get(indice);
    }

    public Carte getCarte(int type, int deplacement) {
        for (int i = 0; i < getTaille(); i++)
            if (getCarte(i).getType() == type && getCarte(i).getDeplacement() == deplacement)
                return getCarte(i);
        throw new RuntimeException("Modele.Paquet.getCarte() : Type ou deplacement invalide.");
    }

    public int getIndiceCarte(Carte carte) {
        return cartes.indexOf(carte);
    }

    public int getTaille() {
        return cartes.size();
    }

    public int getNombreTypeCarte(int type) {
        int nb = 0;
        for (int i = 0; i < getTaille(); i++)
            if (cartes.get(i).getType() == type)
                nb++;
        return nb;
    }

    public int getNombreCarte(int type, int deplacement) {
        int nb = 0;
        for (int i = 0; i < getTaille(); i++)
            if (cartes.get(i).getType() == type && cartes.get(i).getDeplacement() == deplacement)
                nb++;
        return nb;
    }

    void ajouter(Carte carte) {
        cartes.add(carte);
    }

    Carte piocher() {
        if (!ordonne)
            throw new RuntimeException("Modele.Paquet.piocher() : Impossible de piocher une carte dans un paquet non ordonne.");
        if (!estVide())
            return cartes.remove(getTaille()-1);
        return null;
    }

    Carte defausser(Carte carte) {
        if (ordonne)
            throw new RuntimeException("Modele.Paquet.defausser() : Impossible de defausser une carte dans un paquet ordonne.");
        cartes.remove(carte);
        return carte;
    }

    Carte defausser(int indiceCarte) {
        if (ordonne)
            throw new RuntimeException("Modele.Paquet.defausser() : Impossible de defaussser une carte dans un paquet ordonne.");
        return cartes.remove(indiceCarte);
    }

    void permuterCartes(Carte carte1, Carte carte2) {
        if (ordonne)
            throw new RuntimeException("Modele.Paquet.permuterCartes() : Impossible de permuter des cartes dans un paquet ordonne.");
        int indiceCarte1 = cartes.indexOf(carte1);
        int indiceCarte2 = cartes.indexOf(carte2);
        cartes.set(indiceCarte1, carte2);
        cartes.set(indiceCarte2, carte1);
    }

    void permuterCartes(int indiceCarte1, int indiceCarte2) {
        if (ordonne)
            throw new RuntimeException("Modele.Paquet.permuterCartes() : Impossible de permuter des cartes dans un paquet ordonne.");
        Carte carte1 = cartes.get(indiceCarte1);
        Carte carte2 = cartes.get(indiceCarte2);
        cartes.set(indiceCarte1, carte2);
        cartes.set(indiceCarte2, carte1);
    }

    void remplir() {
        for (int i = 0; i < JEU_CARTES.getTaille(); i++)
            ajouter(JEU_CARTES.getCarte(i));
    }

    void transferer(Paquet paquet) {
        if (ordonne)
            while (!paquet.estVide())
                ajouter(paquet.piocher());
        else
            while (!paquet.estVide())
                ajouter(paquet.defausser(paquet.getTaille()-1));
    }

    void copier(Paquet paquet) {
        for (int i = 0; i < paquet.getTaille(); i++)
            ajouter(paquet.getCarte(i));
    }

    void melanger() {
        Collections.shuffle(cartes);
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

    static void creerJeuCartes() {
        JEU_CARTES = new Paquet(ORDONNE);

        creerCartes(12, Type.ROI, 1);
        creerCartes(4, Type.GAR, 1);
        creerCartes(10, Type.GAR, Carte.DEPLACEMENT_GAR_1PLUS1);
        creerCartes(2, Type.GAR, Carte.DEPLACEMENT_GAR_CENTRE);
        creerCartes(2, Type.SOR, 1);
        creerCartes(8, Type.SOR, 2);
        creerCartes(2, Type.SOR, 3);
        creerCartes(1, Type.FOU, 1);
        creerCartes(3, Type.FOU, 2);
        creerCartes(4, Type.FOU, 3);
        creerCartes(3, Type.FOU, 4);
        creerCartes(1, Type.FOU, 5);
        creerCartes(2, Type.FOU, Carte.DEPLACEMENT_FOU_CENTRE);

        JEU_CARTES.trier();
    }

    private static void creerCartes(int nb, int type, int deplacement) {
        Carte carte;
        for (int i = 0; i < nb; i++) {
            carte = new Carte(type, deplacement);
            JEU_CARTES.ajouter(carte);
        }
    }

    public static Carte texteEnCarte(String txt) {
        try {
            return entierEnCarte(Carte.texteEnValeur(txt));
        } catch (RuntimeException e) {
            return null;
        }
    }

    public static Carte entierEnCarte(int val) {
        int type = val / 10;
        int deplacement = val % 10;
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
                if (!(getCarte(i).equals(paquet.getCarte(i))))
                    return false;
            return true;
        } else if (ordonne == NON_ORDONNE && paquet.ordonne == NON_ORDONNE) {
            for (int i = 0; i < getTaille(); i++)
                if (!(paquet.contientCarte(getCarte(i))))
                    return false;
            return true;
        }

        return false;
    }

    @Override
    public Paquet clone() {
        try {
            Paquet resultat = (Paquet) super.clone();
            resultat.ordonne = ordonne;
            resultat.cartes = new ArrayList<>();
            for (int i = 0; i < getTaille(); i++)
                resultat.ajouter(getCarte(i).clone());
            return resultat;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Modele.Paquet.cloner() : Paquet non clonable.");
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
