package app.gaugiciel.amical.repository.specification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.controller.form.SpotForm;
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
		return (root, query, criteriaBuilder) -> Objects.isNull(nom) ? null
				: criteriaBuilder.like(criteriaBuilder.upper(root.get(Spot_.NOM)), "%" + nom.toUpperCase() + "%");
	}

	public static Specification<Spot> lieuContaining(String lieu) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.isNull(lieu)) {
				return null;
			}

			Subquery<Long> subqueryLieuFrance = query.subquery(Long.class);
			Root<LieuFrance> rootLieuFrance = subqueryLieuFrance.from(LieuFrance.class);

			Predicate critereRegion = criteriaBuilder.like(
					criteriaBuilder.upper(rootLieuFrance.get(LieuFrance_.REGION)), "%" + lieu.toUpperCase() + "%");
			Predicate critereDepartement = criteriaBuilder.like(
					criteriaBuilder.upper(rootLieuFrance.get(LieuFrance_.DEPARTEMENT)), "%" + lieu.toUpperCase() + "%");
			Predicate critereCodePostal = criteriaBuilder.like(
					criteriaBuilder.upper(rootLieuFrance.get(LieuFrance_.CODE_POSTALE)),
					"%" + lieu.toUpperCase() + "%");
			Predicate critereNomVille = criteriaBuilder
					.like(criteriaBuilder.upper(rootLieuFrance.get(LieuFrance_.VILLE)), "%" + lieu.toUpperCase() + "%");

			subqueryLieuFrance.select(rootLieuFrance.get(LieuFrance_.ID))
					.where(criteriaBuilder.or(critereRegion, critereDepartement, critereCodePostal, critereNomVille));
			query.where(root.get(Spot_.LIEU_FRANCE).in(subqueryLieuFrance));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> officielEqual(Boolean tagQ) {
		return (root, query, criteriaBuilder) -> Objects.isNull(tagQ) ? null
				: tagQ ? criteriaBuilder.isTrue(root.get(Spot_.TAG_Q)) : criteriaBuilder.isFalse(root.get(Spot_.TAG_Q));
	}

	public static Specification<Spot> nomSecteurContaining(String nomSecteur) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.isNull(nomSecteur)) {
				return null;
			}

			Subquery<Long> subquerySecteur = query.subquery(Long.class);
			Root<Secteur> rootSecteur = subquerySecteur.from(Secteur.class);

			Predicate critereNomSecteur = criteriaBuilder.like(criteriaBuilder.upper(rootSecteur.get(Secteur_.NOM)),
					"%" + nomSecteur.toUpperCase() + "%");
			subquerySecteur.select(rootSecteur.get(Secteur_.SPOT)).where(critereNomSecteur);
			query.where(root.get(Spot_.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> nomVoieContaining(String nomVoie) {
		return (root, query, criteriaBuilder) -> {
			if (Objects.isNull(nomVoie)) {
				return null;
			}

			Subquery<Long> subquerySecteur = query.subquery(Long.class);
			Root<Secteur> rootSecteur = subquerySecteur.from(Secteur.class);
			Subquery<Long> subqueryVoie = subquerySecteur.subquery(Long.class);
			Root<Voie> rootVoie = subqueryVoie.from(Voie.class);

			Predicate critereNomVoie = criteriaBuilder.like(criteriaBuilder.upper(rootVoie.get(Voie_.NOM)),
					"%" + nomVoie.toUpperCase() + "%");
			subqueryVoie.select(rootVoie.get(Voie_.SECTEUR)).where(critereNomVoie);
			subquerySecteur.select(rootSecteur.get(Secteur_.SPOT)).where(rootSecteur.get(Secteur_.ID).in(subqueryVoie));
			query.where(root.get(Spot_.ID).in(subquerySecteur));

			return query.getRestriction();
		};
	}

	public static Specification<Spot> cotationVoieBetween(String minUnitePrincipale, String minUniteSecondaire,
			String minUniteTertiaire, String maxUnitePrincipale, String maxUniteSecondaire, String maxUniteTertiaire) {
		return (root, query, builder) -> {
			if (Objects.isNull(minUnitePrincipale) && Objects.isNull(maxUnitePrincipale)) {
				return null;
			}

			// IntStream unitePrincipaleRange = IntStream.rangeClosed(3, 9);
			String[] unitePrincipaleRange = { "3", "4", "5", "6", "7", "8", "9" };
			String[] uniteSecondaireRange = { "", "a", "b", "c" };
			String[] uniteTertiaireRange = { "", "+" };
			String[][] unites = { { "3", "4", "5", "6", "7", "8", "9" }, { "", "a", "b", "c" }, { "", "+" } };

			for (int i = 0; i < unites.length; i++) {

			}

			Map<String, Integer> unitePrincipale = new HashMap<>();
			unitePrincipale.put("3", 0);
			unitePrincipale.put("4", 1);
			unitePrincipale.put("5", 2);
			unitePrincipale.put("6", 3);
			unitePrincipale.put("7", 4);
			unitePrincipale.put("8", 5);
			unitePrincipale.put("9", 6);
			Map<String, Integer> uniteSecondaire = new HashMap<>();
			unitePrincipale.put("", 0);
			unitePrincipale.put("a", 1);
			unitePrincipale.put("b", 2);
			unitePrincipale.put("c", 3);
			Map<String, Integer> uniteTertiaire = new HashMap<>();
			unitePrincipale.put("", 0);
			unitePrincipale.put("+", 1);

			return query.getRestriction();
		};
	}

	public static Specification<Spot> hasAll(SpotForm spotForm) {
		return Specification.where(nomContaining(spotForm.getNomSpot()))
				.and(lieuContaining(spotForm.getLieuFranceSpot()).and(officielEqual(spotForm.getIsOfficielSpot())))
				.and(nomSecteurContaining(spotForm.getNomSecteur())).and(nomVoieContaining(spotForm.getNomVoie()))
				.and(cotationVoieBetween(spotForm.getCotationMinVoieUnitePrincipale(),
						spotForm.getCotationMinVoieUniteSecondaire(), spotForm.getCotationMinVoieUniteTertiaire(),
						spotForm.getCotationMaxVoieUnitePrincipale(), spotForm.getCotationMaxVoieUniteSecondaire(),
						spotForm.getCotationMaxVoieUniteTertiaire()));
	}

}
