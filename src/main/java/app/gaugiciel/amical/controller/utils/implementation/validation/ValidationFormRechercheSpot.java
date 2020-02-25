package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFrance;
import app.gaugiciel.amical.controller.form.RechercheSpotForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.controller.utils.implementation.comparaison.ComparaisonFieldInteger;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormRechercheSpot extends ValidationForm<RechercheSpotForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormRechercheSpot.class);

	@Autowired
	private ComparaisonFieldInteger comparaisonFieldInteger;
	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean isValide(@NotNull RechercheSpotForm spotForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		if (!spotForm.estCotationVide()) {
			List<ServiceCotationFrance> allCotationsFromMin = ServiceCotationFrance
					.getBetween(spotForm.getCotationMin(), ServiceCotationFrance.COTATION_MAX);
			if (!spotForm.getCotationMax().getUnitePrincipale().estVide()
					&& !allCotationsFromMin.contains(spotForm.getCotationMax())) {
				listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), RechercheSpotForm.LISTE_COTATION,
						messageSource.getMessage("validation.cotationFranceUniteMax", null, Locale.getDefault())));
			}
		}
		if (!Objects.isNull(spotForm.getHauteurMinVoie()) && !Objects.isNull(spotForm.getHauteurMaxVoie())
				&& comparaisonFieldInteger.comparer(spotForm.getHauteurMinVoie(), spotForm.getHauteurMaxVoie()) > 0) {
			listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), RechercheSpotForm.HAUTEUR_MAX_VOIE,
					messageSource.getMessage("validation.hauteurMaxVoie", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
