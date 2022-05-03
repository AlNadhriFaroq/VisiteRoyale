package Modele;

public class Coup implements Cloneable {
    Jeu jeu;
    /* autres attributs */

    public Coup(/* arguments */) {
        /* a faire */
    }

    public void fixerJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    /* autres methodes */

    public void executer() {
        return;
    }

    public void desexecuter() {
        return;
    }

    @Override
    public Coup clone() {
        return null;
    }
}
