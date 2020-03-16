package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheLieuFrance;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRecherchePlan;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositorySpot;
import app.gaugiciel.amical.controller.form.NouveauSpotForm;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Spot;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormAjoutSpot implements Enregistrement<NouveauSpotForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormAjoutSpot.class);

	@Autowired
	private ServiceRepositorySpot serviceRepositorySpot;
	@Getter
	@Setter
	private Spot spot;
	@Autowired
	private ServiceRechercheLieuFrance serviceRechercheLieuFrance;
	@Autowired
	private ServiceRecherchePlan serviceRecherchePlan;

	@Override
	public void enregistrer(NouveauSpotForm ajoutSpotForm) {
		LOGGER.info("Start {}()", "enregistrer");
		LieuFrance lieuFrance = serviceRechercheLieuFrance.findByNomComplet(ajoutSpotForm.getLieuFrance()).orElse(null);
		Plan plan = serviceRecherchePlan.findOne(ajoutSpotForm.getPlan());
		spot = Spot.creer(ajoutSpotForm.getNom(), false, lieuFrance);
		spot.setPlan(plan);
		spot.setDescription(ajoutSpotForm.getDescription());
		spot.setRemarque(ajoutSpotForm.getRemarque());
		serviceRepositorySpot.enregistrer(spot);
	}

}
