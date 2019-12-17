package app.gaugiciel.amical.business.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRecherche;
import app.gaugiciel.amical.business.utils.Utils;
import app.gaugiciel.amical.controller.form.SpotForm;
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

@Service
public class ServiceRechercheSpot implements ServiceRecherche<Spot, SpotForm> {

	@Autowired
	private SpotRepository spotRepository;
	@Autowired
	private LieuFranceRepository lieuFranceRepository;
	@Autowired
	private SecteurRepository secteurRepository;
	@Autowired
	private VoieRepository voieRepository;

	@Override
	public List<Spot> rechercher(SpotForm spotForm) {
		return spotRepository.findAll(SpotSpecification.hasAll(spotForm));
	}

	public List<String> rechercherNomSpot(String nomSpot) {
		List<Spot> listeSpots = spotRepository.findAll(SpotSpecification.nomContaining(nomSpot));
		return listeSpots.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS).map(spot -> spot.getNom())
				.collect(Collectors.toList());
	}

	public List<String> rechercherLieuSpot(String lieuSpot) {
		String lieuSpotNormalise = Utils.normaliser(lieuSpot);
		List<LieuFrance> listeLieuFrance = lieuFranceRepository
				.findAll(LieuFranceSpecification.lieuContaining(lieuSpot));
		return listeLieuFrance.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS)
				.flatMap(lieu -> lieu.getProprietes().stream())
				.filter(propriete -> Utils.normaliser(propriete).contains(lieuSpotNormalise)).distinct()
				.collect(Collectors.toList());
	}

	public List<String> rechercherNomSecteur(String nomSecteur) {
		List<Secteur> listeSecteur = secteurRepository.findAll(SecteurSpecification.nomContaining(nomSecteur));
		return listeSecteur.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS)
				.map(secteur -> secteur.getNom()).collect(Collectors.toList());
	}

	public List<String> rechercherNomVoie(String nomVoie) {
		List<Voie> listeVoie = voieRepository.findAll(VoieSpecification.nomContaining(nomVoie));
		return listeVoie.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS).map(voie -> voie.getNom())
				.collect(Collectors.toList());
	}

}
