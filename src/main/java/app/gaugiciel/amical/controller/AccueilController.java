package app.gaugiciel.amical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.gaugiciel.amical.business.implementation.ServiceRechercheUtilisateur;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
public class AccueilController {

	@Autowired
	private ServiceRechercheUtilisateur serviceRechercheUtilisateur;

	@GetMapping(value = "/")
	public String home() {
		return "redirect:/visiteur/accueil";
	}

	@GetMapping(value = "/visiteur/accueil")
	public String visiteurAccueil(Model model) {
		model.addAttribute("accueilActive", "active");
		return "visiteur_accueil";
	}

	@GetMapping(value = "/ami/accueil")
	public String amiAccueil(Authentication authentication, Model model) {
		Utilisateur utilisateur = serviceRechercheUtilisateur.findByEmail(authentication.getName());
		model.addAttribute("utilisateur", utilisateur);
		return "ami_accueil";
	}

}
