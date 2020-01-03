package app.gaugiciel.amical.business.contrat;

import org.springframework.stereotype.Service;

@Service
public interface ServiceRepository<M> {

	public void enregistrer(M model);

	public void modifier();

	public void supprimer(M model);

}
