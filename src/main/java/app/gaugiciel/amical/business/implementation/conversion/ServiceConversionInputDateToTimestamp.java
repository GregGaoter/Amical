package app.gaugiciel.amical.business.implementation.conversion;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Conversion;
import app.gaugiciel.amical.business.implementation.comparaison.ServiceComparaisonVoieByNumero;
import app.gaugiciel.amical.utilitaire.Utils;

@Service
public class ServiceConversionInputDateToTimestamp implements Conversion<String, Timestamp> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConversionInputDateToTimestamp.class);

	@Override
	public Timestamp convertir(String source) {
		if (Utils.isValid(source)) {
			return Timestamp.valueOf(source + " 00:00:00");
		} else {
			return null;
		}
	}

}
