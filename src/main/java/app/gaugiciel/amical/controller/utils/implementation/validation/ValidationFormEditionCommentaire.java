package app.gaugiciel.amical.controller.utils.implementation.validation;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import app.gaugiciel.amical.controller.form.EditionCommentaireForm;
import app.gaugiciel.amical.controller.utils.contrat.ValidationForm;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ValidationFormEditionCommentaire extends ValidationForm<EditionCommentaireForm> {

	@Override
	public boolean isValide(@NotNull EditionCommentaireForm editionCommentaireForm) {
		listeFieldError.clear();
		if (editionCommentaireForm.getCommentaire().length() > 2000) {
			listeFieldError.add(new FieldError(editionCommentaireForm.getClass().getSimpleName(),
					EditionCommentaireForm.COMMENTAIRE,
					messageSource.getMessage("message.commentaireTropLong", null, Locale.getDefault())));
		}
		return listeFieldError.isEmpty();
	}

}
