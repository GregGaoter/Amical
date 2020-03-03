package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.utilitaire.Utils;

public class SecteurSpecification {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecteurSpecification.class);

	public static Specification<Secteur> nomContaining(String nom) {
		LOGGER.info("Start {}()", "nomContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(nom)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Secteur.NOM))),
					"%" + Utils.normaliser(nom) + "%");
		};
	}

}
