package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.business.utils.Utils;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Secteur_;

public class SecteurSpecification {

	public static Specification<Secteur> nomContaining(String nom) {
		return (root, query, builder) -> {
			if (Utils.valideQ(nom)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Secteur_.NOM))),
					"%" + Utils.normaliser(nom) + "%");
		};
	}

}
