package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheAuthentification;
import app.gaugiciel.amical.controller.form.EditionEmailForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormEditionEmail extends ValidationForm<EditionEmailForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormEditionEmail.class);

	@Autowired
	private ServiceRechercheAuthentification serviceRechercheAuthentification;

	@Override
	public boolean isValide(@NotNull EditionEmailForm editionEmailForm) {
		listeFieldError.clear();
		if (!Utils.isValid(editionEmailForm.getEmail())) {
			listeFieldError.add(new FieldError(editionEmailForm.getClass().getSimpleName(), EditionEmailForm.EMAIL,
					messageSource.getMessage("validation.notnull", null, Locale.getDefault())));
		}
		if (serviceRechercheAuthentification.existsByEmail(editionEmailForm.getEmail())) {
			listeFieldError.add(new FieldError(editionEmailForm.getClass().getSimpleName(), EditionEmailForm.EMAIL,
					messageSource.getMessage("validation.emailExist", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
