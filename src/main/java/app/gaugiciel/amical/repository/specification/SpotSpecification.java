package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Spot;

public class SpotSpecification {

	public static Specification<Spot> hasNomIgnoreCaseContaining(String nom) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("nom")),
				"%" + nom.toUpperCase() + "%");
	}

	public static Specification<Spot> isOfficiel() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("tagQ"));
	}

	public static Specification<Spot> isNotOfficiel() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("tagQ"));
	}

}
