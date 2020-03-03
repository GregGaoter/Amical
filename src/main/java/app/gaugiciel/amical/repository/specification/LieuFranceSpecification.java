package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.utilitaire.Utils;

public class LieuFranceSpecification {

	private static final Logger LOGGER = LoggerFactory.getLogger(LieuFranceSpecification.class);

	public static Specification<LieuFrance> regionContaining(String region) {
		LOGGER.info("Start {}()", "regionContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(region)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance.REGION))),
					"%" + Utils.normaliser(region) + "%");
		};
	}

	public static Specification<LieuFrance> regionEqual(String region) {
		LOGGER.info("Start {}()", "regionEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(region)) {
				return null;
			}
			return builder.equal(root.get(LieuFrance.REGION), region);
		};
	}

	public static Specification<LieuFrance> departementContaining(String departement) {
		LOGGER.info("Start {}()", "departementContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(departement)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance.DEPARTEMENT))),
					"%" + Utils.normaliser(departement) + "%");
		};
	}

	public static Specification<LieuFrance> departementEqual(String departement) {
		LOGGER.info("Start {}()", "departementEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(departement)) {
				return null;
			}
			return builder.equal(root.get(LieuFrance.DEPARTEMENT), departement);
		};
	}

	public static Specification<LieuFrance> codePostalContaining(String codePostal) {
		LOGGER.info("Start {}()", "codePostalContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(codePostal)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance.CODE_POSTAL))),
					"%" + Utils.normaliser(codePostal) + "%");
		};
	}

	public static Specification<LieuFrance> codePostalEqual(String codePostal) {
		LOGGER.info("Start {}()", "codePostalEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(codePostal)) {
				return null;
			}
			return builder.equal(root.get(LieuFrance.CODE_POSTAL), codePostal);
		};
	}

	public static Specification<LieuFrance> villeContaining(String ville) {
		LOGGER.info("Start {}()", "villeContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(ville)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(LieuFrance.VILLE))),
					"%" + Utils.normaliser(ville) + "%");
		};
	}

	public static Specification<LieuFrance> villeEqual(String ville) {
		LOGGER.info("Start {}()", "villeEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(ville)) {
				return null;
			}
			return builder.equal(root.get(LieuFrance.VILLE), ville);
		};
	}

	public static Specification<LieuFrance> lieuContaining(String lieu) {
		LOGGER.info("Start {}()", "lieuContaining");
		if (!Utils.isValid(lieu)) {
			return null;
		}
		return Specification.where(regionContaining(lieu)).or(departementContaining(lieu))
				.or(codePostalContaining(lieu)).or(villeContaining(lieu));
	}

	public static Specification<LieuFrance> nomCompletEqual(String nomComplet) {
		LOGGER.info("Start {}()", "nomCompletEqual");
		if (!Utils.isValid(nomComplet)) {
			return null;
		}
		String[] nomCompletSplit = nomComplet.split(", ");
		String region = nomCompletSplit[0];
		String departement = nomCompletSplit[1];
		String codePostal = nomCompletSplit[2];
		String ville = nomCompletSplit[3];
		return Specification.where(regionEqual(region)).and(departementEqual(departement))
				.and(codePostalEqual(codePostal)).and(villeEqual(ville));
	}

}
