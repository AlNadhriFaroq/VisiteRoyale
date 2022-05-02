package Modele;

public class Partie extends Historique implements Cloneable {
    /* attributs */

    public Partie(/* arguments */) {
        /* instanciations des attributs */
        initialiser(/* arguments */);
    }

    public void initialiser(/* arguments */) {
        /* attribution de valeurs aux attributs */
        return;
    }

    /* getters */

    /* setters simples */

    public Coup creerCoup(/* arguments */) {
        Coup resultat = new Coup();
        /* creer un coup selon les arguments ou renvoie null si le coup n'est pas possible */
        return resultat;
    }

    /* autres methodes / manipulation plus complexe des attributs */

    public boolean estJouable(Coup coup) {
        return false;
    }

    public boolean estTerminee() {
        return false;
    }

    /* autres tests */

    @Override
    public Partie clone() {
        Partie resultat = new Partie();
        return resultat;
    }

    @Override
    public String toString() {
        return "";
    }
}
