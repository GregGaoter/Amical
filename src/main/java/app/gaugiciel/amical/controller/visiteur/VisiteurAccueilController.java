package app.gaugiciel.amical.controller.visiteur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisiteurAccueilController {

	private static final Logger logger = LoggerFactory.getLogger(VisiteurAccueilController.class);

	@GetMapping(value = "/")
	public String home() {
		return "redirect:/visiteur/accueil";
	}

	@GetMapping(value = "/visiteur/accueil")
	public String accueil(Model model) {
		logger.trace("Start accueil()");
		model.addAttribute("accueilActive", "active");
		return "visiteur_accueil";
	}

}
