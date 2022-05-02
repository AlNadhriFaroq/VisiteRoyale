package Controleur;

public abstract class Animation {
    int lenteur;
    int decompte;

    public Animation(int l) {
        lenteur = l;
        decompte = l;
    }

    public void tictac() {
        decompte--;
        if (decompte <= 0) {
            decompte = lenteur;
            miseAJour();
        }
    }

    public abstract void miseAJour();

    public boolean estTerminee() {
        return false;
    }
}
