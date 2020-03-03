package app.gaugiciel.amical.controller.visiteur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteTertiaire;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheCommentaire;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheLongueur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSecteur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheVoie;
import app.gaugiciel.amical.business.implementation.stockage.ServiceStockagePlan;
import app.gaugiciel.amical.controller.form.RechercheSpotForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormRechercheSpot;
import app.gaugiciel.amical.model.Commentaire;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.utilitaire.Utils;

@Controller
@ControllerAdvice
public class VisiteurSpotController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VisiteurSpotController.class);

	private final int PAGE_SIZE = 5;

	@Autowired
	private RechercheSpotForm spotForm;
	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ValidationFormRechercheSpot validationFormSpot;
	@Autowired
	private ServiceRechercheSecteur serviceRechercheSecteur;
	@Autowired
	private ServiceRechercheVoie serviceRechercheVoie;
	@Autowired
	private ServiceRechercheLongueur serviceRechercheLongueur;
	@Autowired
	private ServiceRechercheCommentaire serviceRechercheCommentaire;

	@GetMapping("/visiteur/spot/recherche")
	public String showSpotForm(Model model) {
		LOGGER.info("Start {}()", "showSpotForm");
		spotForm.reinitialiser();
		model.addAttribute("spotForm", spotForm);
		model.addAttribute("spotActive", "active");
		return "visiteur_spot_recherche";
	}

	@PostMapping("/visiteur/spot/recherche")
	public String checkFormFindSpot(@Valid RechercheSpotForm spotForm, BindingResult bindingResult, Model model) {
		LOGGER.info("Start {}()", "checkFormFindSpot");

		this.spotForm = spotForm;

		spotForm.setIsFieldsCotationValid(RechercheSpotForm.LISTE_FIELDS_COTATION.stream()
				.map(field -> bindingResult.hasFieldErrors(field)).anyMatch(b -> true));

		if (!validationFormSpot.isValide(spotForm)) {
			validationFormSpot.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			return "visiteur_spot_recherche";
		}

		spotForm.setListeCotations(
				ServiceCotationFrance.getBetween(spotForm.getCotationMin(), spotForm.getCotationMax()));

		return "redirect:/visiteur/spot/recherche/resultat";
	}

	@GetMapping("/visiteur/spot/recherche/resultat")
	public String resultSpotForm(@ModelAttribute("spotForm") RechercheSpotForm spotForm,
			@PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
		LOGGER.info("Start {}()", "resultSpotForm");

		Page<Spot> pageSpots = serviceRechercheSpot.rechercher(spotForm, pageable);

		model.addAttribute("listeSpots", pageSpots.getContent());
		model.addAttribute("nbSpots", pageSpots.getTotalElements());
		model.addAttribute("listePages", IntStream.range(0, pageSpots.getTotalPages()).toArray());
		model.addAttribute("nbPages", pageSpots.getTotalPages());
		model.addAttribute("pageNumber", pageSpots.getNumber());
		model.addAttribute("pageSize", PAGE_SIZE);
		model.addAttribute("spotActive", "active");

		return "visiteur_spot_recherche";
	}

	@GetMapping("/visiteur/spot/{spotId}")
	public String showSpot(@PathVariable Long spotId, @RequestParam(required = false) Long secteurId, int page,
			int size, Model model) {
		LOGGER.info("Start {}()", "showSpot");
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
		List<Commentaire> listeCommentaires = serviceRechercheCommentaire.findAllBySpot(spot);
		listeCommentaires
				.forEach(commentaire -> commentaire.setDateString(Utils.formaterTimestamp(commentaire.getDate())));
		model.addAttribute("secteurId", secteurId);
		model.addAttribute("spot", spot);
		model.addAttribute("listeSecteurs", listeSecteurs);
		model.addAttribute("mapVoies", mapVoies);
		model.addAttribute("mapLongueurs", mapLongueurs);
		model.addAttribute("mapNbSpits", mapNbSpits);
		model.addAttribute("listeCommentaires", listeCommentaires);
		model.addAttribute("nbCommentaires", listeCommentaires.size());
		model.addAttribute("pageNumber", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("spotActive", "active");
		return "visiteur_spot";
	}

	@GetMapping("/visiteur/spot/{spotId}/secteur/{secteurId}")
	public String showSecteur(@PathVariable Long spotId, @PathVariable Long secteurId, int page, int size,
			Model model) {
		LOGGER.info("Start {}()", "showSecteur");
		return "redirect:/visiteur/spot/" + spotId + "?secteurId=" + secteurId + "&page=" + page + "&size=" + size;
	}

	@GetMapping("/visiteur/spot/{spotId}/secteur/{secteurId}/voie/{voieId}")
	public String showVoie(@PathVariable Long spotId, @PathVariable Long secteurId, @PathVariable Long voieId, int page,
			int size, Model model) {
		LOGGER.info("Start {}()", "showVoie");
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
		return "visiteur_voie";
	}

	@GetMapping("/visiteur/spot/{spotId}/secteur/{secteurId}/voie/{voieId}/longueur/{longueurId}")
	public String showLongueur(@PathVariable Long spotId, @PathVariable Long secteurId, @PathVariable Long voieId,
			@PathVariable Long longueurId, int page, int size, Model model) {
		LOGGER.info("Start {}()", "showLongueur");
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
		return "visiteur_longueur";
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
		return serviceRechercheSpot.rechercherNomVoie(nomVoie);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("spotForm", spotForm);
		model.addAttribute("unitePrincipaleLabels", CotationFranceUnitePrincipale.LABELS);
		model.addAttribute("uniteSecondaireLabels", CotationFranceUniteSecondaire.LABELS);
		model.addAttribute("uniteTertiaireLabels", CotationFranceUniteTertiaire.LABELS);
		model.addAttribute("cheminPlan", ServiceStockagePlan.RESOURCE_HANDLER_PLAN);
	}

}
