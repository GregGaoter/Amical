package app.gaugiciel.amical.controller.form;

import org.springframework.stereotype.Component;

import app.gaugiciel.amical.model.Utilisateur;
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
public class EditionNomForm {

	public static final String NOM = "nom";

	@NonNull
	private Utilisateur utilisateur;
	@NonNull
	private String nom;

}
