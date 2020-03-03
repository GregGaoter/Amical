package app.gaugiciel.amical.repository.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import app.gaugiciel.amical.model.Commentaire;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.utilitaire.Utils;

public class CommentaireSpecification {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommentaireSpecification.class);

	public static Specification<Commentaire> spotEqual(Spot spot) {
		LOGGER.info("Start {}()", "spotEqual");
		return (root, query, builder) -> {
			if (!Utils.isValid(spot)) {
				return null;
			}
			return builder.equal(root.get(Commentaire.SPOT), spot);
		};
	}

}
