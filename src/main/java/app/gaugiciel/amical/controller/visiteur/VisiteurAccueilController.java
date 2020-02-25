package app.gaugiciel.amical.controller.visiteur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisiteurAccueilController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VisiteurAccueilController.class);

	@GetMapping(value = "/")
	public String home() {
		LOGGER.info("Start {}()", "home");
		return "redirect:/visiteur/accueil";
	}

	@GetMapping(value = "/visiteur/accueil")
	public String accueil(Model model) {
		LOGGER.info("Start {}()", "accueil");
		model.addAttribute("accueilActive", "active");
		return "visiteur_accueil";
	}

}
