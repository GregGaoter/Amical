package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.repository.LongueurRepository;

@Service
public class ServiceRepositoryLongueur implements Repository<Longueur> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositoryLongueur.class);

	@Autowired
	private LongueurRepository longueurRepository;

	@Override
	public Longueur enregistrer(Longueur longueur) {
		return longueurRepository.save(longueur);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Longueur longueur) {

	}

}
