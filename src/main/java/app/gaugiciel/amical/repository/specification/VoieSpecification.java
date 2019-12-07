package app.gaugiciel.amical.repository.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.model.Voie_;

public class VoieSpecification {

	public static Specification<Voie> nomContaining(String nom) {
		return (root, query, builder) -> {
			if (nom.strip().length() == 0) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Voie_.NOM))),
					"%" + StringUtils.stripAccents(nom).toUpperCase() + "%");
		};
	}

}
