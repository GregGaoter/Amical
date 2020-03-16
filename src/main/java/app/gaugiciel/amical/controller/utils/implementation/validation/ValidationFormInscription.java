package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;
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
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		String prenom = inscriptionForm.getPrenom();
		if (prenom.length() < 1 || prenom.length() > 64) {
			listeFieldError.add(new FieldError(inscriptionForm.getClass().getSimpleName(), InscriptionForm.PRENOM,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "64" },
							Locale.getDefault())));
		}
		String nom = inscriptionForm.getNom();
		if (nom.length() < 1 || nom.length() > 64) {
			listeFieldError.add(new FieldError(inscriptionForm.getClass().getSimpleName(), InscriptionForm.NOM,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "64" },
							Locale.getDefault())));
		}
		EmailValidator emailValidator = EmailValidator.getInstance();
		if (!emailValidator.isValid(inscriptionForm.getEmail())) {
			listeFieldError.add(new FieldError(inscriptionForm.getClass().getSimpleName(), InscriptionForm.EMAIL,
					messageSource.getMessage("validation.email", null, Locale.getDefault())));
		}
		if (serviceRechercheAuthentification.existsByEmail(inscriptionForm.getEmail())) {
			listeFieldError.add(new FieldError(inscriptionForm.getClass().getSimpleName(), InscriptionForm.EMAIL,
					messageSource.getMessage("validation.emailExist", null, Locale.getDefault())));
		}
		String motDePasse = inscriptionForm.getMotDePasse();
		if (motDePasse.length() < 8 || motDePasse.length() > 16) {
			listeFieldError.add(new FieldError(inscriptionForm.getClass().getSimpleName(), InscriptionForm.MOT_DE_PASSE,
					messageSource.getMessage("validation.form.size.interval", new String[] { "8", "16" },
							Locale.getDefault())));
		}
		String motDePasseConfirmation = inscriptionForm.getMotDePasseConfirmation();
		if (motDePasseConfirmation.length() < 8 || motDePasseConfirmation.length() > 16) {
			listeFieldError.add(new FieldError(inscriptionForm.getClass().getSimpleName(),
					InscriptionForm.MOT_DE_PASSE_CONFIRMATION, messageSource.getMessage("validation.form.size.interval",
							new String[] { "8", "16" }, Locale.getDefault())));
		}
		if (!motDePasse.equals(motDePasseConfirmation)) {
			listeFieldError.add(new FieldError(inscriptionForm.getClass().getSimpleName(),
					InscriptionForm.MOT_DE_PASSE_CONFIRMATION,
					messageSource.getMessage("validation.motDePasseConfirmation", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
