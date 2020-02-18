package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.PretEmpruntManuel;
import app.gaugiciel.amical.model.PretEmpruntManuel_;
import app.gaugiciel.amical.utilitaire.Utils;

public class PretEmpruntManuelSpecification {

	public static Specification<PretEmpruntManuel> preteurEqual(Authentification preteur) {
		return (root, query, builder) -> {
			if (!Utils.isValid(preteur)) {
				return null;
			}
			return builder.equal(root.get(PretEmpruntManuel_.PRETEUR), preteur);
		};
	}

	public static Specification<PretEmpruntManuel> emprunteurEqual(Authentification emprunteur) {
		return (root, query, builder) -> {
			if (!Utils.isValid(emprunteur)) {
				return null;
			}
			return builder.equal(root.get(PretEmpruntManuel_.EMPRUNTEUR), emprunteur);
		};
	}

	public static Specification<PretEmpruntManuel> manuelEqual(Manuel manuel) {
		return (root, query, builder) -> {
			if (!Utils.isValid(manuel)) {
				return null;
			}
			return builder.equal(root.get(PretEmpruntManuel_.MANUEL), manuel);
		};
	}

}
