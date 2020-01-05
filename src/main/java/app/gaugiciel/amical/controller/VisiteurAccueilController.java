package app.gaugiciel.amical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisiteurAccueilController {

	@GetMapping(value = "/")
	public String home() {
		return "redirect:/visiteur/accueil";
	}

	@GetMapping(value = "/visiteur/accueil")
	public String visiteurAccueil(Model model) {
		model.addAttribute("accueilActive", "active");
		return "visiteur_accueil";
	}

}
