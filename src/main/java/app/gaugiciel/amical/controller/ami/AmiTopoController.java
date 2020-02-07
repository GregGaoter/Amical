package app.gaugiciel.amical.controller.ami;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import app.gaugiciel.amical.business.implementation.enumeration.CategorieManuel;
import app.gaugiciel.amical.business.implementation.enumeration.EtatManuel;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheTopo;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheUtilisateur;
import app.gaugiciel.amical.controller.form.RechercheTopoForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormRechercheTopo;
import app.gaugiciel.amical.model.Manuel;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
@ControllerAdvice
public class AmiTopoController {

	@Autowired
	private HttpSession session;
	@Autowired
	private ServiceRechercheTopo serviceRechercheTopo;
	@Autowired
	private ValidationFormRechercheTopo validationFormRechercheTopo;
	@Autowired
	private ServiceRechercheUtilisateur serviceRechercheUtilisateur;
	private RechercheTopoForm rechercheTopoForm;

	@GetMapping("/ami/topo/recherche")
	public String showRechercheTopoForm(Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		String urlRedirection = "redirect:/ami/topo/recherche";
		model.addAttribute("rechercheTopoForm", new RechercheTopoForm());
		model.addAttribute("topoActive", "active");
		session.setAttribute(RedirectionUrl.RECHERCHE_TOPO_FORM.label, urlRedirection);
		session.setAttribute(RedirectionUrl.PREVIOUS_URL.label, urlRedirection);
		return "ami_topo_recherche";
	}

	@PostMapping("/ami/topo/recherche")
	public String checkRechercheTopoForm(@Valid RechercheTopoForm rechercheTopoForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		if (!validationFormRechercheTopo.isValide(rechercheTopoForm)) {
			validationFormRechercheTopo.getListeFieldError().forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("topoActive", "active");
			return "ami_topo_recherche";
		}
		redirectAttributes.addFlashAttribute("rechercheTopoForm", rechercheTopoForm);
		this.rechercheTopoForm = rechercheTopoForm;
		return "redirect:/ami/topo/recherche/resultat/taille/" + TailleResultatRecherche.TOPO + "/page/" + 0;
	}

	@GetMapping("/ami/topo/recherche/resultat/taille/{taille}/page/{page}")
	public String resultRechercheTopoForm(@PathVariable int taille, @PathVariable int page, Model model,
			HttpServletRequest request) {
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
		Map<Manuel, Utilisateur> listeManuelsUtilisateurs = new HashMap<>();
		for (Manuel manuel : pagesManuels.getContent()) {
			listeManuelsUtilisateurs.put(manuel,
					serviceRechercheUtilisateur.findByEmail(manuel.getAuthentification().getEmail()));
		}
		model.addAttribute("rechercheTopoForm", rechercheTopoForm);
		model.addAttribute("listeManuelsUtilisateurs", listeManuelsUtilisateurs);
		model.addAttribute("nbManuels", pagesManuels.getTotalElements());
		model.addAttribute("listePages", IntStream.range(0, pagesManuels.getTotalPages()).toArray());
		model.addAttribute("nbPages", pagesManuels.getTotalPages());
		model.addAttribute("pageNumber", pagesManuels.getNumber());
		model.addAttribute("pageSize", TailleResultatRecherche.TOPO);
		model.addAttribute("topoActive", "active");
		return "ami_topo_recherche";
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
		model.addAttribute(NomModel.UTILISATEUR.label, session.getAttribute(NomModel.UTILISATEUR.label));
		model.addAttribute("categorieManuelLabels", CategorieManuel.LABELS);
		model.addAttribute("etatManuelLabels", EtatManuel.LABELS);
	}

}
