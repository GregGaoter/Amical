package app.gaugiciel.amical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.gaugiciel.amical.model.Voie;

@Repository
public interface VoieRepository extends JpaRepository<Voie, Long>, JpaSpecificationExecutor<Voie> {

}
