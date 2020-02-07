package app.gaugiciel.amical.business.contrat;

public interface Repository<M> {

	public M enregistrer(M model);

	public void modifier();

	public void supprimer(M model);

}
