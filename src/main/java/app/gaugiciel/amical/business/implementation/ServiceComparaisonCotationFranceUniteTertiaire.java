package app.gaugiciel.amical.business.implementation;

import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceComparaison;

@Service
public class ServiceComparaisonCotationFranceUniteTertiaire
		implements ServiceComparaison<ServiceCotationUniteFranceTertiaire> {

	@Override
	public int comparer(ServiceCotationUniteFranceTertiaire u1, ServiceCotationUniteFranceTertiaire u2) {
		return u1.value - u2.value;
	}

}
