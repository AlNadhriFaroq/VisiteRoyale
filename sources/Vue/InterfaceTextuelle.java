package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;

import java.util.Scanner;

public class InterfaceTextuelle implements InterfaceUtilisateur {
    Jeu jeu;
    ControleurMediateur ctrl;

    public InterfaceTextuelle(Jeu jeu, ControleurMediateur ctrl) {
        this.jeu = jeu;
        this.ctrl = ctrl;
        this.jeu.ajouterObservateur(this);
    }

    public static void demarrer(Jeu jeu, ControleurMediateur ctrl) {
        InterfaceUtilisateur vue = new InterfaceTextuelle(jeu, ctrl);
        vue.mettreAJour();
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print("Commande > ");
            ctrl.executerCommande(s.nextLine());
        }
    }

    public void basculerPleinEcran() {
        return;
    }

    @Override
    public void mettreAJour() {
        System.out.println(jeu.toString());
    }
}
