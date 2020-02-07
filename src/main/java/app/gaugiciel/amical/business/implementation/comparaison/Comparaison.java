package app.gaugiciel.amical.business.implementation.comparaison;

import org.springframework.beans.factory.annotation.Autowired;

import app.gaugiciel.amical.utilitaire.ComparateurAlphaNumeric;

public abstract class Comparaison {

	@Autowired
	protected ComparateurAlphaNumeric comparateurAlphaNumeric;

}
