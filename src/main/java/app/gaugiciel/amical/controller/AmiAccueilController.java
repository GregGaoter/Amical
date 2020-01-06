package app.gaugiciel.amical.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import app.gaugiciel.amical.business.implementation.ServiceUtils;

@Controller
@ControllerAdvice
public class AmiAccueilController {

	@Autowired
	private HttpSession session;

	@GetMapping(value = "/ami/accueil")
	public String accueil(Model model) {
		model.addAttribute("accueilActive", "active");
		return "ami_accueil";
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute(ServiceUtils.UTILISATEUR, session.getAttribute(ServiceUtils.UTILISATEUR));
	}

}
