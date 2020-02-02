package app.gaugiciel.amical.business.implementation.enregistrement;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceEnregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryAuthentification;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryUtilisateur;
import app.gaugiciel.amical.business.implementation.role.ServiceRoleUtilisateur;
import app.gaugiciel.amical.controller.form.InscriptionForm;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.Role;
import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.repository.AuthentificationRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormInscription implements ServiceEnregistrement<InscriptionForm> {

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
		this.inscriptionForm = inscriptionForm;
		enregistrerAuthentification();
		enregistrerUtilisateur();
	}

	private void enregistrerAuthentification() {
		authentification = serviceRepositoryAuthentification.enregistrer(Authentification.creer(
				inscriptionForm.getEmail(), passwordEncoder.encode(inscriptionForm.getMotDePasse()), true,
				Stream.of(Role.creer(ServiceRoleUtilisateur.AMI.name())).collect(Collectors.toSet())));
	}

	private void enregistrerUtilisateur() {
		utilisateur = serviceRepositoryUtilisateur
				.enregistrer(Utilisateur.creer(inscriptionForm.getEmail(), inscriptionForm.getPrenom(),
						inscriptionForm.getNom(), authentificationRepository.findByEmail(inscriptionForm.getEmail())));
	}

}