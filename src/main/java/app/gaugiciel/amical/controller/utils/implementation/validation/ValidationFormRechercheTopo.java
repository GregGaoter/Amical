package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.conversion.ServiceConversionInputDateToTimestamp;
import app.gaugiciel.amical.business.implementation.conversion.ServiceConversionLocalDateTimeToTimestamp;
import app.gaugiciel.amical.controller.form.RechercheTopoForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.controller.utils.implementation.comparaison.ComparaisonFieldTimestamp;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormRechercheTopo extends ValidationForm<RechercheTopoForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormRechercheTopo.class);

	@Autowired
	private ServiceConversionInputDateToTimestamp serviceConversionInputDateToTimestamp;
	@Autowired
	private ServiceConversionLocalDateTimeToTimestamp serviceConversionLocalDateTimeToTimestamp;
	@Autowired
	private ComparaisonFieldTimestamp comparaisonFieldTimestamp;

	@Override
	public boolean isValide(@NotNull RechercheTopoForm rechercheTopoForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		String nom = rechercheTopoForm.getNom();
		if (Utils.isValid(nom) && nom.length() > 128) {
			listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(), RechercheTopoForm.NOM,
					messageSource.getMessage("validation.form.size.max", new String[] { "128" }, Locale.getDefault())));
		}
		String lieuFranceTemplate = rechercheTopoForm.getLieuFranceTemplate();
		if (Utils.isValid(lieuFranceTemplate) && lieuFranceTemplate.length() > 64) {
			listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(),
					RechercheTopoForm.LIEU_FRANCE_TEMPLATE,
					messageSource.getMessage("validation.form.size.max", new String[] { "64" }, Locale.getDefault())));
		}
		String categorie = rechercheTopoForm.getCategorie();
		if (Utils.isValid(categorie) && categorie.length() > 64) {
			listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(),
					RechercheTopoForm.CATEGORIE,
					messageSource.getMessage("validation.form.size.max", new String[] { "64" }, Locale.getDefault())));
		}
		String etat = rechercheTopoForm.getEtat();
		if (Utils.isValid(etat) && etat.length() > 64) {
			listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(), RechercheTopoForm.ETAT,
					messageSource.getMessage("validation.form.size.max", new String[] { "64" }, Locale.getDefault())));
		}
		String auteur = rechercheTopoForm.getAuteur();
		if (Utils.isValid(auteur) && auteur.length() > 128) {
			listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(), RechercheTopoForm.AUTEUR,
					messageSource.getMessage("validation.form.size.max", new String[] { "128" }, Locale.getDefault())));
		}
		String proprietaireTemplate = rechercheTopoForm.getProprietaireTemplate();
		if (Utils.isValid(proprietaireTemplate) && proprietaireTemplate.length() > 64) {
			listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(),
					RechercheTopoForm.PROPRIETAIRE_TEMPLATE,
					messageSource.getMessage("validation.form.size.max", new String[] { "64" }, Locale.getDefault())));
		}
		Timestamp dateParutionMin = serviceConversionInputDateToTimestamp
				.convertir(rechercheTopoForm.getDateParutionMinInput());
		Timestamp dateParutionMax = serviceConversionInputDateToTimestamp
				.convertir(rechercheTopoForm.getDateParutionMaxInput());
		rechercheTopoForm.setDateParutionMin(dateParutionMin);
		rechercheTopoForm.setDateParutionMax(dateParutionMax);
		Timestamp now = serviceConversionLocalDateTimeToTimestamp.convertir(LocalDateTime.now());
		if (Utils.isValid(dateParutionMin) && dateParutionMin.after(now)) {
			listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(),
					RechercheTopoForm.DATE_PARUTION_MIN_INPUT,
					messageSource.getMessage("validation.pastorpresent", null, Locale.getDefault())));
		}
		if (Utils.isValid(dateParutionMax) && dateParutionMax.after(now)) {
			listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(),
					RechercheTopoForm.DATE_PARUTION_MAX_INPUT,
					messageSource.getMessage("validation.pastorpresent", null, Locale.getDefault())));
		} else {
			if (dateParutionMin != null && dateParutionMax != null
					&& comparaisonFieldTimestamp.comparer(dateParutionMin, dateParutionMax) > 0) {
				listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(),
						RechercheTopoForm.DATE_PARUTION_MAX_INPUT,
						messageSource.getMessage("validation.dateParutionMaxManuel", null, Locale.getDefault())));
			}
		}
		return listeFieldError.isEmpty();
	}

}
