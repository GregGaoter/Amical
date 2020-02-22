package app.gaugiciel.amical.controller.form;

import org.springframework.stereotype.Component;

import app.gaugiciel.amical.model.Authentification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//Spring
@Component
//Lombok
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "creer")
@Getter
@Setter
public class EditionEmailForm {

	public static final String EMAIL = "email";

	@NonNull
	private Authentification authentification;
	@NonNull
	private String email;

}
