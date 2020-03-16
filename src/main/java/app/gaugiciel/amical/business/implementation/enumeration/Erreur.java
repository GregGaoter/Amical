package app.gaugiciel.amical.business.implementation.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum Erreur {

	SUFFIXE("Erreur");

	public static final List<String> LABELS = new ArrayList<>();

	static {
		for (Erreur e : values()) {
			LABELS.add(e.label);
		}
	}

	public final String label;

	private Erreur(String label) {
		this.label = label;
	}

}
