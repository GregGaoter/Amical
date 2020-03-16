package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.conversion.ServiceConversionInputDateToTimestamp;
import app.gaugiciel.amical.business.implementation.conversion.ServiceConversionLocalDateTimeToTimestamp;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheAuthentification;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheLieuFrance;
import app.gaugiciel.amical.controller.form.NouveauTopoForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormNouveauTopo extends ValidationForm<NouveauTopoForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormNouveauTopo.class);

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ServiceConversionInputDateToTimestamp serviceConversionInputDateToTimestamp;
	@Autowired
	private ServiceRechercheLieuFrance serviceRechercheLieuFrance;
	@Autowired
	private ServiceConversionLocalDateTimeToTimestamp serviceConversionLocalDateTimeToTimestamp;
	@Autowired
	private ServiceRechercheAuthentification serviceRechercheAuthentification;

	@Override
	public boolean isValide(@NotNull NouveauTopoForm nouveauTopoForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		String nom = nouveauTopoForm.getNom();
		if (nom.length() < 1 || nom.length() > 128) {
			listeFieldError.add(new FieldError(nouveauTopoForm.getClass().getSimpleName(), NouveauTopoForm.NOM,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "128" },
							Locale.getDefault())));
		}
		if (nouveauTopoForm.getAuteur().length() > 128) {
			listeFieldError.add(new FieldError(nouveauTopoForm.getClass().getSimpleName(), NouveauTopoForm.AUTEUR,
					messageSource.getMessage("validation.form.size.max", new String[] { "128" }, Locale.getDefault())));
		}
		if (nouveauTopoForm.getDescription().length() > 2000) {
			listeFieldError.add(new FieldError(nouveauTopoForm.getClass().getSimpleName(), NouveauTopoForm.DESCRIPTION,
					messageSource.getMessage("validation.form.size.max", new String[] { "2000" },
							Locale.getDefault())));
		}
		if (nouveauTopoForm.getRemarque().length() > 2000) {
			listeFieldError.add(
					new FieldError(nouveauTopoForm.getClass().getSimpleName(), NouveauTopoForm.REMARQUE, messageSource
							.getMessage("validation.form.size.max", new String[] { "2000" }, Locale.getDefault())));
		}
		String etat = nouveauTopoForm.getEtat();
		if (etat.length() < 1 || etat.length() > 64) {
			listeFieldError.add(new FieldError(nouveauTopoForm.getClass().getSimpleName(), NouveauTopoForm.ETAT,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "64" },
							Locale.getDefault())));
		}
		String categorie = nouveauTopoForm.getCategorie();
		if (categorie.length() < 1 || categorie.length() > 64) {
			listeFieldError.add(new FieldError(nouveauTopoForm.getClass().getSimpleName(), NouveauTopoForm.CATEGORIE,
					messageSource.getMessage("validation.form.size.interval", new String[] { "1", "64" },
							Locale.getDefault())));
		}
		Timestamp dateTimeParution = serviceConversionInputDateToTimestamp
				.convertir(nouveauTopoForm.getDateParutionInput());
		Authentification authentification = serviceRechercheAuthentification
				.findByEmail(nouveauTopoForm.getAuthentificationEmailInput());
		if (dateTimeParution != null
				&& dateTimeParution.after(serviceConversionLocalDateTimeToTimestamp.convertir(LocalDateTime.now()))) {
			listeFieldError
					.add(new FieldError(nouveauTopoForm.getClass().getSimpleName(), NouveauTopoForm.DATE_PARUTION_INPUT,
							messageSource.getMessage("validation.pastorpresent", null, Locale.getDefault())));
		}
		LieuFrance lieuFrance;
		String lieuFranceInput = nouveauTopoForm.getLieuFranceInput();
		if (Utils.isValid(lieuFranceInput)) {
			lieuFrance = lieuFranceInput.split(", ").length == 4
					? serviceRechercheLieuFrance.findByNomComplet(lieuFranceInput).orElse(null)
					: null;
			if (lieuFrance == null) {
				listeFieldError.add(new FieldError(nouveauTopoForm.getClass().getSimpleName(),
						NouveauTopoForm.LIEU_FRANCE_INPUT,
						messageSource.getMessage("validation.lieuFranceNomComplet", null, Locale.getDefault())));
			}
		} else {
			lieuFrance = null;
		}
		nouveauTopoForm.setObjets(dateTimeParution, authentification, lieuFrance);
		return listeFieldError.isEmpty();
	}

}
