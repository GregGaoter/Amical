package app.gaugiciel.amical.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import app.gaugiciel.amical.business.implementation.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.ServiceValidationFormSpot;
import app.gaugiciel.amical.controller.form.SpotForm;
import app.gaugiciel.amical.model.Spot;

@Controller
public class SpotController {

	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ServiceValidationFormSpot serviceValidationFormSpot;

	@GetMapping("/visiteur/spot/recherche")
	public String showSpotForm(SpotForm spotForm) {
		return "spot_recherche";
	}

	@PostMapping("/visiteur/spot/recherche")
	private String checkFormFindSpot(@Valid SpotForm spotForm, BindingResult bindingResult, Model model) {
		if (!serviceValidationFormSpot.isValide(spotForm)) {
			for (FieldError fieldError : serviceValidationFormSpot.getListeFieldError()) {
				bindingResult.addError(fieldError);
			}
		}
		if (bindingResult.hasErrors()) {
			return "spot_recherche";
		}
		List<Spot> listeSpots = serviceRechercheSpot.rechercher(spotForm);
		model.addAttribute("listeSpots", listeSpots);
		model.addAttribute("nbSpots", listeSpots.size());
		return "spot_recherche";
	}

}
