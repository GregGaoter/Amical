package app.gaugiciel.amical.controller.ami;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import app.gaugiciel.amical.business.implementation.constante.TailleResultatRecherche;
import app.gaugiciel.amical.business.implementation.cotation.ServiceCotationFrance;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormAjoutSpot;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormEditionSpot;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUnitePrincipale;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteSecondaire;
import app.gaugiciel.amical.business.implementation.enumeration.CotationFranceUniteTertiaire;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheCommentaire;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheLieuFrance;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheLongueur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRecherchePlan;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSecteur;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheVoie;
import app.gaugiciel.amical.business.implementation.stockage.ServiceStockagePlan;
import app.gaugiciel.amical.controller.form.EditionSpotForm;
import app.gaugiciel.amical.controller.form.NouveauCommentaireForm;
import app.gaugiciel.amical.controller.form.NouveauSpotForm;
import app.gaugiciel.amical.controller.form.RechercheSpotForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormEditionSpot;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormRechercheSpot;
import app.gaugiciel.amical.model.Commentaire;
import app.gaugiciel.amical.model.Longueur;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Voie;
import app.gaugiciel.amical.utilitaire.Utils;

@Controller
@ControllerAdvice
public class AmiSpotController {

	@Autowired
	private RechercheSpotForm spotForm;
	@Autowired
	private NouveauSpotForm ajoutSpotForm;
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
	private ServiceEnregistrementFormAjoutSpot serviceEnregistrementFormAjoutSpot;
	@Autowired
	private ServiceEnregistrementFormEditionSpot serviceEnregistrementFormEditionSpot;
	@Autowired
	private ServiceRechercheLieuFrance serviceRechercheLieuFrance;
	@Autowired
	private ServiceRecherchePlan serviceRecherchePlan;
	@Autowired
	private ValidationFormEditionSpot validationFormEditionSpot;
	@Autowired
	private ServiceRechercheCommentaire serviceRechercheCommentaire;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private HttpSession session;

	@GetMapping("/ami/spot/recherche")
	public String showSpotForm(RechercheSpotForm spotForm, Model model) {
		spotForm.reinitialiser();
		model.addAttribute("spotActive", "active");
		return "ami_spot_recherche";
	}

	@PostMapping("/ami/spot/recherche")
	public String checkFormFindSpot(@Valid RechercheSpotForm spotForm, BindingResult bindingResult, Model model) {

		this.spotForm = spotForm;

		spotForm.setIsFieldsCotationValid(RechercheSpotForm.LISTE_FIELDS_COTATION.stream()
				.map(field -> bindingResult.hasFieldErrors(field)).anyMatch(b -> true));

		if (!validationFormSpot.isValide(spotForm)) {
			validationFormSpot.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("spotActive", "active");
			return "ami_spot_recherche";
		}

		spotForm.setListeCotations(
				ServiceCotationFrance.getBetween(spotForm.getCotationMin(), spotForm.getCotationMax()));

		return "redirect:/ami/spot/recherche/resultat";
	}

	@GetMapping("/ami/spot/recherche/resultat")
	public String resultSpotForm(@ModelAttribute("spotForm") RechercheSpotForm spotForm,
			@PageableDefault(size = TailleResultatRecherche.SPOT) Pageable pageable, Model model) {

		Page<Spot> pageSpots = serviceRechercheSpot.rechercher(spotForm, pageable);

		model.addAttribute("listeSpots", pageSpots.getContent());
		model.addAttribute("nbSpots", pageSpots.getTotalElements());
		model.addAttribute("listePages", IntStream.range(0, pageSpots.getTotalPages()).toArray());
		model.addAttribute("nbPages", pageSpots.getTotalPages());
		model.addAttribute("pageNumber", pageSpots.getNumber());
		model.addAttribute("pageSize", TailleResultatRecherche.SPOT);
		model.addAttribute("spotActive", "active");

		return "ami_spot_recherche";
	}

