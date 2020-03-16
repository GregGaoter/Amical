package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.EditionPrenomForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormEditionPrenom extends ValidationForm<EditionPrenomForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormEditionPrenom.class);

	@Override
	public boolean isValide(@NotNull EditionPrenomForm editionPrenomForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		String prenom = editionPrenomForm.getPrenom();
		if (!Utils.isValid(prenom)) {
			listeFieldError.add(new FieldError(editionPrenomForm.getClass().getSimpleName(), EditionPrenomForm.PRENOM,
					messageSource.getMessage("validation.notnull", null, Locale.getDefault())));
		}
		if (prenom.length() < 1 || prenom.length() > 64) {
			listeFieldError.add(new FieldError(editionPrenomForm.getClass().getSimpleName(), EditionPrenomForm.PRENOM,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "64" },
							Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
