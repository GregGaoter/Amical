package app.gaugiciel.amical.business.contrat;

import org.springframework.stereotype.Service;

@Service
public interface ServiceEnregistrementForm<F> {

	public void enregistrer(F form);

}
