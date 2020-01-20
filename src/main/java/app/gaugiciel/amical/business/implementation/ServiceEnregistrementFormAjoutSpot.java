package app.gaugiciel.amical.business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceEnregistrement;
import app.gaugiciel.amical.controller.form.AjoutSpotForm;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Spot;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormAjoutSpot implements ServiceEnregistrement<AjoutSpotForm> {

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
	public void enregistrer(AjoutSpotForm ajoutSpotForm) {
		LieuFrance lieuFrance = serviceRechercheLieuFrance.findOne(ajoutSpotForm.getLieuFrance());
		Plan plan = serviceRecherchePlan.findOne(ajoutSpotForm.getPlan());
		Spot spotForm = Spot.creer(ajoutSpotForm.getNom(), false, lieuFrance);
		spotForm.setPlan(plan);
		spotForm.setDescription(ajoutSpotForm.getDescription());
		spotForm.setRemarque(ajoutSpotForm.getRemarque());
		spot = serviceRepositorySpot.enregistrer(Spot.creer(ajoutSpotForm.getNom(), false, lieuFrance));
	}

}
