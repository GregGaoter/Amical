package app.gaugiciel.amical.controller.visiteur;

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
	public String accueil(Model model) {
		model.addAttribute("accueilActive", "active");
		return "visiteur_accueil";
	}

}
