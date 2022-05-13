import Modele.Jeu;
import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.*;

public class VisiteRoyale {
    public static void main(String[] args){
        Programme prog = new Programme();
        ControleurMediateur ctrl = new ControleurMediateur(prog);
        //InterfaceTextuelle vue = new InterfaceTextuelle(prog, ctrl);
        InterfaceGraphique vue = new InterfaceGraphique(prog, ctrl);
        vue.run();
    }
}
