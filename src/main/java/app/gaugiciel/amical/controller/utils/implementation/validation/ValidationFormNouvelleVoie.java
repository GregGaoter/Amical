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
		String nomSpot = nouvelleVoieForm.getNomSpot();
		if (nomSpot.length() < 1 || nomSpot.length() > 128) {
			listeFieldError.add(new FieldError(nouvelleVoieForm.getClass().getSimpleName(), NouvelleVoieForm.NOM_SPOT,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "128" },
							Locale.getDefault())));
		}
		if (nouvelleVoieForm.getNom().length() > 128) {
			listeFieldError.add(new FieldError(nouvelleVoieForm.getClass().getSimpleName(), NouvelleVoieForm.NOM,
					messageSource.getMessage("validation.form.size.max", new String[] { "128" }, Locale.getDefault())));
		}
		if (nouvelleVoieForm.getDescription().length() > 2000) {
			listeFieldError.add(new FieldError(nouvelleVoieForm.getClass().getSimpleName(),
					NouvelleVoieForm.DESCRIPTION, messageSource.getMessage("validation.form.size.max",
							new String[] { "2000" }, Locale.getDefault())));
		}
		if (nouvelleVoieForm.getRemarque().length() > 2000) {
			listeFieldError.add(
					new FieldError(nouvelleVoieForm.getClass().getSimpleName(), NouvelleVoieForm.REMARQUE, messageSource
							.getMessage("validation.form.size.max", new String[] { "2000" }, Locale.getDefault())));
		}
		if (nouvelleVoieForm.getNumero().length() > 32) {
			listeFieldError.add(new FieldError(nouvelleVoieForm.getClass().getSimpleName(), NouvelleVoieForm.NUMERO,
					messageSource.getMessage("validation.form.size.max", new String[] { "32" }, Locale.getDefault())));
		}
		if (!nouvelleVoieForm.estCotationVide() && !Utils.isValid(nouvelleVoieForm.getCotationUnitePrincipale())) {
			listeFieldError.add(new FieldError(nouvelleVoieForm.getClass().getSimpleName(),
					NouvelleVoieForm.COTATION_UNITE_PRINCIPALE,
					messageSource.getMessage("validation.cotationFranceUnitePrincipale", null, Locale.getDefault())));
		}
		if (nouvelleVoieForm.getHauteur() != null && nouvelleVoieForm.getHauteur() <= 0) {
			listeFieldError.add(new FieldError(nouvelleVoieForm.getClass().getSimpleName(), NouvelleVoieForm.HAUTEUR,
					messageSource.getMessage("validation.positive", null, Locale.getDefault())));
		}
		if (nouvelleVoieForm.getNomPlan().length() > 256) {
			listeFieldError.add(new FieldError(nouvelleVoieForm.getClass().getSimpleName(), NouvelleVoieForm.NOM_PLAN,
					messageSource.getMessage("validation.form.size.max", new String[] { "256" }, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
