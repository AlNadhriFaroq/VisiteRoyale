package Modele;

import Patterns.Observable;

public class Jeu extends Observable {
    Partie partie;

    public Jeu(/* arguments */) {
        partie = new Partie(/* arguments */);
    }

    public Partie partie() {
        return partie;
    }

    public void nouvellePartie(/* arguments */) {
        partie = new Partie(/* arguments */);
        mettreAJour();
    }

    public Coup creerCoup(/* arguments */) {
        return partie.creerCoup(/* arguments */);
    }

    public void jouerCoup(Coup coup) {
        coup.fixerPartie(partie);
        partie.faire(coup);
        mettreAJour();
    }

    public Coup annulerCoup() {
        Coup coup = partie.annuler();
        mettreAJour();
        return coup;
    }

    public Coup refaireCoup() {
        Coup coup = partie.refaire();
        mettreAJour();
        return coup;
    }

    public boolean partieTerminee() {
        return partie.estTerminee();
    }

    public boolean jeuTerminee() {
        return false;
    }

    public boolean peutAnnuler() {
        return partie.peutAnnuler();
    }

    public boolean peutRefaire() {
        return partie.peutRefaire();
    }
}
