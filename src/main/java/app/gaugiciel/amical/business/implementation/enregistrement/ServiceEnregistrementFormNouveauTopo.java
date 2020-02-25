package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryManuel;
import app.gaugiciel.amical.controller.form.NouveauTopoForm;
import app.gaugiciel.amical.model.Manuel;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormNouveauTopo implements Enregistrement<NouveauTopoForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormNouveauTopo.class);

	@Autowired
	private ServiceRepositoryManuel serviceRepositoryManuel;
	@Getter
	@Setter
	private Manuel manuel;

	@Override
	public void enregistrer(NouveauTopoForm nouveauTopoForm) {
		LOGGER.info("Start {}()", "enregistrer");
		manuel = serviceRepositoryManuel.enregistrer(Manuel.creer(nouveauTopoForm.getNom(),
				nouveauTopoForm.getDateTimeParution(), nouveauTopoForm.getAuteur(), nouveauTopoForm.getDescription(),
				nouveauTopoForm.getRemarque(), nouveauTopoForm.getEtat(), nouveauTopoForm.getCategorie(),
				nouveauTopoForm.getAuthentification(), nouveauTopoForm.getLieuFrance()));
	}

}
