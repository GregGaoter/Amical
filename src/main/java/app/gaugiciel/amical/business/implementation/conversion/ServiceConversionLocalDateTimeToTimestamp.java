package app.gaugiciel.amical.business.implementation.conversion;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Conversion;

@Service
public class ServiceConversionLocalDateTimeToTimestamp implements Conversion<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertir(LocalDateTime localDateTime) {
		return Timestamp.valueOf(localDateTime.toString().replace("T", " "));
	}

}
