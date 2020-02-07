package app.gaugiciel.amical.business.implementation.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum EtatManuel {

	NULL(""), DISPONIBLE("DISPONIBLE"), INDISPONIBLE("INDISPONIBLE"), PRETE("PRETE");

	public static final List<String> LABELS = new ArrayList<>();

	static {
		for (EtatManuel e : values()) {
			LABELS.add(e.label);
		}
	}

	public final String label;

	private EtatManuel(String label) {
		this.label = label;
	}

}
