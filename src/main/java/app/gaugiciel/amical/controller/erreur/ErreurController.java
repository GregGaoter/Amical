package app.gaugiciel.amical.controller.erreur;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
@ControllerAdvice
public class ErreurController implements ErrorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErreurController.class);

	@Autowired
	private HttpSession session;

	@GetMapping("/error")
	public String handleError() {
		LOGGER.info("Start {}()", "handleError");
		return "error";
	}

	@Override
	public String getErrorPath() {
		LOGGER.info("Start {}()", "getErrorPath");
		return "/error";
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		if (utilisateur != null) {
			model.addAttribute(NomModel.UTILISATEUR.label, utilisateur);
		}
	}

}
