package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.DemandePretEmpruntManuel;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.utilitaire.Utils;

public class DemandePretEmpruntManuelSpecification {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemandePretEmpruntManuelSpecification.class);

	public static Specification<DemandePretEmpruntManuel> proprietaireEqual(Authentification proprietaire) {
		LOGGER.info("Start {}()", "proprietaireEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(proprietaire)) {
				return null;
			}
			return builder.equal(root.get(DemandePretEmpruntManuel.PROPRIETAIRE), proprietaire);
		};
	}

	public static Specification<DemandePretEmpruntManuel> demandeurEqual(Authentification demandeur) {
		LOGGER.info("Start {}()", "demandeurEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(demandeur)) {
				return null;
			}
			return builder.equal(root.get(DemandePretEmpruntManuel.DEMANDEUR), demandeur);
		};
	}

	public static Specification<DemandePretEmpruntManuel> manuelIdEqual(Long manuelId) {
		LOGGER.info("Start {}()", "manuelIdEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(manuelId)) {
				return null;
			}
			return builder.equal(root.get(DemandePretEmpruntManuel.MANUEL), manuelId);
		};
	}

	public static Specification<DemandePretEmpruntManuel> manuelEqual(Manuel manuel) {
		LOGGER.info("Start {}()", "manuelEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(manuel)) {
				return null;
			}
			return builder.equal(root.get(DemandePretEmpruntManuel.MANUEL), manuel);
		};
	}

}
