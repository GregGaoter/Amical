package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.model.Voie_;
import app.gaugiciel.amical.utilitaire.Utils;

public class VoieSpecification {

	public static Specification<Voie> nomContaining(String nom) {
		return (root, query, builder) -> {
			if (!Utils.isValid(nom)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Voie_.NOM))),
					"%" + Utils.normaliser(nom) + "%");
		};
	}

	public static Specification<Voie> hauteurBetween(Integer min, Integer max) {
		return (root, query, builder) -> {
			if (!Utils.isValid(min, max)) {
				return null;
			}
			if (!Utils.isValid(min) && Utils.isValid(max)) {
				return builder.between(root.get(Voie_.HAUTEUR), 0, max);
			}
			if (Utils.isValid(min) && !Utils.isValid(max)) {
				return builder.between(root.get(Voie_.HAUTEUR), min, Integer.MAX_VALUE);
			}
			return builder.between(root.get(Voie_.HAUTEUR), min, max);
		};
	}

	public static Specification<Voie> secteurIdEqual(Long secteurId) {
		return (root, query, builder) -> {
			if (!Utils.isValid(secteurId)) {
				return null;
			}
			return builder.equal(root.get(Voie_.SECTEUR), secteurId);
		};
	}

}
