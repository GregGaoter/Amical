package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.business.utils.Utils;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.model.Voie_;

public class VoieSpecification {

	public static Specification<Voie> nomContaining(String nom) {
		return (root, query, builder) -> {
			if (Utils.valideQ(nom)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Voie_.NOM))),
					"%" + Utils.normaliser(nom) + "%");
		};
	}

	public static Specification<Voie> hauteurBetween(Integer min, Integer max) {
		return (root, query, builder) -> {
			if (Utils.valideQ(min, max)) {
				return null;
			}
			if (Utils.valideQ(min) && !Utils.valideQ(max)) {
				return builder.between(root.get(Voie_.HAUTEUR), 0, max);
			}
			if (!Utils.valideQ(min) && Utils.valideQ(max)) {
				return builder.between(root.get(Voie_.HAUTEUR), min, Integer.MAX_VALUE);
			}
			return builder.between(root.get(Voie_.HAUTEUR), min, max);
		};
	}

}
