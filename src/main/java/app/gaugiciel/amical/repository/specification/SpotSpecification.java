package app.gaugiciel.amical.repository.specification;

import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFrance;
import app.gaugiciel.amical.controller.form.RechercheSpotForm;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.utilitaire.Utils;

public class SpotSpecification {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpotSpecification.class);

	public static Specification<Spot> nomContaining(String nom) {
		LOGGER.info("Start {}()", "nomContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(nom)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Spot.NOM))),
					"%" + Utils.normaliser(nom) + "%");
		};
	}

	public static Specification<Spot> lieuContaining(String lieu) {
		LOGGER.info("Start {}()", "lieuContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(lieu)) {
				return null;
			}

			Subquery<Long> subqueryLieuFrance = query.subquery(Long.class);
			Root<LieuFrance> rootLieuFrance = subqueryLieuFrance.from(LieuFrance.class);

			subqueryLieuFrance.select(rootLieuFrance.get(LieuFrance.ID))
					.where(LieuFranceSpecification.lieuContaining(lieu).toPredicate(rootLieuFrance, query, builder));
			query.where(root.get(Spot.LIEU_FRANCE).in(subqueryLieuFrance));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> officielEqual(Boolean tagQ) {
		LOGGER.info("Start {}()", "officielEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(tagQ)) {
				return null;
			}
			return tagQ ? builder.isTrue(root.get(Spot.TAG_Q)) : builder.isFalse(root.get(Spot.TAG_Q));
		};
	}

	public static Specification<Spot> nomSecteurContaining(String nomSecteur) {
		LOGGER.info("Start {}()", "nomSecteurContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(nomSecteur)) {
				return null;
			}

			Subquery<Long> subquerySecteur = query.subquery(Long.class);
			Root<Secteur> rootSecteur = subquerySecteur.from(Secteur.class);

			Predicate critereNomSecteur = SecteurSpecification.nomContaining(nomSecteur).toPredicate(rootSecteur, query,
					builder);
			subquerySecteur.select(rootSecteur.get(Secteur.SPOT)).where(critereNomSecteur);
			query.where(root.get(Spot.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> nomVoieContaining(String nomVoie) {
		LOGGER.info("Start {}()", "nomVoieContaining");
		return (root, query, builder) -> {
			if (!Utils.isValid(nomVoie)) {
				return null;
			}

			Subquery<Long> subquerySecteur = query.subquery(Long.class);
			Root<Secteur> rootSecteur = subquerySecteur.from(Secteur.class);
			Subquery<Long> subqueryVoie = subquerySecteur.subquery(Long.class);
			Root<Voie> rootVoie = subqueryVoie.from(Voie.class);

			Predicate critereNomVoie = VoieSpecification.nomContaining(nomVoie).toPredicate(rootVoie, query, builder);
			subqueryVoie.select(rootVoie.get(Voie.SECTEUR)).where(critereNomVoie);
			subquerySecteur.select(rootSecteur.get(Secteur.SPOT)).where(rootSecteur.get(Secteur.ID).in(subqueryVoie));
			query.where(root.get(Spot.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> cotationVoieEqual(List<ServiceCotationFrance> listeCotations) {
		LOGGER.info("Start {}()", "cotationVoieEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(listeCotations)) {
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

			subqueryCotation.select(rootCotation.get(CotationFrance.ID)).where(builder.or(listePredicatesCotations));
			subqueryVoie.select(rootVoie.get(Voie.SECTEUR))
					.where(rootVoie.get(Voie.COTATION_FRANCE).in(subqueryCotation));
			subquerySecteur.select(rootSecteur.get(Secteur.SPOT)).where(rootSecteur.get(Secteur.ID).in(subqueryVoie));
			query.where(root.get(Spot.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> hauteurVoieBetween(Integer min, Integer max) {
		LOGGER.info("Start {}()", "hauteurVoieBetween");
		return (root, query, builder) -> {
			if (!Utils.isValid(min, max)) {
				return null;
			}

			Subquery<Long> subquerySecteur = query.subquery(Long.class);
			Root<Secteur> rootSecteur = subquerySecteur.from(Secteur.class);
			Subquery<Long> subqueryVoie = subquerySecteur.subquery(Long.class);
			Root<Voie> rootVoie = subqueryVoie.from(Voie.class);

			Predicate critereHauteurVoie = VoieSpecification.hauteurBetween(min, max).toPredicate(rootVoie, query,
					builder);
			subqueryVoie.select(rootVoie.get(Voie.SECTEUR)).where(critereHauteurVoie);
			subquerySecteur.select(rootSecteur.get(Secteur.SPOT)).where(rootSecteur.get(Secteur.ID).in(subqueryVoie));
			query.where(root.get(Spot.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> hasAll(RechercheSpotForm rechercheSpotForm) {
		LOGGER.info("Start {}()", "hasAll");
		if (rechercheSpotForm.estVide()) {
			return null;
		}
		return Specification.where(nomContaining(rechercheSpotForm.getNomSpot()))
				.and(lieuContaining(rechercheSpotForm.getLieuFranceSpot())
						.and(officielEqual(rechercheSpotForm.getIsOfficielSpot())))
				.and(nomSecteurContaining(rechercheSpotForm.getNomSecteur()))
				.and(nomVoieContaining(rechercheSpotForm.getNomVoie()))
				.and(cotationVoieEqual(rechercheSpotForm.getListeCotations()))
				.and(hauteurVoieBetween(rechercheSpotForm.getHauteurMinVoie(), rechercheSpotForm.getHauteurMaxVoie()));
	}

}
