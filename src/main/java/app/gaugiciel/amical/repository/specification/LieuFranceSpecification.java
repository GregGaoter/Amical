package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.LieuFrance_;
import app.gaugiciel.amical.utilitaire.Utils;

public class LieuFranceSpecification {

	public static Specification<LieuFrance> regionContaining(String region) {
		return (root, query, builder) -> {
			if (!Utils.isValid(region)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.REGION))),
					"%" + Utils.normaliser(region) + "%");
		};
	}

	public static Specification<LieuFrance> departementContaining(String departement) {
		return (root, query, builder) -> {
			if (!Utils.isValid(departement)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.DEPARTEMENT))),
					"%" + Utils.normaliser(departement) + "%");
		};
	}

	public static Specification<LieuFrance> codePostalContaining(String codePostal) {
		return (root, query, builder) -> {
			if (!Utils.isValid(codePostal)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.CODE_POSTALE))),
					"%" + Utils.normaliser(codePostal) + "%");
		};
	}

	public static Specification<LieuFrance> villeContaining(String ville) {
		return (root, query, builder) -> {
			if (!Utils.isValid(ville)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.VILLE))),
					"%" + Utils.normaliser(ville) + "%");
		};
	}

	public static Specification<LieuFrance> lieuContaining(String lieu) {
		return Specification.where(regionContaining(lieu)).or(departementContaining(lieu))
				.or(codePostalContaining(lieu)).or(villeContaining(lieu));
	}

}
