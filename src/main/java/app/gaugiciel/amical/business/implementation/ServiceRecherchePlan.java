package app.gaugiciel.amical.business.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRecherche;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.repository.PlanRepository;
import app.gaugiciel.amical.repository.specification.PlanSpecification;
import app.gaugiciel.amical.utilitaire.Utils;

@Service
public class ServiceRecherchePlan implements ServiceRecherche<Plan, Object> {

	@Autowired
	private PlanRepository planRepository;

	public Plan findOne(String plan) {
		Plan p = new Plan();
		p.setPlan(plan);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("description");
		return planRepository.findOne(Example.of(p, exampleMatcher)).orElseThrow();
	}

	public List<String> rechercherPlan(String template) {
		List<Plan> listePlans = planRepository.findAll(PlanSpecification.allContaining(template));
		return listePlans.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS).map(plan -> plan.getPlan())
				.collect(Collectors.toList());
	}

}
