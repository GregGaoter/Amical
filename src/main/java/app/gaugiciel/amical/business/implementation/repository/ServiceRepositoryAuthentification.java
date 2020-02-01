package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRepository;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.repository.AuthentificationRepository;

@Service
public class ServiceRepositoryAuthentification implements ServiceRepository<Authentification> {

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
