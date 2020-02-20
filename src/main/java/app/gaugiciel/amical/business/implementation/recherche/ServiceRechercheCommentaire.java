package app.gaugiciel.amical.business.implementation.recherche;

import java.util.List;

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

	@Autowired
	private CommentaireRepository commentaireRepository;

	public List<Commentaire> findAllBySpot(Spot spot) {
		return commentaireRepository.findAll(CommentaireSpecification.spotEqual(spot),
				Sort.by(Sort.Direction.DESC, "date"));
	}

}
