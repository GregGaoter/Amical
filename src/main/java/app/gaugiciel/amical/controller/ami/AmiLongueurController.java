package app.gaugiciel.amical.controller.ami;

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

import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormNouvelleLongueur;
import app.gaugiciel.amical.business.implementation.model.ServiceModel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSecteur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheVoie;
import app.gaugiciel.amical.business.implementation.stockage.ServiceStockagePlan;
import app.gaugiciel.amical.business.implementation.url.ServiceRedirectionUrl;
import app.gaugiciel.amical.controller.form.NouvelleLongueurForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormNouvelleLongueur;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Voie;

@Controller
@ControllerAdvice
public class AmiLongueurController {

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
	private MessageSource messageSource;
	@Autowired
	private HttpSession session;

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/nouveau")
	public String showNouvelleLongueurForm(@PathVariable Long spotId, @PathVariable Long secteurId,
			@PathVariable Long voieId, Model model, HttpServletRequest request) {
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
		model.addAttribute("urlVoie", ((String) session.getAttribute(ServiceRedirectionUrl.VOIE.label)).split(":")[1]);
		session.setAttribute(ServiceRedirectionUrl.LONGUEUR_FORM.label,
				"redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId + "/longueur/nouveau");
		session.setAttribute(ServiceRedirectionUrl.PREVIOUS_URL.label,
				"redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/" + voieId + "/longueur/nouveau");
		return "ami_longueur_nouvelle";
	}

	@PostMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/nouveau")
	public String checkNouvelleLongueurForm(@Valid NouvelleLongueurForm nouvelleLongueurForm, @PathVariable Long spotId,
			@PathVariable Long secteurId, @PathVariable Long voieId, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
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
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			NouvelleLongueurForm nouvelleLongueurForm = (NouvelleLongueurForm) inputFlashMap
					.get("nouvelleLongueurForm");
			Voie voie = serviceRechercheVoie.findById(voieId);
			nouvelleLongueurForm.setVoie(voie);
			serviceEnregistrementFormNouvelleLongueur.enregistrer(nouvelleLongueurForm);
			redirectAttributes.addFlashAttribute("longueur", serviceEnregistrementFormNouvelleLongueur.getLongueur());
		}
		return (String) session.getAttribute(ServiceRedirectionUrl.VOIE.label);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute(ServiceModel.UTILISATEUR.label, session.getAttribute(ServiceModel.UTILISATEUR.label));
		model.addAttribute("cheminPlan", ServiceStockagePlan.RESOURCE_HANDLER_PLAN);
	}

}
