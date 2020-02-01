package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRepository;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.repository.VoieRepository;

@Service
public class ServiceRepositoryVoie implements ServiceRepository<Voie> {

	@Autowired
	private VoieRepository voieRepository;

	@Override
	public Voie enregistrer(Voie voie) {
		return voieRepository.save(voie);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Voie voie) {

	}

}
