package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.NouveauCommentaireForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import app.gaugiciel.amical.utilitaire.Utils;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormNouveauCommentaire extends ValidationForm<NouveauCommentaireForm> {

	@Override
	public boolean isValide(@NotNull NouveauCommentaireForm nouveauCommentaireForm) {
		listeFieldError.clear();
		if (!Utils.isValid(nouveauCommentaireForm.getCommentaire())) {
			listeFieldError.add(new FieldError(nouveauCommentaireForm.getClass().getSimpleName(),
					NouveauCommentaireForm.COMMENTAIRE,
					messageSource.getMessage("validation.notnull", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
