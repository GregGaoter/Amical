package app.gaugiciel.amical.controller.ami;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormNouveauPlan;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementPlanLocal;
import app.gaugiciel.amical.business.implementation.enumeration.Erreur;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.controller.form.NouveauPlanForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormNouveauPlan;

@Controller
@ControllerAdvice
public class AmiPlanController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AmiPlanController.class);

	@Autowired
	private NouveauPlanForm nouveauPlanForm;
	@Autowired
	private ValidationFormNouveauPlan validationFormNouveauPlan;
	@Autowired
	private ServiceEnregistrementFormNouveauPlan serviceEnregistrementFormNouveauPlan;
	@Autowired
	private ServiceEnregistrementPlanLocal serviceEnregistrementPlanLocal;
	@Autowired
	private HttpSession session;

	@GetMapping(value = "/ami/plan/nouveau")
	public String showNouveauPlanForm(Model model) {
		LOGGER.info("Start {}()", "showNouveauPlanForm");
		nouveauPlanForm.reinitialiser();
		model.addAttribute("spotActive", "active");
		return "ami_plan_nouveau";
	}

	@PostMapping("/ami/plan/nouveau")
	public String checkNouveauPlanForm(NouveauPlanForm nouveauPlanForm, BindingResult bindingResult, Model model) {
		LOGGER.info("Start {}()", "checkNouveauPlanForm");
		this.nouveauPlanForm = nouveauPlanForm;
		if (!validationFormNouveauPlan.isValide(nouveauPlanForm)) {
			validationFormNouveauPlan.getListeFieldError().forEach(fieldError -> model
					.addAttribute(fieldError.getField() + Erreur.SUFFIXE.label, fieldError.getDefaultMessage()));
			model.addAttribute("spotActive", "active");
			return "ami_plan_nouveau";
		}
		serviceEnregistrementPlanLocal.enregistrer(nouveauPlanForm.getFichier());
		return "redirect:/ami/plan/nouveau/enregistrement";
	}

	@GetMapping(value = "/ami/plan/nouveau/enregistrement")
	public String saveNouveauPlanForm(RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "saveNouveauPlanForm");
		serviceEnregistrementFormNouveauPlan.enregistrer(nouveauPlanForm);
		redirectAttributes.addFlashAttribute("plan", serviceEnregistrementFormNouveauPlan.getPlan());
		return (String) session.getAttribute(RedirectionUrl.PREVIOUS_URL.label);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("nouveauPlanForm", nouveauPlanForm);
		model.addAttribute(NomModel.UTILISATEUR.label, session.getAttribute(NomModel.UTILISATEUR.label));
	}

}
