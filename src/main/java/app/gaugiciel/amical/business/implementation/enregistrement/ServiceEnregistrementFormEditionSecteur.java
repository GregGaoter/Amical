package app.gaugiciel.amical.business.implementation.enregistrement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.gaugiciel.amical.business.contrat.ServiceEnregistrement;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositorySecteur;
import app.gaugiciel.amical.controller.form.EditionSecteurForm;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.repository.SecteurRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class ServiceEnregistrementFormEditionSecteur implements ServiceEnregistrement<EditionSecteurForm> {

	@Autowired
	private ServiceRepositorySecteur serviceRepositorySecteur;
	@Getter
	@Setter
	private Secteur secteur;
	@Autowired
	private SecteurRepository secteurRepository;

	@Override
	public void enregistrer(EditionSecteurForm editionSecteurForm) {
		secteur = secteurRepository.findById(editionSecteurForm.getId()).orElseThrow();
		editionSecteurForm.updateSecteur(secteur);
		serviceRepositorySecteur.enregistrer(secteur);
	}

}
