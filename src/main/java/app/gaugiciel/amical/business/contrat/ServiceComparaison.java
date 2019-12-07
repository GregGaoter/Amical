package app.gaugiciel.amical.business.contrat;

import org.springframework.stereotype.Service;

@Service
public interface ServiceComparaison<T> {

	public int comparer(T arg1, T arg2);

}
