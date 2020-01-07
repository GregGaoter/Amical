package app.gaugiciel.amical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthentificationController {
	
	@GetMapping(value = "/authentification")
	public String showAuthentificationForm() {
		return "authentification";
	}

}
