package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.repository.UtilisateurRepository;

@Service
public class ServiceRepositoryUtilisateur implements Repository<Utilisateur> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositoryUtilisateur.class);

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Override
	public Utilisateur enregistrer(Utilisateur utilisateur) {
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Utilisateur utilisateur) {

	}

}
