package app.gaugiciel.amical.business.implementation.recherche;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.business.implementation.comparaison.ServiceComparaisonVoieByNumero;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.repository.VoieRepository;

@Service
public class ServiceRechercheVoie implements Recherche<Voie, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheVoie.class);

	@Autowired
	private VoieRepository voieRepository;
	@Autowired
	private ServiceComparaisonVoieByNumero serviceComparaisonVoieByNumero;

	public Voie findById(Long id) {
		return voieRepository.findById(id).orElseThrow();
	}

	public List<Voie> findBySecteurIdOrderByNumero(Long secteurId) {
		List<Voie> listeVoies = voieRepository.findBySecteur_id(secteurId);
		Collections.sort(listeVoies, serviceComparaisonVoieByNumero);
		return listeVoies;
	}

}
