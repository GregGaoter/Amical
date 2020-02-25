package app.gaugiciel.amical.business.implementation.conversion;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Conversion;

@Service
public class ServiceConversionLocalDateTimeToTimestamp implements Conversion<LocalDateTime, Timestamp> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConversionLocalDateTimeToTimestamp.class);

	@Override
	public Timestamp convertir(LocalDateTime localDateTime) {
		return Timestamp.valueOf(localDateTime.toString().replace("T", " "));
	}

}
