package Controleur;

import Global.Configuration;
import Modele.*;
import Vue.InterfaceGraphique;

public class ControleurMediateur {
    Jeu jeu;
    InterfaceGraphique vue;

    public ControleurMediateur(Jeu jeu) {
        this.jeu = jeu;
    }

    public void ajouterInterfaceGraphique(InterfaceGraphique vue) {
        this.vue = vue;
    }

    public void toucheClavier(String touche) {
        switch (touche) {
            case "Annuler":
                annuler();
                break;
            case "Refaire":
                refaire();
                break;
            case "Pause":
                //basculeAnimations();
                break;
            case "IA":
                //basculeIA();
                break;
            case "PleinEcran":
                basculerPleinEcran();
                break;
            case "NouvellePartie":
                nouvellePartie();
                break;
            case "Quitter":
                System.exit(0);
                break;
            default:
                System.out.println("Touche inconnue : " + touche);
        }
    }

    public void clicSouris(int x, int y) {
        return;
    }

    public void jouer(Coup coup) {
        if (coup != null) {
            jeu.jouerCoup(coup);
        }
    }

    public void annuler() {
        if (jeu.peutAnnuler()) {
            Coup coup = jeu.annulerCoup();
        }
    }

    public void refaire() {
        if (jeu.peutRefaire()) {
            Coup coup = jeu.refaireCoup();
        }
    }

    public void basculerPleinEcran() {
        Configuration.instance().ecrire("PleinEcran", String.valueOf(!Boolean.parseBoolean(Configuration.instance().lire("PleinEcran"))));
        vue.mettreAJourPleinEcran();
    }

    public void nouvellePartie() {
        jeu.nouvellePartie();
    }
}
