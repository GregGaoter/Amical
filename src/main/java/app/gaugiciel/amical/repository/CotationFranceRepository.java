package app.gaugiciel.amical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import app.gaugiciel.amical.model.CotationFrance;

@Repository
public interface CotationFranceRepository
		extends JpaRepository<CotationFrance, Long>, JpaSpecificationExecutor<CotationFrance> {

}
