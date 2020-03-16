package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.NouveauPlanForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormNouveauPlan extends ValidationForm<NouveauPlanForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormNouveauPlan.class);

	@Override
	public boolean isValide(@NotNull NouveauPlanForm nouveauPlanForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		String nomFichier = nouveauPlanForm.getNomFichier();
		if (nomFichier.length() < 3 || nomFichier.length() > 256) {
			listeFieldError.add(new FieldError(nouveauPlanForm.getClass().getSimpleName(), NouveauPlanForm.FICHIER,
					messageSource.getMessage("validation.form.size.interval", new String[] { "3", "256" },
							Locale.getDefault())));
		}
		if (nouveauPlanForm.getDescription().length() > 2000) {
			listeFieldError.add(new FieldError(nouveauPlanForm.getClass().getSimpleName(), NouveauPlanForm.DESCRIPTION,
					messageSource.getMessage("validation.form.size.max", new String[] { "2000" },
							Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
