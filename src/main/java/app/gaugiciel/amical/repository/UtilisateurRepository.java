package app.gaugiciel.amical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.gaugiciel.amical.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {

}
