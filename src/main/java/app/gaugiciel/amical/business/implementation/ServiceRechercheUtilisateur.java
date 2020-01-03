package app.gaugiciel.amical.business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRecherche;
import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.repository.UtilisateurRepository;

@Service
public class ServiceRechercheUtilisateur implements ServiceRecherche<Utilisateur, Object> {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	public Utilisateur findByEmail(String email) {
		return utilisateurRepository.findByAuthentification_email(email);
	}

}
