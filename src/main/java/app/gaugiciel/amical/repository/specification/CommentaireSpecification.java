package app.gaugiciel.amical.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Commentaire;
import app.gaugiciel.amical.model.Commentaire_;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.utilitaire.Utils;

public class CommentaireSpecification {

	public static Specification<Commentaire> spotEqual(Spot spot) {
		return (root, query, builder) -> {
			if (!Utils.isValid(spot)) {
				return null;
			}
			return builder.equal(root.get(Commentaire_.SPOT), spot);
		};
	}

}
