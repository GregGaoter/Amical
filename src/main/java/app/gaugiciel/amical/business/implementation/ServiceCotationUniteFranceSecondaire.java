package app.gaugiciel.amical.business.implementation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ServiceCotationUniteFranceSecondaire {

	NULL("", 0), A("a", 1), B("b", 2), C("c", 3);

	public final String label;
	public final int value;

}
