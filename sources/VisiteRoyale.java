import Modele.Jeu;
import Controleur.ControleurMediateur;
import Vue.*;

public class VisiteRoyale {
    public static void main(String[] args){
        Jeu jeu = new Jeu();
        ControleurMediateur ctrl = new ControleurMediateur(jeu);
        InterfaceTextuelle.demarrer(jeu, ctrl);
    }
}
