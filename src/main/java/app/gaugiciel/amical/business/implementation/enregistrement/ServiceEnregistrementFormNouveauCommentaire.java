package app.gaugiciel.amical.business.implementation.enregistrement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryCommentaire;
import app.gaugiciel.amical.controller.form.NouveauCommentaireForm;
import app.gaugiciel.amical.model.Commentaire;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormNouveauCommentaire implements Enregistrement<NouveauCommentaireForm> {

	@Autowired
	private ServiceRepositoryCommentaire serviceRepositoryCommentaire;
	@Getter
	@Setter
	private Commentaire commentaire;

	@Override
	public void enregistrer(NouveauCommentaireForm nouveauCommentaireForm) {
		commentaire = serviceRepositoryCommentaire.enregistrer(
				Commentaire.creer(nouveauCommentaireForm.getCommentaire(), nouveauCommentaireForm.getDate(),
						nouveauCommentaireForm.getUtilisateur(), nouveauCommentaireForm.getSpot()));
	}

}
