package app.gaugiciel.amical.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import app.gaugiciel.amical.business.implementation.ServiceEnregistrementFormInscription;
import app.gaugiciel.amical.controller.form.InscriptionForm;
import app.gaugiciel.amical.controller.utils.implementation.ValidationFormInscription;

@Controller
public class InscriptionController {

	@Autowired
	private InscriptionForm inscriptionForm;
	@Autowired
	private ValidationFormInscription validationFormInscription;
	@Autowired
	private ServiceEnregistrementFormInscription serviceEnregistrementFormInscription;

	@GetMapping(value = "/visiteur/inscription")
	public String showInscriptionForm() {
		inscriptionForm.reinitialiser();
		return "inscription";
	}

	@PostMapping("/visiteur/inscription")
	public String checkInscriptionForm(@Valid InscriptionForm inscriptionForm, BindingResult bindingResult,
			Model model) {
		if (!validationFormInscription.isValide(inscriptionForm)) {
			validationFormInscription.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			this.inscriptionForm = inscriptionForm;
			return "inscription";
		}
		this.inscriptionForm = inscriptionForm;
		return "redirect:/visiteur/inscription/enregistrement";
	}

	@GetMapping(value = "/visiteur/inscription/enregistrement")
	public String saveInscriptionForm(@ModelAttribute("inscriptionForm") InscriptionForm inscriptionForm, Model model) {
		serviceEnregistrementFormInscription.enregistrer(inscriptionForm);
		return "redirect:/visiteur/inscription/enregistrement/autoLogin";
	}

	@GetMapping(value = "/visiteur/inscription/enregistrement/autoLogin")
	public String autoLoginApresInscription(@ModelAttribute("inscriptionForm") InscriptionForm inscriptionForm,
			HttpServletRequest request) {
		try {
			request.login(inscriptionForm.getEmail(), inscriptionForm.getMotDePasse());
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
