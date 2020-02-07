package app.gaugiciel.amical.business.implementation.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CotationFranceUnitePrincipale {

	NULL("", 0), TROIS("3", 3), QUATRE("4", 4), CINQ("5", 5), SIX("6", 6), SEPT("7", 7), HUIT("8", 8), NEUF("9", 9);

	private static final Map<String, CotationFranceUnitePrincipale> BY_LABEL = new HashMap<>();
	public static final List<String> LABELS = new ArrayList<>();
	static {
		for (CotationFranceUnitePrincipale c : values()) {
			BY_LABEL.put(c.label, c);
			LABELS.add(c.label);
		}
	}
	public static final int SIZE = CotationFranceUnitePrincipale.values().length;
	public static final CotationFranceUnitePrincipale UNITE_MIN = CotationFranceUnitePrincipale
			.values()[0];
	public static final CotationFranceUnitePrincipale UNITE_MAX = CotationFranceUnitePrincipale
			.values()[SIZE - 1];
	public static final String LABEL_MIN = LABELS.get(0);
	public static final String LABEL_MAX = LABELS.get(SIZE - 1);

	public final String label;
	public final int value;

	public static CotationFranceUnitePrincipale ofLabel(String label) {
		return BY_LABEL.get(label);
	}

	public boolean estVide() {
		return this == NULL;
	}

}
