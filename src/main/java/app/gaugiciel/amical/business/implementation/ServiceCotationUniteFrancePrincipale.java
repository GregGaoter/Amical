package app.gaugiciel.amical.business.implementation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ServiceCotationUniteFrancePrincipale {

	TROIS("3", 3), QUATRE("4", 4), CINQ("5", 5), SIX("6", 6), SEPT("7", 7), HUIT("8", 8), NEUF("9", 9);
	
	public final String label;
	public final int value;

}
