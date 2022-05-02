package Patterns;

import java.util.*;

public class Observable {
	List<Observateur> observateurs;

	public Observable() {
		observateurs = new ArrayList<>();
	}

	public void ajouterObservateur(Observateur obs) {
		observateurs.add(obs);
	}

	public void mettreAJour() {
		Iterator<Observateur> it;

		it = observateurs.iterator();
		while (it.hasNext()) {
			Observateur o = it.next();
			o.mettreAJour();
		}
	}
}
