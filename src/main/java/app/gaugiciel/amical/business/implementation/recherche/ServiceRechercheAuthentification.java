package app.gaugiciel.amical.business.implementation.recherche;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementPlanServeur;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.repository.AuthentificationRepository;

@Service
public class ServiceRechercheAuthentification implements Recherche<Authentification, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheAuthentification.class);

	@Autowired
	private AuthentificationRepository authentificationRepository;

	public Authentification findByEmail(String email) {
		return authentificationRepository.findByEmail(email);
	}

	public boolean existsByEmail(String email) {
		return authentificationRepository.existsById(email);
	}

}
