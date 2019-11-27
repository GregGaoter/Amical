package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Spot_;

public class SpotSpecification {

	public static Specification<Spot> creerSpecification(CritereRecherche critereRecherche) {
		return (root, query, criteriaBuilder) -> {
			return null;
		};
	}

	public static Specification<Spot> hasNomIgnoreCaseContaining(String nom) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get(Spot_.NOM)),
				"%" + nom.toUpperCase() + "%");
	}

	public static Specification<Spot> isOfficiel() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(Spot_.TAG_Q));
	}

	public static Specification<Spot> isNotOfficiel() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get(Spot_.TAG_Q));
	}

	public static Specification<Spot> hasAll() {
		return Specification.where(hasNomIgnoreCaseContaining("")).and(isOfficiel());
	}

}
