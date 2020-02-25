package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.repository.CotationFranceRepository;

@Service
public class ServiceRepositoryCotationFrance implements Repository<CotationFrance> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositoryCotationFrance.class);

	@Autowired
	private CotationFranceRepository cotationFranceRepository;

	@Override
	public CotationFrance enregistrer(CotationFrance cotationFrance) {
		LOGGER.info("Start {}()", "enregistrer");
		return cotationFranceRepository.save(cotationFrance);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(CotationFrance cotationFrance) {

	}

}
