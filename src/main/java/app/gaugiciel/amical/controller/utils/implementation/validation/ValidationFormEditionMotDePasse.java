package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.EditionMotDePasseForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormEditionMotDePasse extends ValidationForm<EditionMotDePasseForm> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean isValide(@NotNull EditionMotDePasseForm editionMotDePasseForm) {
		listeFieldError.clear();
		String ancienMotDePasse = editionMotDePasseForm.getAncienMotDePasse();
		String nouveauMotDePasse = editionMotDePasseForm.getNouveauMotDePasse();
		String nouveauMotDePasseConfirmation = editionMotDePasseForm.getNouveauMotDePasseConfirmation();
		boolean erreurQ = false;
		if (!Utils.isValid(ancienMotDePasse)) {
			erreurQ = true;
		}
		if (ancienMotDePasse.length() < 8) {
			erreurQ = true;
		}
		if (ancienMotDePasse.length() > 16) {
			erreurQ = true;
		}
		if (!Utils.isValid(nouveauMotDePasse)) {
			erreurQ = true;
		}
		if (nouveauMotDePasse.length() < 8) {
			erreurQ = true;
		}
		if (nouveauMotDePasse.length() > 16) {
			erreurQ = true;
		}
		if (!Utils.isValid(nouveauMotDePasseConfirmation)) {
			erreurQ = true;
		}
		if (nouveauMotDePasseConfirmation.length() < 8) {
			erreurQ = true;
		}
		if (nouveauMotDePasseConfirmation.length() > 16) {
			erreurQ = true;
		}
		if (!passwordEncoder.matches(ancienMotDePasse, editionMotDePasseForm.getAuthentification().getMotDePasse())) {
			erreurQ = true;
		}
		if (!nouveauMotDePasse.equals(nouveauMotDePasseConfirmation)) {
			erreurQ = true;
		}
		if (erreurQ) {
			listeFieldError.add(new FieldError(editionMotDePasseForm.getClass().getSimpleName(),
					EditionMotDePasseForm.ANCIEN_MOT_DE_PASSE,
					messageSource.getMessage("validation.erreur", null, Locale.getDefault())));
			listeFieldError.add(new FieldError(editionMotDePasseForm.getClass().getSimpleName(),
					EditionMotDePasseForm.NOUVEAU_MOT_DE_PASSE,
					messageSource.getMessage("validation.erreur", null, Locale.getDefault())));
			listeFieldError.add(new FieldError(editionMotDePasseForm.getClass().getSimpleName(),
					EditionMotDePasseForm.NOUVEAU_MOT_DE_PASSE_CONFIRMATION,
					messageSource.getMessage("validation.erreur", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
