package app.gaugiciel.amical.repository.specification;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.business.implementation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteTertiaire;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.CotationFrance_;

public class CotationFranceSpecification {

	public static Specification<CotationFrance> unitePrincipaleEqual(
			ServiceCotationFranceUnitePrincipale unitePrincipale) {
		return (root, query, builder) -> {
			if (Objects.isNull(unitePrincipale)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(CotationFrance_.UNITE_PRINCIPALE))),
					"%" + StringUtils.stripAccents(unitePrincipale.label).toUpperCase() + "%");
		};
	}

	public static Specification<CotationFrance> uniteSecondaireContaining(
			ServiceCotationFranceUniteSecondaire uniteSecondaire) {
		return (root, query, builder) -> {
			if (Objects.isNull(uniteSecondaire)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(CotationFrance_.UNITE_SECONDAIRE))),
					"%" + StringUtils.stripAccents(uniteSecondaire.label).toUpperCase() + "%");
		};
	}

	public static Specification<CotationFrance> uniteTertiaireEqual(
			ServiceCotationFranceUniteTertiaire uniteTertiaire) {
		return (root, query, builder) -> {
			if (Objects.isNull(uniteTertiaire)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(CotationFrance_.UNITE_TERTIAIRE))),
					"%" + StringUtils.stripAccents(uniteTertiaire.label).toUpperCase() + "%");
		};
	}

	public static Specification<CotationFrance> cotationEqual(ServiceCotationFrance cotation) {
		return Specification.where(unitePrincipaleEqual(cotation.getUnitePrincipale()))
				.and(uniteSecondaireContaining(cotation.getUniteSecondaire()))
				.and(uniteTertiaireEqual(cotation.getUniteTertiaire()));
	}

}
