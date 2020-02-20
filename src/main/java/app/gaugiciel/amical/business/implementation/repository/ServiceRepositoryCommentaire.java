package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Commentaire;
import app.gaugiciel.amical.repository.CommentaireRepository;

@Service
public class ServiceRepositoryCommentaire implements Repository<Commentaire> {

	@Autowired
	private CommentaireRepository commentaireRepository;

	@Override
	public Commentaire enregistrer(Commentaire commentaire) {
		return commentaireRepository.save(commentaire);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Commentaire commentaire) {
		commentaireRepository.delete(commentaire);
	}

}
