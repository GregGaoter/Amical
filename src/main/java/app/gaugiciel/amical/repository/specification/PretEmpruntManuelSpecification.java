package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.PretEmpruntManuel;
import app.gaugiciel.amical.utilitaire.Utils;

public class PretEmpruntManuelSpecification {

	private static final Logger LOGGER = LoggerFactory.getLogger(PretEmpruntManuelSpecification.class);

	public static Specification<PretEmpruntManuel> preteurEqual(Authentification preteur) {
		LOGGER.info("Start {}()", "preteurEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(preteur)) {
				return null;
			}
			return builder.equal(root.get(PretEmpruntManuel.PRETEUR), preteur);
		};
	}

	public static Specification<PretEmpruntManuel> emprunteurEqual(Authentification emprunteur) {
		LOGGER.info("Start {}()", "emprunteurEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(emprunteur)) {
				return null;
			}
			return builder.equal(root.get(PretEmpruntManuel.EMPRUNTEUR), emprunteur);
		};
	}

	public static Specification<PretEmpruntManuel> manuelEqual(Manuel manuel) {
		LOGGER.info("Start {}()", "manuelEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(manuel)) {
				return null;
			}
			return builder.equal(root.get(PretEmpruntManuel.MANUEL), manuel);
		};
	}

}
