package app.gaugiciel.amical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.gaugiciel.amical.model.Authentification;

@Repository
public interface AuthentificationRepository
		extends JpaRepository<Authentification, String>, JpaSpecificationExecutor<Authentification> {

	public Authentification findByEmail(String email);

}
