package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheAuthentification;
import app.gaugiciel.amical.controller.form.InscriptionForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormInscription extends ValidationForm<InscriptionForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormInscription.class);

	@Autowired
	private ServiceRechercheAuthentification serviceRechercheAuthentification;
	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean isValide(InscriptionForm inscriptionForm) {
		listeFieldError.clear();
		if (serviceRechercheAuthentification.existsByEmail(inscriptionForm.getEmail())) {
			listeFieldError.add(new FieldError(inscriptionForm.getClass().getSimpleName(), InscriptionForm.EMAIL,
					messageSource.getMessage("validation.emailExist", null, Locale.getDefault())));
		}
		if (!inscriptionForm.getMotDePasse().equals(inscriptionForm.getMotDePasseConfirmation())) {
			listeFieldError.add(new FieldError(inscriptionForm.getClass().getSimpleName(),
					InscriptionForm.MOT_DE_PASSE_CONFIRMATION,
					messageSource.getMessage("validation.motDePasseConfirmation", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
