package app.gaugiciel.amical.business.implementation;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.contrat.ServiceValidationForm;
import app.gaugiciel.amical.controller.form.SpotForm;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ServiceValidationFormSpot extends ServiceValidationForm<SpotForm> {

	@Autowired
	private ServiceComparaisonString serviceComparaisonFormInputString;

	@Override
	public boolean isValide(@NotNull SpotForm spotForm) {
		validerCotationMaxVoieUnitePrincipale(spotForm);
		validerCotationMaxVoieUniteSecondaire(spotForm);
		validerCotationMaxVoieUniteTertiaire(spotForm);
		return listeFieldError.isEmpty();
	}

	private void validerCotationMaxVoieUnitePrincipale(@NotNull SpotForm spotForm) {
		if (spotForm.getCotationMinVoieUnitePrincipale().strip().length() != 0
				&& spotForm.getCotationMaxVoieUnitePrincipale().strip().length() != 0) {
			if (serviceComparaisonFormInputString.comparer(spotForm.getCotationMinVoieUnitePrincipale(),
					spotForm.getCotationMaxVoieUnitePrincipale()) > 0) {
				listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(),
						"cotationMaxVoieUnitePrincipale", "{validation.cotationFranceUnitePrincipaleMax}"));
			}
		}
	}

	private void validerCotationMaxVoieUniteSecondaire(@NotNull SpotForm spotForm) {
		if (spotForm.getCotationMinVoieUniteSecondaire().strip().length() != 0
				&& spotForm.getCotationMaxVoieUniteSecondaire().strip().length() != 0) {
			if (serviceComparaisonFormInputString.comparer(spotForm.getCotationMinVoieUniteSecondaire(),
					spotForm.getCotationMaxVoieUniteSecondaire()) > 0) {
				listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(),
						"cotationMaxVoieUniteSecondaire", "{validation.cotationFranceUniteSecondaireMax}"));
			}
		}
	}

	private void validerCotationMaxVoieUniteTertiaire(@NotNull SpotForm spotForm) {
		if (spotForm.getCotationMinVoieUniteTertiaire().strip().length() != 0
				&& spotForm.getCotationMaxVoieUniteTertiaire().strip().length() != 0) {
			if (serviceComparaisonFormInputString.comparer(spotForm.getCotationMinVoieUniteTertiaire(),
					spotForm.getCotationMaxVoieUniteTertiaire()) > 0) {
				listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), "cotationMaxVoieUniteTertiaire",
						"{validation.cotationFranceUniteTertiaireMax}"));
			}
		}
	}

}
