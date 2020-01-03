package app.gaugiciel.amical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import app.gaugiciel.amical.model.Utilisateur;

public interface UtilisateurRepository
		extends JpaRepository<Utilisateur, String>, JpaSpecificationExecutor<Utilisateur> {

	public Utilisateur findByAuthentification_email(String email);

}
