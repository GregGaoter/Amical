package app.gaugiciel.amical.controller.result;

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
public class SpotResult {

	private String nom;
	private String region;
	private String departement;
	private String codePostal;
	private String ville;
	private Boolean officiel;

}
