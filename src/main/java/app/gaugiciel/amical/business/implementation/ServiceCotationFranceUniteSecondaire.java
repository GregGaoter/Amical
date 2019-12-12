package app.gaugiciel.amical.business.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ServiceCotationFranceUniteSecondaire {

	NULL("", 0), A("a", 1), B("b", 2), C("c", 3);

	private static final Map<String, ServiceCotationFranceUniteSecondaire> BY_LABEL = new HashMap<>();
	public static final List<String> LABELS = new ArrayList<>();
	static {
		for (ServiceCotationFranceUniteSecondaire c : values()) {
			BY_LABEL.put(c.label, c);
			LABELS.add(c.label);
		}
	}
	public static final int SIZE = ServiceCotationFranceUniteSecondaire.values().length;
	public static final ServiceCotationFranceUniteSecondaire UNITE_MIN = ServiceCotationFranceUniteSecondaire
			.values()[0];
	public static final ServiceCotationFranceUniteSecondaire UNITE_MAX = ServiceCotationFranceUniteSecondaire
			.values()[SIZE - 1];
	public static final String LABEL_MIN = LABELS.get(0);
	public static final String LABEL_MAX = LABELS.get(SIZE - 1);

	public final String label;
	public final int value;

	public static ServiceCotationFranceUniteSecondaire ofLabel(String label) {
		return BY_LABEL.get(label);
	}
	
	public boolean estVide() {
		return this == NULL;
	}

}
