package app.gaugiciel.amical.business.contrat;

import java.util.List;

public interface Recherche<E, F> {

	public default List<E> rechercher(F form) {
		return null;
	}

}
