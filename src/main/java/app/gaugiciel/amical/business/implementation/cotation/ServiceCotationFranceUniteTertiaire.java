package app.gaugiciel.amical.business.implementation.cotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ServiceCotationFranceUniteTertiaire {

	NULL("", 0), PLUS("+", 1);

	private static final Map<String, ServiceCotationFranceUniteTertiaire> BY_LABEL = new HashMap<>();
	public static final List<String> LABELS = new ArrayList<>();
	static {
		for (ServiceCotationFranceUniteTertiaire c : values()) {
			BY_LABEL.put(c.label, c);
			LABELS.add(c.label);
		}
	}
	public static final int SIZE = ServiceCotationFranceUniteTertiaire.values().length;
	public static final ServiceCotationFranceUniteTertiaire UNITE_MIN = ServiceCotationFranceUniteTertiaire.values()[0];
	public static final ServiceCotationFranceUniteTertiaire UNITE_MAX = ServiceCotationFranceUniteTertiaire
			.values()[SIZE - 1];
	public static final String LABEL_MIN = LABELS.get(0);
	public static final String LABEL_MAX = LABELS.get(SIZE - 1);

	public final String label;
	public final int value;

	public static ServiceCotationFranceUniteTertiaire ofLabel(String label) {
		return BY_LABEL.get(label);
	}

	public boolean estVide() {
		return this == NULL;
	}

}
