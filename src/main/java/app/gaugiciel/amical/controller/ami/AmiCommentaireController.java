package app.gaugiciel.amical.controller.ami;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import app.gaugiciel.amical.business.implementation.enregistrement.ServiceEnregistrementFormNouveauCommentaire;
import app.gaugiciel.amical.business.implementation.enumeration.NomModel;
import app.gaugiciel.amical.business.implementation.enumeration.RedirectionUrl;
import app.gaugiciel.amical.business.implementation.recherche.ServiceRechercheSpot;
import app.gaugiciel.amical.controller.form.NouveauCommentaireForm;
import app.gaugiciel.amical.controller.utils.implementation.validation.ValidationFormNouveauCommentaire;
import app.gaugiciel.amical.model.Spot;
import app.gaugiciel.amical.model.Utilisateur;

@Controller
@ControllerAdvice
public class AmiCommentaireController {

	@Autowired
	private HttpSession session;
	@Autowired
	private ServiceRechercheSpot serviceRechercheSpot;
	@Autowired
	private ValidationFormNouveauCommentaire validationFormNouveauCommentaire;
	@Autowired
	private ServiceEnregistrementFormNouveauCommentaire serviceEnregistrementFormNouveauCommentaire;

	@PostMapping("/ami/spot/{spotId}/commentaire/nouveau")
	public String checkNouveauCommentaireForm(@Valid NouveauCommentaireForm nouveauCommentaireForm,
			@PathVariable Long spotId, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		Spot spot = serviceRechercheSpot.findById(spotId);
		Utilisateur utilisateur = (Utilisateur) session.getAttribute(NomModel.UTILISATEUR.label);
		nouveauCommentaireForm.setDate(Timestamp.from(Instant.now()));
		nouveauCommentaireForm.setSpot(spot);
		nouveauCommentaireForm.setUtilisateur(utilisateur);
		redirectAttributes.addFlashAttribute("nouveauCommentaireForm", nouveauCommentaireForm);
		if (!validationFormNouveauCommentaire.isValide(nouveauCommentaireForm)) {
			validationFormNouveauCommentaire.getListeFieldError()
					.forEach(fieldError -> bindingResult.addError(fieldError));
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("spotActive", "active");
			return (String) session.getAttribute(RedirectionUrl.SPOT.label);
		}
		return "redirect:/ami/spot/" + spotId + "/commentaire/enregistrement";
	}

	@GetMapping("/ami/spot/{spotId}/commentaire/enregistrement")
	public String saveNouveauCommentaireForm(@PathVariable Long spotId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
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

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute(NomModel.UTILISATEUR.label, session.getAttribute(NomModel.UTILISATEUR.label));
	}

}
