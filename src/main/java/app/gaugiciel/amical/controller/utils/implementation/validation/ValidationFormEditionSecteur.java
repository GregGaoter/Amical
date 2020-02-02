package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.recherche.ServiceRecherchePlan;
import app.gaugiciel.amical.controller.form.EditionSecteurForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.model.Plan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
public class ValidationFormEditionSecteur extends ValidationForm<EditionSecteurForm> {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ServiceRecherchePlan serviceRecherchePlan;
	@Getter
	@Setter
	private Plan plan;

	@Override
	public boolean isValide(EditionSecteurForm editionSecteurForm) {
		listeFieldError.clear();
		Plan planForm = serviceRecherchePlan.findOne(editionSecteurForm.getNomPlan());
		if (planForm == null) {
			listeFieldError
					.add(new FieldError(editionSecteurForm.getClass().getSimpleName(), EditionSecteurForm.NOM_PLAN,
							messageSource.getMessage("validation.nomPlan", null, Locale.getDefault())));
		} else {
			plan = planForm;
		}
		return listeFieldError.isEmpty();
	}

}
