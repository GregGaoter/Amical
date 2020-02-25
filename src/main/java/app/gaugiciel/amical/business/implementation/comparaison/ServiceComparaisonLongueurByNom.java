package app.gaugiciel.amical.business.implementation.comparaison;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.model.Longueur;

@Service
public class ServiceComparaisonLongueurByNom extends Comparaison implements Comparator<Longueur> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceComparaisonLongueurByNom.class);

	@Override
	public int compare(Longueur l1, Longueur l2) {
		LOGGER.info("Start {}()", "compare");
		return comparateurAlphaNumeric.compare(l1.getNom(), l2.getNom());
	}

}
