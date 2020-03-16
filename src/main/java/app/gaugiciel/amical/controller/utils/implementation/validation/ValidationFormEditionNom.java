package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.EditionNomForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormEditionNom extends ValidationForm<EditionNomForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormEditionNom.class);

	@Override
	public boolean isValide(@NotNull EditionNomForm editionNomForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		String nom = editionNomForm.getNom();
		if (nom.length() < 1 || nom.length() > 64) {
			listeFieldError.add(new FieldError(editionNomForm.getClass().getSimpleName(), EditionNomForm.NOM,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "64" },
							Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
