package app.gaugiciel.amical.business.implementation.comparaison;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.model.Voie;

@Service
public class ServiceComparaisonVoieByNumero extends Comparaison implements Comparator<Voie> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceComparaisonVoieByNumero.class);

	@Override
	public int compare(Voie v1, Voie v2) {
		return comparateurAlphaNumeric.compare(v1.getNumero(), v2.getNumero());
	}

}
