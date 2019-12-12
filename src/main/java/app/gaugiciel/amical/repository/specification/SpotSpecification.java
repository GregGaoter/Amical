package app.gaugiciel.amical.repository.specification;

import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.business.implementation.ServiceCotationFrance;
import app.gaugiciel.amical.controller.form.SpotForm;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.CotationFrance_;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.LieuFrance_;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Secteur_;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Spot_;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.model.Voie_;

public class SpotSpecification {

	public static Specification<Spot> nomContaining(String nom) {
		return (root, query, builder) -> {
			if (nom.strip().length() == 0) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Spot_.NOM))),
					"%" + StringUtils.stripAccents(nom).toUpperCase() + "%");
		};
	}

	public static Specification<Spot> lieuContaining(String lieu) {
		return (root, query, builder) -> {
			if (lieu.strip().length() == 0) {
				return null;
			}

			Subquery<Long> subqueryLieuFrance = query.subquery(Long.class);
			Root<LieuFrance> rootLieuFrance = subqueryLieuFrance.from(LieuFrance.class);

			Predicate critereRegion = LieuFranceSpecification.regionContaining(lieu).toPredicate(rootLieuFrance, query,
					builder);
			Predicate critereDepartement = LieuFranceSpecification.departementContaining(lieu)
					.toPredicate(rootLieuFrance, query, builder);
			Predicate critereCodePostal = LieuFranceSpecification.codePostalContaining(lieu).toPredicate(rootLieuFrance,
					query, builder);
			Predicate critereNomVille = LieuFranceSpecification.villeContaining(lieu).toPredicate(rootLieuFrance, query,
					builder);

			subqueryLieuFrance.select(rootLieuFrance.get(LieuFrance_.ID))
					.where(builder.or(critereRegion, critereDepartement, critereCodePostal, critereNomVille));
			query.where(root.get(Spot_.LIEU_FRANCE).in(subqueryLieuFrance));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> officielEqual(Boolean tagQ) {
		return (root, query, builder) -> {
			if (Objects.isNull(tagQ)) {
				return null;
			}
			return tagQ ? builder.isTrue(root.get(Spot_.TAG_Q)) : builder.isFalse(root.get(Spot_.TAG_Q));
		};
	}

	public static Specification<Spot> nomSecteurContaining(String nomSecteur) {
		return (root, query, builder) -> {
			if (nomSecteur.strip().length() == 0) {
				return null;
			}

			Subquery<Long> subquerySecteur = query.subquery(Long.class);
			Root<Secteur> rootSecteur = subquerySecteur.from(Secteur.class);

			Predicate critereNomSecteur = SecteurSpecification.nomContaining(nomSecteur).toPredicate(rootSecteur, query,
					builder);
			subquerySecteur.select(rootSecteur.get(Secteur_.SPOT)).where(critereNomSecteur);
			query.where(root.get(Spot_.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> nomVoieContaining(String nomVoie) {
		return (root, query, builder) -> {
			if (nomVoie.strip().length() == 0) {
				return null;
			}

			Subquery<Long> subquerySecteur = query.subquery(Long.class);
			Root<Secteur> rootSecteur = subquerySecteur.from(Secteur.class);
			Subquery<Long> subqueryVoie = subquerySecteur.subquery(Long.class);
			Root<Voie> rootVoie = subqueryVoie.from(Voie.class);

			Predicate critereNomVoie = VoieSpecification.nomContaining(nomVoie).toPredicate(rootVoie, query, builder);
			subqueryVoie.select(rootVoie.get(Voie_.SECTEUR)).where(critereNomVoie);
			subquerySecteur.select(rootSecteur.get(Secteur_.SPOT)).where(rootSecteur.get(Secteur_.ID).in(subqueryVoie));
			query.where(root.get(Spot_.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> cotationVoieEqual(List<ServiceCotationFrance> listeCotations) {
		return (root, query, builder) -> {
			if (listeCotations.isEmpty()) {
				return null;
			}

			Subquery<Long> subquerySecteur = query.subquery(Long.class);
			Root<Secteur> rootSecteur = subquerySecteur.from(Secteur.class);
			Subquery<Long> subqueryVoie = subquerySecteur.subquery(Long.class);
			Root<Voie> rootVoie = subqueryVoie.from(Voie.class);
			Subquery<Long> subqueryCotation = subqueryVoie.subquery(Long.class);
			Root<CotationFrance> rootCotation = subqueryCotation.from(CotationFrance.class);

			Predicate[] listePredicatesCotations = new Predicate[listeCotations.size()];
			int i = 0;
			for (ServiceCotationFrance cotation : listeCotations) {
				listePredicatesCotations[i++] = CotationFranceSpecification.cotationEqual(cotation)
						.toPredicate(rootCotation, query, builder);
			}

			subqueryCotation.select(rootCotation.get(CotationFrance_.ID)).where(builder.or(listePredicatesCotations));
			subqueryVoie.select(rootVoie.get(Voie_.SECTEUR))
					.where(rootVoie.get(Voie_.COTATION_FRANCE).in(subqueryCotation));
			subquerySecteur.select(rootSecteur.get(Secteur_.SPOT)).where(rootSecteur.get(Secteur_.ID).in(subqueryVoie));
			query.where(root.get(Spot_.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> hauteurVoieBetween(Integer min, Integer max) {
		return (root, query, builder) -> {
			if (Objects.isNull(min) && Objects.isNull(max)) {
				return null;
			}

			Subquery<Long> subquerySecteur = query.subquery(Long.class);
			Root<Secteur> rootSecteur = subquerySecteur.from(Secteur.class);
			Subquery<Long> subqueryVoie = subquerySecteur.subquery(Long.class);
			Root<Voie> rootVoie = subqueryVoie.from(Voie.class);

			Predicate critereHauteurVoie = VoieSpecification.hauteurBetween(min, max).toPredicate(rootVoie, query,
					builder);
			subqueryVoie.select(rootVoie.get(Voie_.SECTEUR)).where(critereHauteurVoie);
			subquerySecteur.select(rootSecteur.get(Secteur_.SPOT)).where(rootSecteur.get(Secteur_.ID).in(subqueryVoie));
			query.where(root.get(Spot_.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> hasAll(SpotForm spotForm) {
		if (spotForm.estVide()) {
			return null;
		}
		return Specification.where(nomContaining(spotForm.getNomSpot()))
				.and(lieuContaining(spotForm.getLieuFranceSpot()).and(officielEqual(spotForm.getIsOfficielSpot())))
				.and(nomSecteurContaining(spotForm.getNomSecteur())).and(nomVoieContaining(spotForm.getNomVoie()))
				.and(cotationVoieEqual(spotForm.getListeCotations()))
				.and(hauteurVoieBetween(spotForm.getHauteurMinVoie(), spotForm.getHauteurMaxVoie()));
	}

}
