package app.gaugiciel.amical.business.implementation.enregistrement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryUtilisateur;
import app.gaugiciel.amical.controller.form.EditionPrenomForm;
import app.gaugiciel.amical.model.Utilisateur;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormEditionPrenom implements Enregistrement<EditionPrenomForm> {

	@Autowired
	private ServiceRepositoryUtilisateur serviceRepositoryUtilisateur;
	@Getter
	@Setter
	private Utilisateur utilisateur;

	@Override
	public void enregistrer(EditionPrenomForm editionPrenomForm) {
		utilisateur = serviceRepositoryUtilisateur.enregistrer(editionPrenomForm.getUtilisateur());
	}

}
