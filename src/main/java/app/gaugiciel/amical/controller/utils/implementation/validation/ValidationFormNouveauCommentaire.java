package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.NouveauCommentaireForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormNouveauCommentaire extends ValidationForm<NouveauCommentaireForm> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationFormNouveauCommentaire.class);

	@Override
	public boolean isValide(@NotNull NouveauCommentaireForm nouveauCommentaireForm) {
		LOGGER.info("Start {}()", "isValide");
		listeFieldError.clear();
		String commentaire = nouveauCommentaireForm.getCommentaire();
		if (commentaire.length() < 1 || commentaire.length() > 2000) {
			listeFieldError.add(new FieldError(nouveauCommentaireForm.getClass().getSimpleName(),
					NouveauCommentaireForm.COMMENTAIRE, messageSource.getMessage("validation.form.size.interval",
							new String[] { "1", "2000" }, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
