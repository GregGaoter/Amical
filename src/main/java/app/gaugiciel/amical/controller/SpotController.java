package app.gaugiciel.amical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpotController {

	@RequestMapping(value = "/recherche_spot")
	public String rechercheSpot(Model model) {
		model.addAttribute("spotActive", "active");
		return "recherche_spot";
	}

}
