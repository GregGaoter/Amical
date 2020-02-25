package app.gaugiciel.amical.business.implementation.enregistrement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteTertiaire;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryCotationFrance;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryVoie;
import app.gaugiciel.amical.controller.form.NouvelleVoieForm;
import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.repository.CotationFranceRepository;
import app.gaugiciel.amical.repository.PlanRepository;
import app.gaugiciel.amical.repository.specification.CotationFranceSpecification;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormNouvelleVoie implements Enregistrement<NouvelleVoieForm> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEnregistrementFormNouvelleVoie.class);

	@Autowired
	private ServiceRepositoryVoie serviceRepositoryVoie;
	@Autowired
	private ServiceRepositoryCotationFrance serviceRepositoryCotationFrance;
	@Autowired
	private CotationFranceRepository cotationFranceRepository;
	@Autowired
	private PlanRepository planRepository;
	@Getter
	@Setter
	private Voie voie;

	@Override
	public void enregistrer(NouvelleVoieForm nouvelleVoieForm) {
		LOGGER.info("Start {}()", "enregistrer");
		Plan plan = Utils.isValid(nouvelleVoieForm.getNomPlan())
				? planRepository.findByPlan(nouvelleVoieForm.getNomPlan())
				: null;
		String unitePrincipale = nouvelleVoieForm.getCotationUnitePrincipale();
		String uniteSecondaire = nouvelleVoieForm.getCotationUniteSecondaire();
		String uniteTertiaire = nouvelleVoieForm.getCotationUniteTertiaire();
		CotationFrance cotationFrance = Utils.isValid(unitePrincipale) ? cotationFranceRepository
				.findOne(CotationFranceSpecification.cotationEqual(
						ServiceCotationFrance.creer(CotationFranceUnitePrincipale.ofLabel(unitePrincipale),
								CotationFranceUniteSecondaire.ofLabel(uniteSecondaire),
								CotationFranceUniteTertiaire.ofLabel(uniteTertiaire))))
				.orElse(serviceRepositoryCotationFrance
						.enregistrer(CotationFrance.creer(unitePrincipale, uniteSecondaire, uniteTertiaire)))
				: null;
		voie = serviceRepositoryVoie.enregistrer(Voie.creer(nouvelleVoieForm.getNom(),
				nouvelleVoieForm.getDescription(), nouvelleVoieForm.getRemarque(), nouvelleVoieForm.getNumero(),
				nouvelleVoieForm.getHauteur(), nouvelleVoieForm.getSecteur(), plan, cotationFrance));
	}

}
