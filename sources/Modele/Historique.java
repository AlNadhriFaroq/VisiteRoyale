package Modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Historique implements Serializable {
    private List<Coup> passe;
    private List<Coup> futur;
    private int tour;

    Historique() {
        initialiser();
    }

    void initialiser() {
        passe = new ArrayList<>();
        futur = new ArrayList<>();
        tour = 0;
    }

    public Coup getCoupPasse() {
        return passe.get(passe.size()-1);
    }

    public Coup getCoupFutur() {
        return futur.get(futur.size()-1);
    }

    public int getNbTour() {
        return tour;
    }

    public void jouerCoup(Coup coup) {
        coup.executer();
        passe.add(coup);
        futur.clear();
        if (coup.getTypeCoup() == Coup.FINIR_TOUR)
            tour++;
    }

    private Coup transfererCoup(List<Coup> source, List<Coup> dest) {
        Coup resultat = source.remove(source.size() - 1);
        dest.add(resultat);
        return resultat;
    }

    public void annulerCoup() {
        Coup coup = transfererCoup(passe, futur);
        coup.desexecuter();
        if (coup.getTypeCoup() == Coup.FINIR_TOUR)
            tour--;
    }

    public void refaireCoup() {
        Coup coup = transfererCoup(futur, passe);
        coup.executer();
        if (coup.getTypeCoup() == Coup.FINIR_TOUR)
            tour++;
    }

    public boolean peutAnnuler() {
        return !passe.isEmpty();
    }

    public boolean peutRefaire() {
        return !futur.isEmpty();
    }
}
