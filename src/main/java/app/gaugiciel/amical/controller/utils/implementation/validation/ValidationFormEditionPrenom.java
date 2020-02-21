package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.EditionPrenomForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormEditionPrenom extends ValidationForm<EditionPrenomForm> {

	@Override
	public boolean isValide(@NotNull EditionPrenomForm editionPrenomForm) {
		listeFieldError.clear();
		if (!Utils.isValid(editionPrenomForm.getPrenom())) {
			listeFieldError.add(new FieldError(editionPrenomForm.getClass().getSimpleName(), EditionPrenomForm.PRENOM,
					messageSource.getMessage("validation.notnull", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
