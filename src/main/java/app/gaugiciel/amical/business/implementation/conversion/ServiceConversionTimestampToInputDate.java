package app.gaugiciel.amical.business.implementation.conversion;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Conversion;

@Service
public class ServiceConversionTimestampToInputDate implements Conversion<Timestamp, String> {

	@Override
	public String convertir(Timestamp timestamp) {
		return timestamp != null ? timestamp.toString().split(" ")[0] : "";
	}

}
