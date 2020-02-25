package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryCommentaire;
import app.gaugiciel.amical.controller.form.EditionCommentaireForm;
import app.gaugiciel.amical.model.Commentaire;
import app.gaugiciel.amical.repository.CommentaireRepository;

@Service
public class ServiceEnregistrementFormEditionCommentaire implements Enregistrement<EditionCommentaireForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormEditionCommentaire.class);

	@Autowired
	private ServiceRepositoryCommentaire serviceRepositoryCommentaire;
	@Autowired
	private CommentaireRepository commentaireRepository;

	@Override
	public void enregistrer(EditionCommentaireForm editionCommentaireForm) {
		LOGGER.info("Start {}()", "enregistrer");
		Commentaire commentaire = commentaireRepository.findById(editionCommentaireForm.getId()).orElse(null);
		commentaire.setCommentaire(editionCommentaireForm.getCommentaire());
		serviceRepositoryCommentaire.enregistrer(commentaire);
	}

}
