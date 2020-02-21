package app.gaugiciel.amical.controller.data;

import org.springframework.stereotype.Component;

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
public class AmiParametreData {

	@NonNull
	private String prenom;
	@NonNull
	private String nom;
	@NonNull
	private String email;
	@NonNull
	private String motDePasse;

}
