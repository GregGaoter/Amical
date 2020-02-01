package app.gaugiciel.amical.business.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceRecherche;
import app.gaugiciel.amical.model.LieuFrance;
import app.gaugiciel.amical.repository.LieuFranceRepository;
import app.gaugiciel.amical.repository.specification.LieuFranceSpecification;
import app.gaugiciel.amical.utilitaire.Utils;

@Service
public class ServiceRechercheLieuFrance implements ServiceRecherche<LieuFrance, Object> {

	@Autowired
	private LieuFranceRepository lieuFranceRepository;

	public LieuFrance findById(Long id) {
		return lieuFranceRepository.findById(id).orElseThrow();
	}

	public List<String> rechercherLieuFrance(String lieuFrance) {
		List<LieuFrance> listeLieuFrance = lieuFranceRepository
				.findAll(LieuFranceSpecification.lieuContaining(lieuFrance));
		return listeLieuFrance.stream().sequential().limit(Utils.AUTO_COMPLETE_MAX_RESULTS)
				.map(lieu -> lieu.afficherLieuComplet()).collect(Collectors.toList());
	}

	public LieuFrance findOne(String nomComplet) {
		String[] lieuFranceSplit = nomComplet.split(", ");
		LieuFrance lieuFrance = LieuFrance.creer(lieuFranceSplit[0], lieuFranceSplit[1], lieuFranceSplit[2],
				lieuFranceSplit[3]);
		return lieuFranceRepository.findOne(Example.of(lieuFrance)).orElseThrow();
	}

}