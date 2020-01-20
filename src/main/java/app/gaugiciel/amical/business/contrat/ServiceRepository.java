package app.gaugiciel.amical.business.contrat;

public interface ServiceRepository<M> {

	public M enregistrer(M model);

	public void modifier();

	public void supprimer(M model);

}
