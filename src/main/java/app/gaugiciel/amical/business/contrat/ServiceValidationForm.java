package app.gaugiciel.amical.business.contrat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public abstract class ServiceValidationForm<F> {

	public List<FieldError> listeFieldError;

	public ServiceValidationForm() {
		listeFieldError = new ArrayList<>();
	}

	public abstract boolean isValide(F form);

}
