package app.gaugiciel.amical.controller.utils.contrat;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ValidationForm<F> {

	@Autowired
	protected MessageSource messageSource;
	protected List<FieldError> listeFieldError;

	public ValidationForm() {
		listeFieldError = new ArrayList<>();
	}

	public abstract boolean isValide(@NotNull F form);

}
