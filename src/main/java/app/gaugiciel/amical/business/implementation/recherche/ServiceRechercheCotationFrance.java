package app.gaugiciel.amical.business.implementation.recherche;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.repository.CotationFranceRepository;

@Service
public class ServiceRechercheCotationFrance implements Recherche<CotationFrance, Object> {

	@Autowired
	private CotationFranceRepository cotationFranceRepository;

	public Optional<CotationFrance> findOne(Specification<CotationFrance> specificationCotationEqual) {
		return cotationFranceRepository.findOne(specificationCotationEqual);
	}

}
