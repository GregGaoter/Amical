package app.gaugiciel.amical.controller;

import java.util.List;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.gaugiciel.amical.business.implementation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteTertiaire;
import app.gaugiciel.amical.business.implementation.ServiceRechercheSpot;
import app.gaugiciel.amical.controller.form.SpotForm;
import app.gaugiciel.amical.controller.utils.implementation.ValidationFormSpot;
import app.gaugiciel.amical.model.Spot;

@Controller
@ControllerAdvice
public class SpotController {

	private final int PAGE_SIZE = 5;

	@Autowired
	private SpotForm spotForm;
	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ValidationFormSpot validationFormSpot;

	@GetMapping("/visiteur/spot/recherche")
	public String showSpotForm(SpotForm spotForm, Model model) {
		spotForm.reinitialiser();
		model.addAttribute("spotActive", "active");
		return "spot_recherche";
	}

	@PostMapping("/visiteur/spot/recherche")
	public String checkFormFindSpot(@Valid SpotForm spotForm, BindingResult bindingResult, Model model) {

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

		this.spotForm = spotForm;
		
		model.addAttribute("spotActive", "active");

		return "redirect:/visiteur/spot/recherche/resultat";
	}

	@GetMapping("/visiteur/spot/recherche/resultat")
	public String resultSpotForm(@ModelAttribute("spotForm") SpotForm spotForm,
			@PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {

		Page<Spot> pageSpots = serviceRechercheSpot.rechercher(spotForm, pageable);

		model.addAttribute("listeSpots", pageSpots.getContent());
		model.addAttribute("nbSpots", pageSpots.getTotalElements());
		model.addAttribute("listePages", IntStream.range(0, pageSpots.getTotalPages()).toArray());
		model.addAttribute("nbPages", pageSpots.getTotalPages());
		model.addAttribute("pageNumber", pageSpots.getNumber());
		model.addAttribute("pageSize", PAGE_SIZE);
		model.addAttribute("spotActive", "active");

		return "spot_recherche";
	}

	@GetMapping("/visiteur/spot/recherche/nomSpot")
	@ResponseBody
	public List<String> rechercherNomSpot(@RequestParam("term") String nomSpot) {
		return serviceRechercheSpot.rechercherNomSpot(nomSpot);
	}

	@GetMapping("/visiteur/spot/recherche/lieuSpot")
	@ResponseBody
	public List<String> rechercherLieuSpot(@RequestParam("term") String lieuSpot) {
		return serviceRechercheSpot.rechercherLieuSpot(lieuSpot);
	}

	@GetMapping("/visiteur/spot/recherche/nomSecteur")
	@ResponseBody
	public List<String> rechercherNomSecteur(@RequestParam("term") String nomSecteur) {
		return serviceRechercheSpot.rechercherNomSecteur(nomSecteur);
	}

	@GetMapping("/visiteur/spot/recherche/nomVoie")
	@ResponseBody
	public List<String> rechercherNomVoie(@RequestParam("term") String nomVoie) {
		return serviceRechercheSpot.rechercherNomSecteur(nomVoie);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("spotForm", spotForm);
		model.addAttribute("unitePrincipaleLabels", ServiceCotationFranceUnitePrincipale.LABELS);
		model.addAttribute("uniteSecondaireLabels", ServiceCotationFranceUniteSecondaire.LABELS);
		model.addAttribute("uniteTertiaireLabels", ServiceCotationFranceUniteTertiaire.LABELS);
	}

}
