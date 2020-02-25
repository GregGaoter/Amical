package app.gaugiciel.amical.business.implementation.recherche;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.PretEmpruntManuel;
import app.gaugiciel.amical.repository.PretEmpruntManuelRepository;
import app.gaugiciel.amical.repository.specification.PretEmpruntManuelSpecification;

@Service
public class ServiceRecherchePretEmpruntManuel implements Recherche<PretEmpruntManuel, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRecherchePretEmpruntManuel.class);

	@Autowired
	private PretEmpruntManuelRepository pretEmpruntManuelRepository;

	public List<PretEmpruntManuel> findByPreteur(Authentification preteur) {
		return pretEmpruntManuelRepository.findAll(PretEmpruntManuelSpecification.preteurEqual(preteur));
	}

	public List<PretEmpruntManuel> findByEmprunteur(Authentification emprunteur) {
		return pretEmpruntManuelRepository.findAll(PretEmpruntManuelSpecification.emprunteurEqual(emprunteur));
	}

	public boolean existsByManuel(Manuel manuel) {
		return pretEmpruntManuelRepository.findOne(PretEmpruntManuelSpecification.manuelEqual(manuel)).isPresent();
	}

	public PretEmpruntManuel findByManuel(Manuel manuel) {
		return pretEmpruntManuelRepository.findOne(PretEmpruntManuelSpecification.manuelEqual(manuel)).orElse(null);
	}

}
