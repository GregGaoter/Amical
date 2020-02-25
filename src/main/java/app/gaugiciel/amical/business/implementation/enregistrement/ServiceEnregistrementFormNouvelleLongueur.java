package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryLongueur;
import app.gaugiciel.amical.controller.form.NouvelleLongueurForm;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.repository.PlanRepository;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormNouvelleLongueur implements Enregistrement<NouvelleLongueurForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormNouvelleLongueur.class);

	@Autowired
	private ServiceRepositoryLongueur serviceRepositoryLongueur;
	@Autowired
	private PlanRepository planRepository;
	@Getter
	@Setter
	private Longueur longueur;

	@Override
	public void enregistrer(NouvelleLongueurForm nouvelleLongueurForm) {
		LOGGER.info("Start {}()", "enregistrer");
		Plan plan = Utils.isValid(nouvelleLongueurForm.getNomPlan())
				? planRepository.findByPlan(nouvelleLongueurForm.getNomPlan())
				: null;
		longueur = serviceRepositoryLongueur
				.enregistrer(Longueur.creer(nouvelleLongueurForm.getNom(), nouvelleLongueurForm.getDescription(),
						nouvelleLongueurForm.getRemarque(), nouvelleLongueurForm.getLongueur(),
						nouvelleLongueurForm.getNbSpits(), nouvelleLongueurForm.getVoie(), plan));
	}

}
