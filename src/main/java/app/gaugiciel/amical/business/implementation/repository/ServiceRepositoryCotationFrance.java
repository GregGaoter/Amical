package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.repository.CotationFranceRepository;

@Service
public class ServiceRepositoryCotationFrance implements Repository<CotationFrance> {

	@Autowired
	private CotationFranceRepository cotationFranceRepository;

	@Override
	public CotationFrance enregistrer(CotationFrance cotationFrance) {
		return cotationFranceRepository.save(cotationFrance);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(CotationFrance cotationFrance) {

	}

}
