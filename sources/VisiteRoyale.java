import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.*;

public class VisiteRoyale {
    public static void main(String[] args) {
        Programme prog = new Programme();
        ControleurMediateur ctrl = new ControleurMediateur(prog);
        boolean interfaceGraphique = true;
        InterfaceUtilisateur vue = interfaceGraphique ? new InterfaceGraphique(ctrl, prog) : new InterfaceTextuelle(ctrl, prog);
        vue.run();
    }
}
