package app.gaugiciel.amical.controller.utils.implementation;

import org.springframework.stereotype.Component;

import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteTertiaire;
import app.gaugiciel.amical.controller.utils.contrat.ComparaisonField;

@Component
public class ComparaisonFieldCotationFranceUniteTertiaire implements ComparaisonField<String> {

	@Override
	public int comparer(String u1, String u2) {
		int v1 = ServiceCotationFranceUniteTertiaire.ofLabel(u1).value;
		int v2 = ServiceCotationFranceUniteTertiaire.ofLabel(u2).value;
		return v1 - v2;
	}

}
