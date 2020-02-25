package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryLongueur;
import app.gaugiciel.amical.controller.form.EditionLongueurForm;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.repository.LongueurRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormEditionLongueur implements Enregistrement<EditionLongueurForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormEditionLongueur.class);

	@Autowired
	private ServiceRepositoryLongueur serviceRepositoryLongueur;
	@Getter
	@Setter
	private Longueur longueur;
	@Autowired
	private LongueurRepository longueurRepository;

	@Override
	public void enregistrer(EditionLongueurForm editionLongueurForm) {
		LOGGER.info("Start {}()", "enregistrer");
		longueur = longueurRepository.findById(editionLongueurForm.getId()).orElseThrow();
		editionLongueurForm.updateLongueur(longueur);
		serviceRepositoryLongueur.enregistrer(longueur);
	}

}
