package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.repository.SpotRepository;

@Service
public class ServiceRepositorySpot implements Repository<Spot> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositorySpot.class);

	@Autowired
	private SpotRepository spotRepository;

	@Override
	public Spot enregistrer(Spot spot) {
		LOGGER.info("Start {}()", "enregistrer");
		return spotRepository.save(spot);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Spot spot) {

	}

}
