package Controleur;

import Global.Configuration;
import Modele.*;

import java.util.List;

abstract class IA {
    private Jeu jeu;
    Partie partie;

    static IA nouvelle(Jeu j) {
        IA instance = null;
        String name = Configuration.instance().lire("IA");
        try {
            instance = (IA) ClassLoader.getSystemClassLoader().loadClass(name).newInstance();
            instance.jeu = j;
        } catch (Exception e) {
            System.err.println("Impossible de trouver l'IA : " + name);
            System.err.println(e);
        }
        return instance;
    }

    final List<Coup> elaborerCoups() {
        partie = jeu.partie().clone();
        return jouer();
    }

    final void activeIA() {
        if (jeu.partie() != null)
            partie = jeu.partie().clone();
    }

    List<Coup> jouer() {
        return null;
    }
}
