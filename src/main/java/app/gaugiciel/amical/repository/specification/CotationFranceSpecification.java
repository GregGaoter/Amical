package app.gaugiciel.amical.repository.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.CotationFrance_;

public class CotationFranceSpecification {

	public static Specification<CotationFrance> unitePrincipaleEqual(String unitePrincipale) {
		return (root, query, builder) -> {
			if (unitePrincipale.strip().length() == 0) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(CotationFrance_.UNITE_PRINCIPALE))),
					"%" + StringUtils.stripAccents(unitePrincipale).toUpperCase() + "%");
		};
	}

	public static Specification<CotationFrance> uniteSecondaireContaining(String uniteSecondaire) {
		return (root, query, builder) -> {
			if (uniteSecondaire.strip().length() == 0) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(CotationFrance_.UNITE_SECONDAIRE))),
					"%" + StringUtils.stripAccents(uniteSecondaire).toUpperCase() + "%");
		};
	}

	public static Specification<CotationFrance> uniteTertiaireEqual(String uniteTertiaire) {
		return (root, query, builder) -> {
			if (uniteTertiaire.strip().length() == 0) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class,
							builder.upper(root.get(CotationFrance_.UNITE_TERTIAIRE))),
					"%" + StringUtils.stripAccents(uniteTertiaire).toUpperCase() + "%");
		};
	}

}
