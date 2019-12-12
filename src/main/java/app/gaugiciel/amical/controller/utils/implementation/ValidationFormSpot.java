package app.gaugiciel.amical.controller.utils.implementation;

import java.util.List;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.ServiceCotationFrance;
import app.gaugiciel.amical.controller.form.SpotForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormSpot extends ValidationForm<SpotForm> {

	@Autowired
	private ComparaisonFieldInteger comparaisonFieldInteger;
	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean isValide(@NotNull SpotForm spotForm) {
		listeFieldError.clear();
		if (!spotForm.estCotationVide()) {
			List<ServiceCotationFrance> allCotationsFromMin = ServiceCotationFrance
					.getBetween(spotForm.getCotationMin(), ServiceCotationFrance.COTATION_MAX);
			if (!spotForm.getCotationMax().getUnitePrincipale().estVide()
					&& !allCotationsFromMin.contains(spotForm.getCotationMax())) {
				listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), SpotForm.LISTE_COTATION,
						messageSource.getMessage("validation.cotationFranceUniteMax", null, Locale.getDefault())));
			}
		}
		if (comparaisonFieldInteger.comparer(spotForm.getHauteurMinVoie(), spotForm.getHauteurMaxVoie()) > 0) {
			listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), SpotForm.HAUTEUR_MAX_VOIE,
					messageSource.getMessage("validation.hauteurMaxVoie", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
