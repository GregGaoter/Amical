package app.gaugiciel.amical.business.implementation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.repository.SecteurRepository;

@Service
public class ServiceRepositorySecteur implements Repository<Secteur> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRepositorySecteur.class);

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
