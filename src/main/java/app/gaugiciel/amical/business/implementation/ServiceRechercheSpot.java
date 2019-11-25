package app.gaugiciel.amical.business.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRecherche;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.repository.SpotRepository;

@Service
public class ServiceRechercheSpot implements ServiceRecherche<Spot> {

	@Autowired
	private SpotRepository spotRepository;

	@Override
	public List<Spot> rechercher(Specification<Spot> specification) {
		return spotRepository.findAll(specification);
	}

}
