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
import app.gaugiciel.amical.controller.form.RechercheTopoForm;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.Utilisateur;
import app.gaugiciel.amical.repository.ManuelRepository;
import app.gaugiciel.amical.repository.UtilisateurRepository;
import app.gaugiciel.amical.repository.specification.ManuelSpecification;
import app.gaugiciel.amical.repository.specification.UtilisateurSpecification;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NonNull;

@Service
public class ServiceRechercheTopo implements Recherche<Manuel, RechercheTopoForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRechercheTopo.class);

	@Autowired
	private ManuelRepository manuelRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	public List<String> rechercherNomManuel(String nomManuel) {
		List<Manuel> listeManuels = manuelRepository.findAll(ManuelSpecification.nomContaining(nomManuel));
		return listeManuels.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS).map(manuel -> manuel.getNom())
				.collect(Collectors.toList());
	}

	public List<String> rechercherNomAuteur(String nomAuteur) {
		List<Manuel> listeManuels = manuelRepository.findAll(ManuelSpecification.auteurContaining(nomAuteur));
		return listeManuels.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS)
				.map(manuel -> manuel.getAuteur()).collect(Collectors.toList());
	}

	public List<String> rechercherProprietaire(String proprietaire) {
		String proprietaireNormalise = Utils.normaliser(proprietaire);
		List<Utilisateur> listeUtilisateurs = utilisateurRepository
				.findAll(UtilisateurSpecification.proprietesContaining(proprietaire));
		return listeUtilisateurs.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS)
				.flatMap(utilisateur -> utilisateur.getProprietes().stream())
				.filter(propriete -> Utils.normaliser(propriete).contains(proprietaireNormalise)).distinct()
				.collect(Collectors.toList());
	}

	public Page<Manuel> rechercher(RechercheTopoForm rechercheTopoForm, Pageable pageable) {
		return manuelRepository.findAll(ManuelSpecification.hasAll(rechercheTopoForm), pageable);
	}

	public Manuel findById(long manuelId) {
		return manuelRepository.findById(manuelId).orElse(null);
	}

	public long countProprietaire(@NonNull Authentification proprietaire) {
		return manuelRepository.count(ManuelSpecification.proprietaireEqual(proprietaire));
	}

	public List<Manuel> findByAuthentification(Authentification proprietaire) {
		return manuelRepository.findAll(ManuelSpecification.proprietaireEqual(proprietaire));
	}

}
