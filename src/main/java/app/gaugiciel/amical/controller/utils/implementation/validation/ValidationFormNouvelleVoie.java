package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.NouvelleVoieForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormNouvelleVoie extends ValidationForm<NouvelleVoieForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormNouvelleVoie.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean isValide(@NotNull NouvelleVoieForm nouvelleVoieForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		if (!nouvelleVoieForm.estCotationVide() && !Utils.isValid(nouvelleVoieForm.getCotationUnitePrincipale())) {
			listeFieldError.add(new FieldError(nouvelleVoieForm.getClass().getSimpleName(),
					NouvelleVoieForm.COTATION_UNITE_PRINCIPALE,
					messageSource.getMessage("validation.cotationFranceUnitePrincipale", null, Locale.getDefault())));
		}
		if (nouvelleVoieForm.getHauteur() != null && nouvelleVoieForm.getHauteur() <= 0) {
			listeFieldError.add(new FieldError(nouvelleVoieForm.getClass().getSimpleName(), NouvelleVoieForm.HAUTEUR,
					messageSource.getMessage("validation.positive", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
