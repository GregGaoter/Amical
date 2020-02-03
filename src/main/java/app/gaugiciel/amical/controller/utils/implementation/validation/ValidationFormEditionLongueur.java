package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.recherche.ServiceRecherchePlan;
import app.gaugiciel.amical.controller.form.EditionLongueurForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
public class ValidationFormEditionLongueur extends ValidationForm<EditionLongueurForm> {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ServiceRecherchePlan serviceRecherchePlan;
	@Getter
	@Setter
	private Plan plan;

	@Override
	public boolean isValide(EditionLongueurForm editionLongueurForm) {
		listeFieldError.clear();
		if (Utils.isValid(editionLongueurForm.getNomPlan())) {
			Plan planForm = serviceRecherchePlan.findOne(editionLongueurForm.getNomPlan());
			if (planForm == null) {
				listeFieldError.add(
						new FieldError(editionLongueurForm.getClass().getSimpleName(), EditionLongueurForm.NOM_PLAN,
								messageSource.getMessage("validation.nomPlan", null, Locale.getDefault())));
			} else {
				plan = planForm;
			}
		} else {
			plan = null;
		}
		if (editionLongueurForm.getLongueur() != null && editionLongueurForm.getLongueur() <= 0) {
			listeFieldError
					.add(new FieldError(editionLongueurForm.getClass().getSimpleName(), EditionLongueurForm.LONGUEUR,
							messageSource.getMessage("validation.positive", null, Locale.getDefault())));
		}
		if (editionLongueurForm.getNbSpits() != null && editionLongueurForm.getNbSpits() <= 0) {
			listeFieldError
					.add(new FieldError(editionLongueurForm.getClass().getSimpleName(), EditionLongueurForm.NB_SPITS,
							messageSource.getMessage("validation.positive", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
