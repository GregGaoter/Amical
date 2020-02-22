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
public class EditionMotDePasseForm {

	public static final String ANCIEN_MOT_DE_PASSE = "ancienMotDePasse";
	public static final String NOUVEAU_MOT_DE_PASSE = "nouveauMotDePasse";
	public static final String NOUVEAU_MOT_DE_PASSE_CONFIRMATION = "nouveauMotDePasseConfirmation";

	@NonNull
	private Authentification authentification;
	@NonNull
	private String ancienMotDePasse;
	@NonNull
	private String nouveauMotDePasse;
	@NonNull
	private String nouveauMotDePasseConfirmation;

}
