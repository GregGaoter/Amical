package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryUtilisateur;
import app.gaugiciel.amical.controller.form.EditionNomForm;
import app.gaugiciel.amical.model.Utilisateur;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormEditionNom implements Enregistrement<EditionNomForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormEditionNom.class);

	@Autowired
	private ServiceRepositoryUtilisateur serviceRepositoryUtilisateur;
	@Getter
	@Setter
	private Utilisateur utilisateur;

	@Override
	public void enregistrer(EditionNomForm editionNomForm) {
		LOGGER.info("Start {}()", "enregistrer");
		utilisateur = serviceRepositoryUtilisateur.enregistrer(editionNomForm.getUtilisateur());
	}

}
