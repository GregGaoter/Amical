package app.gaugiciel.amical.business.contrat;

import java.util.List;

public interface ServiceRecherche<E, F> {

	public default List<E> rechercher(F form) {
		return null;
	}

}
