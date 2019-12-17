package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.business.utils.Utils;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.LieuFrance_;

public class LieuFranceSpecification {

	public static Specification<LieuFrance> regionContaining(String region) {
		return (root, query, builder) -> {
			if (Utils.valideQ(region)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.REGION))),
					"%" + Utils.normaliser(region) + "%");
		};
	}

	public static Specification<LieuFrance> departementContaining(String departement) {
		return (root, query, builder) -> {
			if (Utils.valideQ(departement)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.DEPARTEMENT))),
					"%" + Utils.normaliser(departement) + "%");
		};
	}

	public static Specification<LieuFrance> codePostalContaining(String codePostal) {
		return (root, query, builder) -> {
			if (Utils.valideQ(codePostal)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance_.CODE_POSTALE))),
					"%" + Utils.normaliser(codePostal) + "%");
		};
	}

	public static Specification<LieuFrance> villeContaining(String ville) {
		return (root, query, builder) -> {
			if (Utils.valideQ(ville)) {
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
