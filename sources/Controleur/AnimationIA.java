package Controleur;

import Modele.Coup;

import java.util.List;

class AnimationIA extends Animation {
    IA joueur;
    ControleurMediateur ctrl;
    List<Coup> enAttente;

    AnimationIA(int lenteur, IA joueur, ControleurMediateur ctrl) {
        super(lenteur);
        this.joueur = joueur;
        this.ctrl = ctrl;
    }

    @Override
    public void miseAJour() {
        if ((enAttente == null) || enAttente.isEmpty())
            enAttente = joueur.elaborerCoups();
        if ((enAttente == null) || enAttente.isEmpty())
            System.err.println("Bug : l'IA n'a joué aucun coup");
        else
            ctrl.jouer(enAttente.remove(enAttente.size()-1));
    }
}
