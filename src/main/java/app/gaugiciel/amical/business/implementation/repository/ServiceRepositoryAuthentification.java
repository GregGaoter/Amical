package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheVoie;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.repository.AuthentificationRepository;

@Service
public class ServiceRepositoryAuthentification implements Repository<Authentification> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositoryAuthentification.class);

	@Autowired
	private AuthentificationRepository authentificationRepository;

	@Override
	public Authentification enregistrer(Authentification authentification) {
		return authentificationRepository.save(authentification);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Authentification authentification) {

	}

}
