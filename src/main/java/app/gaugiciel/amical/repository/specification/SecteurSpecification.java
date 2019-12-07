package app.gaugiciel.amical.repository.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Secteur_;

public class SecteurSpecification {

	public static Specification<Secteur> nomContaining(String nom) {
		return (root, query, builder) -> {
			if (nom.strip().length() == 0) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Secteur_.NOM))),
					"%" + StringUtils.stripAccents(nom).toUpperCase() + "%");
		};
	}

}
