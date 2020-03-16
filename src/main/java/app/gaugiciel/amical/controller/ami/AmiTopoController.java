package app.gaugiciel.amical.controller.ami;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import app.gaugiciel.amical.business.implementation.constante.TailleResultatRecherche;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormEditionTopo;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormNouveauTopo;
import app.gaugiciel.amical.business.implementation.enumeration.CategorieManuel;
import app.gaugiciel.amical.business.implementation.enumeration.Erreur;
import app.gaugiciel.amical.business.implementation.enumeration.EtatManuel;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheDemandePretEmpruntManuel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRecherchePretEmpruntManuel;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheTopo;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryManuel;
import app.gaugiciel.amical.controller.form.DemandeEmpruntTopoForm;
import app.gaugiciel.amical.controller.form.EditionTopoForm;
import app.gaugiciel.amical.controller.form.NouveauTopoForm;
import app.gaugiciel.amical.controller.form.RechercheTopoForm;
import app.gaugiciel.amical.controller.form.SuppressionTopoForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormEditionTopo;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormNouveauTopo;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormRechercheTopo;
import app.gaugiciel.amical.model.Authentification;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
@ControllerAdvice
public class AmiTopoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AmiTopoController.class);

	@Autowired
	private HttpSession session;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ServiceRechercheTopo serviceRechercheTopo;
	@Autowired
	private ValidationFormRechercheTopo validationFormRechercheTopo;
	@Autowired
	private ValidationFormNouveauTopo validationFormNouveauTopo;
	@Autowired
	private ValidationFormEditionTopo validationFormEditionTopo;
	@Autowired
	private ServiceEnregistrementFormNouveauTopo serviceEnregistrementFormNouveauTopo;
	@Autowired
	private ServiceEnregistrementFormEditionTopo serviceEnregistrementFormEditionTopo;
	@Autowired
	private ServiceRepositoryManuel serviceRepositoryManuel;
	@Autowired
	private ServiceRecherchePretEmpruntManuel serviceRecherchePretEmpruntManuel;
	@Autowired
	private ServiceRechercheDemandePretEmpruntManuel serviceRechercheDemandePretEmpruntManuel;
	private RechercheTopoForm rechercheTopoForm;

	@GetMapping("/ami/topo/recherche")
	public String showRechercheTopoForm(Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "showRechercheTopoForm");
		String urlRedirection = "redirect:/ami/topo/recherche";
		model.addAttribute("rechercheTopoForm", new RechercheTopoForm());
		model.addAttribute("topoActive", "active");
		model.addAttribute("categorieManuelLabels", CategorieManuel.LABELS.stream()
				.filter(label -> label != CategorieManuel.NULL.label).collect(Collectors.toList()));
		model.addAttribute("etatManuelLabels",
				EtatManuel.LABELS.stream()
						.filter(label -> label != EtatManuel.NULL.label && label != EtatManuel.PRETE.label)
						.collect(Collectors.toList()));
		session.setAttribute(RedirectionUrl.RECHERCHE_TOPO_FORM.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_manuel_recherche";
	}

	@PostMapping("/ami/topo/recherche")
	public String checkRechercheTopoForm(RechercheTopoForm rechercheTopoForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "checkRechercheTopoForm");
		if (!validationFormRechercheTopo.isValide(rechercheTopoForm)) {
			validationFormRechercheTopo.getListeFieldError().forEach(fieldError -> model
					.addAttribute(fieldError.getField() + Erreur.SUFFIXE.label, fieldError.getDefaultMessage()));
			model.addAttribute("topoActive", "active");
			model.addAttribute("categorieManuelLabels", CategorieManuel.LABELS.stream()
					.filter(label -> label != CategorieManuel.NULL.label).collect(Collectors.toList()));
			model.addAttribute("etatManuelLabels",
					EtatManuel.LABELS.stream()
							.filter(label -> label != EtatManuel.NULL.label && label != EtatManuel.PRETE.label)
							.collect(Collectors.toList()));
			return "ami_manuel_recherche";
		}
		redirectAttributes.addFlashAttribute("rechercheTopoForm", rechercheTopoForm);
		this.rechercheTopoForm = rechercheTopoForm;
		return "redirect:/ami/topo/recherche/resultat/taille/" + TailleResultatRecherche.TOPO + "/page/" + 0;
	}

	@GetMapping("/ami/topo/recherche/resultat/taille/{taille}/page/{page}")
	public String resultRechercheTopoForm(@PathVariable int taille, @PathVariable int page, Model model,
			HttpServletRequest request) {
		LOGGER.info("Start {}()", "resultRechercheTopoForm");
		RechercheTopoForm rechercheTopoForm;
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("rechercheTopoForm")) {
				rechercheTopoForm = (RechercheTopoForm) inputFlashMap.get("rechercheTopoForm");
			} else {
				rechercheTopoForm = this.rechercheTopoForm;
			}
		} else {
			rechercheTopoForm = this.rechercheTopoForm;
		}
		Page<Manuel> pagesManuels = serviceRechercheTopo.rechercher(rechercheTopoForm, PageRequest.of(page, taille));
		String urlRedirection = "redirect:/ami/topo/recherche/resultat/taille/" + taille + "/page/" + page;
		model.addAttribute("rechercheTopoForm", rechercheTopoForm);
		model.addAttribute("listeManuels", pagesManuels.getContent());
		model.addAttribute("nbManuels", pagesManuels.getTotalElements());
		model.addAttribute("listePages", IntStream.range(0, pagesManuels.getTotalPages()).toArray());
		model.addAttribute("nbPages", pagesManuels.getTotalPages());
		model.addAttribute("pageNumber", pagesManuels.getNumber());
		model.addAttribute("pageSize", TailleResultatRecherche.TOPO);
		model.addAttribute("topoActive", "active");
		session.setAttribute(RedirectionUrl.RECHERCHE_TOPO_FORM.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_manuel_recherche";
	}

	@GetMapping("/ami/topo/{manuelId}/taille/{taille}/page/{page}")
	public String showTopo(@PathVariable long manuelId, @PathVariable int taille, @PathVariable int page, Model model) {
		LOGGER.info("Start {}()", "showTopo");
		Manuel manuel = serviceRechercheTopo.findById(manuelId);
		String urlRedirection = "redirect:/ami/topo/" + manuelId + "/taille/" + taille + "/page/" + page;
		model.addAttribute("manuel", manuel);
		model.addAttribute("pageNumber", page);
		model.addAttribute("pageSize", taille);
		model.addAttribute("topoActive", "active");
		model.addAttribute("suppressionTopoForm", SuppressionTopoForm.creer(manuelId));
		model.addAttribute("demandeEmpruntTopoForm", DemandeEmpruntTopoForm.creer(manuelId));
		session.setAttribute(RedirectionUrl.MANUEL.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_manuel";
	}

	@GetMapping("/ami/topo/topos")
	public String showToposUtilisateur(Model model, HttpServletRequest request) {
		LOGGER.info("Start {}()", "showToposUtilisateur");
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("nomManuelSupprime")) {
				String nomManuelSupprime = (String) inputFlashMap.get("nomManuelSupprime");
				model.addAttribute("messageTopoSupprimeAvecSucces", messageSource.getMessage(
						"message.topoSupprimeAvecSucces", new String[] { nomManuelSupprime }, Locale.getDefault()));
			}
		}
		List<Manuel> listeManuelsUtilisateur = serviceRechercheTopo
				.findByAuthentification((Authentification) session.getAttribute(NomModel.AUTHENTIFICATION.label));
		String urlRedirection = "redirect:/ami/topo/topos";
		model.addAttribute("listeManuelsUtilisateur", listeManuelsUtilisateur);
		model.addAttribute("nbManuels", listeManuelsUtilisateur.size());
		model.addAttribute("topoActive", "active");
		session.setAttribute(RedirectionUrl.TOPOS.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_manuels_utilisateur";
	}

	@GetMapping("/ami/topo/{manuelId}")
	public String showTopoUtilisateur(@PathVariable long manuelId, Model model, HttpServletRequest request) {
		LOGGER.info("Start {}()", "showTopoUtilisateur");
		Manuel manuel;
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("manuel")) {
				manuel = (Manuel) inputFlashMap.get("manuel");
				model.addAttribute("messageTopoEnregistreAvecSucces", messageSource.getMessage(
						"message.topoEnregistreAvecSucces", new String[] { manuel.getNom() }, Locale.getDefault()));
			} else {
				manuel = serviceRechercheTopo.findById(manuelId);
			}
		} else {
			manuel = serviceRechercheTopo.findById(manuelId);
		}
		String urlRedirection = "redirect:/ami/topo/" + manuelId;
		model.addAttribute("manuel", manuel);
		model.addAttribute("manuelUtilisateur", true);
		model.addAttribute("suppressionTopoForm", SuppressionTopoForm.creer(manuelId));
		model.addAttribute("demandeEmpruntTopoForm", DemandeEmpruntTopoForm.creer(manuelId));
		model.addAttribute("topoActive", "active");
		session.setAttribute(RedirectionUrl.MANUEL.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_manuel";
	}

	@GetMapping("/ami/topo/nouveau")
	public String showNouveauTopoForm(Model model) {
		LOGGER.info("Start {}()", "showNouveauTopoForm");
		NouveauTopoForm nouveauTopoForm = new NouveauTopoForm();
		Utilisateur proprietaireTopo = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		nouveauTopoForm.setAuthentification(proprietaireTopo.getAuthentification());
		nouveauTopoForm.setAuthentificationEmailInput(proprietaireTopo.getAuthentificationEmail());
		String urlRedirection = "redirect:/ami/topo/nouveau";
		model.addAttribute("nouveauTopoForm", nouveauTopoForm);
		model.addAttribute("topoActive", "active");
		model.addAttribute("urlTopos", ((String) session.getAttribute(RedirectionUrl.TOPOS.label)).split(":")[1]);
		model.addAttribute("categorieManuelLabels", CategorieManuel.LABELS.stream()
				.filter(label -> label != CategorieManuel.NULL.label).collect(Collectors.toList()));
		model.addAttribute("etatManuelLabels",
				EtatManuel.LABELS.stream()
						.filter(label -> label != EtatManuel.NULL.label && label != EtatManuel.PRETE.label)
						.collect(Collectors.toList()));
		session.setAttribute(RedirectionUrl.TOPO_FORM.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_manuel_nouveau";
	}

	@PostMapping("/ami/topo/nouveau")
	public String checkNouveauTopoForm(NouveauTopoForm nouveauTopoForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "checkNouveauTopoForm");
		if (!validationFormNouveauTopo.isValide(nouveauTopoForm)) {
			validationFormNouveauTopo.getListeFieldError().forEach(fieldError -> model
					.addAttribute(fieldError.getField() + Erreur.SUFFIXE.label, fieldError.getDefaultMessage()));
			model.addAttribute("categorieManuelLabels", CategorieManuel.LABELS.stream()
					.filter(label -> label != CategorieManuel.NULL.label).collect(Collectors.toList()));
			model.addAttribute("etatManuelLabels",
					EtatManuel.LABELS.stream()
							.filter(label -> label != EtatManuel.NULL.label && label != EtatManuel.PRETE.label)
							.collect(Collectors.toList()));
			model.addAttribute("topoActive", "active");
			return "ami_manuel_nouveau";
		}
		redirectAttributes.addFlashAttribute("nouveauTopoForm", nouveauTopoForm);
		return "redirect:/ami/topo/nouveau/enregistrement";
	}

	@GetMapping("/ami/topo/nouveau/enregistrement")
	public String saveNouveauTopoForm(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "saveNouveauTopoForm");
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("nouveauTopoForm")) {
				serviceEnregistrementFormNouveauTopo
						.enregistrer((NouveauTopoForm) inputFlashMap.get("nouveauTopoForm"));
			}
		}
		Manuel manuel = serviceEnregistrementFormNouveauTopo.getManuel();
		redirectAttributes.addFlashAttribute("manuel", manuel);
		return "redirect:/ami/topo/" + manuel.getId();
	}

	@GetMapping("/ami/topo/{manuelId}/edition")
	public String showEditionTopoForm(@PathVariable long manuelId, Model model) {
		LOGGER.info("Start {}()", "showEditionTopoForm");
		Manuel manuel = serviceRechercheTopo.findById(manuelId);
		EditionTopoForm editionTopoForm = EditionTopoForm.creer(manuel);
		String urlRedirection = "redirect:/ami/topo/" + manuelId + "/edition";
		model.addAttribute("manuel", manuel);
		model.addAttribute("editionTopoForm", editionTopoForm);
		model.addAttribute("isDemandePretExist", serviceRechercheDemandePretEmpruntManuel.existsByManuel(manuel));
		model.addAttribute("isPretExist", serviceRecherchePretEmpruntManuel.existsByManuel(manuel));
		model.addAttribute("topoActive", "active");
		model.addAttribute("categorieManuelLabels", CategorieManuel.LABELS.stream()
				.filter(label -> label != CategorieManuel.NULL.label).collect(Collectors.toList()));
		model.addAttribute("etatManuelLabels",
				EtatManuel.LABELS.stream()
						.filter(label -> label != EtatManuel.NULL.label && label != EtatManuel.PRETE.label)
						.collect(Collectors.toList()));
		model.addAttribute("urlTopo", ((String) session.getAttribute(RedirectionUrl.MANUEL.label)).split(":")[1]);
		session.setAttribute(RedirectionUrl.EDITION_TOPO_FORM.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_manuel_edition";
	}

	@PostMapping("/ami/topo/{manuelId}/edition")
	public String checkEditionTopoForm(EditionTopoForm editionTopoForm, @PathVariable long manuelId,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "checkEditionTopoForm");
		if (!validationFormEditionTopo.isValide(editionTopoForm)) {
			validationFormEditionTopo.getListeFieldError().forEach(fieldError -> model
					.addAttribute(fieldError.getField() + Erreur.SUFFIXE.label, fieldError.getDefaultMessage()));
			Manuel manuel = serviceRechercheTopo.findById(manuelId);
			model.addAttribute("manuel", manuel);
			model.addAttribute("isDemandePretExist", serviceRechercheDemandePretEmpruntManuel.existsByManuel(manuel));
			model.addAttribute("isPretExist", serviceRecherchePretEmpruntManuel.existsByManuel(manuel));
			model.addAttribute("topoActive", "active");
			model.addAttribute("categorieManuelLabels", CategorieManuel.LABELS.stream()
					.filter(label -> label != CategorieManuel.NULL.label).collect(Collectors.toList()));
			model.addAttribute("etatManuelLabels",
					EtatManuel.LABELS.stream()
							.filter(label -> label != EtatManuel.NULL.label && label != EtatManuel.PRETE.label)
							.collect(Collectors.toList()));
			return "ami_manuel_edition";
		}
		redirectAttributes.addFlashAttribute("editionTopoForm", editionTopoForm);
		return "redirect:/ami/topo/" + manuelId + "/edition/enregistrement";
	}

	@GetMapping("/ami/topo/{manuelId}/edition/enregistrement")
	public String saveEditionTopoForm(@PathVariable long manuelId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "saveEditionTopoForm");
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("editionTopoForm")) {
				serviceEnregistrementFormEditionTopo
						.enregistrer((EditionTopoForm) inputFlashMap.get("editionTopoForm"));
			}
		}
		redirectAttributes.addFlashAttribute("manuel", serviceEnregistrementFormEditionTopo.getManuel());
		return (String) session.getAttribute(RedirectionUrl.MANUEL.label);
	}

	@PostMapping("/ami/topo/{manuelId}/suppression")
	public String supprimerTopo(SuppressionTopoForm suppressionTopoForm, @PathVariable long manuelId,
			RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "supprimerTopo");
		Manuel manuel = serviceRechercheTopo.findById(manuelId);
		String nomManuelSupprime = manuel.getNom();
		serviceRepositoryManuel.supprimer(manuel);
		redirectAttributes.addFlashAttribute("nomManuelSupprime", nomManuelSupprime);
		return (String) session.getAttribute(RedirectionUrl.TOPOS.label);
	}

	@GetMapping("/ami/topo/recherche/nomManuel")
	@ResponseBody
	public List<String> rechercherNomManuel(@RequestParam("term") String nomManuel) {
		return serviceRechercheTopo.rechercherNomManuel(nomManuel);
	}

	@GetMapping("/ami/topo/recherche/nomAuteur")
	@ResponseBody
	public List<String> rechercherNomAuteur(@RequestParam("term") String nomAuteur) {
		return serviceRechercheTopo.rechercherNomAuteur(nomAuteur);
	}

	@GetMapping("/ami/topo/recherche/proprietaire")
	@ResponseBody
	public List<String> rechercherProprietaire(@RequestParam("term") String proprietaire) {
		return serviceRechercheTopo.rechercherProprietaire(proprietaire);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		if (utilisateur != null) {
			model.addAttribute(NomModel.UTILISATEUR.label, utilisateur);
			model.addAttribute("categorieManuelLabels", CategorieManuel.LABELS);
			model.addAttribute("etatManuelLabels", EtatManuel.LABELS);
			model.addAttribute("nbManuelsUtilisateur",
					serviceRechercheTopo.countProprietaire(utilisateur.getAuthentification()));
		}
	}

}
