package app.gaugiciel.amical.business.implementation.recherche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRecherche;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.repository.AuthentificationRepository;

@Service
public class ServiceRechercheAuthentification implements ServiceRecherche<Authentification, Object> {

	@Autowired
	private AuthentificationRepository authentificationRepository;

	public Authentification findByEmail(String email) {
		return authentificationRepository.findByEmail(email);
	}

	public boolean existsByEmail(String email) {
		return authentificationRepository.existsById(email);
	}

}
