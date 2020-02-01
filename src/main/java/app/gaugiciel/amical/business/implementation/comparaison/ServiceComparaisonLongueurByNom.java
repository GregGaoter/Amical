package app.gaugiciel.amical.business.implementation.comparaison;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import app.gaugiciel.amical.model.Longueur;

@Service
public class ServiceComparaisonLongueurByNom extends ServiceComparaison implements Comparator<Longueur> {

	@Override
	public int compare(Longueur l1, Longueur l2) {
		return comparateurAlphaNumeric.compare(l1.getNom(), l2.getNom());
	}

}
