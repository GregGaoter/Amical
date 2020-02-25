package app.gaugiciel.amical.business.implementation.comparaison;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.model.Secteur;

@Service
public class ServiceComparaisonSecteurByNom extends Comparaison implements Comparator<Secteur> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceComparaisonSecteurByNom.class);

	@Override
	public int compare(Secteur s1, Secteur s2) {
		LOGGER.info("Start {}()", "compare");
		return comparateurAlphaNumeric.compare(s1.getNom(), s2.getNom());
	}

}
