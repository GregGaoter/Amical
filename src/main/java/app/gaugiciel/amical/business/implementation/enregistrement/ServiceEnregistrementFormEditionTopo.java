package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryManuel;
import app.gaugiciel.amical.controller.form.EditionTopoForm;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.repository.ManuelRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormEditionTopo implements Enregistrement<EditionTopoForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormEditionTopo.class);

	@Autowired
	private ServiceRepositoryManuel serviceRepositoryManuel;
	@Getter
	@Setter
	private Manuel manuel;
	@Autowired
	private ManuelRepository manuelRepository;

	@Override
	public void enregistrer(EditionTopoForm editionTopoForm) {
		manuel = manuelRepository.findById(editionTopoForm.getId()).orElseThrow();
		editionTopoForm.updateTopo(manuel);
		serviceRepositoryManuel.enregistrer(manuel);
	}

}
