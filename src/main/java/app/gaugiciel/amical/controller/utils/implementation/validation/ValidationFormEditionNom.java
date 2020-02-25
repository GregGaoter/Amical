package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.EditionNomForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormEditionNom extends ValidationForm<EditionNomForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormEditionNom.class);

	@Override
	public boolean isValide(@NotNull EditionNomForm editionNomForm) {
		listeFieldError.clear();
		if (!Utils.isValid(editionNomForm.getNom())) {
			listeFieldError.add(new FieldError(editionNomForm.getClass().getSimpleName(), EditionNomForm.NOM,
					messageSource.getMessage("validation.notnull", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
