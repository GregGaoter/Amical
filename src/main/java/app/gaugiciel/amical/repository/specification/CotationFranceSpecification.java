package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteTertiaire;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.CotationFrance_;
import app.gaugiciel.amical.utilitaire.Utils;

public class CotationFranceSpecification {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CotationFranceSpecification.class);

	public static Specification<CotationFrance> unitePrincipaleEqual(
			CotationFranceUnitePrincipale unitePrincipale) {
		LOGGER.info("Start {}()", "unitePrincipaleEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(unitePrincipale)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(CotationFrance_.UNITE_PRINCIPALE))),
					"%" + Utils.normaliser(unitePrincipale.label) + "%");
		};
	}

	public static Specification<CotationFrance> uniteSecondaireContaining(
			CotationFranceUniteSecondaire uniteSecondaire) {
		LOGGER.info("Start {}()", "uniteSecondaireContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(uniteSecondaire)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(CotationFrance_.UNITE_SECONDAIRE))),
					"%" + Utils.normaliser(uniteSecondaire.label) + "%");
		};
	}

	public static Specification<CotationFrance> uniteTertiaireEqual(
			CotationFranceUniteTertiaire uniteTertiaire) {
		LOGGER.info("Start {}()", "uniteTertiaireEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(uniteTertiaire)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(CotationFrance_.UNITE_TERTIAIRE))),
					"%" + Utils.normaliser(uniteTertiaire.label) + "%");
		};
	}

	public static Specification<CotationFrance> cotationEqual(ServiceCotationFrance cotation) {
		LOGGER.info("Start {}()", "cotationEqual");
		return Specification.where(unitePrincipaleEqual(cotation.getUnitePrincipale()))
				.and(uniteSecondaireContaining(cotation.getUniteSecondaire()))
				.and(uniteTertiaireEqual(cotation.getUniteTertiaire()));
	}

}
