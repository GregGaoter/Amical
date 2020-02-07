package app.gaugiciel.amical.business.implementation.enregistrement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.Enregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositorySecteur;
import app.gaugiciel.amical.controller.form.NouveauSecteurForm;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.repository.PlanRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormNouveauSecteur implements Enregistrement<NouveauSecteurForm> {

	@Autowired
	private ServiceRepositorySecteur serviceRepositorySecteur;
	@Autowired
	private PlanRepository planRepository;
	@Getter
	@Setter
	private Secteur secteur;

	@Override
	public void enregistrer(NouveauSecteurForm nouveauSecteurForm) {
		Plan plan = planRepository.findByPlan(nouveauSecteurForm.getNomPlan());
		secteur = serviceRepositorySecteur
				.enregistrer(Secteur.creer(nouveauSecteurForm.getNom(), nouveauSecteurForm.getDescription(),
						nouveauSecteurForm.getRemarque(), nouveauSecteurForm.getSpot(), plan));
	}

}
