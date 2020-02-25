package app.gaugiciel.amical.business.implementation.recherche;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.business.implementation.comparaison.ServiceComparaisonSecteurByNom;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.repository.SecteurRepository;

@Service
public class ServiceRechercheSecteur implements Recherche<Secteur, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheSecteur.class);

	@Autowired
	private SecteurRepository secteurRepository;
	@Autowired
	private ServiceComparaisonSecteurByNom serviceComparaisonSecteurByNom;

	public List<Secteur> findBySpotIdOrderByNom(Long spotId) {
		List<Secteur> listeSecteurs = secteurRepository.findBySpot_id(spotId);
		Collections.sort(listeSecteurs, serviceComparaisonSecteurByNom);
		return listeSecteurs;
	}

	public Secteur findById(Long id) {
		return secteurRepository.findById(id).orElseThrow();
	}

}
