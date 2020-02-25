package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.DemandePretEmpruntManuel;
import app.gaugiciel.amical.repository.DemandePretEmpruntManuelRepository;

@Service
public class ServiceRepositoryDemandePretEmpruntManuel implements Repository<DemandePretEmpruntManuel> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositoryDemandePretEmpruntManuel.class);

	@Autowired
	private DemandePretEmpruntManuelRepository demandePretEmpruntManuelRepository;

	@Override
	public DemandePretEmpruntManuel enregistrer(DemandePretEmpruntManuel demandePretEmpruntManuel) {
		return demandePretEmpruntManuelRepository.save(demandePretEmpruntManuel);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(DemandePretEmpruntManuel demandePretEmpruntManuel) {
		demandePretEmpruntManuelRepository.delete(demandePretEmpruntManuel);
	}

}
