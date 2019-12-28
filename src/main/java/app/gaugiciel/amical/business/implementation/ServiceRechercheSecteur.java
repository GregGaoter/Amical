package app.gaugiciel.amical.business.implementation;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRecherche;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.repository.SecteurRepository;

@Service
public class ServiceRechercheSecteur implements ServiceRecherche<Secteur, Object> {

	@Autowired
	private SecteurRepository secteurRepository;
	@Autowired
	private ServiceComparaisonSecteurByNom serviceComparaisonSecteurByNom;

	public List<Secteur> findBySpotIdOrderByNom(Long spotId) {
		List<Secteur> listeSecteurs = secteurRepository.findBySpot_id(spotId);
		Collections.sort(listeSecteurs, serviceComparaisonSecteurByNom);
		return secteurRepository.findBySpot_id(spotId);
	}

	public Secteur findById(Long id) {
		return secteurRepository.findById(id).orElseThrow();
	}

}