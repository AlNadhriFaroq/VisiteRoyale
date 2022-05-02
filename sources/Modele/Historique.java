package Modele;

import java.util.*;

public class Historique {
    List<Coup> passe, futur;

    public Historique() {
        passe = new ArrayList<>();
        futur = new ArrayList<>();
    }

    private Coup transfert(List<Coup> source, List<Coup> dest) {
        Coup resultat = source.remove(source.size()-1);
        dest.add(resultat);
        return resultat;
    }

    public Coup annuler() {
        Coup coup = transfert(passe, futur);
        coup.desexecuter();
        return coup;
    }

    public Coup refaire() {
        Coup coup = transfert(futur, passe);
        coup.executer();
        return coup;
    }

    public void faire(Coup nouveau) {
        nouveau.executer();
        passe.add(nouveau);
        futur.clear();
    }

    public boolean peutAnnuler() {
        return !passe.isEmpty();
    }

    public boolean peutRefaire() {
        return !futur.isEmpty();
    }
}
