package app.gaugiciel.amical.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import app.gaugiciel.amical.business.implementation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.ServiceRechercheSpot;
import app.gaugiciel.amical.controller.form.SpotForm;
import app.gaugiciel.amical.controller.utils.implementation.ValidationFormSpot;
import app.gaugiciel.amical.model.Spot;

@Controller
public class SpotController {

	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ValidationFormSpot validationFormSpot;

	@GetMapping("/visiteur/spot/recherche")
	public String showSpotForm(SpotForm spotForm) {
		return "spot_recherche";
	}

	@PostMapping("/visiteur/spot/recherche")
	private String checkFormFindSpot(@Valid SpotForm spotForm, BindingResult bindingResult, Model model) {
		spotForm.setIsFieldsCotationValid(SpotForm.LISTE_FIELDS_COTATION.stream()
				.map(field -> bindingResult.hasFieldErrors(field)).anyMatch(b -> true));
		if (!validationFormSpot.isValide(spotForm)) {
			validationFormSpot.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			return "spot_recherche";
		}
		spotForm.setListeCotations(
				ServiceCotationFrance.getBetween(spotForm.getCotationMin(), spotForm.getCotationMax()));
		List<Spot> listeSpots = serviceRechercheSpot.rechercher(spotForm);
		model.addAttribute("listeSpots", listeSpots);
		model.addAttribute("nbSpots", listeSpots.size());
		return "spot_recherche";
	}

}
