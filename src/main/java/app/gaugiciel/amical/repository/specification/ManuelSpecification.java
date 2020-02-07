package app.gaugiciel.amical.repository.specification;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.controller.form.RechercheTopoForm;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.LieuFrance_;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.Manuel_;
import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.model.Utilisateur_;
import app.gaugiciel.amical.utilitaire.Utils;

public class ManuelSpecification {

	public static Specification<Manuel> nomContaining(String nom) {
		return (root, query, builder) -> {
			if (!Utils.isValid(nom)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Manuel_.NOM))),
					"%" + Utils.normaliser(nom) + "%");
		};
	}

	public static Specification<Manuel> lieuContaining(String lieu) {
		return (root, query, builder) -> {
			if (!Utils.isValid(lieu)) {
				return null;
			}

			Subquery<Long> subqueryLieuFrance = query.subquery(Long.class);
			Root<LieuFrance> rootLieuFrance = subqueryLieuFrance.from(LieuFrance.class);

			subqueryLieuFrance.select(rootLieuFrance.get(LieuFrance_.ID))
					.where(LieuFranceSpecification.lieuContaining(lieu).toPredicate(rootLieuFrance, query, builder));
			query.where(root.get(Manuel_.LIEU_FRANCE).in(subqueryLieuFrance));

			return query.getRestriction();
		};
	}

	public static Specification<Manuel> categorieEqual(String categorie) {
		return (root, query, builder) -> {
			if (!Utils.isValid(categorie)) {
				return null;
			}
			return builder.equal(root.get(Manuel_.CATEGORIE), categorie);
		};
	}

	public static Specification<Manuel> etatEqual(String etat) {
		return (root, query, builder) -> {
			if (!Utils.isValid(etat)) {
				return null;
			}
			return builder.equal(root.get(Manuel_.ETAT), etat);
		};
	}

	public static Specification<Manuel> dateBetween(Timestamp t1, Timestamp t2) {
		return (root, query, builder) -> {
			if (!Utils.isValid(t1) && !Utils.isValid(t2)) {
				return null;
			}
			if (!Utils.isValid(t1) && Utils.isValid(t2)) {
				return builder.between(root.get(Manuel_.DATE_TIME_PARUTION), Timestamp.valueOf("1900-1-1 00:00:00"),
						t2);
			}
			if (Utils.isValid(t1) && !Utils.isValid(t2)) {
				return builder.between(root.get(Manuel_.DATE_TIME_PARUTION), t1, Timestamp.from(Instant.now()));
			}
			return builder.between(root.get(Manuel_.DATE_TIME_PARUTION), t1, t2);
		};
	}

	public static Specification<Manuel> auteurContaining(String auteur) {
		return (root, query, builder) -> {
			if (!Utils.isValid(auteur)) {
				return null;
			}
			return builder.like(builder.function("unaccent", String.class, builder.upper(root.get(Manuel_.AUTEUR))),
					"%" + Utils.normaliser(auteur) + "%");
		};
	}

	public static Specification<Manuel> proprietaireContaining(String proprietaire) {
		return (root, query, builder) -> {
			if (!Utils.isValid(proprietaire)) {
				return null;
			}
			Subquery<String> subqueryUtilisateur = query.subquery(String.class);
			Root<Utilisateur> rootUtilisateur = subqueryUtilisateur.from(Utilisateur.class);
			subqueryUtilisateur.select(rootUtilisateur.get(Utilisateur_.AUTHENTIFICATION_EMAIL))
					.where(UtilisateurSpecification.proprietesContaining(proprietaire).toPredicate(rootUtilisateur,
							query, builder));
			query.where(root.get(Manuel_.AUTHENTIFICATION).in(subqueryUtilisateur));

			return query.getRestriction();
		};
	}

	public static Specification<Manuel> proprietaireEqual(Authentification proprietaire) {
		return (root, query, builder) -> {
			if (Objects.isNull(proprietaire)) {
				return null;
			}
			return builder.equal(root.get(Manuel_.AUTHENTIFICATION), proprietaire);
		};
	}

	public static Specification<Manuel> hasAll(RechercheTopoForm rechercheTopoForm) {
		if (rechercheTopoForm.estVide()) {
			return null;
		}
		return Specification.where(nomContaining(rechercheTopoForm.getNom()))
				.and(lieuContaining(rechercheTopoForm.getLieuFranceTemplate()))
				.and(categorieEqual(rechercheTopoForm.getCategorie())).and(etatEqual(rechercheTopoForm.getEtat()))
				.and(dateBetween(rechercheTopoForm.getDateParutionMin(), rechercheTopoForm.getDateParutionMax()))
				.and(auteurContaining(rechercheTopoForm.getAuteur()))
				.and(proprietaireContaining(rechercheTopoForm.getProprietaireTemplate()));
	}

}
