package app.gaugiciel.amical.business.contrat;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ServiceRecherche<E, F> {

	public default List<E> rechercher(F form) {
		return null;
	}

}