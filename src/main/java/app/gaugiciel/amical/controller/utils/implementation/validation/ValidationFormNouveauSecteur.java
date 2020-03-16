package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.NouveauSecteurForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormNouveauSecteur extends ValidationForm<NouveauSecteurForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormNouveauSecteur.class);

	@Override
	public boolean isValide(@NotNull NouveauSecteurForm nouveauSecteurForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		if (nouveauSecteurForm.getNom().length() > 128) {
			listeFieldError.add(new FieldError(nouveauSecteurForm.getClass().getSimpleName(), NouveauSecteurForm.NOM,
					messageSource.getMessage("validation.form.size.max", new String[] { "128" }, Locale.getDefault())));
		}
		if (nouveauSecteurForm.getDescription().length() > 2000) {
			listeFieldError.add(new FieldError(nouveauSecteurForm.getClass().getSimpleName(),
					NouveauSecteurForm.DESCRIPTION, messageSource.getMessage("validation.form.size.max",
							new String[] { "2000" }, Locale.getDefault())));
		}
		if (nouveauSecteurForm.getRemarque().length() > 2000) {
			listeFieldError.add(new FieldError(nouveauSecteurForm.getClass().getSimpleName(),
					NouveauSecteurForm.REMARQUE, messageSource.getMessage("validation.form.size.max",
							new String[] { "2000" }, Locale.getDefault())));
		}
		String nomSpot = nouveauSecteurForm.getNomSpot();
		if (nomSpot.length() < 1 || nomSpot.length() > 128) {
			listeFieldError.add(new FieldError(nouveauSecteurForm.getClass().getSimpleName(),
					NouveauSecteurForm.NOM_SPOT, messageSource.getMessage("validation.form.size.interval",
							new String[] { "1", "128" }, Locale.getDefault())));
		}
		if (nouveauSecteurForm.getNomPlan().length() > 256) {
			listeFieldError.add(new FieldError(nouveauSecteurForm.getClass().getSimpleName(),
					NouveauSecteurForm.NOM_PLAN,
					messageSource.getMessage("validation.form.size.max", new String[] { "256" }, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
