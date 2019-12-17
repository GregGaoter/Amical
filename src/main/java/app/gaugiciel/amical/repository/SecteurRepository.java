package app.gaugiciel.amical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.gaugiciel.amical.model.Secteur;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long>, JpaSpecificationExecutor<Secteur> {

}
