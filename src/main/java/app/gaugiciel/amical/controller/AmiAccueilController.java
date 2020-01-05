package app.gaugiciel.amical.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@ControllerAdvice
public class AmiAccueilController {

	@Autowired
	private HttpSession session;

	@GetMapping(value = "/ami/accueil")
	public String amiAccueil(Model model) {
		return "ami_accueil";
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("utilisateur", session.getAttribute("utilisateur"));
	}

}