	@GetMapping("/ami/spot/{spotId}")
	public String showSpot(@PathVariable Long spotId, @RequestParam(required = false) Long secteurId, Integer page,
			Integer size, Model model, HttpServletRequest request) {
		Spot spot;
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("spot")) {
				spot = (Spot) inputFlashMap.get("spot");
				model.addAttribute("messageSpotEnregistreAvecSucces", messageSource.getMessage(
						"message.spotEnregistreAvecSucces", new String[] { spot.getNom() }, Locale.getDefault()));
			} else {
				spot = serviceRechercheSpot.findById(spotId);
			}
			if (inputFlashMap.containsKey("secteur")) {
				Secteur secteur = (Secteur) inputFlashMap.get("secteur");
				model.addAttribute("messageSecteurEnregistreAvecSucces", messageSource.getMessage(
						"message.secteurEnregistreAvecSucces", new String[] { secteur.getNom() }, Locale.getDefault()));
			}
			if (inputFlashMap.containsKey("voie")) {
				Voie voie = (Voie) inputFlashMap.get("voie");
				model.addAttribute("messageVoieEnregistreAvecSucces", messageSource.getMessage(
						"message.voieEnregistreAvecSucces", new String[] { voie.getNom() }, Locale.getDefault()));
			}
			if (inputFlashMap.containsKey("nouveauCommentaireForm")) {
				model.addAttribute("commentairesActive", "active");
			} else {
				model.addAttribute("informationsActive", "active");
			}
		} else {
			spot = serviceRechercheSpot.findById(spotId);
			model.addAttribute("informationsActive", "active");
		}
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
		NouveauCommentaireForm nouveauCommentaireForm = new NouveauCommentaireForm();
		model.addAttribute("secteurId", secteurId);
		model.addAttribute("spot", spot);
		model.addAttribute("listeSecteurs", listeSecteurs);
		model.addAttribute("mapVoies", mapVoies);
		model.addAttribute("mapLongueurs", mapLongueurs);
		model.addAttribute("mapNbSpits", mapNbSpits);
		model.addAttribute("listeCommentaires", listeCommentaires);
		model.addAttribute("nbCommentaires", listeCommentaires.size());
		model.addAttribute("nouveauCommentaireForm", nouveauCommentaireForm);
		model.addAttribute("pageNumber", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("spotActive", "active");
		session.setAttribute(RedirectionUrl.SPOT.label,
				"redirect:/ami/spot/" + spotId + "?page=" + page + "&size=" + size);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label,
				"redirect:/ami/spot/" + spotId + "?page=" + page + "&size=" + size);
		return "ami_spot";
	}

	@GetMapping("/ami/spot/{spotId}/edition")
	public String showEditionSpotForm(@PathVariable Long spotId, Model model) {
		Spot spot = serviceRechercheSpot.findById(spotId);
		EditionSpotForm editionSpotForm = EditionSpotForm.creer(spot);
		model.addAttribute("spot", spot);
		model.addAttribute("editionSpotForm", editionSpotForm);
		model.addAttribute("spotActive", "active");
		model.addAttribute("urlSpot", ((String) session.getAttribute(RedirectionUrl.SPOT.label)).split(":")[1]);
		session.setAttribute(RedirectionUrl.EDITION_SPOT_FORM.label, "redirect:/ami/spot/" + spotId + "/edition");
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, "redirect:/ami/spot/" + spotId + "/edition");
		return "ami_spot_edition";
	}

	@PostMapping("/ami/spot/{spotId}/edition")
	public String checkEditionSpotForm(@Valid EditionSpotForm editionSpotForm, @PathVariable Long spotId,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		Spot spot = serviceRechercheSpot.findById(spotId);
		if (!validationFormEditionSpot.isValide(editionSpotForm)) {
			validationFormEditionSpot.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("spot", spot);
			model.addAttribute("spotActive", "active");
			return "ami_spot_edition";
		}
		redirectAttributes.addFlashAttribute("editionSpotForm", editionSpotForm);
		return "redirect:/ami/spot/" + spotId + "/edition/enregistrement";
	}

	@GetMapping("/ami/spot/{spotId}/edition/enregistrement")
	public String saveEditionSpotForm(@PathVariable Long spotId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			EditionSpotForm editionSpotForm = (EditionSpotForm) inputFlashMap.get("editionSpotForm");
			editionSpotForm.setLieuFrance(validationFormEditionSpot.getLieuFrance());
			editionSpotForm.setPlan(validationFormEditionSpot.getPlan());
			serviceEnregistrementFormEditionSpot.enregistrer(editionSpotForm);
			redirectAttributes.addFlashAttribute("spot", serviceEnregistrementFormEditionSpot.getSpot());
		}
		return (String) session.getAttribute(RedirectionUrl.SPOT.label);
	}

	@GetMapping("/ami/spot/ajout")
	public String showAjoutSpotForm(HttpServletRequest request, NouveauSpotForm ajoutSpotForm, Model model) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			Plan plan = (Plan) inputFlashMap.get("plan");
			model.addAttribute("messagePlanEnregistreAvecSucces", messageSource.getMessage(
					"message.planEnregistreAvecSucces", new String[] { plan.getPlan() }, Locale.getDefault()));
		} else {
			ajoutSpotForm.reinitialiser();
		}
		model.addAttribute("spotActive", "active");
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, "redirect:/ami/spot/ajout");
		return "ami_spot_ajout";
	}

	@PostMapping("/ami/spot/ajout")
	public String checkAjoutSpotForm(@Valid NouveauSpotForm ajoutSpotForm, BindingResult bindingResult) {
		this.ajoutSpotForm = ajoutSpotForm;
		if (bindingResult.hasErrors()) {
			return "ami_spot_ajout";
		}
		return "redirect:/ami/spot/enregistrement";
	}

	@RequestMapping(value = "/ami/spot/ajout", method = RequestMethod.POST, params = "supprimePlan")
	public String supprimerPlanAjoutSpotForm(NouveauSpotForm ajoutSpotForm) {
		ajoutSpotForm.setPlan("");
		return "ami_spot_ajout";
	}

	@GetMapping(value = "/ami/spot/enregistrement")
	public String saveAjoutSpotForm(@ModelAttribute("ajoutSpotForm") NouveauSpotForm ajoutSpotForm,
			RedirectAttributes redirectAttributes) {
		serviceEnregistrementFormAjoutSpot.enregistrer(ajoutSpotForm);
		Spot spot = serviceEnregistrementFormAjoutSpot.getSpot();
		redirectAttributes.addFlashAttribute("spot", spot);
		return "redirect:/ami/spot/" + spot.getId();
	}

	@PostMapping(value = "/ami/spot/ajout/supprimerPlan")
	public String supprimerPlanAjoutSpotForm(NouveauSpotForm ajoutSpotForm, Model model) {
		ajoutSpotForm.setPlan("");
		this.ajoutSpotForm = ajoutSpotForm;
		model.addAttribute("ajoutSpotForm", ajoutSpotForm);
		return "ami_spot_ajout";
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

	@GetMapping("/ami/spot/ajout/lieuFrance")
	@ResponseBody
	public List<String> rechercherLieuFrance(@RequestParam("term") String lieuFrance) {
		return serviceRechercheLieuFrance.rechercherLieuFrance(lieuFrance);
	}

	@GetMapping("/ami/spot/ajout/plan")
	@ResponseBody
	public List<String> rechercherPlan(@RequestParam("term") String plan) {
		return serviceRecherchePlan.rechercherPlan(plan);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("spotForm", spotForm);
		model.addAttribute("ajoutSpotForm", ajoutSpotForm);
		model.addAttribute("unitePrincipaleLabels", CotationFranceUnitePrincipale.LABELS);
		model.addAttribute("uniteSecondaireLabels", CotationFranceUniteSecondaire.LABELS);
		model.addAttribute("uniteTertiaireLabels", CotationFranceUniteTertiaire.LABELS);
		model.addAttribute("cheminPlan", ServiceStockagePlan.RESOURCE_HANDLER_PLAN);
		model.addAttribute(NomModel.UTILISATEUR.label, session.getAttribute(NomModel.UTILISATEUR.label));
	}

}
