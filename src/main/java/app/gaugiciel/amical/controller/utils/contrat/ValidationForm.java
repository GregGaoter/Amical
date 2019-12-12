package app.gaugiciel.amical.controller.utils.contrat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public abstract class ValidationForm<F> {

	public List<FieldError> listeFieldError;

	public ValidationForm() {
		listeFieldError = new ArrayList<>();
	}

	public abstract boolean isValide(F form);

}
