package app.gaugiciel.amical.business.implementation;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import app.gaugiciel.amical.model.Secteur;

@Service
public class ServiceComparaisonSecteurByNom extends ServiceComparaison implements Comparator<Secteur> {

	@Override
	public int compare(Secteur s1, Secteur s2) {
		return comparateurAlphaNumeric.compare(s1.getNom(), s2.getNom());
	}

}
