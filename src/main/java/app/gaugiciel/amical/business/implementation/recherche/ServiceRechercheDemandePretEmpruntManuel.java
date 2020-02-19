package app.gaugiciel.amical.business.implementation.recherche;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.DemandePretEmpruntManuel;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.repository.DemandePretEmpruntManuelRepository;
import app.gaugiciel.amical.repository.specification.DemandePretEmpruntManuelSpecification;

@Service
public class ServiceRechercheDemandePretEmpruntManuel implements Recherche<DemandePretEmpruntManuel, Object> {

	@Autowired
	private DemandePretEmpruntManuelRepository demandePretEmpruntManuelRepository;

	public List<DemandePretEmpruntManuel> findByProprietaire(Authentification proprietaire) {
		return demandePretEmpruntManuelRepository
				.findAll(DemandePretEmpruntManuelSpecification.proprietaireEqual(proprietaire));
	}

	public List<DemandePretEmpruntManuel> findByDemandeur(Authentification demandeur) {
		return demandePretEmpruntManuelRepository
				.findAll(DemandePretEmpruntManuelSpecification.demandeurEqual(demandeur));
	}

	public DemandePretEmpruntManuel findByManuelId(Long manuelId) {
		return demandePretEmpruntManuelRepository.findOne(DemandePretEmpruntManuelSpecification.manuelIdEqual(manuelId))
				.orElse(null);
	}

	public boolean existsByManuel(Manuel manuel) {
		return demandePretEmpruntManuelRepository.findOne(DemandePretEmpruntManuelSpecification.manuelEqual(manuel))
				.isPresent();
	}

}
