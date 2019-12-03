package app.gaugiciel.amical.repository.specification;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.LieuFrance_;

public class LieuFranceSpecification {

	public static Specification<LieuFrance> regionContaining(String region) {
		return (root, query, criteriaBuilder) -> Objects.isNull(region) ? null
				: criteriaBuilder.like(criteriaBuilder.upper(root.get(LieuFrance_.REGION)),
						"%" + region.toUpperCase() + "%");
	}

	public static Specification<LieuFrance> departementContaining(String departement) {
		return (root, query, criteriaBuilder) -> Objects.isNull(departement) ? null
				: criteriaBuilder.like(criteriaBuilder.upper(root.get(LieuFrance_.DEPARTEMENT)),
						"%" + departement.toUpperCase() + "%");
	}

	public static Specification<LieuFrance> codePostalContaining(String codePostal) {
		return (root, query, criteriaBuilder) -> Objects.isNull(codePostal) ? null
				: criteriaBuilder.like(criteriaBuilder.upper(root.get(LieuFrance_.CODE_POSTALE)),
						"%" + codePostal.toUpperCase() + "%");
	}

	public static Specification<LieuFrance> villeContaining(String ville) {
		return (root, query, criteriaBuilder) -> Objects.isNull(ville) ? null
				: criteriaBuilder.like(criteriaBuilder.upper(root.get(LieuFrance_.VILLE)),
						"%" + ville.toUpperCase() + "%");
	}

}
