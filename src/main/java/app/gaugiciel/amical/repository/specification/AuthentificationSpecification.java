package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.Authentification_;
import app.gaugiciel.amical.utilitaire.Utils;

public class AuthentificationSpecification {

	public static Specification<Authentification> emailContaining(String email) {
		return (root, query, builder) -> {
			if (!Utils.isValid(email)) {
				return null;
			}
			return builder.like(
					builder.function("unaccent", String.class, builder.upper(root.get(Authentification_.EMAIL))),
					"%" + Utils.normaliser(email) + "%");
		};
	}

}
