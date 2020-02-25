package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.sql.Timestamp;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.business.implementation.conversion.ServiceConversionInputDateToTimestamp;
import app.gaugiciel.amical.controller.form.RechercheTopoForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.controller.utils.implementation.comparaison.ComparaisonFieldTimestamp;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormRechercheTopo extends ValidationForm<RechercheTopoForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormRechercheTopo.class);

	@Autowired
	private ServiceConversionInputDateToTimestamp serviceConversionInputDateToTimestamp;
	@Autowired
	private ComparaisonFieldTimestamp comparaisonFieldTimestamp;

	@Override
	public boolean isValide(@NotNull RechercheTopoForm rechercheTopoForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		Timestamp dateParutionMin = serviceConversionInputDateToTimestamp
				.convertir(rechercheTopoForm.getDateParutionMinInput());
		Timestamp dateParutionMax = serviceConversionInputDateToTimestamp
				.convertir(rechercheTopoForm.getDateParutionMaxInput());
		rechercheTopoForm.setDateParutionMin(dateParutionMin);
		rechercheTopoForm.setDateParutionMax(dateParutionMax);
		if (dateParutionMin != null && dateParutionMax != null
				&& comparaisonFieldTimestamp.comparer(dateParutionMin, dateParutionMax) > 0) {
			listeFieldError.add(new FieldError(rechercheTopoForm.getClass().getSimpleName(),
					RechercheTopoForm.DATE_PARUTION_MAX_INPUT,
					messageSource.getMessage("validation.dateParutionMaxManuel", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
