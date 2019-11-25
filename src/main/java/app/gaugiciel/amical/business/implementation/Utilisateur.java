package app.gaugiciel.amical.business.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.model.Spot;

@Service
public abstract class Utilisateur {

	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;

	public List<Spot> rechercherSpots(Specification<Spot> specification) {
		return serviceRechercheSpot.rechercher(specification);
	}

}
