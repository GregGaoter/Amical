package app.gaugiciel.amical.business.implementation.comparaison;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.utilitaire.ComparateurAlphaNumeric;

@Service
public abstract class ServiceComparaison {

	@Autowired
	protected ComparateurAlphaNumeric comparateurAlphaNumeric;

}
