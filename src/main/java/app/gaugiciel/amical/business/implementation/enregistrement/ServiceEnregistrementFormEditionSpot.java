package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositorySpot;
import app.gaugiciel.amical.controller.form.EditionSpotForm;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.repository.SpotRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormEditionSpot implements Enregistrement<EditionSpotForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormEditionSpot.class);

	@Autowired
	private ServiceRepositorySpot serviceRepositorySpot;
	@Getter
	@Setter
	private Spot spot;
	@Autowired
	private SpotRepository spotRepository;

	@Override
	public void enregistrer(EditionSpotForm editionSpotForm) {
		spot = spotRepository.findById(editionSpotForm.getId()).orElseThrow();
		editionSpotForm.updateSpot(spot);
		serviceRepositorySpot.enregistrer(spot);
	}

}
