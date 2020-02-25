package app.gaugiciel.amical.controller.authentification;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormInscription;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheUtilisateur;
import app.gaugiciel.amical.controller.ami.AmiVoieController;
import app.gaugiciel.amical.controller.form.InscriptionForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormInscription;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
@ControllerAdvice
public class InscriptionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InscriptionController.class);

	@Autowired
	private InscriptionForm inscriptionForm;
	@Autowired
	private ValidationFormInscription validationFormInscription;
	@Autowired
	private ServiceEnregistrementFormInscription serviceEnregistrementFormInscription;
	@Autowired
	private ServiceRechercheUtilisateur serviceRechercheUtilisateur;

	@GetMapping(value = "/visiteur/inscription")
	public String showInscriptionForm() {
		inscriptionForm.reinitialiser();
		return "inscription";
	}

	@PostMapping("/visiteur/inscription")
	public String checkInscriptionForm(@Valid InscriptionForm inscriptionForm, BindingResult bindingResult,
			Model model) {
		this.inscriptionForm = inscriptionForm;
		if (!validationFormInscription.isValide(inscriptionForm)) {
			validationFormInscription.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			return "inscription";
		}
		return "redirect:/visiteur/inscription/enregistrement";
	}

	@GetMapping(value = "/visiteur/inscription/enregistrement")
	public String saveInscriptionForm(@ModelAttribute("inscriptionForm") InscriptionForm inscriptionForm, Model model) {
		serviceEnregistrementFormInscription.enregistrer(inscriptionForm);
		return "redirect:/visiteur/inscription/enregistrement/autoLogin";
	}

	@GetMapping(value = "/visiteur/inscription/enregistrement/autoLogin")
	public String autoLoginApresInscription(@ModelAttribute("inscriptionForm") InscriptionForm inscriptionForm,
			HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		try {
			request.login(inscriptionForm.getEmail(), inscriptionForm.getMotDePasse());
			HttpSession session = request.getSession();
			Utilisateur utilisateur = serviceRechercheUtilisateur.findByEmail(inscriptionForm.getEmail());
			session.setAttribute(NomModel.UTILISATEUR.label, utilisateur);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/ami/accueil";
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("inscriptionForm", inscriptionForm);
	}

}
