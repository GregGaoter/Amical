package app.gaugiciel.amical.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.gaugiciel.amical.business.implementation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.ServiceCotationFranceUniteTertiaire;
import app.gaugiciel.amical.business.implementation.ServiceRechercheLongueur;
import app.gaugiciel.amical.business.implementation.ServiceRechercheSecteur;
import app.gaugiciel.amical.business.implementation.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.ServiceRechercheVoie;
import app.gaugiciel.amical.business.implementation.ServiceUtils;
import app.gaugiciel.amical.controller.form.SpotForm;
import app.gaugiciel.amical.controller.utils.implementation.ValidationFormSpot;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.utilitaire.Utils;

@Controller
@ControllerAdvice
public class AmiSpotController {

	private final int PAGE_SIZE = 5;

	@Autowired
	private SpotForm spotForm;
	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ValidationFormSpot validationFormSpot;
	@Autowired
	private ServiceRechercheSecteur serviceRechercheSecteur;
	@Autowired
	private ServiceRechercheVoie serviceRechercheVoie;
	@Autowired
	private ServiceRechercheLongueur serviceRechercheLongueur;
	@Autowired
	private HttpSession session;

	@GetMapping("/ami/spot/recherche")
	public String showSpotForm(SpotForm spotForm, Model model) {
		spotForm.reinitialiser();
		model.addAttribute("spotActive", "active");
		return "ami_spot_recherche";
	}

	@PostMapping("/ami/spot/recherche")
	public String checkFormFindSpot(@Valid SpotForm spotForm, BindingResult bindingResult, Model model) {

		this.spotForm = spotForm;
		
		spotForm.setIsFieldsCotationValid(SpotForm.LISTE_FIELDS_COTATION.stream()
				.map(field -> bindingResult.hasFieldErrors(field)).anyMatch(b -> true));

		if (!validationFormSpot.isValide(spotForm)) {
			validationFormSpot.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			return "ami_spot_recherche";
		}

		spotForm.setListeCotations(
				ServiceCotationFrance.getBetween(spotForm.getCotationMin(), spotForm.getCotationMax()));

		return "redirect:/ami/spot/recherche/resultat";
	}

	@GetMapping("/ami/spot/recherche/resultat")
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

		return "ami_spot_recherche";
	}

	@GetMapping("/ami/spot/{spotId}")
	public String showSpot(@PathVariable Long spotId, @RequestParam(required = false) Long secteurId, int page,
			int size, Model model) {
		Spot spot = serviceRechercheSpot.findById(spotId);
		List<Secteur> listeSecteurs = serviceRechercheSecteur.findBySpotIdOrderByNom(spotId);
		Map<Long, List<Voie>> mapVoies = new HashMap<>();
		if (!listeSecteurs.isEmpty()) {
			listeSecteurs.forEach(secteur -> mapVoies.put(secteur.getId(),
					serviceRechercheVoie.findBySecteurIdOrderByNumero(secteur.getId())));
		}
		Map<Long, List<Longueur>> mapLongueurs = new HashMap<>();
		if (!mapVoies.isEmpty()) {
			mapVoies.entrySet().forEach(set -> set.getValue().forEach(voie -> mapLongueurs.put(voie.getId(),
					serviceRechercheLongueur.findByVoieIdOrderByNom(voie.getId()))));
		}
		Map<Long, Integer> mapNbSpits = new HashMap<>();
		if (!mapLongueurs.isEmpty()) {
			try {
				mapLongueurs.entrySet().forEach(set -> {
					int nbSpits = set.getValue().stream().mapToInt(longueur -> longueur.getNbSpits()).sum();
					mapNbSpits.put(set.getKey(), nbSpits == 0 ? null : nbSpits);
				});
			} catch (Exception e) {
			}
		}
		model.addAttribute("secteurId", secteurId);
		model.addAttribute("spot", spot);
		model.addAttribute("listeSecteurs", listeSecteurs);
		model.addAttribute("mapVoies", mapVoies);
		model.addAttribute("mapLongueurs", mapLongueurs);
		model.addAttribute("mapNbSpits", mapNbSpits);
		model.addAttribute("pageNumber", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("spotActive", "active");
		return "ami_spot";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}")
	public String showSecteur(@PathVariable Long spotId, @PathVariable Long secteurId, int page, int size,
			Model model) {
		return "redirect:/ami/spot/" + spotId + "?secteurId=" + secteurId + "&page=" + page + "&size=" + size;
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}")
	public String showVoie(@PathVariable Long spotId, @PathVariable Long secteurId, @PathVariable Long voieId, int page,
			int size, Model model) {
		Voie voie = serviceRechercheVoie.findById(voieId);
		Secteur secteur = serviceRechercheSecteur.findById(secteurId);
		Spot spot = serviceRechercheSpot.findById(spotId);
		List<Longueur> listeLongueurs = serviceRechercheLongueur.findByVoieIdOrderByNom(voieId);
		model.addAttribute("voie", voie);
		model.addAttribute("secteur", secteur);
		model.addAttribute("spot", spot);
		model.addAttribute("listeLongueurs", listeLongueurs);
		model.addAttribute("pageNumber", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("spotActive", "active");
		return "ami_voie";
	}

	@GetMapping("/ami/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/{longueurId}")
	public String showLongueur(@PathVariable Long spotId, @PathVariable Long secteurId, @PathVariable Long voieId,
			@PathVariable Long longueurId, int page, int size, Model model) {
		Longueur longueur = serviceRechercheLongueur.findById(longueurId);
		Voie voie = serviceRechercheVoie.findById(voieId);
		Secteur secteur = serviceRechercheSecteur.findById(secteurId);
		Spot spot = serviceRechercheSpot.findById(spotId);
		model.addAttribute("longueur", longueur);
		model.addAttribute("voie", voie);
		model.addAttribute("secteur", secteur);
		model.addAttribute("spot", spot);
		model.addAttribute("pageNumber", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("spotActive", "active");
		return "ami_longueur";
	}

	@GetMapping("/ami/spot/recherche/nomSpot")
	@ResponseBody
	public List<String> rechercherNomSpot(@RequestParam("term") String nomSpot) {
		return serviceRechercheSpot.rechercherNomSpot(nomSpot);
	}

	@GetMapping("/ami/spot/recherche/lieuSpot")
	@ResponseBody
	public List<String> rechercherLieuSpot(@RequestParam("term") String lieuSpot) {
		return serviceRechercheSpot.rechercherLieuSpot(lieuSpot);
	}

	@GetMapping("/ami/spot/recherche/nomSecteur")
	@ResponseBody
	public List<String> rechercherNomSecteur(@RequestParam("term") String nomSecteur) {
		return serviceRechercheSpot.rechercherNomSecteur(nomSecteur);
	}

	@GetMapping("/ami/spot/recherche/nomVoie")
	@ResponseBody
	public List<String> rechercherNomVoie(@RequestParam("term") String nomVoie) {
		return serviceRechercheSpot.rechercherNomVoie(nomVoie);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("spotForm", spotForm);
		model.addAttribute("unitePrincipaleLabels", ServiceCotationFranceUnitePrincipale.LABELS);
		model.addAttribute("uniteSecondaireLabels", ServiceCotationFranceUniteSecondaire.LABELS);
		model.addAttribute("uniteTertiaireLabels", ServiceCotationFranceUniteTertiaire.LABELS);
		model.addAttribute("cheminPlan", Utils.CHEMIN_PLAN);
		model.addAttribute(ServiceUtils.UTILISATEUR, session.getAttribute(ServiceUtils.UTILISATEUR));
	}

}
