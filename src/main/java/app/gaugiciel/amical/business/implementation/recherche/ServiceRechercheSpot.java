package app.gaugiciel.amical.business.implementation.recherche;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Recherche;
import app.gaugiciel.amical.controller.form.RechercheSpotForm;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.repository.LieuFranceRepository;
import app.gaugiciel.amical.repository.SecteurRepository;
import app.gaugiciel.amical.repository.SpotRepository;
import app.gaugiciel.amical.repository.VoieRepository;
import app.gaugiciel.amical.repository.specification.LieuFranceSpecification;
import app.gaugiciel.amical.repository.specification.SecteurSpecification;
import app.gaugiciel.amical.repository.specification.SpotSpecification;
import app.gaugiciel.amical.repository.specification.VoieSpecification;
import app.gaugiciel.amical.utilitaire.Utils;

@Service
public class ServiceRechercheSpot implements Recherche<Spot, RechercheSpotForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheSpot.class);

	@Autowired
	private SpotRepository spotRepository;
	@Autowired
	private LieuFranceRepository lieuFranceRepository;
	@Autowired
	private SecteurRepository secteurRepository;
	@Autowired
	private VoieRepository voieRepository;

	@Override
	public List<Spot> rechercher(RechercheSpotForm spotForm) {
		LOGGER.info("Start {}()", "rechercher");
		return spotRepository.findAll(SpotSpecification.hasAll(spotForm));
	}

	public Page<Spot> rechercher(RechercheSpotForm spotForm, Pageable pageable) {
		LOGGER.info("Start {}()", "rechercher");
		return spotRepository.findAll(SpotSpecification.hasAll(spotForm), pageable);
	}

	public Spot findById(Long id) {
		LOGGER.info("Start {}()", "findById");
		return spotRepository.findById(id).orElseThrow();
	}

	public List<String> rechercherNomSpot(String nomSpot) {
		LOGGER.info("Start {}()", "rechercherNomSpot");
		List<Spot> listeSpots = spotRepository.findAll(SpotSpecification.nomContaining(nomSpot));
		return listeSpots.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS).map(spot -> spot.getNom())
				.collect(Collectors.toList());
	}

	public List<String> rechercherLieuSpot(String lieuSpot) {
		LOGGER.info("Start {}()", "rechercherLieuSpot");
		String lieuSpotNormalise = Utils.normaliser(lieuSpot);
		List<LieuFrance> listeLieuFrance = lieuFranceRepository
				.findAll(LieuFranceSpecification.lieuContaining(lieuSpot));
		return listeLieuFrance.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS)
				.flatMap(lieu -> lieu.getProprietes().stream())
				.filter(propriete -> Utils.normaliser(propriete).contains(lieuSpotNormalise)).distinct()
				.collect(Collectors.toList());
	}

	public List<String> rechercherNomSecteur(String nomSecteur) {
		LOGGER.info("Start {}()", "rechercherNomSecteur");
		List<Secteur> listeSecteur = secteurRepository.findAll(SecteurSpecification.nomContaining(nomSecteur));
		return listeSecteur.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS)
				.map(secteur -> secteur.getNom()).collect(Collectors.toList());
	}

	public List<String> rechercherNomVoie(String nomVoie) {
		LOGGER.info("Start {}()", "rechercherNomVoie");
		List<Voie> listeVoie = voieRepository.findAll(VoieSpecification.nomContaining(nomVoie));
		return listeVoie.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS).map(voie -> voie.getNom())
				.collect(Collectors.toList());
	}

}
