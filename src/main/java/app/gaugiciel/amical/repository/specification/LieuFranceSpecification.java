package app.gaugiciel.amical.repository.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.LieuFrance_;

public class LieuFranceSpecification {

	public static Specification<LieuFrance> regionContaining(String region) {
		return (root, query, builder) -> {
			if (region.strip().length() == 0) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.REGION))),
					"%" + StringUtils.stripAccents(region).toUpperCase() + "%");
		};
	}

	public static Specification<LieuFrance> departementContaining(String departement) {
		return (root, query, builder) -> {
			if (departement.strip().length() == 0) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.DEPARTEMENT))),
					"%" + StringUtils.stripAccents(departement).toUpperCase() + "%");
		};
	}

	public static Specification<LieuFrance> codePostalContaining(String codePostal) {
		return (root, query, builder) -> {
			if (codePostal.strip().length() == 0) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.CODE_POSTALE))),
					"%" + StringUtils.stripAccents(codePostal).toUpperCase() + "%");
		};
	}

	public static Specification<LieuFrance> villeContaining(String ville) {
		return (root, query, builder) -> {
			if (ville.strip().length() == 0) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.VILLE))),
					"%" + StringUtils.stripAccents(ville).toUpperCase() + "%");
		};
	}

}
