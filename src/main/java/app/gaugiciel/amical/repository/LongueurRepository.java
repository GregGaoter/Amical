package app.gaugiciel.amical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.gaugiciel.amical.model.Longueur;

@Repository
public interface LongueurRepository extends JpaRepository<Longueur, Long>, JpaSpecificationExecutor<Longueur> {

	public List<Longueur> findByVoie_idOrderByNom(Long voieId);

}
