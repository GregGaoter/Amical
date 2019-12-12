package app.gaugiciel.amical.business.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ServiceCotationFranceUnitePrincipale {

	NULL("", 0), TROIS("3", 3), QUATRE("4", 4), CINQ("5", 5), SIX("6", 6), SEPT("7", 7), HUIT("8", 8), NEUF("9", 9);

	private static final Map<String, ServiceCotationFranceUnitePrincipale> BY_LABEL = new HashMap<>();
	public static final List<String> LABELS = new ArrayList<>();
	static {
		for (ServiceCotationFranceUnitePrincipale c : values()) {
			BY_LABEL.put(c.label, c);
			LABELS.add(c.label);
		}
	}
	public static final int SIZE = ServiceCotationFranceUnitePrincipale.values().length;
	public static final ServiceCotationFranceUnitePrincipale UNITE_MIN = ServiceCotationFranceUnitePrincipale
			.values()[0];
	public static final ServiceCotationFranceUnitePrincipale UNITE_MAX = ServiceCotationFranceUnitePrincipale
			.values()[SIZE - 1];
	public static final String LABEL_MIN = LABELS.get(0);
	public static final String LABEL_MAX = LABELS.get(SIZE - 1);

	public final String label;
	public final int value;

	public static ServiceCotationFranceUnitePrincipale ofLabel(String label) {
		return BY_LABEL.get(label);
	}

	public boolean estVide() {
		return this == NULL;
	}

}
