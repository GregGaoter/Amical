package app.gaugiciel.amical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccueilController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/visiteur/accueil";
	}

	@RequestMapping(value = "/visiteur/accueil")
	public String visiteurAccueil(Model model) {
		model.addAttribute("accueilActive", "active");
		return "visiteur_accueil";
	}

	@RequestMapping(value = "/ami/accueil")
	public String amiAccueil() {
		return "ami_accueil";
	}

}
