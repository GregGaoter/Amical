package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRepository;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.repository.PlanRepository;

@Service
public class ServiceRepositoryPlan implements ServiceRepository<Plan> {

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
