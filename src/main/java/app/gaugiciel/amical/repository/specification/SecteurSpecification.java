package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Secteur_;
import app.gaugiciel.amical.utilitaire.Utils;

public class SecteurSpecification {

	public static Specification<Secteur> nomContaining(String nom) {
		return (root, query, builder) -> {
			if (!Utils.isValid(nom)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Secteur_.NOM))),
					"%" + Utils.normaliser(nom) + "%");
		};
	}

}
