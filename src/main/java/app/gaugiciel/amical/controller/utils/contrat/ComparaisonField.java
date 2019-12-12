package app.gaugiciel.amical.controller.utils.contrat;

import org.springframework.stereotype.Component;

@Component
public interface ComparaisonField<T> {

	public int comparer(T arg1, T arg2);

}
