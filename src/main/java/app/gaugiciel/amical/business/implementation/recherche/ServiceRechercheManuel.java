package app.gaugiciel.amical.business.implementation.recherche;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.repository.ManuelRepository;

@Service
public class ServiceRechercheManuel implements Recherche<Manuel, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheManuel.class);

	@Autowired
	private ManuelRepository manuelRepository;

	public Manuel findById(Long id) {
		return manuelRepository.findById(id).orElse(null);
	}

}
