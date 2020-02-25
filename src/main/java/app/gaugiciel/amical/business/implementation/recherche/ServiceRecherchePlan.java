package app.gaugiciel.amical.business.implementation.recherche;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.repository.PlanRepository;
import app.gaugiciel.amical.repository.specification.PlanSpecification;
import app.gaugiciel.amical.utilitaire.Utils;

@Service
public class ServiceRecherchePlan implements Recherche<Plan, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRecherchePlan.class);

	@Autowired
	private PlanRepository planRepository;

	public Plan findOne(String plan) {
		LOGGER.info("Start {}()", "findOne");
		Plan p = new Plan();
		p.setPlan(plan);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("description");
		return planRepository.findOne(Example.of(p, exampleMatcher)).orElse(null);
	}

	public List<String> rechercherPlan(String template) {
		LOGGER.info("Start {}()", "rechercherPlan");
		List<Plan> listePlans = planRepository.findAll(PlanSpecification.allContaining(template));
		return listePlans.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS).map(plan -> plan.getPlan())
				.collect(Collectors.toList());
	}

}
