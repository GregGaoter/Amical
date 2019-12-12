package app.gaugiciel.amical.controller.utils.implementation;

import org.springframework.stereotype.Component;

import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUnitePrincipale;
import app.gaugiciel.amical.controller.utils.contrat.ComparaisonField;

@Component
public class ComparaisonFieldCotationFranceUnitePrincipale implements ComparaisonField<String> {

	@Override
	public int comparer(String u1, String u2) {
		if (u1.strip().length() == 0 || u2.strip().length() == 0) {
			return 0;
		}
		int v1 = ServiceCotationFranceUnitePrincipale.ofLabel(u1).value;
		int v2 = ServiceCotationFranceUnitePrincipale.ofLabel(u2).value;
		return v1 - v2;
	}

}
