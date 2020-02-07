package app.gaugiciel.amical.controller.form;

import java.sql.Timestamp;

import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import app.gaugiciel.amical.utilitaire.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Spring
@Component
//Lombok
@NoArgsConstructor
@Getter
@Setter
public class RechercheTopoForm {

	public static final String NOM = "nom";
	public static final String LIEU_FRANCE_TEMPLATE = "lieuFranceTemplate";
	public static final String CATEGORIE = "categorie";
	public static final String ETAT = "etat";
	public static final String DATE_PARUTION_MIN_INPUT = "dateParutionMinInput";
	public static final String DATE_PARUTION_MAX_INPUT = "dateParutionMaxInput";
	public static final String AUTEUR = "auteur";
	public static final String PROPRIETAIRE_TEMPLATE = "proprietaireTemplate";

	@Size(max = 128, message = "{validation.size.max}")
	private String nom;

	@Size(max = 64, message = "{validation.size.max}")
	private String lieuFranceTemplate;

	@Size(max = 64, message = "{validation.size.max}")
	private String categorie;

	@Size(max = 64, message = "{validation.size.max}")
	private String etat;

	private String dateParutionMinInput;
	private Timestamp dateParutionMin;

	private String dateParutionMaxInput;
	private Timestamp dateParutionMax;

	@Size(max = 128, message = "{validation.size.max}")
	private String auteur;

	@Size(max = 64, message = "{validation.size.max}")
	private String proprietaireTemplate;

	public boolean estVide() {
		return !Utils.isValid(nom) && !Utils.isValid(lieuFranceTemplate) && !Utils.isValid(categorie)
				&& !Utils.isValid(etat) && !Utils.isValid(dateParutionMinInput) && !Utils.isValid(dateParutionMaxInput)
				&& !Utils.isValid(auteur) & !Utils.isValid(proprietaireTemplate);
	}
	
	public void setObjets() {
		if(Utils.isValid(dateParutionMinInput)) {
			
		}
	}

}
