package Modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Historique implements Serializable {
    private List<Coup> passe;
    private List<Coup> futur;

    Historique() {
        initialiser();
    }

    void initialiser() {
        passe = new ArrayList<>();
        futur = new ArrayList<>();
    }

    public void jouerCoup(Coup coup) {
        coup.executer();
        passe.add(coup);
        futur.clear();
    }

    private Coup transfererCoup(List<Coup> source, List<Coup> dest) {
        Coup resultat = source.remove(source.size() - 1);
        dest.add(resultat);
        return resultat;
    }

    public void annulerCoup() {
        Coup coup = transfererCoup(passe, futur);
        coup.desexecuter();
    }

    public void refaireCoup() {
        Coup coup = transfererCoup(futur, passe);
        coup.executer();
    }

    public boolean peutAnnuler() {
        return !passe.isEmpty();
    }

    public boolean peutRefaire() {
        return !futur.isEmpty();
    }
}
