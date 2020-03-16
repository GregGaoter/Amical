package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.validation.constraints.NotNull;

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
import app.gaugiciel.amical.controller.form.RechercheSpotForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.controller.utils.implementation.comparaison.ComparaisonFieldInteger;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormRechercheSpot extends ValidationForm<RechercheSpotForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormRechercheSpot.class);

	@Autowired
	private ComparaisonFieldInteger comparaisonFieldInteger;
	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean isValide(@NotNull RechercheSpotForm spotForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		if (spotForm.getNomSpot().length() > 128) {
			listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), RechercheSpotForm.NOM_SPOT,
					messageSource.getMessage("validation.form.size.max", new String[] { "128" }, Locale.getDefault())));
		}
		if (spotForm.getLieuFranceSpot().length() > 64) {
			listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), RechercheSpotForm.LIEU_FRANCE_SPOT,
					messageSource.getMessage("validation.form.size.max", new String[] { "64" }, Locale.getDefault())));
		}
		if (spotForm.getNomSecteur().length() > 128) {
			listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), RechercheSpotForm.NOM_SECTEUR,
					messageSource.getMessage("validation.form.size.max", new String[] { "128" }, Locale.getDefault())));
		}
		if (spotForm.getNomVoie().length() > 128) {
			listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), RechercheSpotForm.NOM_VOIE,
					messageSource.getMessage("validation.form.size.max", new String[] { "128" }, Locale.getDefault())));
		}
		if (!spotForm.estCotationVide()) {
			ServiceCotationFrance cotationMin = spotForm.getCotationMin();
			ServiceCotationFrance cotationMax = spotForm.getCotationMax();
			CotationFranceUnitePrincipale cotationMinUnitePrincipale = cotationMin.getUnitePrincipale();
			CotationFranceUniteSecondaire cotationMinUniteSecondaire = cotationMin.getUniteSecondaire();
			CotationFranceUniteTertiaire cotationMinUniteTertiaire = cotationMin.getUniteTertiaire();
			CotationFranceUnitePrincipale cotationMaxUnitePrincipale = cotationMax.getUnitePrincipale();
			CotationFranceUniteSecondaire cotationMaxUniteSecondaire = cotationMax.getUniteSecondaire();
			CotationFranceUniteTertiaire cotationMaxUniteTertiaire = cotationMax.getUniteTertiaire();
			List<ServiceCotationFrance> allCotationsFromMin = ServiceCotationFrance.getBetween(cotationMin,
					ServiceCotationFrance.COTATION_MAX);
			if ((cotationMinUnitePrincipale.estVide()
					&& (!cotationMinUniteSecondaire.estVide() || !cotationMinUniteTertiaire.estVide()))
					|| (cotationMaxUnitePrincipale.estVide()
							&& (!cotationMaxUniteSecondaire.estVide() || !cotationMaxUniteTertiaire.estVide()))) {
				listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(),
						RechercheSpotForm.LISTE_COTATION, messageSource
								.getMessage("validation.cotationFranceUnitePrincipale", null, Locale.getDefault())));
			} else {
				if (!spotForm.getCotationMax().getUnitePrincipale().estVide()
						&& !allCotationsFromMin.contains(cotationMax)) {
					listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(),
							RechercheSpotForm.LISTE_COTATION,
							messageSource.getMessage("validation.cotationFranceUniteMax", null, Locale.getDefault())));
				}
			}
		}
		Integer hauteurMinVoie = spotForm.getHauteurMinVoie();
		Integer hauteurMaxVoie = spotForm.getHauteurMaxVoie();
		if (!Objects.isNull(hauteurMinVoie) && hauteurMinVoie < 0) {
			listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), RechercheSpotForm.HAUTEUR_MIN_VOIE,
					messageSource.getMessage("validation.positive", null, Locale.getDefault())));
		}
		if (!Objects.isNull(hauteurMaxVoie) && hauteurMaxVoie < 0) {
			listeFieldError.add(new FieldError(spotForm.getClass().getSimpleName(), RechercheSpotForm.HAUTEUR_MAX_VOIE,
					messageSource.getMessage("validation.positive", null, Locale.getDefault())));
		} else {
			if (!Objects.isNull(hauteurMinVoie) && !Objects.isNull(hauteurMaxVoie)
					&& comparaisonFieldInteger.comparer(hauteurMinVoie, hauteurMaxVoie) > 0) {
				listeFieldError
						.add(new FieldError(spotForm.getClass().getSimpleName(), RechercheSpotForm.HAUTEUR_MAX_VOIE,
								messageSource.getMessage("validation.hauteurMaxVoie", null, Locale.getDefault())));
			}
		}
		return listeFieldError.isEmpty();
	}

}
