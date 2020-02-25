package app.gaugiciel.amical.business.implementation.enregistrement;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.enumeration.RoleUtilisateur;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryAuthentification;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryUtilisateur;
import app.gaugiciel.amical.controller.form.InscriptionForm;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.Role;
import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.repository.AuthentificationRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormInscription implements Enregistrement<InscriptionForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormInscription.class);

	@Autowired
	private InscriptionForm inscriptionForm;
	@Autowired
	private ServiceRepositoryAuthentification serviceRepositoryAuthentification;
	@Autowired
	private ServiceRepositoryUtilisateur serviceRepositoryUtilisateur;
	@Autowired
	private AuthentificationRepository authentificationRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Getter
	@Setter
	private Authentification authentification;
	@Getter
	@Setter
	private Utilisateur utilisateur;

	@Override
	public void enregistrer(InscriptionForm inscriptionForm) {
		LOGGER.info("Start {}()", "enregistrer");
		this.inscriptionForm = inscriptionForm;
		enregistrerAuthentification();
		enregistrerUtilisateur();
	}

	private void enregistrerAuthentification() {
		LOGGER.info("Start {}()", "enregistrerAuthentification");
		authentification = serviceRepositoryAuthentification.enregistrer(Authentification.creer(
				inscriptionForm.getEmail(), passwordEncoder.encode(inscriptionForm.getMotDePasse()), true,
				Stream.of(Role.creer(RoleUtilisateur.AMI.name())).collect(Collectors.toSet())));
	}

	private void enregistrerUtilisateur() {
		LOGGER.info("Start {}()", "enregistrerUtilisateur");
		utilisateur = serviceRepositoryUtilisateur
				.enregistrer(Utilisateur.creer(inscriptionForm.getEmail(), inscriptionForm.getPrenom(),
						inscriptionForm.getNom(), authentificationRepository.findByEmail(inscriptionForm.getEmail())));
	}

}
