package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Plan_;
import app.gaugiciel.amical.utilitaire.Utils;

public class PlanSpecification {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanSpecification.class);

	public static Specification<Plan> planContaining(String plan) {
		return (root, query, builder) -> {
			if (!Utils.isValid(plan)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Plan_.PLAN))),
					"%" + Utils.normaliser(plan) + "%");
		};
	}

	public static Specification<Plan> descriptionContaining(String description) {
		return (root, query, builder) -> {
			if (!Utils.isValid(description)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Plan_.DESCRIPTION))),
					"%" + Utils.normaliser(description) + "%");
		};
	}

	public static Specification<Plan> allContaining(String template) {
		return Specification.where(planContaining(template)).or(descriptionContaining(template));
	}

}
