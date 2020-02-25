package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryVoie;
import app.gaugiciel.amical.controller.form.EditionVoieForm;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.repository.VoieRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormEditionVoie implements Enregistrement<EditionVoieForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormEditionVoie.class);

	@Autowired
	private ServiceRepositoryVoie serviceRepositoryVoie;
	@Getter
	@Setter
	private Voie voie;
	@Autowired
	private VoieRepository voieRepository;

	@Override
	public void enregistrer(EditionVoieForm editionVoieForm) {
		LOGGER.info("Start {}()", "enregistrer");
		voie = voieRepository.findById(editionVoieForm.getId()).orElseThrow();
		editionVoieForm.updateVoie(voie);
		serviceRepositoryVoie.enregistrer(voie);
	}

}
