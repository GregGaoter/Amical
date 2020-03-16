package app.gaugiciel.amical.controller.form;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.gaugiciel.amical.model.CotationFrance;
import app.gaugiciel.amical.model.Plan;
import app.gaugiciel.amical.model.Secteur;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@Getter
@Setter
public class NouvelleVoieForm {

	private static final Logger LOGGER = LoggerFactory.getLogger(NouvelleVoieForm.class);

	public static final String NOM_SPOT = "nomSpot";
	public static final String NOM = "nom";
	public static final String DESCRIPTION = "description";
	public static final String REMARQUE = "remarque";
	public static final String NUMERO = "numero";
	public static final String HAUTEUR = "hauteur";
	public static final String NOM_SECTEUR = "nomSecteur";
	public static final String NOM_PLAN = "nomPlan";
	public static final String COTATION_UNITE_PRINCIPALE = "cotationUnitePrincipale";
	public static final String COTATION_UNITE_SECONDAIRE = "cotationUniteSecondaire";
	public static final String COTATION_UNITE_TERTIAIRE = "cotationUniteTertiaire";
	private static final Map<String, Boolean> IS_NOT_NULL = new HashMap<>();

	static {
		IS_NOT_NULL.put(NOM_SPOT, true);
		IS_NOT_NULL.put(NOM, false);
		IS_NOT_NULL.put(DESCRIPTION, false);
		IS_NOT_NULL.put(REMARQUE, false);
		IS_NOT_NULL.put(NUMERO, false);
		IS_NOT_NULL.put(HAUTEUR, false);
		IS_NOT_NULL.put(NOM_SECTEUR, true);
		IS_NOT_NULL.put(NOM_PLAN, false);
		IS_NOT_NULL.put(COTATION_UNITE_PRINCIPALE, false);
		IS_NOT_NULL.put(COTATION_UNITE_SECONDAIRE, false);
		IS_NOT_NULL.put(COTATION_UNITE_TERTIAIRE, false);
	}

	private String nomSpot;

	private String nom;

	private String description;

	private String remarque;

	private String numero;

	private Double hauteur;

	private String nomSecteur;
	private Secteur secteur;

	private String nomPlan;
	private Plan plan;

	private String cotationUnitePrincipale;
	private String cotationUniteSecondaire;
	private String cotationUniteTertiaire;
	private CotationFrance cotationFrance;

	public boolean isNotNull(String nomAttribut) {
		return IS_NOT_NULL.get(nomAttribut);
	}

	public boolean estCotationVide() {
		LOGGER.info("Start {}()", "estCotationVide");
		return estNull(cotationUnitePrincipale) && estNull(cotationUniteSecondaire) && estNull(cotationUniteTertiaire);
	}

	private boolean estNull(String str) {
		return str == null ? true : str.strip().length() == 0;
	}

}
