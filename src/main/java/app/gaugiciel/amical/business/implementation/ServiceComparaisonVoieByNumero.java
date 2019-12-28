package app.gaugiciel.amical.business.implementation;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import app.gaugiciel.amical.model.Voie;

@Service
public class ServiceComparaisonVoieByNumero extends ServiceComparaison implements Comparator<Voie> {

	@Override
	public int compare(Voie v1, Voie v2) {
		return comparateurAlphaNumeric.compare(v1.getNumero(), v2.getNumero());
	}

}
