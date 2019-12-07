package app.gaugiciel.amical.business.implementation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ServiceCotationUniteFranceTertiaire {

	NULL("", 0), PLUS("+", 1);

	public final String label;
	public final int value;

}
