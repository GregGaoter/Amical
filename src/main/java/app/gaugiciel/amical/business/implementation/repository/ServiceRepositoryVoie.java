package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.repository.VoieRepository;

@Service
public class ServiceRepositoryVoie implements Repository<Voie> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositoryVoie.class);

	@Autowired
	private VoieRepository voieRepository;

	@Override
	public Voie enregistrer(Voie voie) {
		LOGGER.info("Start {}()", "enregistrer");
		return voieRepository.save(voie);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Voie voie) {

	}

}
