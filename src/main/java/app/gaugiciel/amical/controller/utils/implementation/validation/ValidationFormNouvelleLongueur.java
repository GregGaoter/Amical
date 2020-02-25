package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.NouvelleLongueurForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormNouvelleLongueur extends ValidationForm<NouvelleLongueurForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormNouvelleLongueur.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean isValide(@NotNull NouvelleLongueurForm nouvelleLongueurForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		if (nouvelleLongueurForm.getLongueur() != null && nouvelleLongueurForm.getLongueur() <= 0) {
			listeFieldError
					.add(new FieldError(nouvelleLongueurForm.getClass().getSimpleName(), NouvelleLongueurForm.LONGUEUR,
							messageSource.getMessage("validation.positive", null, Locale.getDefault())));
		}
		if (nouvelleLongueurForm.getNbSpits() != null && nouvelleLongueurForm.getNbSpits() <= 0) {
			listeFieldError
					.add(new FieldError(nouvelleLongueurForm.getClass().getSimpleName(), NouvelleLongueurForm.NB_SPITS,
							messageSource.getMessage("validation.positive", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
