package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.repository.ManuelRepository;

@Service
public class ServiceRepositoryManuel implements Repository<Manuel> {

	@Autowired
	private ManuelRepository manuelRepository;

	@Override
	public Manuel enregistrer(Manuel manuel) {
		return manuelRepository.save(manuel);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Manuel manuel) {
		manuelRepository.delete(manuel);
	}

}
