package app.gaugiciel.amical.controller.form;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Spring
@Component
//Lombok
@NoArgsConstructor
@Getter
@Setter
public class InscriptionForm {

	public static final String PRENOM = "prenom";
	public static final String NOM = "nom";
	public static final String EMAIL = "email";
	public static final String MOT_DE_PASSE = "motDePasse";
	public static final String MOT_DE_PASSE_CONFIRMATION = "motDePasseConfirmation";
	private static final Map<String, Boolean> IS_NOT_NULL = new HashMap<>();

	static {
		IS_NOT_NULL.put(PRENOM, true);
		IS_NOT_NULL.put(NOM, true);
		IS_NOT_NULL.put(EMAIL, true);
		IS_NOT_NULL.put(MOT_DE_PASSE, true);
		IS_NOT_NULL.put(MOT_DE_PASSE_CONFIRMATION, true);
	}

	private String prenom;

	private String nom;

	private String email;

	private String motDePasse;

	private String motDePasseConfirmation;

	public void reinitialiser() {
		Stream.of(getClass().getDeclaredFields()).forEach(field -> {
			field.setAccessible(true);
			try {
				field.set(this, null);
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		});
	}

	public boolean isNotNull(String nomAttribut) {
		return IS_NOT_NULL.get(nomAttribut);
	}

}
