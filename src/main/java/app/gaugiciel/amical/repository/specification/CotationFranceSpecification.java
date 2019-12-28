package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.business.implementation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteTertiaire;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.CotationFrance_;
import app.gaugiciel.amical.utilitaire.Utils;

public class CotationFranceSpecification {

	public static Specification<CotationFrance> unitePrincipaleEqual(
			ServiceCotationFranceUnitePrincipale unitePrincipale) {
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
			ServiceCotationFranceUniteSecondaire uniteSecondaire) {
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
			ServiceCotationFranceUniteTertiaire uniteTertiaire) {
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
		return Specification.where(unitePrincipaleEqual(cotation.getUnitePrincipale()))
				.and(uniteSecondaireContaining(cotation.getUniteSecondaire()))
				.and(uniteTertiaireEqual(cotation.getUniteTertiaire()));
	}

}
