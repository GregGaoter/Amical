package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteTertiaire;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheCotationFrance;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRecherchePlan;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryCotationFrance;
import app.gaugiciel.amical.controller.form.EditionVoieForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.repository.specification.CotationFranceSpecification;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
public class ValidationFormEditionVoie extends ValidationForm<EditionVoieForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormEditionVoie.class);

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ServiceRecherchePlan serviceRecherchePlan;
	@Autowired
	private ServiceRechercheCotationFrance serviceRechercheCotationFrance;
	@Autowired
	private ServiceRepositoryCotationFrance serviceRepositoryCotationFrance;
	@Getter
	@Setter
	private Plan plan;
	@Getter
	@Setter
	private CotationFrance cotationFrance;

	@Override
	public boolean isValide(EditionVoieForm editionVoieForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		if (Utils.isValid(editionVoieForm.getNomPlan())) {
			Plan planForm = serviceRecherchePlan.findOne(editionVoieForm.getNomPlan());
			if (planForm == null) {
				listeFieldError.add(new FieldError(editionVoieForm.getClass().getSimpleName(), EditionVoieForm.NOM_PLAN,
						messageSource.getMessage("validation.nomPlan", null, Locale.getDefault())));
			} else {
				plan = planForm;
			}
		} else {
			plan = null;
		}
		if (!editionVoieForm.estCotationVide() && !Utils.isValid(editionVoieForm.getCotationUnitePrincipale())) {
			listeFieldError.add(new FieldError(editionVoieForm.getClass().getSimpleName(),
					EditionVoieForm.COTATION_UNITE_PRINCIPALE,
					messageSource.getMessage("validation.cotationFranceUnitePrincipale", null, Locale.getDefault())));
		} else {
			String unitePrincipale = editionVoieForm.getCotationUnitePrincipale();
			String uniteSecondaire = editionVoieForm.getCotationUniteSecondaire();
			String uniteTertiaire = editionVoieForm.getCotationUniteTertiaire();
			Optional<CotationFrance> optionalCotationFrance = serviceRechercheCotationFrance
					.findOne(CotationFranceSpecification.cotationEqual(
							ServiceCotationFrance.creer(CotationFranceUnitePrincipale.ofLabel(unitePrincipale),
									CotationFranceUniteSecondaire.ofLabel(uniteSecondaire),
									CotationFranceUniteTertiaire.ofLabel(uniteTertiaire))));
			cotationFrance = optionalCotationFrance.isEmpty()
					? serviceRepositoryCotationFrance
							.enregistrer(CotationFrance.creer(unitePrincipale, uniteSecondaire, uniteTertiaire))
					: optionalCotationFrance.get();
		}
		if (editionVoieForm.getHauteur() != null && editionVoieForm.getHauteur() <= 0) {
			listeFieldError.add(new FieldError(editionVoieForm.getClass().getSimpleName(), EditionVoieForm.HAUTEUR,
					messageSource.getMessage("validation.positive", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
