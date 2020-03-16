package app.gaugiciel.amical.controller.form;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(RechercheTopoForm.class);

	public static final String NOM = "nom";
	public static final String LIEU_FRANCE_TEMPLATE = "lieuFranceTemplate";
	public static final String CATEGORIE = "categorie";
	public static final String ETAT = "etat";
	public static final String DATE_PARUTION_MIN_INPUT = "dateParutionMinInput";
	public static final String DATE_PARUTION_MAX_INPUT = "dateParutionMaxInput";
	public static final String AUTEUR = "auteur";
	public static final String PROPRIETAIRE_TEMPLATE = "proprietaireTemplate";

	private String nom;

	private String lieuFranceTemplate;

	private String categorie;

	private String etat;

	private String dateParutionMinInput;
	private Timestamp dateParutionMin;

	private String dateParutionMaxInput;
	private Timestamp dateParutionMax;

	private String auteur;

	private String proprietaireTemplate;

	public boolean estVide() {
		LOGGER.info("Start {}()", "estVide");
		return !Utils.isValid(nom) && !Utils.isValid(lieuFranceTemplate) && !Utils.isValid(categorie)
				&& !Utils.isValid(etat) && !Utils.isValid(dateParutionMinInput) && !Utils.isValid(dateParutionMaxInput)
				&& !Utils.isValid(auteur) & !Utils.isValid(proprietaireTemplate);
	}

	public void setObjets() {
		LOGGER.info("Start {}()", "setObjets");
		if (Utils.isValid(dateParutionMinInput)) {

		}
	}

}
