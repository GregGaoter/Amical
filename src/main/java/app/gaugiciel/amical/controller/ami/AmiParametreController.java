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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormEditionNom;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormEditionPrenom;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.controller.data.AmiParametreData;
import app.gaugiciel.amical.controller.form.EditionNomForm;
import app.gaugiciel.amical.controller.form.EditionPrenomForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormEditionNom;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormEditionPrenom;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
@ControllerAdvice
public class AmiParametreController {

	@Autowired
	private HttpSession session;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ValidationFormEditionPrenom validationFormEditionPrenom;
	@Autowired
	private ValidationFormEditionNom validationFormEditionNom;
	@Autowired
	private ServiceEnregistrementFormEditionPrenom serviceEnregistrementFormEditionPrenom;
	@Autowired
	private ServiceEnregistrementFormEditionNom serviceEnregistrementFormEditionNom;

	@GetMapping("/ami/parametres")
	public String showParametre(Model model, HttpServletRequest request) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("messagePrenomEnregistreAvecSucces")) {
				String prenom = (String) inputFlashMap.get("messagePrenomEnregistreAvecSucces");
				model.addAttribute("messagePrenomEnregistreAvecSucces", messageSource.getMessage(
						"message.prenomEnregistreAvecSucces", new String[] { prenom }, Locale.getDefault()));
			}
			if (inputFlashMap.containsKey("messageNomEnregistreAvecSucces")) {
				String nom = (String) inputFlashMap.get("messageNomEnregistreAvecSucces");
				model.addAttribute("messageNomEnregistreAvecSucces", messageSource
						.getMessage("message.nomEnregistreAvecSucces", new String[] { nom }, Locale.getDefault()));
			}
		}
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		Authentification authentification = utilisateur.getAuthentification();
		AmiParametreData amiParametreData = AmiParametreData.creer(utilisateur.getPrenom(), utilisateur.getNom(),
				authentification.getEmail(), authentification.getMotDePasse());
		String urlRedirection = "redirect:/ami/parametres";
		model.addAttribute("amiParametreData", amiParametreData);
		model.addAttribute("editionPrenomForm", new EditionPrenomForm());
		model.addAttribute("editionNomForm", new EditionNomForm());
		session.setAttribute(RedirectionUrl.PARAMETRES.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_parametre";
	}

	@PostMapping("/ami/parametre/prenom/edition")
	public String checkPrenom(@Valid EditionPrenomForm editionPrenomForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (!validationFormEditionPrenom.isValide(editionPrenomForm)) {
			validationFormEditionPrenom.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			return (String) session.getAttribute(RedirectionUrl.PARAMETRES.label);
		}
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		utilisateur.setPrenom(editionPrenomForm.getPrenom());
		editionPrenomForm.setUtilisateur(utilisateur);
		redirectAttributes.addFlashAttribute("editionPrenomForm", editionPrenomForm);
		return "redirect:/ami/parametre/prenom/edition/enregistrement";
	}

	@GetMapping("/ami/parametre/prenom/edition/enregistrement")
	public String savePrenom(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("editionPrenomForm")) {
				EditionPrenomForm editionPrenomForm = (EditionPrenomForm) inputFlashMap.get("editionPrenomForm");
				serviceEnregistrementFormEditionPrenom.enregistrer(editionPrenomForm);
				redirectAttributes.addFlashAttribute("messagePrenomEnregistreAvecSucces",
						editionPrenomForm.getPrenom());
			}
		}
		return (String) session.getAttribute(RedirectionUrl.PARAMETRES.label);
	}

	@PostMapping("/ami/parametre/nom/edition")
	public String checkNom(@Valid EditionNomForm editionNomForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (!validationFormEditionNom.isValide(editionNomForm)) {
			validationFormEditionNom.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			return (String) session.getAttribute(RedirectionUrl.PARAMETRES.label);
		}
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		utilisateur.setNom(editionNomForm.getNom());
		editionNomForm.setUtilisateur(utilisateur);
		redirectAttributes.addFlashAttribute("editionNomForm", editionNomForm);
		return "redirect:/ami/parametre/nom/edition/enregistrement";
	}

	@GetMapping("/ami/parametre/nom/edition/enregistrement")
	public String saveNom(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("editionNomForm")) {
				EditionNomForm editionNomForm = (EditionNomForm) inputFlashMap.get("editionNomForm");
				serviceEnregistrementFormEditionNom.enregistrer(editionNomForm);
				redirectAttributes.addFlashAttribute("messageNomEnregistreAvecSucces", editionNomForm.getNom());
			}
		}
		return (String) session.getAttribute(RedirectionUrl.PARAMETRES.label);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute(NomModel.UTILISATEUR.label, session.getAttribute(NomModel.UTILISATEUR.label));
	}

}
