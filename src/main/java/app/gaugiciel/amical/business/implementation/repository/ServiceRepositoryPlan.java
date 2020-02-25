package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.repository.PlanRepository;

@Service
public class ServiceRepositoryPlan implements Repository<Plan> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositoryPlan.class);

	@Autowired
	private PlanRepository planRepository;

	@Override
	public Plan enregistrer(Plan plan) {
		return planRepository.save(plan);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Plan plan) {

	}

}
