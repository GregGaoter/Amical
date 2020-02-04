package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheLieuFrance;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRecherchePlan;
import app.gaugiciel.amical.controller.form.EditionSpotForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
public class ValidationFormEditionSpot extends ValidationForm<EditionSpotForm> {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ServiceRechercheLieuFrance serviceRechercheLieuFrance;
	@Autowired
	private ServiceRecherchePlan serviceRecherchePlan;
	@Getter
	@Setter
	private LieuFrance lieuFrance;
	@Getter
	@Setter
	private Plan plan;

	@Override
	public boolean isValide(@NotNull EditionSpotForm editionSpotForm) {
		listeFieldError.clear();
		Optional<LieuFrance> optionalLieuFrance = serviceRechercheLieuFrance
				.findByNomComplet(editionSpotForm.getLieuFranceNomComplet());
		if (optionalLieuFrance.isEmpty()) {
			listeFieldError.add(
					new FieldError(editionSpotForm.getClass().getSimpleName(), EditionSpotForm.LIEU_FRANCE_NOM_COMPLET,
							messageSource.getMessage("validation.lieuFranceNomComplet", null, Locale.getDefault())));
		} else {
			lieuFrance = optionalLieuFrance.get();
		}
		if (Utils.isValid(editionSpotForm.getNomPlan())) {
			Plan planForm = serviceRecherchePlan.findOne(editionSpotForm.getNomPlan());
			if (planForm == null) {
				listeFieldError.add(new FieldError(editionSpotForm.getClass().getSimpleName(), EditionSpotForm.NOM_PLAN,
						messageSource.getMessage("validation.nomPlan", null, Locale.getDefault())));
			} else {
				plan = planForm;
			}
		} else {
			plan = null;
		}
		return listeFieldError.isEmpty();
	}

}
