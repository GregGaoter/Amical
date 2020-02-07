package app.gaugiciel.amical.business.implementation.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CotationFranceUniteTertiaire {

	NULL("", 0), PLUS("+", 1);

	private static final Map<String, CotationFranceUniteTertiaire> BY_LABEL = new HashMap<>();
	public static final List<String> LABELS = new ArrayList<>();
	static {
		for (CotationFranceUniteTertiaire c : values()) {
			BY_LABEL.put(c.label, c);
			LABELS.add(c.label);
		}
	}
	public static final int SIZE = CotationFranceUniteTertiaire.values().length;
	public static final CotationFranceUniteTertiaire UNITE_MIN = CotationFranceUniteTertiaire.values()[0];
	public static final CotationFranceUniteTertiaire UNITE_MAX = CotationFranceUniteTertiaire
			.values()[SIZE - 1];
	public static final String LABEL_MIN = LABELS.get(0);
	public static final String LABEL_MAX = LABELS.get(SIZE - 1);

	public final String label;
	public final int value;

	public static CotationFranceUniteTertiaire ofLabel(String label) {
		return BY_LABEL.get(label);
	}

	public boolean estVide() {
		return this == NULL;
	}

}
