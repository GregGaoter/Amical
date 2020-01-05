package app.gaugiciel.amical.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Getter
@Setter
public class LoginForm {

	public static final String EMAIL = "email";
	public static final String MOT_DE_PASSE = "motDePasse";

	@NotNull(message = "{validation.notnull}")
	@Email(message = "{validation.email}")
	@Size(max = 64, message = "{validation.size.max}")
	private String email;

	@NotNull(message = "{validation.notnull}")
	@Size(min = 8, max = 60, message = "{validation.size.interval}")
	private String motDePasse;

}
