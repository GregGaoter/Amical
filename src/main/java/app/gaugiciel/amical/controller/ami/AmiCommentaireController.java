package app.gaugiciel.amical.controller.ami;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormEditionCommentaire;
import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormNouveauCommentaire;
import app.gaugiciel.amical.business.implementation.enumeration.Erreur;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheCommentaire;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSpot;
import app.gaugiciel.amical.business.implementation.repository.ServiceRepositoryCommentaire;
import app.gaugiciel.amical.controller.form.EditionCommentaireForm;
import app.gaugiciel.amical.controller.form.NouveauCommentaireForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormEditionCommentaire;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormNouveauCommentaire;
import app.gaugiciel.amical.model.Commentaire;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
@ControllerAdvice
public class AmiCommentaireController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AmiCommentaireController.class);

	@Autowired
	private HttpSession session;
	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ValidationFormNouveauCommentaire validationFormNouveauCommentaire;
	@Autowired
	private ServiceEnregistrementFormNouveauCommentaire serviceEnregistrementFormNouveauCommentaire;
	@Autowired
	private ValidationFormEditionCommentaire validationFormEditionCommentaire;
	@Autowired
	private ServiceEnregistrementFormEditionCommentaire serviceEnregistrementFormEditionCommentaire;
	@Autowired
	private ServiceRechercheCommentaire serviceRechercheCommentaire;
	@Autowired
	private ServiceRepositoryCommentaire serviceRepositoryCommentaire;

	@PostMapping("/ami/spot/{spotId}/commentaire/nouveau")
	public String checkNouveauCommentaireForm(NouveauCommentaireForm nouveauCommentaireForm, @PathVariable Long spotId,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "checkNouveauCommentaireForm");
		Spot spot = serviceRechercheSpot.findById(spotId);
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		nouveauCommentaireForm.setDate(Timestamp.from(Instant.now()));
		nouveauCommentaireForm.setSpot(spot);
		nouveauCommentaireForm.setUtilisateur(utilisateur);
		redirectAttributes.addFlashAttribute("nouveauCommentaireForm", nouveauCommentaireForm);
		if (!validationFormNouveauCommentaire.isValide(nouveauCommentaireForm)) {
			validationFormNouveauCommentaire.getListeFieldError().forEach(fieldError -> model
					.addAttribute(fieldError.getField() + Erreur.SUFFIXE.label, fieldError.getDefaultMessage()));
			model.addAttribute("spotActive", "active");
			return (String) session.getAttribute(RedirectionUrl.SPOT.label);
		}
		return "redirect:/ami/spot/" + spotId + "/commentaire/enregistrement";
	}

	@GetMapping("/ami/spot/{spotId}/commentaire/enregistrement")
	public String saveNouveauCommentaireForm(@PathVariable Long spotId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "saveNouveauCommentaireForm");
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("nouveauCommentaireForm")) {
				NouveauCommentaireForm nouveauCommentaireForm = (NouveauCommentaireForm) inputFlashMap
						.get("nouveauCommentaireForm");
				serviceEnregistrementFormNouveauCommentaire.enregistrer(nouveauCommentaireForm);
				redirectAttributes.addFlashAttribute("nouveauCommentaireForm", nouveauCommentaireForm);
			}
		}
		return (String) session.getAttribute(RedirectionUrl.SPOT.label);
	}

	@GetMapping("/admin/commentaire/{commentaireId}/edition")
	public String showEditionCommentaireForm(@PathVariable Long commentaireId, Model model) {
		LOGGER.info("Start {}()", "showEditionCommentaireForm");
		Commentaire commentaire = serviceRechercheCommentaire.findById(commentaireId);
		model.addAttribute("spotActive", "active");
		model.addAttribute("commentaire", commentaire);
		model.addAttribute("editionCommentaireForm", EditionCommentaireForm.creer(commentaire));
		model.addAttribute("urlSpot", ((String) session.getAttribute(RedirectionUrl.SPOT.label)).split(":")[1]);
		return "admin_commentaire_edition";
	}

	@PostMapping("/admin/commentaire/{commentaireId}/edition")
	public String checkEditionCommentaireForm(EditionCommentaireForm editionCommentaireForm,
			@PathVariable Long commentaireId, Model model, RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "checkEditionCommentaireForm");
		if (!validationFormEditionCommentaire.isValide(editionCommentaireForm)) {
			validationFormEditionCommentaire.getListeFieldError().forEach(fieldError -> model
					.addAttribute(fieldError.getField() + Erreur.SUFFIXE.label, fieldError.getDefaultMessage()));
			Commentaire commentaire = serviceRechercheCommentaire.findById(commentaireId);
			commentaire.setCommentaire(editionCommentaireForm.getCommentaire());
			model.addAttribute("spotActive", "active");
			model.addAttribute("commentaire", commentaire);
			return "admin_commentaire_edition";
		}
		redirectAttributes.addFlashAttribute("editionCommentaireForm", editionCommentaireForm);
		return "redirect:/admin/commentaire/" + commentaireId + "/edition/enregistrement";
	}

	@GetMapping("/admin/commentaire/{commentaireId}/edition/enregistrement")
	public String saveEditionCommentaireForm(@PathVariable Long commentaireId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		LOGGER.info("Start {}()", "saveEditionCommentaireForm");
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null && !inputFlashMap.isEmpty()) {
			if (inputFlashMap.containsKey("editionCommentaireForm")) {
				EditionCommentaireForm editionCommentaireForm = (EditionCommentaireForm) inputFlashMap
						.get("editionCommentaireForm");
				serviceEnregistrementFormEditionCommentaire.enregistrer(editionCommentaireForm);
				redirectAttributes.addFlashAttribute("editionCommentaireForm", true);
			}
		}
		return (String) session.getAttribute(RedirectionUrl.SPOT.label);
	}

	@GetMapping("/admin/commentaire/{commentaireId}/suppression")
	public String deleteCommentaire(@PathVariable Long commentaireId) {
		LOGGER.info("Start {}()", "deleteCommentaire");
		Commentaire commentaire = serviceRechercheCommentaire.findById(commentaireId);
		serviceRepositoryCommentaire.supprimer(commentaire);
		return (String) session.getAttribute(RedirectionUrl.SPOT.label);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute(NomModel.UTILISATEUR.label, session.getAttribute(NomModel.UTILISATEUR.label));
	}

}
