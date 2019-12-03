package app.gaugiciel.amical.business.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRecherche;
import app.gaugiciel.amical.controller.form.SpotForm;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.repository.SpotRepository;
import app.gaugiciel.amical.repository.specification.SpotSpecification;

@Service
public class ServiceRechercheSpot implements ServiceRecherche<Spot, SpotForm> {

	@Autowired
	private SpotRepository spotRepository;

	@Override
	public List<Spot> rechercher(SpotForm spotForm) {
		return spotRepository.findAll(SpotSpecification.hasAll(spotForm));
	}

}
