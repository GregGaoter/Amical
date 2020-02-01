package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRepository;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.repository.SecteurRepository;

@Service
public class ServiceRepositorySecteur implements ServiceRepository<Secteur> {

	@Autowired
	private SecteurRepository secteurRepository;

	@Override
	public Secteur enregistrer(Secteur secteur) {
		return secteurRepository.save(secteur);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(Secteur secteur) {

	}

}
