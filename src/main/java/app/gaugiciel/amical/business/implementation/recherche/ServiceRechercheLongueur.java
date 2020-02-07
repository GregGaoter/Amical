package app.gaugiciel.amical.business.implementation.recherche;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.business.implementation.comparaison.ServiceComparaisonLongueurByNom;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.repository.LongueurRepository;

@Service
public class ServiceRechercheLongueur implements Recherche<Longueur, Object> {

	@Autowired
	private LongueurRepository longueurRepository;
	@Autowired
	private ServiceComparaisonLongueurByNom serviceComparaisonLongueurByNom;

	public List<Longueur> findByVoieIdOrderByNom(Long voieId) {
		List<Longueur> listeLongueur = longueurRepository.findByVoie_idOrderByNom(voieId);
		Collections.sort(listeLongueur, serviceComparaisonLongueurByNom);
		return listeLongueur;
	}

	public Longueur findById(Long id) {
		return longueurRepository.findById(id).orElseThrow();
	}

}
