package app.gaugiciel.amical.business.contrat;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public interface ServiceRecherche<T> {

	public List<T> rechercher(Specification<T> specification);

}
