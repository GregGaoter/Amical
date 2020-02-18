package app.gaugiciel.amical.business.implementation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Repository;
import app.gaugiciel.amical.model.PretEmpruntManuel;
import app.gaugiciel.amical.repository.PretEmpruntManuelRepository;

@Service
public class ServiceRepositoryPretEmpruntManuel implements Repository<PretEmpruntManuel> {

	@Autowired
	private PretEmpruntManuelRepository pretEmpruntManuelRepository;

	@Override
	public PretEmpruntManuel enregistrer(PretEmpruntManuel pretEmpruntManuel) {
		return pretEmpruntManuelRepository.save(pretEmpruntManuel);
	}

	@Override
	public void modifier() {

	}

	@Override
	public void supprimer(PretEmpruntManuel pretEmpruntManuel) {
		pretEmpruntManuelRepository.delete(pretEmpruntManuel);
	}

}
