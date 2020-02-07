package app.gaugiciel.amical.business.implementation.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum CategorieManuel {

	NULL(""), TOPO("TOPO");

	public static final List<String> LABELS = new ArrayList<>();

	static {
		for (CategorieManuel c : values()) {
			LABELS.add(c.label);
		}
	}

	public final String label;

	private CategorieManuel(String label) {
		this.label = label;
	}

}
