package app.gaugiciel.amical.controller.authentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import app.gaugiciel.amical.utilitaire.GenerateurMotDePasse;

@Controller
public class MotsDePasseController {

	@Autowired
	private GenerateurMotDePasse generateurMotDePasse;

	@GetMapping(value = "/motsDePasse/warning")
	public String showWarningGenerationMotDePasse() {
		return "mots_de_passe_warning";
	}

	@GetMapping(value = "/motsDePasse/creation")
	public String creerMotDePasse() {
		generateurMotDePasse.genererMotsDePasse();
		return "mots_de_passe_generation";
	}

}
