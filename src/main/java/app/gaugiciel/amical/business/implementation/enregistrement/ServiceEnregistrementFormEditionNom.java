package app.gaugiciel.amical.business.implementation.enregistrement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryUtilisateur;
import app.gaugiciel.amical.controller.form.EditionNomForm;
import app.gaugiciel.amical.model.Utilisateur;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormEditionNom implements Enregistrement<EditionNomForm> {

	@Autowired
	private ServiceRepositoryUtilisateur serviceRepositoryUtilisateur;
	@Getter
	@Setter
	private Utilisateur utilisateur;

	@Override
	public void enregistrer(EditionNomForm editionNomForm) {
		utilisateur = serviceRepositoryUtilisateur.enregistrer(editionNomForm.getUtilisateur());
	}

}
