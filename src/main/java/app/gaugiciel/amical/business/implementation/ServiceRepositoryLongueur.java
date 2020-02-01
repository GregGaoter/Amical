package app.gaugiciel.amical.business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRepository;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.repository.LongueurRepository;

@Service
public class ServiceRepositoryLongueur implements ServiceRepository<Longueur> {

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
