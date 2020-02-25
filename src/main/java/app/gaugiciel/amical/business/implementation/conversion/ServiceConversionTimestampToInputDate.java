package app.gaugiciel.amical.business.implementation.conversion;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Conversion;

@Service
public class ServiceConversionTimestampToInputDate implements Conversion<Timestamp, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConversionTimestampToInputDate.class);

	@Override
	public String convertir(Timestamp timestamp) {
		LOGGER.info("Start {}()", "convertir");
		return timestamp != null ? timestamp.toString().split(" ")[0] : "";
	}

}
