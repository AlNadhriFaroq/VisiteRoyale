import Modele.Jeu;
import Controleur.ControleurMediateur;
import Vue.InterfaceGraphique;

public class Application {
    public static void main(String[] args){
        Jeu jeu = new Jeu();
        ControleurMediateur ctrl = new ControleurMediateur(jeu);
        InterfaceGraphique.demarrer(jeu, ctrl);
    }
}
