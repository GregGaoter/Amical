package app.gaugiciel.amical.controller.ami;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormEditionLongueur;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormNouvelleLongueur;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheLongueur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSecteur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheVoie;
import app.gaugiciel.amical.business.implementation.stockage.ServiceStockagePlan;
import app.gaugiciel.amical.controller.form.EditionLongueurForm;
import app.gaugiciel.amical.controller.form.NouvelleLongueurForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormEditionLongueur;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormNouvelleLongueur;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Voie;

@Controller
@ControllerAdvice
public class AmiLongueurController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AmiLongueurController.class);

	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ServiceRechercheSecteur serviceRechercheSecteur;
	@Autowired
	private ServiceRechercheVoie serviceRechercheVoie;
	@Autowired
	private ValidationFormNouvelleLongueur validationFormNouvelleLongueur;
	@Autowired
	private ServiceEnregistrementFormNouvelleLongueur serviceEnregistrementFormNouvelleLongueur;
	@Autowired
	private ServiceRechercheLongueur serviceRechercheLongueur;
	@Autowired
	private ValidationFormEditionLongueur validationFormEditionLongueur;
	@Autowired
	private ServiceEnregistrementFormEditionLongueur serviceEnregistrementFormEditionLongueur;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private HttpSession session;

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/nouveau")
	public String showNouvelleLongueurForm(@PathVariable Long spotId, @PathVariable Long secteurId,
			@PathVariable Long voieId, Model model, HttpServletRequest request) {
		LOGGER.info("Start {}()", "showNouvelleLongueurForm");
		NouvelleLongueurForm nouvelleLongueurForm = new NouvelleLongueurForm();
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			Plan plan = (Plan) inputFlashMap.get("plan");
			model.addAttribute("messagePlanEnregistreAvecSucces", messageSource.getMessage(
					"message.planEnregistreAvecSucces", new String[] { plan.getPlan() }, Locale.getDefault()));
			nouvelleLongueurForm.setPlan(plan);
		}
		Spot spot = serviceRechercheSpot.findById(spotId);
		nouvelleLongueurForm.setNomSpot(spot.getNom());
		Secteur secteur = serviceRechercheSecteur.findById(secteurId);
		nouvelleLongueurForm.setNomSecteur(secteur.getNom());
		Voie voie = serviceRechercheVoie.findById(voieId);
		nouvelleLongueurForm.setVoie(voie);
		nouvelleLongueurForm.setNomVoie(voie.getNom());
		model.addAttribute("nouvelleLongueurForm", nouvelleLongueurForm);
		model.addAttribute("spot", spot);
		model.addAttribute("secteur", secteur);
		model.addAttribute("voie", voie);
		model.addAttribute("spotActive", "active");
		model.addAttribute("urlVoie", ((String) session.getAttribute(RedirectionUrl.VOIE.label)).split(":")[1]);
		session.setAttribute(RedirectionUrl.LONGUEUR_FORM.label,
				"redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId + "/longueur/nouveau");
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label,
				"redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId + "/longueur/nouveau");
		return "ami_longueur_nouvelle";
	}

	@PostMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/nouveau")
	public String checkNouvelleLongueurForm(@Valid NouvelleLongueurForm nouvelleLongueurForm, @PathVariable Long spotId,
			@PathVariable Long secteurId, @PathVariable Long voieId, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "checkNouvelleLongueurForm");
		if (!validationFormNouvelleLongueur.isValide(nouvelleLongueurForm)) {
			validationFormNouvelleLongueur.getListeFieldError()
					.forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			Spot spot = serviceRechercheSpot.findById(spotId);
			Secteur secteur = serviceRechercheSecteur.findById(secteurId);
			Voie voie = serviceRechercheVoie.findById(voieId);
			model.addAttribute("spot", spot);
			model.addAttribute("secteur", secteur);
			model.addAttribute("voie", voie);
			model.addAttribute("spotActive", "active");
			return "ami_longueur_nouvelle";
		}
		redirectAttributes.addFlashAttribute("nouvelleLongueurForm", nouvelleLongueurForm);
		return "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId
				+ "/longueur/nouveau/enregistrement";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/nouveau/enregistrement")
	public String saveNouvelleLongueurForm(@PathVariable Long spotId, @PathVariable Long secteurId,
			@PathVariable Long voieId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "saveNouvelleLongueurForm");
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			NouvelleLongueurForm nouvelleLongueurForm = (NouvelleLongueurForm) inputFlashMap
					.get("nouvelleLongueurForm");
			Voie voie = serviceRechercheVoie.findById(voieId);
			nouvelleLongueurForm.setVoie(voie);
			serviceEnregistrementFormNouvelleLongueur.enregistrer(nouvelleLongueurForm);
			redirectAttributes.addFlashAttribute("longueur", serviceEnregistrementFormNouvelleLongueur.getLongueur());
		}
		return (String) session.getAttribute(RedirectionUrl.VOIE.label);
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/{longueurId}")
	public String showLongueur(@PathVariable Long spotId, @PathVariable Long secteurId, @PathVariable Long voieId,
			@PathVariable Long longueurId, Integer page, Integer size, Model model, HttpServletRequest request) {
		LOGGER.info("Start {}()", "showLongueur");
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("longueur")) {
				Longueur longueur = (Longueur) inputFlashMap.get("longueur");
				model.addAttribute("messageLongueurEnregistreAvecSucces",
						messageSource.getMessage("message.LongueurEnregistreAvecSucces",
								new String[] { longueur.getNom() }, Locale.getDefault()));
			}
		}
		Longueur longueur = serviceRechercheLongueur.findById(longueurId);
		Voie voie = serviceRechercheVoie.findById(voieId);
		Secteur secteur = serviceRechercheSecteur.findById(secteurId);
		Spot spot = serviceRechercheSpot.findById(spotId);
		String redirectUrlLongueur = "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId
				+ "/longueur/" + longueurId + "?page=" + page + "&size=" + size;
		model.addAttribute("longueur", longueur);
		model.addAttribute("voie", voie);
		model.addAttribute("secteur", secteur);
		model.addAttribute("spot", spot);
		model.addAttribute("pageNumber", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("spotActive", "active");
		session.setAttribute(RedirectionUrl.LONGUEUR.label, redirectUrlLongueur);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, redirectUrlLongueur);
		return "ami_longueur";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/{longueurId}/edition")
	public String showEditionLongueurForm(@PathVariable Long spotId, @PathVariable Long secteurId,
			@PathVariable Long voieId, @PathVariable Long longueurId, Model model) {
		LOGGER.info("Start {}()", "showEditionLongueurForm");
		Longueur longueur = serviceRechercheLongueur.findById(longueurId);
		EditionLongueurForm editionLongueurForm = EditionLongueurForm.creer(longueur);
		String urlRedirection = "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId
				+ "/longueur/" + longueurId + "/edition";
		model.addAttribute("spot", editionLongueurForm.getSpot());
		model.addAttribute("secteur", editionLongueurForm.getSecteur());
		model.addAttribute("voie", editionLongueurForm.getVoie());
		model.addAttribute("longueur", longueur);
		model.addAttribute("editionLongueurForm", editionLongueurForm);
		model.addAttribute("spotActive", "active");
		model.addAttribute("urlLongueur", ((String) session.getAttribute(RedirectionUrl.LONGUEUR.label)).split(":")[1]);
		session.setAttribute(RedirectionUrl.EDITION_LONGUEUR_FORM.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_longueur_edition";
	}

	@PostMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/{longueurId}/edition")
	public String checkEditionLongueurForm(@Valid EditionLongueurForm editionLongueurForm, @PathVariable Long spotId,
			@PathVariable Long secteurId, @PathVariable Long voieId, @PathVariable Long longueurId,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "checkEditionLongueurForm");
		Longueur longueur = serviceRechercheLongueur.findById(longueurId);
		Voie voie = longueur.getVoie();
		Secteur secteur = voie.getSecteur();
		Spot spot = secteur.getSpot();
		editionLongueurForm.setVoie(voie);
		editionLongueurForm.setSecteur(secteur);
		editionLongueurForm.setSpot(spot);
		if (!validationFormEditionLongueur.isValide(editionLongueurForm)) {
			validationFormEditionLongueur.getListeFieldError()
					.forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("spot", editionLongueurForm.getSpot());
			model.addAttribute("secteur", editionLongueurForm.getSecteur());
			model.addAttribute("voie", editionLongueurForm.getVoie());
			model.addAttribute("longueur", longueur);
			model.addAttribute("spotActive", "active");
			return "ami_longueur_edition";
		}
		redirectAttributes.addFlashAttribute("editionLongueurForm", editionLongueurForm);
		return "redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId + "/longueur/" + longueurId
				+ "/edition/enregistrement";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/{longueurId}/edition/enregistrement")
	public String saveEditionLongueurForm(@PathVariable Long spotId, @PathVariable Long secteurId,
			@PathVariable Long voieId, @PathVariable Long longueurId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "saveEditionLongueurForm");
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			EditionLongueurForm editionLongueurForm = (EditionLongueurForm) inputFlashMap.get("editionLongueurForm");
			editionLongueurForm.setPlan(validationFormEditionLongueur.getPlan());
			serviceEnregistrementFormEditionLongueur.enregistrer(editionLongueurForm);
			redirectAttributes.addFlashAttribute("longueur", serviceEnregistrementFormEditionLongueur.getLongueur());
		}
		return (String) session.getAttribute(RedirectionUrl.LONGUEUR.label);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute(NomModel.UTILISATEUR.label, session.getAttribute(NomModel.UTILISATEUR.label));
		model.addAttribute("cheminPlan", ServiceStockagePlan.RESOURCE_HANDLER_PLAN);
	}

}
