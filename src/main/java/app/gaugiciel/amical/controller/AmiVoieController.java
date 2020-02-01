package app.gaugiciel.amical.controller;

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

import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteTertiaire;
import app.gaugiciel.amical.business.implementation.ServiceEnregistrementFormNouvelleVoie;
import app.gaugiciel.amical.business.implementation.ServiceModel;
import app.gaugiciel.amical.business.implementation.ServiceRechercheSecteur;
import app.gaugiciel.amical.business.implementation.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.ServiceRedirectionUrl;
import app.gaugiciel.amical.business.implementation.ServiceStockagePlan;
import app.gaugiciel.amical.controller.form.NouvelleVoieForm;
import app.gaugiciel.amical.controller.utils.implementation.ValidationFormNouvelleVoie;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;

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
		session.setAttribute(ServiceRedirectionUrl.VOIE_FORM.label,
				"redirect:/ami/spot/" + spotId + "/secteur/" + secteurId + "/voie/nouveau");
		session.setAttribute(ServiceRedirectionUrl.PREVIOUS_URL.label,
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
		return (String) session.getAttribute(ServiceRedirectionUrl.SPOT.label);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute(ServiceModel.UTILISATEUR.label, session.getAttribute(ServiceModel.UTILISATEUR.label));
		model.addAttribute("unitePrincipaleLabels", ServiceCotationFranceUnitePrincipale.LABELS);
		model.addAttribute("uniteSecondaireLabels", ServiceCotationFranceUniteSecondaire.LABELS);
		model.addAttribute("uniteTertiaireLabels", ServiceCotationFranceUniteTertiaire.LABELS);
		model.addAttribute("cheminPlan", ServiceStockagePlan.RESOURCE_HANDLER_PLAN);
	}

}
