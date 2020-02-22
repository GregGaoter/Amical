package app.gaugiciel.amical.business.implementation.enregistrement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryAuthentification;
import app.gaugiciel.amical.controller.form.EditionEmailForm;
import app.gaugiciel.amical.model.Authentification;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormEditionEmail implements Enregistrement<EditionEmailForm> {

	@Autowired
	private ServiceRepositoryAuthentification serviceRepositoryAuthentification;
	@Getter
	@Setter
	private Authentification authentification;

	@Override
	public void enregistrer(EditionEmailForm editionEmailForm) {
		authentification = serviceRepositoryAuthentification.enregistrer(editionEmailForm.getAuthentification());
	}

}
