package app.gaugiciel.amical.controller.ami;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormEditionVoie;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormNouvelleVoie;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteTertiaire;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheLongueur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSecteur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheVoie;
import app.gaugiciel.amical.business.implementation.stockage.ServiceStockagePlan;
import app.gaugiciel.amical.controller.form.EditionVoieForm;
import app.gaugiciel.amical.controller.form.NouvelleVoieForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormEditionVoie;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormNouvelleVoie;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Voie;

@Controller
@ControllerAdvice
public class AmiVoieController {

	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ServiceRechercheSecteur serviceRechercheSecteur;
	@Autowired
	private ValidationFormNouvelleVoie validationFormNouvelleVoie;
	@Autowired
	private ServiceEnregistrementFormNouvelleVoie serviceEnregistrementFormNouvelleVoie;
	@Autowired
	private ServiceRechercheVoie serviceRechercheVoie;
	@Autowired
	private ServiceRechercheLongueur serviceRechercheLongueur;
	@Autowired
	private ValidationFormEditionVoie validationFormEditionVoie;
	@Autowired
	private ServiceEnregistrementFormEditionVoie serviceEnregistrementFormEditionVoie;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private HttpSession session;

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/nouveau")
	public String showNouvelleVoieForm(@PathVariable Long spotId, @PathVariable Long secteurId, Model model,
			HttpServletRequest request) {
		NouvelleVoieForm nouvelleVoieForm = new NouvelleVoieForm();
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			Plan plan = (Plan) inputFlashMap.get("plan");
			model.addAttribute("messagePlanEnregistreAvecSucces", messageSource.getMessage(
					"message.planEnregistreAvecSucces", new String[] { plan.getPlan() }, Locale.getDefault()));
			nouvelleVoieForm.setPlan(plan);
		}
		Spot spot = serviceRechercheSpot.findById(spotId);
		nouvelleVoieForm.setNomSpot(spot.getNom());
		Secteur secteur = serviceRechercheSecteur.findById(secteurId);
		nouvelleVoieForm.setSecteur(secteur);
		nouvelleVoieForm.setNomSecteur(secteur.getNom());
		model.addAttribute("nouvelleVoieForm", nouvelleVoieForm);
		model.addAttribute("spot", spot);
		model.addAttribute("secteur", secteur);
		model.addAttribute("spotActive", "active");
		session.setAttribute(RedirectionUrl.VOIE_FORM.label,
				"redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/nouveau");
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label,
				"redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/nouveau");
		return "ami_voie_nouvelle";
	}

	@PostMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/nouveau")
	public String checkNouvelleVoieForm(@Valid NouvelleVoieForm nouvelleVoieForm, @PathVariable Long spotId,
			@PathVariable Long secteurId, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (!validationFormNouvelleVoie.isValide(nouvelleVoieForm)) {
			validationFormNouvelleVoie.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			Spot spot = serviceRechercheSpot.findById(spotId);
			Secteur secteur = serviceRechercheSecteur.findById(secteurId);
			model.addAttribute("spot", spot);
			model.addAttribute("secteur", secteur);
			model.addAttribute("spotActive", "active");
			return "ami_voie_nouvelle";
		}
		redirectAttributes.addFlashAttribute("nouvelleVoieForm", nouvelleVoieForm);
		return "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/nouveau/enregistrement";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/nouveau/enregistrement")
	public String saveNouvelleVoieForm(@PathVariable Long spotId, @PathVariable Long secteurId,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			NouvelleVoieForm nouvelleVoieForm = (NouvelleVoieForm) inputFlashMap.get("nouvelleVoieForm");
			Secteur secteur = serviceRechercheSecteur.findById(secteurId);
			nouvelleVoieForm.setSecteur(secteur);
			serviceEnregistrementFormNouvelleVoie.enregistrer(nouvelleVoieForm);
			redirectAttributes.addFlashAttribute("voie", serviceEnregistrementFormNouvelleVoie.getVoie());
		}
		return (String) session.getAttribute(RedirectionUrl.SPOT.label);
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}")
	public String showVoie(@PathVariable Long spotId, @PathVariable Long secteurId, @PathVariable Long voieId,
			Integer page, Integer size, Model model, HttpServletRequest request) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("longueur")) {
				Longueur longueur = (Longueur) inputFlashMap.get("longueur");
				model.addAttribute("messageLongueurEnregistreAvecSucces",
						messageSource.getMessage("message.LongueurEnregistreAvecSucces",
								new String[] { longueur.getNom() }, Locale.getDefault()));
			}
			if (inputFlashMap.containsKey("voie")) {
				Voie voie = (Voie) inputFlashMap.get("voie");
				model.addAttribute("messageVoieEnregistreAvecSucces", messageSource.getMessage(
						"message.voieEnregistreAvecSucces", new String[] { voie.getNom() }, Locale.getDefault()));
			}
		}
		Voie voie = serviceRechercheVoie.findById(voieId);
		Secteur secteur = serviceRechercheSecteur.findById(secteurId);
		Spot spot = serviceRechercheSpot.findById(spotId);
		List<Longueur> listeLongueurs = serviceRechercheLongueur.findByVoieIdOrderByNom(voieId);
		model.addAttribute("voie", voie);
		model.addAttribute("secteur", secteur);
		model.addAttribute("spot", spot);
		model.addAttribute("listeLongueurs", listeLongueurs);
		model.addAttribute("pageNumber", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("spotActive", "active");
		session.setAttribute(RedirectionUrl.VOIE.label, "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId
				+ "/voie/" + voieId + "?page=" + page + "&size=" + size);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId
				+ "/voie/" + voieId + "?page=" + page + "&size=" + size);
		return "ami_voie";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/edition")
	public String showEditionVoieForm(@PathVariable Long spotId, @PathVariable Long secteurId,
			@PathVariable Long voieId, Model model) {
		Voie voie = serviceRechercheVoie.findById(voieId);
		EditionVoieForm editionVoieForm = EditionVoieForm.creer(voie);
		String urlRedirection = "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId
				+ "/edition";
		model.addAttribute("spot", editionVoieForm.getSpot());
		model.addAttribute("secteur", editionVoieForm.getSecteur());
		model.addAttribute("voie", voie);
		model.addAttribute("editionVoieForm", editionVoieForm);
		model.addAttribute("spotActive", "active");
		model.addAttribute("urlVoie", ((String) session.getAttribute(RedirectionUrl.VOIE.label)).split(":")[1]);
		session.setAttribute(RedirectionUrl.EDITION_VOIE_FORM.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_voie_edition";
	}

	@PostMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/edition")
	public String checkEditionVoieForm(@Valid EditionVoieForm editionVoieForm, @PathVariable Long spotId,
			@PathVariable Long secteurId, @PathVariable Long voieId, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		Voie voie = serviceRechercheVoie.findById(voieId);
		if (!validationFormEditionVoie.isValide(editionVoieForm)) {
			validationFormEditionVoie.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("spot", editionVoieForm.getSpot());
			model.addAttribute("secteur", editionVoieForm.getSecteur());
			model.addAttribute("voie", voie);
			model.addAttribute("spotActive", "active");
			return "ami_voie_edition";
		}
		redirectAttributes.addFlashAttribute("editionVoieForm", editionVoieForm);
		return "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId + "/edition/enregistrement";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/edition/enregistrement")
	public String saveEditionVoieForm(@PathVariable Long spotId, @PathVariable Long secteurId,
			@PathVariable Long voieId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			EditionVoieForm editionVoieForm = (EditionVoieForm) inputFlashMap.get("editionVoieForm");
			editionVoieForm.setPlan(validationFormEditionVoie.getPlan());
			editionVoieForm.setCotationFrance(validationFormEditionVoie.getCotationFrance());
			serviceEnregistrementFormEditionVoie.enregistrer(editionVoieForm);
			redirectAttributes.addFlashAttribute("voie", serviceEnregistrementFormEditionVoie.getVoie());
		}
		return (String) session.getAttribute(RedirectionUrl.VOIE.label);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute(NomModel.UTILISATEUR.label, session.getAttribute(NomModel.UTILISATEUR.label));
		model.addAttribute("unitePrincipaleLabels", CotationFranceUnitePrincipale.LABELS);
		model.addAttribute("uniteSecondaireLabels", CotationFranceUniteSecondaire.LABELS);
		model.addAttribute("uniteTertiaireLabels", CotationFranceUniteTertiaire.LABELS);
		model.addAttribute("cheminPlan", ServiceStockagePlan.RESOURCE_HANDLER_PLAN);
	}

}
