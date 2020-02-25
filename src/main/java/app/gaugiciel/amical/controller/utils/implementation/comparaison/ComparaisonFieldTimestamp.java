package app.gaugiciel.amical.controller.utils.implementation.comparaison;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.controller.utils.contrat.ComparaisonField;

@Component
public class ComparaisonFieldTimestamp implements ComparaisonField<Timestamp> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComparaisonFieldTimestamp.class);

	@Override
	public int comparer(Timestamp t1, Timestamp t2) {
		return t1.compareTo(t2);
	}

}
