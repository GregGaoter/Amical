package app.gaugiciel.amical.repository.specification;

import java.util.Objects;

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

	public static Specification<Voie> hauteurBetween(int min, int max) {
		return (root, query, builder) -> {
			if (Objects.isNull(min) && Objects.isNull(max)) {
				return null;
			}
			if (Objects.isNull(min) && !Objects.isNull(max)) {
				return builder.between(root.get(Voie_.HAUTEUR), 0, max);
			}
			if (!Objects.isNull(min) && Objects.isNull(max)) {
				return builder.between(root.get(Voie_.HAUTEUR), min, Integer.MAX_VALUE);
			}
			return builder.between(root.get(Voie_.HAUTEUR), min, max);
		};
	}

}
