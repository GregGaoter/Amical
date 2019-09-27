package fr.lesamisdelescalade.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MaquetteController {

	// Action par défaut appelée après le login
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/visiteur/accueil";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/403")
	public String refuserAcces() {
		return "403";
	}

	@RequestMapping(value = "/visiteur/accueil")
	public String accueil() {
		return "accueil";
	}

}
