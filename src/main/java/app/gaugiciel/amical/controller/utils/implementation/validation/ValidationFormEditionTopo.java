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
import app.gaugiciel.amical.controller.form.EditionTopoForm;
import app.gaugiciel.amical.controller.form.NouveauTopoForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormEditionTopo extends ValidationForm<EditionTopoForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormEditionTopo.class);

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
	public boolean isValide(@NotNull EditionTopoForm editionTopoForm) {
		listeFieldError.clear();
		Timestamp dateTimeParution = serviceConversionInputDateToTimestamp
				.convertir(editionTopoForm.getDateParutionInput());
		Authentification authentification = serviceRechercheAuthentification
				.findByEmail(editionTopoForm.getAuthentificationEmailInput());
		if (dateTimeParution != null
				&& dateTimeParution.after(serviceConversionLocalDateTimeToTimestamp.convertir(LocalDateTime.now()))) {
			listeFieldError
					.add(new FieldError(editionTopoForm.getClass().getSimpleName(), NouveauTopoForm.DATE_PARUTION_INPUT,
							messageSource.getMessage("validation.pastorpresent", null, Locale.getDefault())));
		}
		LieuFrance lieuFrance;
		String lieuFranceInput = editionTopoForm.getLieuFranceInput();
		if (Utils.isValid(lieuFranceInput)) {
			lieuFrance = lieuFranceInput.split(", ").length == 4
					? serviceRechercheLieuFrance.findByNomComplet(lieuFranceInput).orElse(null)
					: null;
			if (lieuFrance == null) {
				listeFieldError.add(new FieldError(editionTopoForm.getClass().getSimpleName(),
						NouveauTopoForm.LIEU_FRANCE_INPUT,
						messageSource.getMessage("validation.lieuFranceNomComplet", null, Locale.getDefault())));
			}
		} else {
			lieuFrance = null;
		}
		editionTopoForm.setObjets(dateTimeParution, authentification, lieuFrance);
		return listeFieldError.isEmpty();
	}

}
