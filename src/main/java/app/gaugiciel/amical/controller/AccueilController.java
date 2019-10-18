package app.gaugiciel.amical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccueilController {

	// Action par défaut appelée après le login
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/visiteur/accueil";
	}

	@RequestMapping(value = "/visiteur/accueil")
	public String visiteurAccueil() {
		return "visiteur_accueil";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/403")
	public String accessDenied() {
		return "403";
	}

}
