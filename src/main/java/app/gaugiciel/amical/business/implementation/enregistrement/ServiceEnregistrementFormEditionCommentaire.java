package app.gaugiciel.amical.business.implementation.enregistrement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryCommentaire;
import app.gaugiciel.amical.controller.form.EditionCommentaireForm;
import app.gaugiciel.amical.model.Commentaire;
import app.gaugiciel.amical.repository.CommentaireRepository;

@Service
public class ServiceEnregistrementFormEditionCommentaire implements Enregistrement<EditionCommentaireForm> {

	@Autowired
	private ServiceRepositoryCommentaire serviceRepositoryCommentaire;
	@Autowired
	private CommentaireRepository commentaireRepository;

	@Override
	public void enregistrer(EditionCommentaireForm editionCommentaireForm) {
		Commentaire commentaire = commentaireRepository.findById(editionCommentaireForm.getId()).orElse(null);
		commentaire.setCommentaire(editionCommentaireForm.getCommentaire());
		serviceRepositoryCommentaire.enregistrer(commentaire);
	}

}
