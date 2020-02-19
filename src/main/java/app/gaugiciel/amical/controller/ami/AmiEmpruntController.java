package app.gaugiciel.amical.controller.ami;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.gaugiciel.amical.business.implementation.enumeration.EtatManuel;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheDemandePretEmpruntManuel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheManuel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRecherchePretEmpruntManuel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheUtilisateur;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryDemandePretEmpruntManuel;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryManuel;
import app.gaugiciel.amical.controller.data.AmiManuelEmpruntData;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.DemandePretEmpruntManuel;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.PretEmpruntManuel;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
@ControllerAdvice
public class AmiEmpruntController {

	@Autowired
	private HttpSession session;
	@Autowired
	private ServiceRechercheUtilisateur serviceRechercheUtilisateur;
	@Autowired
	private ServiceRechercheDemandePretEmpruntManuel serviceRechercheDemandePretEmpruntManuel;
	@Autowired
	private ServiceRecherchePretEmpruntManuel serviceRecherchePretEmpruntManuel;
	@Autowired
	private ServiceRepositoryDemandePretEmpruntManuel serviceRepositoryDemandePretEmpruntManuel;
	@Autowired
	private ServiceRechercheManuel serviceRechercheManuel;
	@Autowired
	private ServiceRepositoryManuel serviceRepositoryManuel;

	@GetMapping("/ami/topo/emprunts")
	public String showEmprunts(Model model) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		Authentification authentification = utilisateur.getAuthentification();
		List<DemandePretEmpruntManuel> listeDemandesEmpruntsManuels = serviceRechercheDemandePretEmpruntManuel
				.findByDemandeur(authentification);
		List<PretEmpruntManuel> listeEmpruntsManuels = serviceRecherchePretEmpruntManuel
				.findByEmprunteur(authentification);
		int nbDemandesEmpruntsManuels = listeDemandesEmpruntsManuels.size();
		int nbEmpruntsManuels = listeEmpruntsManuels.size();
		List<AmiManuelEmpruntData> listeAmiManuelEmpruntDataDemande = new ArrayList<>(nbDemandesEmpruntsManuels);
		List<AmiManuelEmpruntData> listeAmiManuelEmpruntDataEnCours = new ArrayList<>(nbEmpruntsManuels);
		AmiManuelEmpruntData amiManuelEmpruntData = new AmiManuelEmpruntData();
		for (DemandePretEmpruntManuel d : listeDemandesEmpruntsManuels) {
			Long idTopoDemandeEmprunt = d.getManuel().getId();
			String nomTopoDemandeEmprunt = d.getManuel().getNom();
			String dateDemandePret = d.getDate().toString().split(" ")[0];
			listeAmiManuelEmpruntDataDemande.add(amiManuelEmpruntData.getDemandeEmpruntData(idTopoDemandeEmprunt,
					nomTopoDemandeEmprunt, dateDemandePret));
		}
		for (PretEmpruntManuel d : listeEmpruntsManuels) {
			Long idTopoEmpruntEnCours = d.getManuel().getId();
			String nomTopoEmpruntEnCours = d.getManuel().getNom();
			String emailProprietaire = d.getPreteur().getEmail();
			Utilisateur proprietaire = serviceRechercheUtilisateur.findByEmail(emailProprietaire);
			String prenomProprietaire = proprietaire.getPrenom();
			String nomProprietaire = proprietaire.getNom();
			String dateEmprunt = d.getDate().toString().split(" ")[0];
			listeAmiManuelEmpruntDataEnCours.add(amiManuelEmpruntData.getEmpruntEnCoursData(idTopoEmpruntEnCours,
					nomTopoEmpruntEnCours, prenomProprietaire, nomProprietaire, emailProprietaire, dateEmprunt));
		}
		String urlRedirection = "redirect:/ami/topo/emprunts";
		model.addAttribute("listeAmiManuelEmpruntDataDemande", listeAmiManuelEmpruntDataDemande);
		model.addAttribute("listeAmiManuelEmpruntDataEnCours", listeAmiManuelEmpruntDataEnCours);
		model.addAttribute("nbDemandesEmpruntsManuels", nbDemandesEmpruntsManuels);
		model.addAttribute("nbEmpruntsManuels", nbEmpruntsManuels);
		model.addAttribute("topoActive", "active");
		session.setAttribute(RedirectionUrl.EMPRUNTS.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_manuel_emprunt";
	}

	@GetMapping("/ami/topo/{topoId}/emprunt/annuler")
	public String annulerEmprunt(@PathVariable long topoId, Model model) {
		DemandePretEmpruntManuel demandeEmpruntManuel = serviceRechercheDemandePretEmpruntManuel.findByManuelId(topoId);
		serviceRepositoryDemandePretEmpruntManuel.supprimer(demandeEmpruntManuel);
		Manuel topo = serviceRechercheManuel.findById(topoId);
		topo.setEtat(EtatManuel.DISPONIBLE.label);
		serviceRepositoryManuel.enregistrer(topo);
		return (String) session.getAttribute(RedirectionUrl.PRETS.label);
	}

	@PostMapping("/ami/topo/{topoId}/demandeEmprunt")
	public String demanderEmprunt(@PathVariable long topoId, Model model) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		Authentification authentification = utilisateur.getAuthentification();
		Manuel topo = serviceRechercheManuel.findById(topoId);
		serviceRepositoryDemandePretEmpruntManuel.enregistrer(DemandePretEmpruntManuel.creer(authentification,
				topo.getAuthentification(), topo, Timestamp.from(Instant.now())));
		topo.setEtat(EtatManuel.INDISPONIBLE.label);
		serviceRepositoryManuel.enregistrer(topo);
		return "redirect:/ami/topo/emprunts";
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		if (utilisateur != null) {
			List<DemandePretEmpruntManuel> listeDemandesEmpruntsManuels = serviceRechercheDemandePretEmpruntManuel
					.findByDemandeur(utilisateur.getAuthentification());
			List<PretEmpruntManuel> listeEmpruntsManuels = serviceRecherchePretEmpruntManuel
					.findByEmprunteur(utilisateur.getAuthentification());
			model.addAttribute(NomModel.UTILISATEUR.label, utilisateur);
			model.addAttribute("nbDemandesEmpruntsManuels", listeDemandesEmpruntsManuels.size());
			model.addAttribute("nbEmpruntsManuels", listeEmpruntsManuels.size());
		}
	}

}
