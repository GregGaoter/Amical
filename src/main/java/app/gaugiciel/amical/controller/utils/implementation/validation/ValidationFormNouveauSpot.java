package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.NouveauSpotForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormNouveauSpot extends ValidationForm<NouveauSpotForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormNouveauSpot.class);

	@Override
	public boolean isValide(@NotNull NouveauSpotForm nouveauSpotForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		String nom = nouveauSpotForm.getNom();
		if (nom.length() < 1 || nom.length() > 128) {
			listeFieldError.add(new FieldError(nouveauSpotForm.getClass().getSimpleName(), NouveauSpotForm.NOM,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "128" },
							Locale.getDefault())));
		}
		if (nouveauSpotForm.getDescription().length() > 2000) {
			listeFieldError.add(new FieldError(nouveauSpotForm.getClass().getSimpleName(), NouveauSpotForm.DESCRIPTION,
					messageSource.getMessage("validation.form.size.max", new String[] { "2000" },
							Locale.getDefault())));
		}
		if (nouveauSpotForm.getRemarque().length() > 2000) {
			listeFieldError.add(
					new FieldError(nouveauSpotForm.getClass().getSimpleName(), NouveauSpotForm.REMARQUE, messageSource
							.getMessage("validation.form.size.max", new String[] { "2000" }, Locale.getDefault())));
		}
		if (!Utils.isValid(nouveauSpotForm.getTagQ())) {
			listeFieldError.add(new FieldError(nouveauSpotForm.getClass().getSimpleName(), NouveauSpotForm.TAG_Q,
					messageSource.getMessage("validation.notnull", null, Locale.getDefault())));
		}
		String lieuFrance = nouveauSpotForm.getLieuFrance();
		if (lieuFrance.length() < 1 || lieuFrance.length() > 256) {
			listeFieldError.add(new FieldError(nouveauSpotForm.getClass().getSimpleName(), NouveauSpotForm.LIEU_FRANCE,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "256" },
							Locale.getDefault())));
		}
		if (nouveauSpotForm.getPlan().length() > 256) {
			listeFieldError.add(new FieldError(nouveauSpotForm.getClass().getSimpleName(), NouveauSpotForm.PLAN,
					messageSource.getMessage("validation.form.size.max", new String[] { "256" }, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
