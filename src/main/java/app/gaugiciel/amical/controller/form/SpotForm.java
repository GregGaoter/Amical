package app.gaugiciel.amical.controller.form;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Spring
@Component
//Lombok
@NoArgsConstructor
@Getter
@Setter
// Hibernate validator constraints
@ScriptAssert(lang = "javascript", script = "_this.hauteurMinVoie<=_this.hauteurMaxVoie", message = "{validation.scriptassert.hauteurMaxVoie}")
public class SpotForm {

	@Size(max = 128, message = "{validation.size.max}")
	private String nomSpot;

	@Size(max = 64, message = "{validation.size.max}")
	private String lieuFranceSpot;

	private Boolean isOfficielSpot;

	@Size(max = 128, message = "{validation.size.max}")
	private String nomSecteur;

	@Size(max = 128, message = "{validation.size.max}")
	private String nomVoie;

	@Size(max = 1, message = "{validation.size.max}")
	private String cotationMinVoieUnitePrincipale;

	@Size(max = 3, message = "{validation.size.max}")
	private String cotationMinVoieUniteSecondaire;

	@Size(max = 1, message = "{validation.size.max}")
	private String cotationMinVoieUniteTertiaire;

	@Size(max = 1, message = "{validation.size.max}")
	private String cotationMaxVoieUnitePrincipale;

	@Size(max = 3, message = "{validation.size.max}")
	private String cotationMaxVoieUniteSecondaire;

	@Size(max = 1, message = "{validation.size.max}")
	private String cotationMaxVoieUniteTertiaire;

	@PositiveOrZero(message = "{validation.positiveorzero}")
	private Integer hauteurMinVoie;

	private Integer hauteurMaxVoie;

}
