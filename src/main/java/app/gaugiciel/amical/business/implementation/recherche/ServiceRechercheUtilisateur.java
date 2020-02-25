package app.gaugiciel.amical.business.implementation.recherche;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.repository.UtilisateurRepository;

@Service
public class ServiceRechercheUtilisateur implements Recherche<Utilisateur, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheUtilisateur.class);

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	public Utilisateur findByEmail(String email) {
		LOGGER.info("Start {}()", "findByEmail");
		return utilisateurRepository.findByAuthentification_email(email);
	}

}
