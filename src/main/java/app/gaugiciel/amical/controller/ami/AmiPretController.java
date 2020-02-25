package app.gaugiciel.amical.controller.ami;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import app.gaugiciel.amical.business.implementation.enumeration.EtatManuel;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheDemandePretEmpruntManuel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheManuel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRecherchePretEmpruntManuel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheUtilisateur;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryDemandePretEmpruntManuel;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryManuel;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryPretEmpruntManuel;
import app.gaugiciel.amical.controller.data.AmiManuelPretData;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.DemandePretEmpruntManuel;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.PretEmpruntManuel;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
@ControllerAdvice
public class AmiPretController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AmiPretController.class);

	@Autowired
	private HttpSession session;
	@Autowired
	private ServiceRechercheUtilisateur serviceRechercheUtilisateur;
	@Autowired
	private ServiceRechercheDemandePretEmpruntManuel serviceRechercheDemandePretEmpruntManuel;
	@Autowired
	private ServiceRecherchePretEmpruntManuel serviceRecherchePretEmpruntManuel;
	@Autowired
	private ServiceRepositoryPretEmpruntManuel serviceRepositoryPretEmpruntManuel;
	@Autowired
	private ServiceRepositoryDemandePretEmpruntManuel serviceRepositoryDemandePretEmpruntManuel;
	@Autowired
	private ServiceRechercheManuel serviceRechercheManuel;
	@Autowired
	private ServiceRepositoryManuel serviceRepositoryManuel;

	@GetMapping("/ami/topo/prets")
	public String showPrets(Model model) {
		LOGGER.info("Start {}()", "showPrets");
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		Authentification authentification = utilisateur.getAuthentification();
		List<DemandePretEmpruntManuel> listeDemandesPretsManuels = serviceRechercheDemandePretEmpruntManuel
				.findByProprietaire(authentification);
		List<PretEmpruntManuel> listePretsManuels = serviceRecherchePretEmpruntManuel.findByPreteur(authentification);
		int nbDemandesPretsManuels = listeDemandesPretsManuels.size();
		int nbPretsManuels = listePretsManuels.size();
		List<AmiManuelPretData> listeAmiManuelPretDataDemande = new ArrayList<>(nbDemandesPretsManuels);
		List<AmiManuelPretData> listeAmiManuelPretDataEnCours = new ArrayList<>(nbPretsManuels);
		AmiManuelPretData amiManuelPretData = new AmiManuelPretData();
		for (DemandePretEmpruntManuel d : listeDemandesPretsManuels) {
			Long idTopoDemandePret = d.getManuel().getId();
			String nomTopoDemandePret = d.getManuel().getNom();
			String emailDemandeurPret = d.getDemandeur().getEmail();
			Utilisateur demandeurPret = serviceRechercheUtilisateur.findByEmail(emailDemandeurPret);
			String prenomDemandeurPret = demandeurPret.getPrenom();
			String nomDemandeurPret = demandeurPret.getNom();
			String dateDemandePret = d.getDate().toString().split(" ")[0];
			listeAmiManuelPretDataDemande.add(amiManuelPretData.getDemandePretData(idTopoDemandePret,
					nomTopoDemandePret, prenomDemandeurPret, nomDemandeurPret, emailDemandeurPret, dateDemandePret));
		}
		for (PretEmpruntManuel d : listePretsManuels) {
			Long idTopoPretEnCours = d.getManuel().getId();
			String nomTopoPretEnCours = d.getManuel().getNom();
			String emailEmprunteur = d.getEmprunteur().getEmail();
			Utilisateur emprunteur = serviceRechercheUtilisateur.findByEmail(emailEmprunteur);
			String prenomEmprunteur = emprunteur.getPrenom();
			String nomEmprunteur = emprunteur.getNom();
			String dateEmprunt = d.getDate().toString().split(" ")[0];
			listeAmiManuelPretDataEnCours.add(amiManuelPretData.getPretEnCoursData(idTopoPretEnCours,
					nomTopoPretEnCours, prenomEmprunteur, nomEmprunteur, emailEmprunteur, dateEmprunt));
		}
		String urlRedirection = "redirect:/ami/topo/prets";
		model.addAttribute("listeAmiManuelPretDataDemande", listeAmiManuelPretDataDemande);
		model.addAttribute("listeAmiManuelPretDataEnCours", listeAmiManuelPretDataEnCours);
		model.addAttribute("nbDemandesPretsManuels", nbDemandesPretsManuels);
		model.addAttribute("nbPretsManuels", nbPretsManuels);
		model.addAttribute("topoActive", "active");
		session.setAttribute(RedirectionUrl.PRETS.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_manuel_pret";
	}

	@GetMapping("/ami/topo/{topoId}/pret/accepte")
	public String acceptPret(@PathVariable long topoId, Model model) {
		LOGGER.info("Start {}()", "acceptPret");
		DemandePretEmpruntManuel demandePretManuel = serviceRechercheDemandePretEmpruntManuel.findByManuelId(topoId);
		serviceRepositoryPretEmpruntManuel.enregistrer(PretEmpruntManuel.creer(demandePretManuel.getProprietaire(),
				demandePretManuel.getDemandeur(), demandePretManuel.getManuel(), Timestamp.from(Instant.now())));
		serviceRepositoryDemandePretEmpruntManuel.supprimer(demandePretManuel);
		Manuel topo = serviceRechercheManuel.findById(topoId);
		topo.setEtat(EtatManuel.INDISPONIBLE.label);
		serviceRepositoryManuel.enregistrer(topo);
		return (String) session.getAttribute(RedirectionUrl.PRETS.label);
	}

	@GetMapping("/ami/topo/{topoId}/pret/refuse")
	public String refusePret(@PathVariable long topoId, Model model) {
		LOGGER.info("Start {}()", "refusePret");
		DemandePretEmpruntManuel demandePretManuel = serviceRechercheDemandePretEmpruntManuel.findByManuelId(topoId);
		serviceRepositoryDemandePretEmpruntManuel.supprimer(demandePretManuel);
		return (String) session.getAttribute(RedirectionUrl.PRETS.label);
	}

	@GetMapping("/ami/topo/{topoId}/pret/retour")
	public String retourPret(@PathVariable long topoId, Model model) {
		LOGGER.info("Start {}()", "retourPret");
		Manuel topo = serviceRechercheManuel.findById(topoId);
		PretEmpruntManuel pretManuel = serviceRecherchePretEmpruntManuel.findByManuel(topo);
		serviceRepositoryPretEmpruntManuel.supprimer(pretManuel);
		topo.setEtat(EtatManuel.DISPONIBLE.label);
		serviceRepositoryManuel.enregistrer(topo);
		return (String) session.getAttribute(RedirectionUrl.PRETS.label);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		if (utilisateur != null) {
			List<DemandePretEmpruntManuel> listeDemandesPretsManuels = serviceRechercheDemandePretEmpruntManuel
					.findByProprietaire(utilisateur.getAuthentification());
			List<PretEmpruntManuel> listePretsManuels = serviceRecherchePretEmpruntManuel
					.findByPreteur(utilisateur.getAuthentification());
			model.addAttribute(NomModel.UTILISATEUR.label, utilisateur);
			model.addAttribute("nbDemandesPretsManuels", listeDemandesPretsManuels.size());
			model.addAttribute("nbPretsManuels", listePretsManuels.size());
		}
	}

}
