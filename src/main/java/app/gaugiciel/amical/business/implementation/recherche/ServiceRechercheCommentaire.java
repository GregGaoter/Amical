package app.gaugiciel.amical.business.implementation.recherche;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.model.Commentaire;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.repository.CommentaireRepository;
import app.gaugiciel.amical.repository.specification.CommentaireSpecification;

@Service
public class ServiceRechercheCommentaire implements Recherche<Commentaire, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheCommentaire.class);

	@Autowired
	private CommentaireRepository commentaireRepository;

	public List<Commentaire> findAllBySpot(Spot spot) {
		LOGGER.info("Start {}()", "findAllBySpot");
		return commentaireRepository.findAll(CommentaireSpecification.spotEqual(spot),
				Sort.by(Sort.Direction.DESC, "date"));
	}

	public Commentaire findById(Long commentaireId) {
		LOGGER.info("Start {}()", "findById");
		return commentaireRepository.findById(commentaireId).orElse(null);
	}

}
